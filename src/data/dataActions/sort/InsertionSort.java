package data.dataActions.sort;

import data.ArrayVisualizer;
import data.ArrayVisualizers;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class InsertionSort {
    public static void sortAndVisualizeDataWithDelay(int delayMilliseconds, ArrayVisualizer data, JPanel panel) {
        ArrayVisualizers.performActionOn(data, arr -> {
            for (int i = 0; i < arr.length; i++) {
                for (int j = i; j > 0; j--) {
                    if (arr[j] > arr[j - 1]) break;
                    swap(arr, j, j - 1);
                    try {
                        SwingUtilities.invokeAndWait(panel::repaint);
                        Thread.sleep(delayMilliseconds);
                    } catch (InterruptedException | InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private static void swap(int[] arr, int i, int j) {
        var t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }
}
