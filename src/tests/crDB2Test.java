package tests;

import crDB2.Rectangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

interface DEFAULTS {
    double length = 1.0;
    double width = 1.0;
    double perimeter = 2 * (length + width);
    double area = length * width;
    String output = "Area 1.00, perimeter: 4.00";
}

class crDB2Test {
    // Ensure that the default constructor produces the expected values
    @Test
    void rectangleDefaultValues() {
        Rectangle rect = new Rectangle();

        assertEquals(DEFAULTS.length, rect.getLength());

        assertEquals(DEFAULTS.width, rect.getWidth());

        assertEquals(DEFAULTS.perimeter, rect.getPerimeter());

        assertEquals(DEFAULTS.area, rect.getArea());

        assertEquals(DEFAULTS.output, rect.toString());
    }
    // Show that passing 1.0 for length and width in constructor is equivalent to default constructor
    @Test
    void rectangleSetValues() {
        Rectangle rect = new Rectangle(1.0, 1.0);

        assertEquals(DEFAULTS.length, rect.getLength());

        assertEquals(DEFAULTS.width, rect.getWidth());

        assertEquals(DEFAULTS.perimeter, rect.getPerimeter());

        assertEquals(DEFAULTS.area, rect.getArea());

        assertEquals(DEFAULTS.output, rect.toString());
    }
    // See if using values larger than 20 will set the value to defaults
    @Test
    void rectangleLargeValues() {
        Rectangle rect = new Rectangle(21.0, 21.0);

        assertEquals(DEFAULTS.length, rect.getLength());

        assertEquals(DEFAULTS.width, rect.getWidth());

        assertEquals(DEFAULTS.perimeter, rect.getPerimeter());

        assertEquals(DEFAULTS.area, rect.getArea());

        assertEquals(DEFAULTS.output, rect.toString());
    }
}