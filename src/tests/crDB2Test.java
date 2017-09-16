package tests;

import crDB2.Rectangle;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

interface DEFAULTS {
    double length = 2.0;
    double width = 1.0;
}

class crDB2Test {
    @Test
    public void rectangleDefaultValues() {
        Rectangle rect = new Rectangle();
        assertEquals(DEFAULTS.length, rect.getLength());

    }
}