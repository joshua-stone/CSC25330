/*
   Program: OvalDisplay.java
   Written by: Joshua Stone
   Description:
   Challenges:
   Time Spent:

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
*/

package DB5;

import javax.swing.*;
import java.awt.*;

public class OvalDisplay extends JPanel {
    // Make oval coordinates and dimensions immutable since GUI only sets them once
    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private JButton colorPicker;
    private Color ovalColor;

    public OvalDisplay(final int x, final int y, final int width, final int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;

        // Default color
        this.setColor(Color.lightGray);

        this.setLayout(new BorderLayout());

        this.colorPicker = new JButton("Change color");
        colorPicker.addActionListener(event -> this.openColorPicker());

        this.add(colorPicker, BorderLayout.SOUTH);
    }
    public void setColor(final Color color) {
        this.ovalColor = color;
        this.repaint();
    }
    private void openColorPicker() {
        final Color newColor = JColorChooser.showDialog(this, "Choose a color", this.ovalColor);

        if (newColor != null) {
            this.setColor(newColor);
        }
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(this.ovalColor);
        g.fillOval(this.x, this.y, this.width, this.height);
    }
    private static int getInput(final String dialogText) {
        int result;

        while (true) {
            try {
                result = Integer.parseInt(JOptionPane.showInputDialog(null, dialogText));
                break;
                // NumberFormatException is caused by incorrect input, and NullPointerException is caused by clicking
                // Cancel button or Close button
            } catch (NumberFormatException | NullPointerException e) {
                JOptionPane.showMessageDialog(null, "Please enter a valid number.");
            }
        }
        return result;
    }
    private void initWindow() {
        // Main window creation logic
        JFrame window = new JFrame();
        window.setTitle("Draw Oval");
        window.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        window.add(this);

        window.setSize(375, 375);
        window.setLocationRelativeTo(null);
        window.setVisible(true);
    }
    public static void main(final String args[]) {
        final int x = getInput("Enter upper left X");
        final int y = getInput("Enter upper left Y");
        final int width = getInput("Enter width");
        final int height = getInput("Enter height");

        OvalDisplay ovalDisplay = new OvalDisplay(x, y, width, height);
        ovalDisplay.initWindow();
    }
}