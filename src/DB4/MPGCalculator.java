/*
   Program: MPGCalculator.java
   Written by: Joshua Stone
   Description: A GUI program that asks for number of miles and number of gallons, then calculates MPG
   Challenges: Finding the cleanest way to present the different widgets
   Time Spent: 3 hours

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/24/17     Joshua Stone    Initial commit
   10/24/17     Joshua Stone    Added constructor
   10/24/17     Joshua Stone    Use JPanel and JFrame for windowing
   10/24/17     Joshua Stone    Use GridLayout to manage widgets
   10/24/17     Joshua Stone    Implement calculate() logic
   10/24/17     Joshua Stone    Implement clear() logic
   10/24/17     Joshua Stone    Represent default input text in an interface for reuse and documentation
   10/24/17     Joshua Stone    Add action listeners as lambdas
   10/24/17     Joshua Stone    Add all buttons together to JFrame
   10/24/17     Joshua Stone    use this.pack() to resize widgets
   10/24/17     Joshua Stone    Add JOptionPane window as child of main window
   10/24/17     Joshua Stone    Set window position to center
   10/24/17     Joshua Stone    Add a root JPanel pane to manage children panes so they are formatted better
   10/25/17     Joshua Stone    Use FlowLayout for buttons and BorderLayout for root panel
   10/25/17     Joshua Stone    Remove unneeded interface since clear() could set needed fields
   10/25/17     Joshua Stone    Remove complicated error-handling logic in calculate()
*/

package DB4;

import java.awt.Color;
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MPGCalculator extends JFrame {
    // Fields that can change across action events
    private final JTextField inputGallons;
    private final JTextField inputMiles;
    private final JLabel mpgResult;
    // Default constructor
    private MPGCalculator() {
        this.setTitle("Miles per Gallon Calculator");
        this.setResizable(false);
        // Program won't stop after pressing Close button unless setting close operation to exit
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Create root panel which will be added to the final window
        final JPanel rootPanel = new JPanel(new BorderLayout());
        // Set layout to 3 rows by 2 columns
        final JPanel textInputGroup = new JPanel(new GridLayout(3, 2));

        final JLabel gallonsLabel = new JLabel("Gallons of gas:");
        this.inputGallons = new JTextField();

        final JLabel milesLabel = new JLabel("Number of miles:");
        this.inputMiles = new JTextField();

        final JLabel mpgLabel = new JLabel("Miles per gallon (MPG):   ");
        this.mpgResult = new JLabel();
        this.mpgResult.setForeground(Color.BLUE);
        // Set all input fields to their default values after creating them
        this.clear();
        // Upon clicking, calculate MPG
        final JButton mpgButton = new JButton("Calculate MPG");
        mpgButton.addActionListener(event -> this.calculate());
        // Upon clicking, clear gallons, miles, and MPG result
        final JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(event -> this.clear());
        // Upon clicking, close window
        final JButton exitButton = new JButton("Exit");
        // disclose() tears down window and cleans up resources
        exitButton.addActionListener(event -> this.dispose());
        // First row
        textInputGroup.add(gallonsLabel);
        textInputGroup.add(this.inputGallons);
        // Second row
        textInputGroup.add(milesLabel);
        textInputGroup.add(this.inputMiles);
        // Third row
        textInputGroup.add(mpgLabel);
        textInputGroup.add(this.mpgResult);

        final JPanel buttonRow = new JPanel(new FlowLayout());
        // Fourth row
        buttonRow.add(mpgButton);
        buttonRow.add(clearButton);
        buttonRow.add(exitButton);

        rootPanel.add(textInputGroup, BorderLayout.CENTER);
        rootPanel.add(buttonRow, BorderLayout.SOUTH);
        // Add final root panel after composing widgets
        this.add(rootPanel);
        // Pack widgets after adding so the window is sized correctly
        this.pack();
    }
    private void calculate() {
        try {
            // Attempt to parse input fields by converting them to doubles and storing the results
            final double gallons = Double.parseDouble(this.inputGallons.getText());
            final double miles = Double.parseDouble(this.inputMiles.getText());

            // Basic check to ensure both numbers are not less than 0, as distance and gallons don't
            // make sense as negative numbers
            if (gallons <= 0 || miles < 0) {
                throw new ArithmeticException();
            }
            // Attempt to calculate MPG
            this.mpgResult.setText(String.format("%.2f", miles / gallons));
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Enter a number");
        } catch (ArithmeticException e) {
            JOptionPane.showMessageDialog(this, "Numbers must be greater than 0");
        }
    }
    // Set all input fields to default values
    private void clear() {
        this.inputGallons.setText("");
        this.inputMiles.setText("");
        this.mpgResult.setText("(Result here)");
    }
    public static void main(final String args[]) {
        MPGCalculator mpgCalculator = new MPGCalculator();
        // If set to null, window position will be at the center of the screen
        mpgCalculator.setLocationRelativeTo(null);
        mpgCalculator.setVisible(true);
    }
}