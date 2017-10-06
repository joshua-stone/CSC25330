package DB3;

/*
   Program: IllegalTriangleException.java
   Written by: Joshua Stone
   Description: Custom exception for Triangle class
   Challenges:
   Time Spent:

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/05/17     Joshua Stone    Initial commit
   10/05/17     Joshua Stone    Extend Exception for custom exception for Triangle
*/

import static java.lang.String.format;

public class IllegalTriangleException extends Exception {
    public IllegalTriangleException() {
        this("Illegal Triangle");
    }
    public IllegalTriangleException(final String message) {
        super(message);
    }
    public IllegalTriangleException(final Triangle triangle) {
        this(format("Illegal Triangle:\n" +
                    "Side1: %.1f\n" +
                    "Side2: %.1f\n" +
                    "Side3: %.1f", triangle.getSide1(), triangle.getSide2(), triangle.getSide3()));
    }
}
