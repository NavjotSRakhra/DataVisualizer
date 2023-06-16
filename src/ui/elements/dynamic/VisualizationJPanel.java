package ui.elements.dynamic;

import data.visualizable.Visualizable;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class VisualizationJPanel extends JPanel {
    private final int circleDiameter;
    private Visualizable visualizableData;
    private Map<Integer, Color> colorMap;

    public VisualizationJPanel(Visualizable visualizableData) {
        this.visualizableData = visualizableData;
        circleDiameter = 10;

        initializeColorMap();
    }

    private void initializeColorMap() {
        colorMap = new HashMap<>(visualizableData.size());
        Random random = new Random();
        for (int i = 0; i < visualizableData.size(); i++) {
            colorMap.putIfAbsent(visualizableData.getYAt(i), new Color(random.nextInt(0xFFFFFF)));
        }
    }

    public void setNewData(Visualizable newData) {
        visualizableData = newData;
        initializeColorMap();
    }

    @Override
    public void paint(Graphics g) {
        var dim = getSize();
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, dim.width, dim.height);
        double componentXLengthPixels = dim.width - circleDiameter;
        double componentYLengthPixels = dim.height - circleDiameter;
        double maxDataY = visualizableData.maxY();

        for (int i = 0; i < visualizableData.size(); i++) {
            g.setColor(colorMap.get(visualizableData.getYAt(i)));
            g.fillOval((int) (componentXLengthPixels * i / visualizableData.size()), (int) (componentYLengthPixels * (1 - visualizableData.getYAt(i) / maxDataY)), circleDiameter, circleDiameter);
        }
    }
}
