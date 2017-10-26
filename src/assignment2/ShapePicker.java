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

    private ShapePicker() {
        String options[] = {
            "Circle",
            "Rectangle",
            "Square"
        };

        final JPanel widgets = new JPanel(new GridLayout(5, 1));

        this.radius = new JTextField();
        this.width = new JTextField();
        this.height = new JTextField();
        this.side = new JTextField();


        this.shapes = new JComboBox<>(options);

        shapes.addActionListener(event -> this.setEdit());

        widgets.add(shapes);
        widgets.add(this.radius);
        widgets.add(this.width);
        widgets.add(this.height);
        widgets.add(side);

        final JPanel rootPane = new JPanel(new BorderLayout());
        rootPane.add(widgets, BorderLayout.NORTH);
        this.setEdit();
        this.add(rootPane);
        this.pack();
    }
    private void setEdit() {
        String currentShape = this.shapes.getSelectedItem().toString();

        if (currentShape.equals("Circle")) {
            this.radius.setEditable(true);
            this.width.setEditable(false);
            this.height.setEditable(false);
            this.side.setEditable(false);
        } else if (currentShape.equals("Rectangle")){
            this.radius.setEditable(false);
            this.width.setEditable(true);
            this.height.setEditable(true);
            this.side.setEditable(false);
        } else {
            this.radius.setEditable(false);
            this.width.setEditable(false);
            this.height.setEditable(false);
            this.side.setEditable(true);
        }
    }
    public static void main(String[] args) {
        ShapePicker gui = new ShapePicker();
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
    }
}