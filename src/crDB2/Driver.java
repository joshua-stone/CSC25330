package crDB2;

/*
   Revision History:
   Date:        By:             Action:
   ---------------------------------------------------
   09/13/17     Joshua Stone    Initial commit
   09/13/17     Joshua Stone    Make Main() class
   09/13/17     Joshua Stone    Use Rectangle() class
   09/13/17     Joshua Stone    Use Scanner() for input
   09/13/17     Joshua Stone    Parse doubles from scanner for setting length and width of rectangle
   09/13/17     Joshua Stone    Display results for perimeter and area
*/

import java.util.Scanner;

public class Driver {
    public static void main(String[] args) {
        // Scanner() is used for parsing user input
        Scanner input = new Scanner(System.in);
        // Instantiate a Rectangle with default arguments
        Rectangle rectangle = new Rectangle();

        // Ask for length, and pass the result into rectangle.setLength()
        System.out.print("Enter length of rectangle: ");
        rectangle.setLength(input.nextDouble());
        System.out.println();

        // Ask for width, and pass the result into rectangle.setWidth()
        System.out.print("Enter width of rectangle: ");
        rectangle.setWidth(input.nextDouble());
        System.out.println();

        // toString() is called implicitly; should return "Area: <number>, perimeter: <number>"
        System.out.println(rectangle);
    }
}