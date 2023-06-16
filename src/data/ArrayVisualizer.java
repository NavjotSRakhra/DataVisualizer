package data;

import data.visualizable.Visualizable;

import java.util.Arrays;

public class ArrayVisualizer implements Visualizable {
    final int[] arr;

    public ArrayVisualizer(int[] arr) {
        this.arr = arr;
    }

    public void setYAt(int index, int newValue) {
        arr[index] = newValue;
    }

    public void swap(int i, int j) {
        int t = arr[i];
        arr[i] = arr[j];
        arr[j] = t;
    }

    @Override
    public int getYAt(int x) {
        return arr[x];
    }

    @Override
    public int maxY() {
        return Arrays.stream(arr).max().orElse(-1);
    }

    @Override
    public int size() {
        return arr.length;
    }
}
