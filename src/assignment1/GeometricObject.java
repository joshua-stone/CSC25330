/*
   Program: GeometricObject.java
   Written by: Joshua Stone
   Description: A more specialized abstract class of a geometric object that includes an instantiation timestamp
   Challenges: Making compareTo and max fit together to produce the expected results
   Time Spent: 2 hours

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/21/17     Joshua Stone    Initial commit
   09/21/17     Joshua Stone    Added constructors
   09/21/17     Joshua Stone    Added setters and getter
   09/21/17     Joshua Stone    Initial compareTo() implementation
   09/21/17     Joshua Stone    Added max()
   09/30/17     Joshua Stone    Use SimpleDateFormat for date formatting
   09/30/17     Joshua Stone    Complete compareTo() implementation
   10/03/17     Joshua Stone    Static import String.format
*/

package assignment1;

import java.util.Date;
import java.text.SimpleDateFormat;

import static java.lang.String.format;

public abstract class GeometricObject extends Shape implements Comparable<GeometricObject> {
    // Keep fields private for encapsulation
    private String color;
    private boolean filled;
    private final Date dateCreated;

    // Default constructor
    public GeometricObject() {
        this(1, 1, "white", false);
    }
    // Constructor where a color and fill field can be specified
    public GeometricObject(final String color, final boolean filled) {
        this(1, 1, color, filled);
    }
    // Constructor which all other contructors may call
    public GeometricObject(final int x, final int y, final String color, final boolean filled) {
        this.setX(x);
        this.setY(y);
        this.setColor(color);
        this.setFilled(filled);
        // Would've used a private method like setDate(), but spec doesn't specify whether it's allowed
        this.dateCreated = new Date();
    }
    // Set color value with string
    public void setColor(final String color) {
        this.color = color;
    }
    // Set filled value with a boolean
    public void setFilled(final boolean filled) {
        this.filled = filled;
    }
    // Return color string
    public String getColor() {
        return this.color;
    }
    // Return fill boolean
    public boolean isFilled() {
        return this.filled;
    }
    // Return timestamp as Date()
    public Date getDateCreated() {
        return this.dateCreated;
    }
    // Returns timestamp, color, and whether shape is filled
    @Override
    public String toString() {
        // weekday month day hour:minute:second timezone year
        final String datePattern = "E MMM dd HH:mm:ss z yyyy";
        // SimpleDateFormat appears to be the easiest way to parse and format Date()
        final String date = new SimpleDateFormat(datePattern).format(this.getDateCreated());

        return format("created on %s\ncolor: %s and filled %b\n\n", date, this.getColor(), this.isFilled());
    }
    // Returns first object if its area is larger than the second one, or else return the second object
    public static GeometricObject max(final GeometricObject o1, final GeometricObject o2) {
        final GeometricObject max;

        if (o1.compareTo(o2) > 0) {
            max = o1;
        } else {
            max = o2;
        }
        return max;
    }
    // Returns 1 if object's area is greater than the argument's, -1 if object is smaller than the
    // argument, or 0 if they're equal
    @Override
    public int compareTo(final GeometricObject o) {
        final int result;

        if(this.getArea() > o.getArea()) {
            result = 1;
        } else if (this.getArea() < o.getArea()) {
            result = -1;
        } else {
            result = 0;
        }
        return result;
    }
}
