package DB3;

/*
   Program: TriangleExceptionTest.java
   Written by: Joshua Stone
   Description: Triangle tester class that shows that the exception works
   Challenges:
   Time Spent:

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/05/17     Joshua Stone    Initial commit
*/

public class TriangleExceptionTest {
    public static void main(final String args[]) {

        try {
            final TriangleWithException t1 = new TriangleWithException(1.5, 2.0, 3.0);
            final TriangleWithException t2 = new TriangleWithException(1, 2.0, 3.0);
        } catch (IllegalTriangleException e) {
            System.out.println(e.getMessage());
        }
    }
}
