package DB3;

/*
   Program: Triangle.java
   Written by: Joshua Stone
   Description: Triangle class that has three sides and returns an area or perimeter
   Challenges: None
   Time Spent: 45 minutes

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/05/17     Joshua Stone    Initial commit
   10/05/17     Joshua Stone    Added constructors
   10/05/17     Joshua Stone    Add setters and getters
   10/05/17     Joshua Stone    Add getPerimeter() and getArea()
   10/05/17     Joshua Stone    Add getName()
   10/07/17     Joshua Stone    Remove setters since they're not needed
*/

import static java.lang.Math.sqrt;
import static java.lang.String.format;

public class Triangle extends GeometricObject {
    // Since no setters are present, these fields can be final, making the Triangle immutable
    private final double side1;
    private final double side2;
    private final double side3;

    // Default constructor sets all sides to 1.0
    public Triangle() {
        this(1.0, 1.0, 1.0);
    }
    // Each of the three sides can be set at instantiation
    public Triangle(final double side1, final double side2, final double side3) {
        this.side1 = side1;
        this.side2 = side2;
        this.side3 = side3;
    }
    // Get side 1
    public double getSide1() {
        return this.side1;
    }
    // Get side 2
    public double getSide2() {
        return this.side2;
    }
    // Get side 3
    public double getSide3() {
        return this.side3;
    }
    // Add all three sides to get the perimeter
    public double getPerimeter() {
        return this.getSide1() + this.getSide2() + this.getSide3();
    }
    // Calculate Triangle area using its semiperimeter and Heron's formula
    public double getArea() {
        final double s = this.getPerimeter() / 2;

        return sqrt(s * (s - this.getSide1()) * (s - this.getSide2()) * (s - this.getSide3()));
    }
    // Print out all three side lengths of Triangle
    public String getName() {
        return format("Triangle: side1 = %.1f side2 = %.1f side3 = %.1f", this.getSide1(), this.getSide2(), this.getSide3());
    }
}
