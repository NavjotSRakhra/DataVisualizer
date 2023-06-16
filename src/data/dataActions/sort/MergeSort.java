package data.dataActions.sort;

import data.ArrayVisualizer;
import data.ArrayVisualizers;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;

public class MergeSort {
    public static void sortAndVisualizeDataWithDelay(int delay, ArrayVisualizer data, JPanel panel) {
        ArrayVisualizers.performActionOn(data, arr -> mergeSortWithDelayAndVisualization(arr, 0, arr.length - 1, delay, panel));
    }

    private static void mergeSortWithDelayAndVisualization(int[] arr, int startingIndex, int endingIndex, int delay, JPanel panel) {
        if (startingIndex >= endingIndex) return;

        int mid = (startingIndex + endingIndex) / 2;

        mergeSortWithDelayAndVisualization(arr, startingIndex, mid, delay, panel);
        mergeSortWithDelayAndVisualization(arr, mid + 1, endingIndex, delay, panel);

        merge(arr, startingIndex, mid, endingIndex, delay, panel);
    }

    private static void merge(int[] arr, int startingIndex, int mid, int endingIndex, int delay, JPanel panel) {
        int[] temp = Arrays.stream(arr, startingIndex, endingIndex + 1).toArray();

        int i = startingIndex, j = mid + 1, c = 0;
        while (i <= mid && j <= endingIndex) {
            if (temp[i - startingIndex] <= temp[j - startingIndex]) {
                arr[startingIndex + c++] = temp[i++ - startingIndex];
            } else {
                arr[startingIndex + c++] = temp[j++ - startingIndex];
            }
            updateUI(delay, panel);
        }
        while (i <= mid) {
            arr[startingIndex + c++] = temp[i++ - startingIndex];
            updateUI(delay, panel);
        }
        while (j <= endingIndex) {
            arr[startingIndex + c++] = temp[j++ - startingIndex];
            updateUI(delay, panel);
        }
    }

    private static void updateUI(int delay, JPanel panel) {
        try {
            SwingUtilities.invokeAndWait(panel::repaint);
            Thread.sleep(delay);
        } catch (InterruptedException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }
}
