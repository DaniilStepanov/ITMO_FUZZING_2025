package org.itmo.fuzzing.lect1;

import java.util.Arrays;

public class ShellSort {

    public static int[] shellSort(int[] elems) {
        int[] gaps = {701, 301, 132, 57, 23, 10, 4, 1};

        int[] sortedElems = Arrays.copyOf(elems, elems.length);

        for (int gap : gaps) {
            for (int i = gap; i < sortedElems.length; i++) {
                int temp = sortedElems[i];
                int j = i;
                while (j >= gap && sortedElems[j - gap] > temp) {
                    sortedElems[j] = sortedElems[j - gap];
                    j -= gap;
                }
                sortedElems[j] = temp;
            }
        }

        return sortedElems;
    }

    public static void main(String[] args) {
        int[] elems = {5, 2, 9, 1, 5, 6};
        var sortedElems = shellSort(elems);
        for (int i = 0; i < sortedElems.length; i++) {
            System.out.printf("sortedElems[%d] = %d\n", i, sortedElems[i]);
        }

        //Part 1: Manual Test Cases
        //Part 2: Random Inputs
        //isSorted
        //is_permutation
        //Generate and test 1,000 lists.
    }
}