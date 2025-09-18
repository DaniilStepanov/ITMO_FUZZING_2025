package org.itmo.fuzzing.lect1;

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
 *     Для синхронизации доступа к монетам используйте synchronized или атомарные классы из java.util.concurrent.atomic
 *     Для устранения дедлока унифицируйте порядок захвата блокировок
 *     Для потокобезопасной работы с журналом используйте синхронизацию
 * Ожидаемый результат после исправлений
 *     Общее количество монет всегда должно быть равно 2000 (1000 из сундука + 1000 от артефакта)
 *     В сундуке не должно остаться монет (0)
 *
 */
public class TempleExplorationBuggy {
    private static int totalCoins = 0;                // общий счётчик (небезопасно)
    private static int chestCoins = 1000;             // монеты в сундуке (небезопасно)
    private static final Object key1 = new Object();  // ключи к артефакту
    private static final Object key2 = new Object();
    private static final StringBuilder explorationLog = new StringBuilder(); // не потокобезопасен

    static class Archaeologist extends Thread {
        private int personalCoins = 0;

        public Archaeologist(String name) {
            super(name);
        }

        @Override
        public void run() {
            // Задача 1: Race Condition при сборе монет
            for (int i = 0; i < 100; i++) {
                collectCoins();
            }

            // Задача 2: Deadlock при активации артефакта
            activateMagicalArtifact();

            // Задача 3: Race Condition при записи в журнал
            writeToLog("Археолог " + getName() + " собрал " + personalCoins + " монет");
        }

        private void collectCoins() {
            // Проблема: неатомарная проверка/изменение chestCoins и totalCoins
            if (chestCoins > 0) {
                try { Thread.sleep(1); } catch (InterruptedException ignored) {}
                int coins = Math.min(10, chestCoins);
                chestCoins -= coins;         // небезопасно
                personalCoins += coins;

                int temp = totalCoins;
                temp += coins;
                totalCoins = temp;           // небезопасно
            }
        }

        private void activateMagicalArtifact() {
            // Проблема: разный порядок захвата блоков -> возможен deadlock
            if (getName().equals("Джонс")) {
                synchronized (key1) {
                    System.out.println(getName() + " взял первый ключ");
                    try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                    synchronized (key2) {
                        System.out.println(getName() + " активировал артефакт!");
                        personalCoins += 500;
                        totalCoins += 500; // небезопасно
                    }
                }
            } else {
                synchronized (key2) {
                    System.out.println(getName() + " взял второй ключ");
                    try { Thread.sleep(100); } catch (InterruptedException ignored) {}
                    synchronized (key1) {
                        System.out.println(getName() + " активировал артефакт!");
                        personalCoins += 500;
                        totalCoins += 500; // небезопасно
                    }
                }
            }
        }

        private void writeToLog(String message) {
            // Проблема: StringBuilder не потокобезопасен
            explorationLog.append(message).append('\n');
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Archaeologist jones = new Archaeologist("Джонс");
        Archaeologist smith = new Archaeologist("Смит");

        jones.start();
        smith.start();

        jones.join();
        smith.join();

        System.out.println("Всего собрано монет: " + totalCoins);
        System.out.println("Осталось в сундуке: " + chestCoins);
        System.out.println("\nЖурнал исследований:\n" + explorationLog);
    }
}