package DB3;

/*
   Program: IllegalTriangleException.java
   Written by: Joshua Stone
   Description: Custom exception for Triangle class
   Challenges: Making a clean custom exception design
   Time Spent: 30 minutes

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/05/17     Joshua Stone    Initial commit
   10/05/17     Joshua Stone    Extend Exception for custom exception for Triangle
   10/05/17     Joshua Stone    Add a constructor for IllegalTriangleException to take a Triangle argument
*/

public class IllegalTriangleException extends Exception {
    // Default constructor that simply states a triangle is illegal
    public IllegalTriangleException() {
        this("Illegal Triangle");
    }
    // Option to pass a Triangle and output its sides
    public IllegalTriangleException(final Triangle triangle) {
        this("Illegal Triangle:\n" +
             "Side1: " + triangle.getSide1() + "\n" +
             "Side2: " + triangle.getSide2() + "\n" +
             "Side3: " + triangle.getSide3() + "\n");
    }
    // Even though we can use super() for the previous constructors, having the
    // option to pass strings provides some flexibility
    public IllegalTriangleException(final String message) {
        super(message);
    }
}
