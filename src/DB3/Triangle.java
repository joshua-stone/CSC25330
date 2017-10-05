package DB3;

public class Triangle {
    private double side1;
    private double side2;
    private double side3;

    public Triangle() {
        this(1.0,1.0,1.0);
    }
    public Triangle(final double side1, final double side2, final double side3) {
        this.setSide1(side1);
        this.setSide2(side2);
        this.setSide3(side3);
    }
    public void setSide1(final double side1) {
        this.side1 = side1;
    }
    public void setSide2(final double side2) {
        this.side2 = side2;
    }
    public void setSide3(final double side3) {
        this.side3 = side3;
    }
    public double getSide1() {
        return this.side1;
    }
    public double getSide2() {
        return this.side2;
    }
    public double getSide3() {
        return this.side3;
    }
}
