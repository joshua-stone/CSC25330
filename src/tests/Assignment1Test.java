package tests;

import assignment1.Circle;
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
    @Test
    void CircleDefaultValues() {
        Circle circle = new Circle();
        CircleTest circleTest = new CircleTest();
        assertEquals(circle.getRadius(), circleTest.RADIUS);
        assertEquals(circle.getName(), circleTest.NAME);
        assertEquals(circle.getArea(), circleTest.AREA);
        assertEquals(circle.getPerimeter(), circleTest.PERIMETER);
    }
}
