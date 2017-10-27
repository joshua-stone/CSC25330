/*
   Program: ShapePicker.java
   Written by: Joshua Stone
   Description: A GUI program for calculating area and perimeter based on which shape is picked
   Challenges:
   Time Spent:

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/25/17     Joshua Stone    Initial commit
   10/25/17     Joshua Stone    Added more labels for shapes
*/

package assignment2;

import java.awt.*;
import javax.swing.*;

public class ShapePicker extends JFrame {
    private final JTextField radius;
    private final JTextField width;
    private final JTextField height;
    private final JTextField side;
    private final JComboBox<String> shapes;
    private final String options[];
    private ShapePicker() {
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.options = new String[] {
            "",
            "Circle",
            "Rectangle",
            "Square"
        };
        final JPanel widgets = new JPanel(new GridLayout(5, 2));

        final JLabel shapesLabel = new JLabel("Pick up one shape: ");
        this.shapes = new JComboBox<>(options);
        shapes.addActionListener(event -> this.setEdit());
        final JLabel radiusLabel = new JLabel("radius: ");
        this.radius = new JTextField();
        final JLabel widthLabel = new JLabel("width: ");
        this.width = new JTextField();
        final JLabel heightLabel = new JLabel("height: ");
        this.height = new JTextField();
        final JLabel sideLabel = new JLabel("side: ");
        this.side = new JTextField();

        widgets.add(shapesLabel);
        widgets.add(shapes);
        widgets.add(radiusLabel);
        widgets.add(this.radius);
        widgets.add(widthLabel);
        widgets.add(this.width);
        widgets.add(heightLabel);
        widgets.add(this.height);
        widgets.add(sideLabel);
        widgets.add(this.side);

        final JPanel rootPane = new JPanel(new BorderLayout());
        rootPane.add(widgets, BorderLayout.CENTER);

        this.add(rootPane);
        this.clear();
        this.pack();
    }
    private void setEdit() {
        final int shapeIndex = this.shapes.getSelectedIndex();
        final String currentShape = this.options[shapeIndex];

        this.clear();

        switch (currentShape) {
            case "Circle":            // If circle, then set radius as editable
                this.radius.setEditable(true);
                break;
            case "Rectangle":  // If rectangle, then set width and height as editable
                this.width.setEditable(true);
                this.height.setEditable(true);
                break;
            case "Square":    // Else, assume square is picked and set side as editable
                this.side.setEditable(true);
                break;
        }
    }
    private void clear() {
        this.radius.setEditable(false);
        this.width.setEditable(false);
        this.height.setEditable(false);
        this.side.setEditable(false);
    }
    public static void main(String[] args) {
        ShapePicker gui = new ShapePicker();
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
    }
}