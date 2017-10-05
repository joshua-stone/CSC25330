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

public class IllegalTriangleException extends Exception {
    public IllegalTriangleException() {}

    public IllegalTriangleException(String message) {
        super(message);
    }
}
