package DB3;

/*
   Program: TriangleExceptionTest.java
   Written by: Joshua Stone
   Description: Triangle tester class that shows that the exception works
   Challenges:
   Time Spent: 15 minutes

   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   10/05/17     Joshua Stone    Initial commit
   10/05/17     Joshua Stone    Add t1 and t2
   10/05/17     Joshua Stone    Put in try block
   10/05/17     Joshua Stone    Catch IllegalTriangleException
*/

public class TriangleExceptionTest {
    public static void main(final String args[]) {
        try {
            final TriangleWithException t1 = new TriangleWithException(1.5, 2.0, 3.0);

            System.out.println("Perimeter for t1: " + t1.getPerimeter());
            System.out.println("Area for t1: " + t1.getArea());

            // This should throw an IllegalTriangleException
            final TriangleWithException t2 = new TriangleWithException(1.0, 2.0, 3.0);

            System.out.println("Perimeter for t1: " + t1.getPerimeter());
            System.out.println("Area for t1: " + t1.getArea());
        } catch (IllegalTriangleException error) {
            System.out.println(error.getMessage());
        }

    }
}
