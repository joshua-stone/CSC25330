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
   10/05/17     Joshua Stone    Add constructor
   10/05/17     Joshua Stone    Have constructor throw exception
   10/05/17     Joshua Stone    Add a private triangle side test
   10/05/17     Joshua Stone    Call triangle test in constructor
*/

public class TriangleWithException extends Triangle {
    public TriangleWithException(final double side1, final double side2, final double side3) throws IllegalTriangleException {
        this.setSide1(side1);
        this.setSide2(side2);
        this.setSide3(side3);

        if (!isValidTriangle()) {
            throw new IllegalTriangleException();
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
