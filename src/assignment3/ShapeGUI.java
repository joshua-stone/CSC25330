/*
    Program: ShapeGUI.java
    Written by: Joshua Stone
    Description: A program that takes mouse clicks for input to display shapes and info on them, as well as setting colors
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
    11/25/17     Joshua Stone    Rewrite functions for better argument ordering
    11/25/17     Joshua Stone    Implement text placement with foreground, background, and font setting
    11/25/17     Joshua Stone    Implement JComboBox shape options
    11/25/17     Joshua Stone    Implement Circle and Rectangle for drawing
    11/25/17     Joshua Stone    Remove unneeded instance variables
    11/25/17     Joshua Stone    Create methods for returning top-left and bottom-right coordinates
    11/25/17     Joshua Stone    Made graphics drawing account for mouse release coordinates being set at different positions
*/

package assignment3;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import assignment1.Circle;
import assignment1.Rectangle;

import static java.lang.String.format;
import static java.lang.Math.sqrt;
import static javax.swing.JColorChooser.showDialog;

interface SetString {
    void setValue(final String value);
}
interface SetBool {
    void setValue(final boolean value);
}
class JMenuCustom extends JMenu {
    public JMenuCustom(final String name) {
        super(name);
    }
    public void addMenuItem(final String item, final SetString function) {
        JMenuItem menuItem = new JMenuItem(item);
        menuItem.addActionListener(event -> function.setValue(item));
        this.add(menuItem);
    }
    public void addRadioButtonGroup(final String[] items, final SetString function, final String defaultValue) {
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
    public void addCheckBox(final String item, final SetBool function, final boolean defaultValue) {
            JCheckBox checkbox = new JCheckBox(item);
            checkbox.addActionListener(event -> function.setValue(checkbox.isSelected()));
            checkbox.setSelected(defaultValue);
            function.setValue(defaultValue);
            this.add(checkbox);
    }
}
public class ShapeGUI extends JFrame {
    private int mousePressedX;
    private int mousePressedY;
    private int mouseReleasedX;
    private int mouseReleasedY;
    private final String[] shapeOptions;
    private Color textColor;
    private String fontName;
    private Color shapeColor;
    private boolean isBold;
    private boolean isItalic;
    private final JComboBox<String> shapePicker;
    private final JMenuCustom textColorMenu;
    private final JMenuCustom textFontMenu;
    private final JMenuCustom textBackgroundMenu;
    private final JPanel shapePanel;
    private final JTextArea shapeText;
    private final JCheckBox filledCheckBox;

