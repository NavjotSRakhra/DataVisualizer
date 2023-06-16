package ui.gui;

import data.ArrayVisualizer;
import data.dataActions.sort.*;
import ui.elements.dynamic.VisualizationJPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Objects;

public class GUI {
    private static final int MIN_DELAY = 0, MAX_DELAY = 50;
    private final JFrame mainFrame;
    private final VisualizationJPanel visualizationPanel;
    private final JComboBox<SortAction> actionsList;
    private final JSlider delaySlider;
    @SuppressWarnings("FieldCanBeLocal")
    private final int V_PANEL_WIDTH = 400, V_PANEL_HEIGHT = 400, ARRAY_SIZE = 200;
    private final JPanel actionPanel;
    private ArrayVisualizer arr;

    public GUI() {
        mainFrame = new JFrame("Data visualizer");
        actionPanel = initializePanelWithLayout(BoxLayout.Y_AXIS);
        actionsList = new JComboBox<>();
        delaySlider = new JSlider(JSlider.HORIZONTAL, MIN_DELAY, MAX_DELAY, MIN_DELAY);

        initializeActionPanelElements();

        arr = new ArrayVisualizer(initializeRandomArray());
        visualizationPanel = new VisualizationJPanel(arr);

        initializeVisualizationPanel();
        initializeMainFrame();
    }

    private JPanel initializePanelWithLayout(int axis) {
        final JPanel actionPanel;
        actionPanel = new JPanel();
        final var layout = new BoxLayout(actionPanel, axis);
        actionPanel.setLayout(layout);
        return actionPanel;
    }

    private void initializeActionPanelElements() {

        actionPanel.add(addUpperActionPanel());
        actionPanel.add(new Box.Filler(new Dimension(0, 10), new Dimension(0, 10), new Dimension(0, 10)));
        actionPanel.add(new Label("Delay in ms"));
        actionPanel.add(addLowerActionPanel());
    }

    private JPanel addLowerActionPanel() {
        JPanel lowerActionPanel = initializePanelWithLayout(BoxLayout.X_AXIS);

        delaySlider.setMajorTickSpacing(10);
        delaySlider.setMinorTickSpacing(5);
        delaySlider.setPaintTicks(true);
        delaySlider.setPaintLabels(true);
        delaySlider.setSnapToTicks(true);

        lowerActionPanel.add(delaySlider);

        return lowerActionPanel;
    }

    private JPanel addUpperActionPanel() {
        JPanel upperActionPanel = initializePanelWithLayout(BoxLayout.X_AXIS);

        JButton sort = new JButton("Sort");
        sort.addActionListener(this::startSorting);
        upperActionPanel.add(sort);

        upperActionPanel.add(new Box.Filler(new Dimension(20, 0), new Dimension(20, 0), new Dimension(20, 0)));

        JButton shuffle = new JButton("Shuffle");
        shuffle.addActionListener(this::shuffleData);
        upperActionPanel.add(shuffle);

        upperActionPanel.add(new Box.Filler(new Dimension(20, 0), new Dimension(20, 0), new Dimension(20, 0)));

        Arrays.stream(SortAction.values()).forEach(actionsList::addItem);

        upperActionPanel.add(actionsList);
        return upperActionPanel;
    }

    private void shuffleData(ActionEvent actionEvent) {
        arr = new ArrayVisualizer(initializeRandomArray());
        visualizationPanel.setNewData(arr);
        SwingUtilities.invokeLater(visualizationPanel::repaint);
    }

    private int[] initializeRandomArray() {
        ArrayList<Integer> arrayList = new ArrayList<>(ARRAY_SIZE);
        for (int i = 0; i < ARRAY_SIZE; i++) {
            arrayList.add(i + 1);
        }
        Collections.shuffle(arrayList);
        return arrayList.stream().mapToInt(Integer::intValue).toArray();
    }

    private void initializeVisualizationPanel() {
        visualizationPanel.setPreferredSize(new Dimension(V_PANEL_WIDTH, V_PANEL_HEIGHT));
        visualizationPanel.setLayout(new GridBagLayout());
    }

    private void initializeMainFrame() {
        mainFrame.setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();

        c.gridx = 0;
        c.gridy = 0;

        mainFrame.add(visualizationPanel, c);

        c.gridy = 1;

        c.insets = new Insets(10, 0, 10, 0);
        mainFrame.add(actionPanel, c);

        mainFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        mainFrame.setResizable(false);
        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null);
        mainFrame.setVisible(true);
    }

    private void startSorting(ActionEvent e) {
        int delayMillisecond = delaySlider.getValue();
        new Thread(() -> {
            switch ((SortAction) Objects.requireNonNull(actionsList.getSelectedItem())) {
                case BUBBLE_SORT -> BubbleSort.sortAndVisualizeDataWithDelay(delayMillisecond, arr, visualizationPanel);
                case INSERTION_SORT ->
                        InsertionSort.sortAndVisualizeDataWithDelay(delayMillisecond, arr, visualizationPanel);
                case QUICK_SORT -> QuickSort.sortAndVisualizeDataWithDelay(delayMillisecond, arr, visualizationPanel);
                case MERGE_SORT -> MergeSort.sortAndVisualizeDataWithDelay(delayMillisecond, arr, visualizationPanel);
                case SELECTION_SORT ->
                        SelectionSort.sortAndVisualizeDataWithDelay(delayMillisecond, arr, visualizationPanel);
            }
        }).start();
    }
}
