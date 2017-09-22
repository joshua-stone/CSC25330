/*
   Program: Rectangle.java
   Written by: Joshua Stone
   Description:
   Challenges:
   Time Spent:

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/21/17     Joshua Stone    Initial commit
   09/21/17     Joshua Stone    Added constructors
   09/21/17     Joshua Stone    Added data fields
   09/21/17     Joshua Stone    Added setters and getters
*/

package assignment1;

public class Rectangle {
    private double width;
    private double height;

    public Rectangle() {
        this(1.0, 1.0);
    }
    public Rectangle(final double width, final double height) {
        this.setWidth(width);
        this.setHeight(height);
    }
    public void setWidth(final double width) {
        this.width = width;
    }
    public void setHeight(final double height) {
        this.height = height;
    }
    public double getWidth() {
        return this.width;
    }
    public double getHeight() {
        return this.height;
    }
}
