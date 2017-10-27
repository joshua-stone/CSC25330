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
   10/25/17     Joshua Stone    Added results labels and buttons
*/

package assignment2;

import java.awt.*;
import javax.swing.*;

public class ShapePicker extends JFrame {
    private final JTextField radiusField;
    private final JTextField widthField;
    private final JTextField heightField;
    private final JTextField sideField;
    private final JTextField shapeResult;
    private final JTextField areaResult;
    private final JTextField perimeterResult;
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
        final JLabel shapesLabel = new JLabel("Pick up one shape: ");
        this.shapes = new JComboBox<>(options);
        shapes.addActionListener(event -> this.setEdit());

        final JPanel shapePickerPanel = new JPanel(new GridLayout(1, 2));
        shapePickerPanel.add(shapesLabel);
        shapePickerPanel.add(shapes);

        final JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Input Data:"));

        final JLabel radiusLabel = new JLabel("radius: ");
        this.radiusField = new JTextField();
        final JLabel widthLabel = new JLabel("width: ");
        this.widthField = new JTextField();
        final JLabel heightLabel = new JLabel("height: ");
        this.heightField = new JTextField();
        final JLabel sideLabel = new JLabel("side: ");
        this.sideField = new JTextField();

        inputPanel.add(radiusLabel);
        inputPanel.add(this.radiusField);
        inputPanel.add(widthLabel);
        inputPanel.add(this.widthField);
        inputPanel.add(heightLabel);
        inputPanel.add(this.heightField);
        inputPanel.add(sideLabel);
        inputPanel.add(this.sideField);

        final JPanel resultPanel = new JPanel(new GridLayout(5, 2));
        resultPanel.setBorder(BorderFactory.createTitledBorder("Result:"));

        final JLabel shapeLabel = new JLabel("Shape is: ");
        this.shapeResult = new JTextField();
        this.shapeResult.setEditable(false);
        final JLabel areaLabel = new JLabel("Area is: ");
        this.areaResult = new JTextField();
        this.areaResult.setEditable(false);
        final JLabel perimeterLabel = new JLabel("height: ");
        this.perimeterResult = new JTextField();
        this.perimeterResult.setEditable(false);

        resultPanel.add(shapeLabel);
        resultPanel.add(this.shapeResult);
        resultPanel.add(areaLabel);
        resultPanel.add(this.areaResult);
        resultPanel.add(perimeterLabel);
        resultPanel.add(this.perimeterResult);

        final JButton getButton = new JButton("Get");
        getButton.addActionListener(event -> this.calculate());

        final JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(event -> this.clear());

        final JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(event -> this.dispose());

        final JPanel buttonPanel = new JPanel(new FlowLayout());
        // Fourth row
        buttonPanel.add(getButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        final JPanel fieldsPanel = new JPanel(new GridLayout(2, 1));

        fieldsPanel.add(inputPanel);
        fieldsPanel.add(resultPanel);

        final JPanel rootPanel = new JPanel(new BorderLayout());

        rootPanel.add(shapePickerPanel, BorderLayout.NORTH);
        rootPanel.add(fieldsPanel, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(rootPanel);
        this.clear();
        this.pack();
    }
    private void calculate() {

    }
    private void setEdit() {
        final int shapeIndex = this.shapes.getSelectedIndex();
        final String currentShape = this.options[shapeIndex];

        this.clear();

        switch (currentShape) {
            case "Circle":            // If circle, then set radius as editable
                this.radiusField.setEditable(true);
                break;
            case "Rectangle":  // If rectangle, then set width and height as editable
                this.widthField.setEditable(true);
                this.heightField.setEditable(true);
                break;
            case "Square":    // Else, assume square is picked and set side as editable
                this.sideField.setEditable(true);
                break;
        }
    }
    private void clear() {
        this.radiusField.setEditable(false);
        this.widthField.setEditable(false);
        this.heightField.setEditable(false);
        this.sideField.setEditable(false);
    }
    public static void main(String[] args) {
        ShapePicker gui = new ShapePicker();
        gui.setVisible(true);
        gui.setLocationRelativeTo(null);
    }
}