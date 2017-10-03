package tests;

import assignment1.Circle;
import assignment1.GeometricObject;
import assignment1.Rectangle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static java.lang.Math.PI;
import static java.lang.Math.pow;
import static java.lang.String.format;

interface CircleDefaults {
    double RADIUS = 1.0;
    double DIAMETER = RADIUS * 2.0;
    double PERIMETER = DIAMETER * PI;
    double AREA = PI * pow(RADIUS, 2.0);
    String COLOR = "white";
    boolean FILLED = false;
    String NAME = String.format("[Circle] radius = %.1f", RADIUS);
}
interface RectangleDefaults {
    double WIDTH = 10.0;
    double HEIGHT = 5.0;
    double AREA = WIDTH * HEIGHT;
    double PERIMETER = 2 * (WIDTH + HEIGHT);
    String COLOR = "white";
    boolean FILLED = false;
    String NAME = format("[Rectangle] width = %.1f and height = %.1f", WIDTH, HEIGHT);
}
class CircleTest {
    final double RADIUS;
    final double DIAMETER;
    final double PERIMETER;
    final double AREA;
    final String COLOR;
    final boolean FILLED;
    final String NAME;

    CircleTest() {
        this(CircleDefaults.RADIUS, CircleDefaults.COLOR, CircleDefaults.FILLED);
    }
    CircleTest(final double radius) {
        this(radius, CircleDefaults.COLOR, CircleDefaults.FILLED);
    }
    CircleTest(final double radius, final String color, final boolean filled) {
        this.RADIUS = radius;
        this.DIAMETER = this.RADIUS * 2.0;
        this.PERIMETER = this.DIAMETER * PI;
        this.AREA = PI * pow(this.RADIUS, 2.0);
        this.COLOR = color;
        this.FILLED = filled;
        this.NAME = format("[Circle] radius = %.1f", this.RADIUS);
    }
}
class RectangleTest {
    final double WIDTH;
    final double HEIGHT;
    final double AREA;
    final double PERIMETER;
    final String COLOR;
    final boolean FILLED;
    final String NAME;

    RectangleTest() {
        this(RectangleDefaults.WIDTH, RectangleDefaults.HEIGHT);
    }
    RectangleTest(final double width, final double height) {
        this.WIDTH = width;
        this.HEIGHT = height;
        this.AREA = this.WIDTH * this.HEIGHT;
        this.PERIMETER = 2 * (this.WIDTH + this.HEIGHT);
        this.COLOR = "white";
        this.FILLED = false;
        this.NAME = format("[Rectangle] width = %.1f and height = %.1f", this.WIDTH, this.HEIGHT);
    }
}
public class Assignment1Test {
    final Circle circle1 = new Circle(3.0);
    final Circle circle2 = new Circle(6.0, "RED", true);
    final Rectangle rect1 = new Rectangle(71, 96);
    final Rectangle rect2 = new Rectangle();
    @Test
    void circleDefaultValues() {
        compareCircles(new Circle(), new CircleTest());
    }
    @Test
    void circle1Values() {
        compareCircles(circle1, new CircleTest(circle1.getRadius()));
    }
    @Test
    void circle2Values() {
        compareCircles(circle2, new CircleTest(circle2.getRadius(), circle2.getColor(), circle2.isFilled()));
    }
    @Test
    void circleMax() {
        assertEquals(GeometricObject.max(circle1, circle2), circle2);
    }
    @Test
    void rectangleDefaultValues() {
        compareRectangles(new Rectangle(), new RectangleTest());
    }
    @Test
    void rect1Values() {
        compareRectangles(rect1, new RectangleTest(rect1.getWidth(), rect1.getHeight()));
    }
    @Test
    void rect2Values() {
        compareRectangles(rect2, new RectangleTest());
    }
    @Test
    void rectangleMax() {
        assertEquals(GeometricObject.max(rect1, rect2), rect1);
    }
    void compareCircles(Circle circle, CircleTest circleTest) {
        assertTrue(circle instanceof GeometricObject);
        assertEquals(circle.getRadius(), circleTest.RADIUS);
        assertEquals(circle.getDiameter(), circleTest.DIAMETER);
        assertEquals(circle.getPerimeter(), circleTest.PERIMETER);
        assertEquals(circle.getArea(), circleTest.AREA);
        assertEquals(circle.getColor(), circleTest.COLOR);
        assertEquals(circle.isFilled(), circleTest.FILLED);
        assertEquals(circle.getName(), circleTest.NAME);
    }
    void compareRectangles(Rectangle rectangle, RectangleTest rectangleTest) {
        assertTrue(rectangle instanceof GeometricObject);
        assertEquals(rectangle.getWidth(), rectangleTest.WIDTH);
        assertEquals(rectangle.getHeight(), rectangleTest.HEIGHT);
        assertEquals(rectangle.getArea(), rectangleTest.AREA);
        assertEquals(rectangle.getPerimeter(), rectangleTest.PERIMETER);
        assertEquals(rectangle.getColor(), rectangleTest.COLOR);
        assertEquals(rectangle.isFilled(), rectangleTest.FILLED);
        assertEquals(rectangle.getName(), rectangleTest.NAME);
    }
}