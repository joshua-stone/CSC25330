/*
    Program: ShapeGUI.java
    Written by: Joshua Stone
    Description:
    Challenges:
    Time Spent:

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    11/17/17     Joshua Stone    Initial commit
    11/17/17     Joshua Stone    Add mouse listener
    11/17/17     Joshua Stone    Add initial menus
*/

package assignment3;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ShapeGUI extends JFrame {
    private int x1;
    private int y1;
    private String shape;

    public ShapeGUI() {
        this.shape = "circle";

        this.setTitle("Drawing shapes and Displaying All Info");
        this.setVisible(true);
        this.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                setX1(event.getX());
                setY1(event.getY());
            }
            public void mouseReleased(MouseEvent e) {
                System.out.println(String.format("(%s, %s) %s", e.getX(), e.getY(), shape));

            }
        });
        JMenu drawMenu = new JMenu("Draw");
        JMenu shapeMenu = new JMenu("Shape");
        JMenuItem circle = new JMenuItem("Circle");
        JMenuItem rectangle = new JMenuItem("Rectangle");
        JMenuItem square = new JMenuItem("Square");

        circle.addActionListener(event -> this.setShape("circle"));
        rectangle.addActionListener(event -> this.setShape("rectangle"));
        square.addActionListener(event -> this.setShape("square"));

        shapeMenu.add(circle);
        shapeMenu.add(rectangle);
        shapeMenu.add(square);

        drawMenu.add(shapeMenu);

        JMenu textMenu = new JMenu("Text");
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(drawMenu);
        menuBar.add(textMenu);
        this.setJMenuBar(menuBar);
        this.setSize(200, 200);
        this.setLocationRelativeTo(null);
    }
    private void setX1(final int x1) {
        this.x1 = x1;
    }
    private void setY1(final int y1) {
        this.y1 = y1;
    }
    private void setShape(final String shape) {
        this.shape = shape;
    }
    public static void main(final String[] args) {
        ShapeGUI shapeGUI = new ShapeGUI();
    }
}
