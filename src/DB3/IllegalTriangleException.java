package DB3;

/*
   Program: Triangle.java
   Written by: Joshua Stone
   Description: Custom exception for Triangle class
   Challenges:
   Time Spent:

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/21/17     Joshua Stone    Initial commit
*/

public class IllegalTriangleException extends Exception {
    public IllegalTriangleException() {}

    public IllegalTriangleException(String message) {
        super(message);
    }
}
