/*
   Program: OvalDisplay.java
   Written by: Joshua Stone
   Description: A GUI program for setting initial oval coordinates and dimensions, as well as having a color picker
   Challenges: Finding the best way to compose JPanel and JFrame for displaying graphics correctly
   Time Spent: 3 hours

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   11/12/17     Joshua Stone    Initial commit
   11/12/17     Joshua Stone    Add x, y, width, and height variables
   11/12/17     Joshua Stone    Create input dialog function
   11/12/17     Joshua Stone    Implement input validation logic
   11/12/17     Joshua Stone    Add graphics painting logic
   11/13/17     Joshua Stone    Rework window creation logic to fix repainting
   11/13/17     Joshua Stone    Add JColorChooser to set new color
   11/13/17     Joshua Stone    Rework JPanel and JFrame composition
   11/13/17     Joshua Stone    Write some documentation fixes
*/

package DB5;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import static java.lang.Integer.parseInt;
import static javax.swing.JColorChooser.showDialog;
import static javax.swing.JOptionPane.showInputDialog;
import static javax.swing.JOptionPane.showMessageDialog;

public class OvalDisplay extends JFrame {
    // Make oval coordinates and dimensions immutable since GUI only sets them once
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final JPanel oval;
    private Color ovalColor;

    public OvalDisplay(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        this.oval = new JPanel(new BorderLayout()) {
            // paint() draws graphics in a JPanel
            public void paint(final Graphics g) {
                super.paint(g);
                g.setColor(ovalColor);
                // x and y are bounding rectangle's top-left corner coordinates
                g.fillOval(x, y, width, height);
            }
        };
        final JButton colorPicker = new JButton("Change color");
        colorPicker.addActionListener(event -> this.openColorPicker());
        // Add color picker button to bottom
        this.oval.add(colorPicker, BorderLayout.SOUTH);

        this.setColor(Color.lightGray);
    }
    private void setColor(final Color color) {
        if (color != null) {
            this.ovalColor = color;
        } else {
            this.ovalColor = Color.lightGray;
        }
        // Manually call repaint() so new panel can be updated with new colors
        this.oval.repaint();
    }
    private void openColorPicker() {
        // Returns null if no color was picked
        final Color newColor = showDialog(this, "Choose a color", this.ovalColor);

        this.setColor(newColor);
    }
    private static int getInput(final String dialogText) {
        int result;

        // Ask for user input, making a looping dialog window until a valid integer is entered
        while (true) {
            try {
                result = parseInt(showInputDialog(null, dialogText));
                break;
                // NumberFormatException is caused by incorrect input, and NullPointerException is caused by clicking
                // Cancel button or Close button
            } catch (NumberFormatException | NullPointerException e) {
                showMessageDialog(null, "Please enter a valid number.");
            }
        }
        return result;
    }
    private void initWindow() {
        // Window frame creation logic
        this.setTitle("Draw Oval");
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setSize(375, 375);
        // Put window in the center of the screen
        this.setLocationRelativeTo(null);
        this.add(this.oval);
        this.setVisible(true);
    }
    public static void main(final String[] args) {
        // Creates four input windows that loop until all have valid integers
        final int x = getInput("Enter upper left X");
        final int y = getInput("Enter upper left Y");
        final int width = getInput("Enter width");
        final int height = getInput("Enter height");

        final OvalDisplay ovalDisplay = new OvalDisplay(x, y, width, height);
        ovalDisplay.initWindow();
    }
}