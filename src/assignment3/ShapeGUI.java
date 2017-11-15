/*
    Program: OvalDisplay.java
    Written by: Joshua Stone
    Description: A GUI program for setting initial oval coordinates and dimensions, as well as having a color picker
    Challenges: Finding the best way to compose JPanel and JFrame for displaying graphics correctly
    Time Spent: 3 hours

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    11/15/17     Joshua Stone    Initial commit
*/

package assignment3;

import javax.swing.JFrame;

public class ShapeGUI extends JFrame {
    public ShapeGUI() {
        this.setTitle("Drawing shapes and Displaying All Info");
        this.setVisible(true);
    }
    public static void main(final String[] args) {
        ShapeGUI shapeGUI = new ShapeGUI();
    }
}
