package data.dataActions.sort;

import data.ArrayVisualizer;
import data.ArrayVisualizers;
import data.dataActions.Action;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class BubbleSort {
    public static void sortAndVisualizeDataWithDelay(int delay, ArrayVisualizer data, JPanel panel) {
        ArrayVisualizers.performActionOn(data, new Action() {
            @Override
            public void action(int[] arr) {
                for (int i = 0; i < arr.length; i++) {
                    for (int j = 0; j < arr.length - 1 - i; j++) {
                        if (arr[j] > arr[j + 1]) swap(arr, j, j + 1);
                        try {
                            SwingUtilities.invokeAndWait(panel::repaint);
                            Thread.sleep(delay);
                        } catch (InterruptedException | InvocationTargetException e) {
                            throw new RuntimeException(e);
                        }
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
