/*
   Program: Circle.java
   Written by: Joshua Stone
   Description: A class for a circle that shows dimensions including radius, circumference, area, and diameter
   Challenges: Thinking which constructors would make the most sense
   Time Spent: 1 hour

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/21/17     Joshua Stone    Initial commit
   09/21/17     Joshua Stone    Added constructors and data field
   09/21/17     Joshua Stone    Added setters and getters
   09/30/17     Joshua Stone    Implemented getName()
   09/30/17     Joshua Stone    Added more setters in constructors
   10/02/17     Joshua Stone    Add checks for negative values
   10/03/17     Joshua Stone    Static import String.format
*/

package assignment3;

import static java.lang.Math.PI;
import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.String.format;

public class Circle extends GeometricObject {
    // Keep radius private so it can only be accessed through setRadius()
    private double radius;

    // Default constructor
    public Circle() {
        this(1.0, "white", false);
    }
    // Specify radius, but set color to white and filled to false
    public Circle(final double radius) {
        this(radius, "white", false);
    }
    // Constructor which all other constructors may call, setting radius, color, and filled
    public Circle(final double radius, final String color, final boolean filled) {
        this.setRadius(radius);
        this.setColor(color);
        this.setFilled(filled);
    }
    // Set radius with double
    public void setRadius(final double radius) {
        this.radius = abs(radius);
    }
    // Set diameter which actually sets radius
    public void setDiameter(final double diameter) {
        this.setRadius(diameter / 2.0);
    }
    // Get radius
    public double getRadius() {
        return this.radius;
    }
    // Get diameter by doubling radius
    public double getDiameter() {
        return this.getRadius() * 2.0;
    }
    // Get area by calling Pi * radius^2
    public double getArea() {
        return PI * pow(this.getRadius(), 2.0);
    }
    // Get perimeter by multiplying Pi by perimeter
    public double getPerimeter() {
        return this.getDiameter() * PI;
    }
    // Return string of shape name followed by its radius
    public String getName() {
        return format("[Circle] radius = %.1f", this.getRadius());
    }
}