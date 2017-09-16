package tests;

import crDB2.Rectangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

interface Defaults {
    double LENGTH = 1.0;
    double WIDTH = 1.0;
    double PERIMETER = 2 * (LENGTH + WIDTH);
    double AREA = LENGTH * WIDTH;
    String OUTPUT = "Area 1.00, perimeter: 4.00";
}

class crDB2Test {
    // Ensure that the default constructor produces the expected values
    @Test
    void rectangleDefaultValues() {
        this.defaultValueTest(new Rectangle());
    }
    // Show that passing 1.0 for length and width in constructor is equivalent to default constructor
    @Test
    void rectangleSetValues() {
        this.defaultValueTest(new Rectangle(1.0, 1.0));
    }
    // See if using values larger than 20 will set the value to defaults
    @Test
    void rectangleLargeValues() {
        this.defaultValueTest(new Rectangle(21.0, 21.0));

        this.defaultValueTest(new Rectangle(21.0, 1.0));

        this.defaultValueTest(new Rectangle(1.0, 21.0));
    }
    void defaultValueTest(Rectangle rect) {
        assertEquals(Defaults.LENGTH, rect.getLength());

        assertEquals(Defaults.WIDTH, rect.getWidth());

        assertEquals(Defaults.PERIMETER, rect.getPerimeter());

        assertEquals(Defaults.AREA, rect.getArea());

        assertEquals(Defaults.OUTPUT, rect.toString());
    }
}