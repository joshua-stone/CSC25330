package tests;

import assignment1.Circle;
import assignment1.GeometricObject;
import assignment1.Rectangle;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import static java.lang.Math.PI;
import static java.lang.Math.pow;

interface CircleDefaults {
    double RADIUS = 1.0;
    double DIAMETER = RADIUS * 2.0;
    double PERIMETER = DIAMETER * PI;
    double AREA = PI * pow(RADIUS, 2.0);
    String COLOR = "white";
    boolean FILLED = false;
    String NAME = String.format("[Circle] radius = %.1f", RADIUS);
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
        this.NAME = String.format("[Circle] radius = %.1f", this.RADIUS);
    }
}
public class Assignment1Test {
    final Circle circle1 = new Circle(3.0);
    final Circle circle2 = new Circle(6.0, "RED", true);
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
    void CircleMax() {
        assertEquals(GeometricObject.max(circle1, circle2), circle2);
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
}