    public ShapeGUI() {
        this.setMousePressedX(0);
        this.setMousePressedX(0);
        this.setMouseReleasedX(0);
        this.setMouseReleasedY(0);
        this.shapeColor = Color.LIGHT_GRAY;
        this.textColor = Color.BLACK;
        this.setTitle("Drawing shapes and Displaying All Info");
        this.setVisible(true);
        this.shapePanel = new JPanel(new BorderLayout()) {
            // paint() draws graphics in a JPanel
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.setColor(shapeColor);
                // x and y are bounding rectangle's top-left corner coordinates

                final int width = getBottomRightX() - getTopLeftX();
                final int height = getBottomRightY() - getTopLeftY();

                switch(getSelectedShape()) {
                    case "Circle":
                        if (filledCheckBox.isSelected()) {
                        g.fillOval(getTopLeftX(), getTopLeftY(), width, width);
                        } else {
                            g.drawOval(getTopLeftX(), getTopLeftY(), width, width);
                        }
                        break;
                    case "Rectangle":
                        if (filledCheckBox.isSelected()) {
                            g.fillRect(getTopLeftX(), getTopLeftY(), width, height);
                        } else {
                            g.drawRect(getTopLeftX(), getTopLeftY(), width, height);
                        }
                        break;
                    case "Square":
                        if (filledCheckBox.isSelected()) {
                            g.fillRect(getTopLeftX(), getTopLeftY(), width, width);
                        } else {
                            g.drawRect(getTopLeftX(), getTopLeftY(), width, width);
                        }
                        break;
                }
            }
        };
        this.shapePanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                setMousePressedX(event.getX());
                setMousePressedY(event.getY());
            }
            public void mouseReleased(MouseEvent event) {
                setMouseReleasedX(event.getX());
                setMouseReleasedY(event.getY());
                setShape();
                //System.out.println(String.format("Pressed: (%s, %s) Released: (%s %s)", x1, y1, x2, y2));
                shapePanel.repaint();
            }
        });
        final JMenu drawMenu = new JMenu("Draw");

        JMenuCustom shapeMenu = new JMenuCustom("Shape");

        shapeMenu.addMenuItem("Circle", this::setShape);
        shapeMenu.addMenuItem("Rectangle", this::setShape);
        shapeMenu.addMenuItem("Square", this::setShape);

        drawMenu.add(shapeMenu);

        final JMenu textMenu = new JMenu("Text");

        this.textColorMenu = new JMenuCustom("Color");

        String[] textColors = {
                "Black",
                "Blue",
                "Red",
                "Green"
        };
        this.shapeText = new JTextArea("\n\n");

        this.textColorMenu.addRadioButtonGroup(textColors, this::setTextColor, "Black");


        final String[] fonts = {
                Font.SERIF,
                Font.MONOSPACED,
                Font.SANS_SERIF
        };
        this.textFontMenu = new JMenuCustom("Font");
        this.textFontMenu.addRadioButtonGroup(fonts, this::setFontName, Font.SERIF);
        this.textFontMenu.addSeparator();
        this.textFontMenu.addCheckBox("Bold", this::setBold, false);
        this.textFontMenu.addCheckBox("Italic", this::setItalic, false);

        this.textBackgroundMenu = new JMenuCustom("Background");

        final String[] backgroundColors = {
            "White",
            "Cyan",
            "Yellow",
            "Light_Gray"
        };
        this.textBackgroundMenu.addRadioButtonGroup(backgroundColors, this::setTextBackgroundColor, "White");

        textMenu.add(textColorMenu);
        textMenu.addSeparator();
        textMenu.add(textFontMenu);
        textMenu.addSeparator();
        textMenu.add(textBackgroundMenu);

        JMenuBar menuBar = new JMenuBar();
        menuBar.add(drawMenu);
        menuBar.add(textMenu);

        final JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.setBackground(Color.GRAY);

        this.shapeOptions = new String[] {
                "Circle",
                "Rectangle",
                "Square"
        };
        this.shapePicker = new JComboBox<>(shapeOptions);
        this.shapePicker.addActionListener(event -> this.setShape());
        buttonPanel.add(shapePicker);

        final JButton colorPicker = new JButton("Pick Color");
        colorPicker.addActionListener(event-> this.openColorPicker());

        this.filledCheckBox = new JCheckBox("Filled");
        filledCheckBox.setBackground(Color.WHITE);
        filledCheckBox.addActionListener(event -> this.shapePanel.repaint());

        buttonPanel.add(colorPicker);
        buttonPanel.add(filledCheckBox);

        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(this.shapeText, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        shapePanel.add(bottomPanel, BorderLayout.SOUTH);
        this.add(shapePanel);
        this.setJMenuBar(menuBar);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    private void setBold(final boolean isSelected) {
        this.isBold = isSelected;
        this.shapeText.setFont(this.getStyle());
    }
    private void setItalic(final boolean isSelected) {
        this.isItalic = isSelected;
        this.shapeText.setFont(this.getStyle());
    }
    private void setTextColor(final String color) {
        this.setTextColor(this.getColorCode(color));
    }
    private void setTextColor(final Color color) {
        this.textColor = color;
        this.shapeText.setForeground(this.textColor);
    }
    private void setTextBackgroundColor(final String textBackgroundColor) {
        this.setTextBackgroundColor(this.getColorCode(textBackgroundColor));
    }
    private void setTextBackgroundColor(final Color textBackgroundColor) {
        this.shapeText.setBackground(textBackgroundColor);
    }
    private void setFontName(final String fontName) {
        this.fontName = fontName;
        this.shapeText.setFont(this.getStyle());
    }
    private String getSelectedShape() {
        final int shapeIndex = this.shapePicker.getSelectedIndex();

        return this.shapeOptions[shapeIndex];
    }
    private void setShapeColor(final Color shapeColor) {
        if (shapeColor != null) {
            this.shapeColor = shapeColor;
        } else {
            this.shapeColor = Color.LIGHT_GRAY;
        }
        System.out.println(shapeColor);
        // Manually call repaint() so new panel can be updated with new colors
        this.shapePanel.repaint();
    }
    private void openColorPicker() {
        // Returns null if no color was picked
        final Color newColor = showDialog(this, "Choose a color", this.shapeColor);

        this.setShapeColor(newColor);
    }
    private void setMousePressedX(final int x) {
        this.mousePressedX = x;
    }
    private void setMousePressedY(final int y) {
        this.mousePressedY = y;
    }
    private void setMouseReleasedX(final int x) {
        this.mouseReleasedX = x;
    }
    private void setMouseReleasedY(final int y) {
        this.mouseReleasedY = y;
    }
    private int getTopLeftX() {
        return this.mousePressedX < this.mouseReleasedX ? this.mousePressedX : this.mouseReleasedX;
    }
    private int getTopLeftY() {
        return this.mousePressedY < this.mouseReleasedY ? this.mousePressedY : this.mouseReleasedY;
    }
    private int getBottomRightX() {
        return this.mousePressedX > this.mouseReleasedX ? this.mousePressedX : this.mouseReleasedX;
    }
    private int getBottomRightY() {
        return this.mousePressedY > this.mouseReleasedY ? this.mousePressedY : this.mouseReleasedY;
    }
    private void setShape() {
        this.setShape(this.getSelectedShape());
    }
    private void setShape(final String shape) {
        final int shapeIndex = Arrays.asList(this.shapeOptions).indexOf(shape);
        this.shapePicker.setSelectedIndex(shapeIndex);

        final String text;

        switch (this.getSelectedShape()) {
            case "Circle":
                double radius = sqrt((this.getBottomRightX() - this.getTopLeftX())^2 + (this.getBottomRightY() - this.getTopLeftY())^2);
                final Circle circle = new Circle(radius);
                text = format("Radius is %f\nArea is %f\nPerimeter is %f",
                              circle.getRadius(),
                              circle.getArea(),
                              circle.getPerimeter());
                break;
            case "Rectangle":
                final int width = this.getBottomRightX() - getTopLeftX();
                final int height = this.getBottomRightY() - this.getTopLeftY();

                final Rectangle rectangle = new Rectangle(width, height);
                text = format("Width is %f\nHeight is %f\nArea is %f\nPerimeter is %f",
                              rectangle.getWidth(),
                              rectangle.getHeight(),
                              rectangle.getArea(),
                              rectangle.getPerimeter());
                break;
            case "Square":
                final int size = this.getBottomRightX() - getTopLeftX();

                final Rectangle square = new Rectangle(size, size);
                text = format("Width is %f\nArea is %f\nPerimeter is %f",
                              square.getWidth(),
                              square.getHeight(),
                              square.getArea(),
                              square.getPerimeter());
                break;
            default:
                text = null;
        }

        this.shapeText.setText(text);
        this.repaint();
    }
    private Font getStyle() {
        int bold = Font.PLAIN;
        int italic = Font.PLAIN;

        if (isBold) {
            bold = Font.BOLD;
        }
        if (isItalic) {
            italic = Font.ITALIC;
        }
        return new Font(this.fontName, bold + italic, 20);
    }
    // A hack that's being used to work around Color's toString not printing the desired output
    private Color getColorCode(final String color) {
        final Color colorCode;

        switch (color) {
            case "Black":
                colorCode = Color.BLACK;
                break;
            case "Blue":
                colorCode = Color.BLUE;
                break;
            case "Red":
                colorCode = Color.RED;
                break;
            case "Green":
                colorCode = Color.GREEN;
                break;
            case "White":
                colorCode = Color.WHITE;
                break;
            case "Cyan":
                colorCode = Color.CYAN;
                break;
            case "Yellow":
                colorCode = Color.YELLOW;
                break;
            case "Light_Gray":
                colorCode = Color.LIGHT_GRAY;
                break;
            default:
                colorCode = null;
        }
        return colorCode;
    }
    public static void main(final String[] args) {
        ShapeGUI shapeGUI = new ShapeGUI();
    }
}