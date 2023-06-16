package data;

import data.dataActions.Action;

public class ArrayVisualizers {
    public static void performActionOn(ArrayVisualizer arrayVisualizer, Action action) {
        action.action(arrayVisualizer.arr);
    }
}
