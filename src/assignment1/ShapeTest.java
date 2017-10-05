package assignment1;
import java.util.ArrayList;


// FileName: ShapeTest.java
// Program tests the Shape hierarchy.
//created by: Sylvia Yeung

public class ShapeTest {
   // create Shape objects and display their information
    public static void main(String args[]) {
    //add four objects 
        ArrayList<Shape> shapes = new ArrayList<>();
        Circle circle1 = new Circle(3.0);
        Circle circle2 = new Circle( 6.0, "RED", true);
        Rectangle rect1 = new Rectangle( 71, 96 );
        Rectangle rect2 = new Rectangle(  );

        shapes.add(circle1);
        shapes.add(circle2);
        shapes.add(rect1);
        shapes.add(rect2);
       
    // call method print on all shapes
        for (Shape currentShape : shapes) {
            System.out.printf( "%s: %s", currentShape.getName(), currentShape);
            if (currentShape instanceof GeometricObject) {
            //  GeometricObject circleShape =
            //   ( Circle) currentShape;
                System.out.printf( "%s's area is %.2f\n", currentShape.getName(), currentShape.getArea());
                System.out.printf( "%s's Perimeter is %.2f\n", currentShape.getName(), currentShape.getPerimeter());
                System.out.println("--------------------------------------------");
            } // end if
        } // end for
    
        // Display the max circle
        Circle circle = (Circle) GeometricObject.max(circle1, circle2);
        System.out.println("The max circle's radius is " + circle.getRadius());
        System.out.println("--------------------------------------------");
        // Display the max rectangle
        Rectangle rect = (Rectangle) GeometricObject.max(rect1, rect2);
        System.out.println("The max rectangle's width is " + rect.getWidth() + " and height is " + rect.getHeight());
    } // end main
} // end class ShapeTest