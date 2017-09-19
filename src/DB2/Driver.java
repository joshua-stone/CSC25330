package DB2;

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
   09/18/17     Joshua Stone    Implement more robust input parsing
*/

import java.util.Scanner;

public class Driver {
    public static void main(final String[] args) {
        // Ask for rectangle length
        final double length = getInput("Enter length of rectangle: ");
        // Ask for rectangle width
        final double width = getInput("Enter width of rectangle: ");

        final Rectangle rect = new Rectangle(length, width);
        // toString() is called implicitly; should return "Area: <number>, perimeter: <number>"
        System.out.println(rect);
    }
    // Helper method for reusing input parsing logic
    private static double getInput(final String inputText) {
        final Scanner input = new Scanner(System.in);
        double nextNumber;

        while (true) {
            try {
                System.out.print(inputText);
                nextNumber = Double.parseDouble(input.nextLine());
                System.out.println();
                break;
            } catch (NumberFormatException e) {
                System.out.println("Incorrect Input. Try again.");
            }
        }
        return nextNumber;
    }
}