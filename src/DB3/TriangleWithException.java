package DB3;

/*
   Program: TriangleWithException.java
   Written by: Joshua Stone
   Description: Triangle class with has a built in exception that throws if it violates the side test
   Challenges:
   Time Spent:

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/05/17     Joshua Stone    Initial commit
   10/05/17     Joshua Stone
   10/05/17     Joshua Stone
   10/05/17     Joshua Stone
*/

import static java.lang.String.format;

public class TriangleWithException extends Triangle {
    public TriangleWithException(final double side1, final double side2, final double side3) throws IllegalTriangleException {
        this.setSide1(side1);
        this.setSide2(side2);
        this.setSide3(side3);

        if (!isValidTriangle()) {
            throw new IllegalTriangleException(this);
        }
    }
    private boolean isValidTriangle() {
        final boolean result;

        if (this.getSide1() + this.getSide2() > this.getSide3() &&
            this.getSide1() + this.getSide3() > this.getSide2() &&
            this.getSide2() + this.getSide3() > this.getSide1()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }
}
