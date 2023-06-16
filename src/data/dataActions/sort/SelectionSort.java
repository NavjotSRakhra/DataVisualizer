package data.dataActions.sort;

import data.ArrayVisualizer;
import data.ArrayVisualizers;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class SelectionSort {
    public static void sortAndVisualizeDataWithDelay(int delayMilliseconds, ArrayVisualizer data, JPanel panel) {
        ArrayVisualizers.performActionOn(data, arr -> {
            for (int i = 0; i < arr.length; i++) {
                int minIndex = i;
                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[minIndex] > arr[j])
                        minIndex = j;
                }
                var t = arr[minIndex];
                arr[minIndex] = arr[i];
                arr[i] = t;

                try {
                    SwingUtilities.invokeAndWait(panel::repaint);
                    Thread.sleep(delayMilliseconds);
                } catch (InterruptedException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}