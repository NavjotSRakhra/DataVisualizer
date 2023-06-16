package data.dataActions.sort;

import data.ArrayVisualizer;
import data.ArrayVisualizers;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.stream.IntStream;

public class QuickSort {
    public static void sortAndVisualizeDataWithDelay(int delayMilliSeconds, ArrayVisualizer data, JPanel panel) {
        ArrayVisualizers.performActionOn(data, arr -> quickSort(arr, 0, arr.length - 1, delayMilliSeconds, panel));
    }

    private static void quickSort(int[] arr, int startIndex, int endIndex, int delay, JPanel panel) {
        if (startIndex >= endIndex) return;

        int partition = partition(arr, startIndex, endIndex, delay, panel);
        quickSort(arr, startIndex, partition - 1, delay, panel);
        quickSort(arr, partition + 1, endIndex, delay, panel);
    }

    private static int partition(final int[] arr, final int startIndex, final int endIndex, final int delay, final JPanel panel) {
        int temp = java.util.Arrays.stream(arr, startIndex, endIndex + 1)
                .parallel()
                .sorted()
                .toArray()
                [(endIndex - startIndex) / 2];
        int ind = IntStream.range(startIndex, endIndex + 1)
                .filter(a -> temp == arr[a])
                .findFirst()
                .orElse(-1);
        swap(arr, endIndex, ind);

        int pointer = startIndex;
        for (int i = startIndex; i < endIndex; i++) {
            if (arr[i] < arr[endIndex]) {
                if (i != pointer) {
                    swap(arr, pointer, i);
                }
                pointer++;
            }
            try {
                SwingUtilities.invokeAndWait(panel::repaint);
                Thread.sleep(delay);
            } catch (InterruptedException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        swap(arr, endIndex, pointer);
        return pointer;
    }

    private static void swap(int[] arr, int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
