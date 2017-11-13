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
*/

package DB5;

import javax.swing.*;

public class OvalDisplay extends JFrame {
    private final double x;
    private final double y;
    private final double width;
    private final double height;

    public OvalDisplay() {
        this.x = this.getDouble("Enter upper left X");
        this.y = this.getDouble("Enter upper left Y");
        this.width = this.getDouble("Enter width");
        this.height = this.getDouble("Enter height");
    }
    private double getDouble(final String input) {
        return Double.parseDouble(JOptionPane.showInputDialog(this, input));
    }
    public static void main(final String args[]) {
        OvalDisplay ovalDisplay = new OvalDisplay();
        ovalDisplay.setVisible(true);
    }
}
