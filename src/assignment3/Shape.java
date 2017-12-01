/*
   Program: Shape.java
   Written by: Joshua Stone
   Description: A general abstract class for a shape
   Challenges: None
   Time Spent: 10 minutes

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/21/17     Joshua Stone    Initial commit
   09/21/17     Joshua Stone    Added setters, getters, and constructors
   09/21/17     Joshua Stone    Added abstract methods for subclasses
*/

package assignment3;

public abstract class Shape {
    // Private X and Y dimension fields
    private int X;
    private int Y;

    // Default constructor
    public Shape() {
        this(1, 1);
    }
    // Constructor that sets X and Y
    public Shape(final int x, final int y) {
        this.setX(x);
        this.setY(y);
    }
    // Set X with int
    public void setX(final int x) {
        this.X = x;
    }
    // Set Y with int
    public void setY(final int y) {
        this.Y = y;
    }
    // Get X
    public int getX() {
        return this.X;
    }
    // Get Y
    public int getY() {
        return this.Y;
    }
    // Abstract methods for name, area, and perimeter to be implemented by a more specific subclass
    public abstract String getName();
    public abstract double getArea();
    public abstract double getPerimeter();
}