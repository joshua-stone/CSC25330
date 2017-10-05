package DB3;

/*
   Program: Triangle.java
   Written by: Joshua Stone
   Description: Triangle class that has three sides and returns an area or perimeter
   Challenges:
   Time Spent:

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/21/17     Joshua Stone    Initial commit
   09/21/17     Joshua Stone    Added constructors
   09/21/17     Joshua Stone    Add setters and getters
*/

import static java.lang.Math.sqrt;

public class Triangle extends GeometricObject {
    private double side1;
    private double side2;
    private double side3;

    public Triangle() {
        this(1.0,1.0,1.0);
    }
    public Triangle(final double side1, final double side2, final double side3) {
        this.setSide1(side1);
        this.setSide2(side2);
        this.setSide3(side3);
    }
    public void setSide1(final double side1) {
        this.side1 = side1;
    }
    public void setSide2(final double side2) {
        this.side2 = side2;
    }
    public void setSide3(final double side3) {
        this.side3 = side3;
    }
    public double getSide1() {
        return this.side1;
    }
    public double getSide2() {
        return this.side2;
    }
    public double getSide3() {
        return this.side3;
    }
    public double getArea() {
        final double s = this.getPerimeter() / 2;
        final double area = sqrt(s * (s - side1) * (s - side2) * (s - side3));

        return area;
    }
    public double getPerimeter() {
        return this.side1 + this.side2 + this.side3;
    }
}
