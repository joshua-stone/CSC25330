package assignment1;

abstract class Shape {
    private int X;
    private int Y;

    public Shape() {
        this(1, 1);
    }
    Shape(final int x, final int y) {
        this.setX(x);
        this.setY(y);
    }
    public void setX(final int x) {
        this.X = x;
    }
    public void setY(final int y) {
        this.Y = y;
    }
}
