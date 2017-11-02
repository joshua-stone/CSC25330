/*
   Program: ShapePicker.java
   Written by: Joshua Stone
   Description: A GUI program for calculating area and perimeter based on which shape is picked
   Challenges:
   Time Spent:

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/26/17     Joshua Stone    Initial commit
   10/26/17     Joshua Stone    Added more labels for shapes
   10/26/17     Joshua Stone    Added results labels and buttons
   10/26/17     Joshua Stone    Implement calculate() logic and exception handling
   10/26/17     Joshua Stone    Make a getCurrentShape() helper method
   10/26/17     Joshua Stone    Add exception handling for invalid states
   10/27/17     Joshua Stone    Fix up UI code so it scales better with resizing
   10/27/17     Joshua Stone    Clean up results field appearance
   10/27/17     Joshua Stone    More syntax cleanups
   10/27/17     Joshua Stone    Change import statements to be more explicit
*/

package assignment2;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.IllegalComponentStateException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import assignment1.Circle;
import assignment1.GeometricObject;
import assignment1.Rectangle;

import static javax.swing.JOptionPane.showMessageDialog;
import static javax.swing.BorderFactory.createTitledBorder;

public class ShapePicker extends JFrame {
    private final JTextField radiusField;
    private final JTextField widthField;
    private final JTextField heightField;
    private final JTextField sideField;
    private final JTextField shapeResult;
    private final JTextField areaResult;
    private final JTextField perimeterResult;
    private final JComboBox<String> shapes;
    private final String[] options;

    private ShapePicker() {
        this.setTitle("GUI Application");
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.options = new String[] {
            "",
            "CIRCLE",
            "RECTANGLE",
            "SQUARE"
        };
        final JLabel shapesLabel = new JLabel("Pick up one shape: ");
        this.shapes = new JComboBox<>(options);
        shapes.addActionListener(event -> this.setEdit());

        final JPanel shapePickerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        shapePickerPanel.add(shapesLabel);
        shapePickerPanel.add(shapes);

        final JPanel inputPanel = new JPanel(new GridLayout(4, 2));
        inputPanel.setBorder(createTitledBorder("Input Data:"));

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

        final JPanel resultPanel = new JPanel(new GridLayout(3, 2));
        resultPanel.setBorder(createTitledBorder("Result:"));

        final JLabel shapeLabel = new JLabel("Shape is: ");
        this.shapeResult = new JTextField();
        this.shapeResult.setEditable(false);
        this.shapeResult.setForeground(Color.BLUE);
        final JLabel areaLabel = new JLabel("Area is: ");
        this.areaResult = new JTextField();
        this.areaResult.setEditable(false);
        this.areaResult.setForeground(Color.BLUE);
        final JLabel perimeterLabel = new JLabel("Perimeter is: ");
        this.perimeterResult = new JTextField();
        this.perimeterResult.setEditable(false);
        this.perimeterResult.setForeground(Color.BLUE);

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

        buttonPanel.add(getButton);
        buttonPanel.add(clearButton);
        buttonPanel.add(exitButton);

        final JPanel middlePanel = new JPanel(new GridLayout(2, 1));

        middlePanel.add(inputPanel);
        middlePanel.add(resultPanel);

        final JPanel rootPanel = new JPanel(new BorderLayout());

        rootPanel.add(shapePickerPanel, BorderLayout.NORTH);
        rootPanel.add(middlePanel, BorderLayout.CENTER);
        rootPanel.add(buttonPanel, BorderLayout.SOUTH);

        this.add(rootPanel);
        this.disableEdit();
        this.pack();
    }
    private void setEdit() {
        this.disableEdit();

        switch (this.getCurrentShape()) {
            case "CIRCLE":
                this.radiusField.setEditable(true);
                break;
            case "RECTANGLE":
                this.widthField.setEditable(true);
                this.heightField.setEditable(true);
                break;
            case "SQUARE":
                this.sideField.setEditable(true);
        }
    }
    private void calculate() {
        final GeometricObject shapeResult;
        final String selectedShape = this.getCurrentShape();

        try {
            switch (selectedShape) {
                case "CIRCLE":
                    shapeResult = new Circle(this.getInput(this.radiusField));
                    break;
                case "RECTANGLE":
                    shapeResult = new Rectangle(this.getInput(this.widthField), this.getInput(this.heightField));
                    break;
                case "SQUARE":
                    shapeResult = new Rectangle(this.getInput(this.sideField), this.getInput(this.sideField));
                    break;
                default:
                    throw new IllegalComponentStateException();
            }
            this.shapeResult.setText(shapeResult.getName());
            this.areaResult.setText(String.format("%.2f", shapeResult.getArea()));
            this.perimeterResult.setText(String.format("%.2f", shapeResult.getPerimeter()));
        } catch (NumberFormatException e) {
            showMessageDialog(this, "Enter valid numbers");
        } catch (IllegalComponentStateException e) {
            showMessageDialog(this, "Select a shape");
        }
    }
    private void clear() {
        this.radiusField.setText("");
        this.widthField.setText("");
        this.heightField.setText("");
        this.sideField.setText("");
        this.shapeResult.setText("");
        this.areaResult.setText("");
        this.perimeterResult.setText("");
    }
    private void disableEdit() {
        this.radiusField.setEditable(false);
        this.widthField.setEditable(false);
        this.heightField.setEditable(false);
        this.sideField.setEditable(false);
    }
    // Private helper method for extracting number input
    private double getInput(JTextField input) {
        return Double.parseDouble(input.getText());
    }
    private String getCurrentShape() {
        // There's getSelectedObject(), but it returns Object which isn't very safe
        final int shapeIndex = this.shapes.getSelectedIndex();

        return this.options[shapeIndex];
    }
    public static void main(String[] args) {
        ShapePicker window = new ShapePicker();
        window.setVisible(true);
    }
}