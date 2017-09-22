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
   09/21/17     Joshua Stone    Added constructors
   09/21/17     Joshua Stone    Added setters and getter
   09/21/17     Joshua Stone    Initial compareTo implementation
   09/21/17     Joshua Stone    Added max()
*/

package assignment1;

import org.jetbrains.annotations.NotNull;

import java.util.Date;

public class GeometricObject extends Shape implements Comparable<GeometricObject> {
    private String color;
    private boolean filled;
    private Date dateCreated;

    public GeometricObject() {

    }
    public GeometricObject(final String color, final boolean filled) {

    }
    public void setColor(final String color) {
        this.color = color;
    }
    public void setFilled(final boolean filled) {
        this.filled = filled;
    }
    public String getColor() {
        return this.color;
    }
    public boolean isFilled() {
        return this.filled;
    }
    public Date getDateCreated() {
        return this.dateCreated;
    }
    @Override
    public double getArea() {
        return 0;
    }
    @Override
    public double getPerimeter() {
        return 0;
    }
    @Override
    public String getName() {
        return "";
    }
    public static GeometricObject max(final GeometricObject o1, final GeometricObject o2) {
        final GeometricObject max;
        if (o1.compareTo(o2) > 0) {
            max = o1;
        } else {
            max = o2;
        }
        return max;
    }
    @Override
    public int compareTo(GeometricObject o) {
        return 0;
    }
}
