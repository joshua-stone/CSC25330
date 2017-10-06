package DB3;

/*
   Program: IllegalTriangleException.java
   Written by: Joshua Stone
   Description: Custom exception for Triangle class
   Challenges:
   Time Spent: 30 minutes

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/05/17     Joshua Stone    Initial commit
   10/05/17     Joshua Stone    Extend Exception for custom exception for Triangle
   10/05/17     Joshua Stone    Add a constructor for IllegalTriangleException to take a Triangle argument
*/

import static java.lang.String.format;

public class IllegalTriangleException extends Exception {
    // Default constructor that simply states a triangle is illegal
    public IllegalTriangleException() {
        this("Illegal Triangle");
    }
    // Option to pass a Triangle and output its sides
    public IllegalTriangleException(final Triangle triangle) {
        this(format("Illegal Triangle:\n" +
                    "Side1: %.1f\n" +
                    "Side2: %.1f\n" +
                    "Side3: %.1f", triangle.getSide1(), triangle.getSide2(), triangle.getSide3()));
    }
    // Even though we can can super() for the previous constructors, having the
    // option to pass strings provides some flexibility
    public IllegalTriangleException(final String message) {
        super(message);
    }
}
