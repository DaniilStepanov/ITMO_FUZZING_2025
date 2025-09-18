// g++ -std=c++17 -O0 -pthread temple_buggy.cpp -o buggy

/**
 *
 * Упражнение: "Проклятие древнего храма"
 * Легенда
 * Два археолога, Джонс и Смит, исследуют древний храм, полный сокровищ и опасностей. Они нашли:
 *     Сундук с 1000 золотыми монетами
 *     Магический артефакт, для активации которого нужны два специальных ключа
 *     Журнал для записи результатов экспедиции

 * Археологи работают параллельно, и их действия могут мешать друг другу. В коде присутствуют несколько серьезных проблем многопоточности, которые вам предстоит исправить.
 * Что происходит в программе?
 *     Сбор монет: Оба археолога пытаются собрать монеты из сундука (до 10 монет за раз, 100 раз каждый)
 *     Активация артефакта: Каждый археолог пытается активировать артефакт, используя два ключа в разном порядке
 *     Запись в журнал: Археологи записывают результаты своей работы в общий журнал
 *
 * Проблемы, которые нужно исправить
 * 1. Гонка данных (Race Condition)
 *     При сборе монет несколько потоков одновременно обращаются к chestCoins и totalCoins
 *     Операции не атомарны, что приводит к некорректным значениям
 *     Задача: Сделать операции с монетами потокобезопасными
 *
 * 2. Взаимная блокировка (Deadlock)
 *     Джонс и Смит берут ключи в разном порядке (key1→key2 vs key2→key1)
 *     Это может привести к ситуации, когда каждый ждет ключ, который держит другой
 *     Задача: Устранить возможность взаимной блокировки
 * 3. Небезопасный доступ к общим ресурсам
 *     StringBuilder не является потокобезопасным
 *     Одновременная запись в журнал может привести к повреждению данных
 *     Задача: Обеспечить безопасную запись в журнал
 * Задание
 *     Запустите программу несколько раз и понаблюдайте за непредсказуемыми результатами
 *     Найдите и исправьте все проблемы многопоточности:
 *         Сделайте операции с монетами атомарными
 *         Устраните возможность взаимной блокировки
 *         Обеспечьте безопасную запись в журнал
 *     Убедитесь, что после исправлений:
 *         Сумма totalCoins всегда корректна
 *         Программа никогда не зависает
 * Подсказки
 *      Используйте std::atomic<int> и std::mutex
 * Ожидаемый результат после исправлений
 *     Общее количество монет всегда должно быть равно 2000 (1000 из сундука + 1000 от артефакта)
 *     В сундуке не должно остаться монет (0)
 *
 */

#include <algorithm>
#include <chrono>
#include <iostream>
#include <mutex>
#include <string>
#include <thread>

int totalCoins = 0;            // общий счётчик (небезопасно)
int chestCoins = 1000;         // монеты в сундуке (небезопасно)
std::mutex key1;               // ключ 1 (для deadlock)
std::mutex key2;               // ключ 2 (для deadlock)
std::string explorationLog;    // лог (не потокобезопасен)

class Archaeologist {
    std::string name;
    int personalCoins = 0;

public:
    explicit Archaeologist(std::string n) : name(std::move(n)) {}

    void run() {
        // Задача 1: гонки при сборе монет
        for (int i = 0; i < 100; ++i) {
            collectCoins();
        }

        // Задача 2: возможный deadlock при активации артефакта
        activateMagicalArtifact();

        // Задача 3: гонка при записи в журнал
        writeToLog("Археолог " + name + " собрал " + std::to_string(personalCoins) + " монет");
    }

private:
    void collectCoins() {
        if (chestCoins > 0) {
            std::this_thread::sleep_for(std::chrono::milliseconds(1)); // имитация работы
            int coins = std::min(10, chestCoins);
            chestCoins -= coins;      // гонка
            personalCoins += coins;

            int temp = totalCoins;    // гонка
            temp += coins;
            totalCoins = temp;
        }
    }

    void activateMagicalArtifact() {
        if (name == "Джонс") {
            key1.lock();
            std::cout << name << " взял первый ключ\n";
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            key2.lock(); // возможен взаимоблок с другим потоком
            std::cout << name << " активировал артефакт!\n";
            personalCoins += 500;
            totalCoins += 500; // гонка
            key2.unlock();
            key1.unlock();
        } else {
            key2.lock();
            std::cout << name << " взял второй ключ\n";
            std::this_thread::sleep_for(std::chrono::milliseconds(100));
            key1.lock(); // возможен взаимоблок с другим потоком
            std::cout << name << " активировал артефакт!\n";
            personalCoins += 500;
            totalCoins += 500; // гонка
            key1.unlock();
            key2.unlock();
        }
    }

    void writeToLog(const std::string& message) {
        explorationLog += message + "\n"; // гонка
    }
};

int main() {
    Archaeologist jones("Джонс");
    Archaeologist smith("Смит");

    std::thread t1(&Archaeologist::run, &jones);
    std::thread t2(&Archaeologist::run, &smith);

    t1.join();
    t2.join();

    std::cout << "Всего собрано монет: " << totalCoins << "\n";
    std::cout << "Осталось в сундуке: " << chestCoins << "\n";
    std::cout << "\nЖурнал исследований:\n" << explorationLog;
}