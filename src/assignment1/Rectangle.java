/*
   Program: Rectangle.java
   Written by: Joshua Stone
   Description: A rectangle class with height, width, area, perimeter
   Challenges: Thinking which constructors would make the most sense
   Time Spent: 1 hour

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/21/17     Joshua Stone    Initial commit
   09/21/17     Joshua Stone    Added constructors
   09/21/17     Joshua Stone    Added data fields
   09/21/17     Joshua Stone    Added setters and getters
   09/30/17     Joshua Stone    Implemented getName()
   09/30/17     Joshua Stone    Added more setters in contructors
   10/02/17     Joshua Stone    Add check for negative values
   10/03/17     Joshua Stone    Static import String.format
*/

package assignment1;

import static java.lang.Math.abs;
import static java.lang.String.format;

public class Rectangle extends GeometricObject {
    private double width;
    private double height;

    // Default constructor
    public Rectangle() {
        this(10.0, 5.0);
    }
    // Constructor which may set width and height
    public Rectangle(final double width, final double height) {
        this.setWidth(width);
        this.setHeight(height);
        this.setColor("white");
        this.setFilled(false);
    }
    // Set rectangle width
    public void setWidth(final double width) {
        this.width = abs(width);
    }
    // Set rectangle height
    public void setHeight(final double height) {
        this.height = abs(height);
    }
    // Return rectangle width
    public double getWidth() {
        return this.width;
    }
    // Return rectangle width
    public double getHeight() {
        return this.height;
    }
    // Calculat rectangle area with height and width
    public double getArea() {
        return this.getWidth() * this.getHeight();
    }
    // Add two sides and multiply to get perimeter
    public double getPerimeter() {
        return 2 * (this.getHeight() + this.getWidth());
    }
    // Return string with the name of the shape followed by width and height
    public String getName() {
        return format("[Rectangle] width = %.1f and height = %.1f", this.getWidth(), this.getHeight());
    }
}
