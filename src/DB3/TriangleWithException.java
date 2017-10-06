package DB3;

/*
   Program: TriangleWithException.java
   Written by: Joshua Stone
   Description: Triangle class with has a built in exception that throws if it violates the side test
   Challenges: Finding a sane
   Time Spent: 30 minutes

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/05/17     Joshua Stone    Initial commit
   10/05/17     Joshua Stone    Create constructors and fields
   10/05/17     Joshua Stone    Implement isValidTriangle()
   10/05/17     Joshua Stone    Have constructor throw exception
*/


public class TriangleWithException extends Triangle {
    // Calling this constructor means the programmer must use exception handling
    public TriangleWithException(final double side1, final double side2, final double side3) throws IllegalTriangleException {
        this.setSide1(side1);
        this.setSide2(side2);
        this.setSide3(side3);

        // Throw an exception if the triangle fails the side test
        if (!isValidTriangle()) {
            throw new IllegalTriangleException(this);
        }
    }
    // Private method to separate triangle side test logic from the constructor
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
