package crDB2;

/*
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
*/

public class Rectangle {
    // Keep attributes private so they are only accessible through Rectangle()'s setters and getters
    private double length;
    private double width;

    // Default constructor sets length and width to 1.0
    Rectangle() {
        this(1.0, 1.0);
    }
    // Let rectangle length and width be defined at instantiation
    Rectangle(final double length, final double width) {
        this.setLength(length);
        this.setWidth(width);
    }
    // Set length to either a number between 0.0 and 20.0, or 1.0
    void setLength(final double length) {
        this.length = this.distanceHelper(length);
    }
    // Set width to either a number between 0.0 and 20.0, or 1.0
    void setWidth(final double width) {
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
    double getLength() {
        return this.length;
    }
    // Return width
    double getWidth() {
        return this.width;
    }
    // Calculate area by multiplying length and width
    double getArea() {
        return this.getLength() * this.getWidth();
    }
    // Calculate perimeter by doubling the sum of length and width
    double getPerimeter() {
        return 2 * (this.getLength() + this.getWidth());
    }
    // Override toString() so information can be printed implicitly. Use formatting for two decimal places
    @Override
    public String toString() {
        super.toString();
        return String.format("Area %.2f, perimeter: %.2f", this.getArea(), this.getPerimeter());
    }
}