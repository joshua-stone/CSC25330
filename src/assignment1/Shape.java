/*
   Program: Shape.java
   Written by: Joshua Stone
   Description:
   Challenges:
   Time Spent:

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/21/17     Joshua Stone    Initial commit
   09/21/17     Joshua Stone    Added setters, getters, and constructors
   09/21/17     Joshua Stone    Added abstract methods for subclasses
*/

package assignment1;

abstract class Shape {
    private int X;
    private int Y;

    public Shape() {
        this(1, 1);
    }
    public Shape(final int x, final int y) {
        this.setX(x);
        this.setY(y);
    }
    public void setX(final int x) {
        this.X = x;
    }
    public void setY(final int y) {
        this.Y = y;
    }
    public int getX() {
        return this.X;
    }
    public int getY() {
        return this.Y;
    }
    public abstract String getName();
    public abstract double getArea();
    public abstract double getPerimeter();
}
