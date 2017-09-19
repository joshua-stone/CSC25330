package DB2;

/*
   Program: Rectangle.java
   Written by: Joshua Stone
   Description: Rectangle class that takes length and width as arguments and returns information like area and perimeter
   Challenges: Writing constructor and setter logic that's reusable
   Time Spent: 1 hour
   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/13/17     Joshua Stone    Initial commit
   09/13/17     Joshua Stone    Write length and width attributes
   09/13/17     Joshua Stone    Make Rectangle() default constructor
   09/13/17     Joshua Stone    Add overloading for Rectangle()
   09/13/17     Joshua Stone    Add setters and getters for length and width
   09/13/17     Joshua Stone    Include getArea(), getPerimeter(), and toString()
   09/13/17     Joshua Stone    Add constraints for setLength() and setWidth()
   09/13/17     Joshua Stone    Override toString() and add string formatting for perimeter and area
   09/13/17     Joshua Stone    Add helper method for setLength() and setWidth()
   09/15/17     Joshua Stone    Make interfaces public so they can be used outside package
*/

public class Rectangle {
    // Keep attributes private so they are only accessible through Rectangle()'s setters and getters
    private double length;
    private double width;

    // Default constructor sets length and width to 1.0
    public Rectangle() {
        this(1.0, 1.0);
    }
    // Let rectangle length and width be defined at instantiation
    public Rectangle(final double length, final double width) {
        this.setLength(length);
        this.setWidth(width);
    }
    // Set length to either a number between 0.0 and 20.0, or 1.0
    public void setLength(final double length) {
        this.length = this.distanceHelper(length);
    }
    // Set width to either a number between 0.0 and 20.0, or 1.0
    public void setWidth(final double width) {
        this.width = this.distanceHelper(width);
    }
    // Private helper method to share common logic between setLength() and setWidth()
    private double distanceHelper(final double distance) {
        final double newDistance;

        if (distance > 0.0 && distance < 20.0) {
            newDistance = distance;
        } else {
            newDistance = 1.0;
        }
        return newDistance;
    }
    // Return length
    public double getLength() {
        return this.length;
    }
    // Return width
    public double getWidth() {
        return this.width;
    }
    // Calculate area by multiplying length and width
    public double getArea() {
        return this.getLength() * this.getWidth();
    }
    // Calculate perimeter by doubling the sum of length and width
    public double getPerimeter() {
        return 2 * (this.getLength() + this.getWidth());
    }
    // Override toString() so information can be printed implicitly. Use formatting for two decimal places
    @Override
    public String toString() {
        return String.format("Area %.2f, perimeter: %.2f", this.getArea(), this.getPerimeter());
    }
}