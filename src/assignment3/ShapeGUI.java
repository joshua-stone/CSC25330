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
    11/18/17     Joshua Stone    Add radio group function for font menu
    11/18/17     Joshua Stone    Use Java 8 lambdas and functional interfaces to reuse menu creation code
    11/18/17     Joshua Stone    Implement custom JMenu class for easier menu creation
*/

package assignment3;

import javax.swing.*;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

interface ButtonGroupAction {
    void setValue(final String value);
}
interface TickboxAction {
    void setValue(final boolean value);
}
class JMenuCustom extends JMenu {
    public JMenuCustom(final String name) {
        super(name);
    }
    public void addRadioButtonGroup(final String defaultValue, final ButtonGroupAction function, final String... items) {
        this.removeAll();
        final ButtonGroup radioButtonGroup = new ButtonGroup();

        for (final String item : items) {
            JRadioButtonMenuItem radioButton = new JRadioButtonMenuItem(item);
            if (item.equals(defaultValue)) {
                radioButton.setSelected(true);
                function.setValue(defaultValue);
            }
            radioButton.addActionListener(event -> function.setValue(item));
            radioButtonGroup.add(radioButton);
            this.add(radioButton);
        }
    }
    public void addCheckBox(final String item, final TickboxAction function, final boolean defaultValue) {
            JCheckBox checkbox = new JCheckBox(item);
            checkbox.addActionListener(event -> function.setValue(checkbox.isSelected()));
            checkbox.setSelected(defaultValue);
            function.setValue(defaultValue);
            this.add(checkbox);
    }
}
public class ShapeGUI extends JFrame {
    private int x1;
    private int y1;
    private String shape;
    private String color;
    private String font;
    private String backgroundColor;
    private boolean bold;
    private JMenuCustom colorMenu;
    private JMenuCustom fontMenu;
    private JMenuCustom backgroundMenu;
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
                System.out.println(String.format("(%s, %s) %s %s %b", e.getX(), e.getY(), backgroundColor, font, bold));
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
        this.colorMenu = new JMenuCustom("Color");
        this.colorMenu.addRadioButtonGroup("Black",
                                           this::setColor,
                                           "Black",
                                           "Blue",
                                           "Red",
                                           "Green");
        this.fontMenu = new JMenuCustom("Font");
        this.fontMenu.addRadioButtonGroup(Font.SERIF,
                                          this::setFont,   // Clicking an item will set the font
                                          Font.SERIF,
                                          Font.MONOSPACED,
                                          Font.SANS_SERIF);
        this.fontMenu.addCheckBox("Bold", this::setBold, true);
        this.backgroundMenu = new JMenuCustom("Background");
        this.backgroundMenu.addRadioButtonGroup("White", this::setBackgroundColor,
                                           "White",
                                           "Cyan",
                                           "Yellow",
                                           "Light_Gray");

        textMenu.add(colorMenu);
        textMenu.addSeparator();
        textMenu.add(fontMenu);
        textMenu.addSeparator();
        textMenu.add(backgroundMenu);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(drawMenu);
        menuBar.add(textMenu);

        this.setJMenuBar(menuBar);
        this.setSize(200, 200);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    private void setBold(final boolean isSelected) {
        this.bold = isSelected;
    }
    private void setColor(final String color) {
        this.color = color;
    }
    private void setFont(final String font) {
        this.font = font;
    }
    private void setBackgroundColor(final String background) {
        this.backgroundColor = background;
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