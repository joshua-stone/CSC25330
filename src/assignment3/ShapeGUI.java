/*
    Program: ShapeGUI.java
    Written by: Joshua Stone
    Description: A program that takes mouse clicks for input to display shapes and info on them, as well as setting colors
    Challenges: Making sure graphics will draw correctly and stay in sync with the bottom text
    Time Spent: 8 hours

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    11/17/17     Joshua Stone    Initial commit
    11/17/17     Joshua Stone    Add mouse listener
    11/17/17     Joshua Stone    Add initial menus
    11/25/17     Joshua Stone    Rewrite functions for better argument ordering
    11/25/17     Joshua Stone    Implement text placement with foreground, background, and font setting
    11/25/17     Joshua Stone    Implement JComboBox shape options
    11/25/17     Joshua Stone    Implement Circle and Rectangle for drawing
    11/25/17     Joshua Stone    Remove unneeded instance variables
    11/25/17     Joshua Stone    Create methods for returning top-left and bottom-right coordinates
    11/25/17     Joshua Stone    Made graphics drawing account for mouse release coordinates being set at different positions
    11/26/17     Joshua Stone    Adding in documentation and fixing up code structure
    12/01/17     Joshua Stone    Add serialVersionUID to pass java linting
    12/01/17     Joshua Stone    Add fix for exception caused by .equals() taking a null value
*/

package assignment3;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;

import static java.lang.String.format;
import static javax.swing.JColorChooser.showDialog;

// Main shape class where GUI construction and drawing takes place
public class ShapeGUI extends JFrame {
    // Set a serial version ID
    private static final long serialVersionUID = 1L;
    private int mousePressedX;
    private int mousePressedY;
    private int mouseReleasedX;
    private int mouseReleasedY;
    private String fontName;
    private Color shapeColor;
    private boolean isBold;
    private boolean isItalic;
    private final JButton colorPicker;
    private final JComboBox<String> shapePicker;
    private final JMenuBar menuBar;
    private final String[] shapeOptions;
    private final JPanel shapePanel;
    private final JTextArea shapeText;
    private final JCheckBox filledCheckBox;

    public ShapeGUI() {
        // Set initial X and Y values
        this.setMousePressedCoordinates(0, 0);
        this.setMouseReleasedCoordinates(0, 0);

        this.shapePanel = new JPanel(new BorderLayout()) {
            // Set a serial version ID
            private static final long serialVersionUID = 1L;
            // paintComponent() updates graphics for shapePanel
            public void paintComponent(final Graphics g) {
                super.paintComponent(g);
                g.setColor(shapeColor);

                switch(getSelectedShape()) {
                    // Even though fillOval() can draw at any width and height, the Circle() class doesn't support
                    // arbitrary widths and heights so pass width twice to make a regular circle for accuracy
                    case "Circle":
                        if (filledCheckBox.isSelected()) {
                            g.fillOval(getTopLeftX(), getTopLeftY(), getShapeWidth(), getShapeWidth());
                        } else {
                            g.drawOval(getTopLeftX(), getTopLeftY(), getShapeWidth(), getShapeWidth());
                        }
                        break;
                    // fillRect() doesn't need any workarounds since Rectangle() can take any width and height
                    case "Rectangle":
                        if (filledCheckBox.isSelected()) {
                            g.fillRect(getTopLeftX(), getTopLeftY(), getShapeWidth(), getShapeHeight());
                        } else {
                            g.drawRect(getTopLeftX(), getTopLeftY(), getShapeWidth(), getShapeHeight());
                        }
                        break;
                    // Reuse fillRect() as a square by using width twice
                    case "Square":
                        if (filledCheckBox.isSelected()) {
                            g.fillRect(getTopLeftX(), getTopLeftY(), getShapeWidth(), getShapeWidth());
                        } else {
                            g.drawRect(getTopLeftX(), getTopLeftY(), getShapeWidth(), getShapeWidth());
                        }
                        break;
                }
            }
        };
        // Pressing a mouse inside shapePanel will set (x1, y1), and releasing anywhere, even outside the window, will
        // set (x2, y2)
        this.shapePanel.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent event) {
                setMousePressedCoordinates(event.getX(), event.getY());
            }
            public void mouseReleased(MouseEvent event) {
                setMouseReleasedCoordinates(event.getX(), event.getY());
                setShape();
            }
        });
        this.shapePanel.setBackground(Color.LIGHT_GRAY);

        final JMenu drawMenu = new JMenu("Draw");

        final JMenuCustom shapeMenu = new JMenuCustom("Shape");
        // Set shameMenu to have three shapes that can be selected to set shape being drawn
        shapeMenu.addMenuItem("Circle", this::setShape);
        shapeMenu.addMenuItem("Rectangle", this::setShape);
        shapeMenu.addMenuItem("Square", this::setShape);

        drawMenu.add(shapeMenu);

        final JMenu textMenu = new JMenu("Text");

        this.shapeText = new JTextArea();
        this.shapeText.setEditable(false);

        final JMenuCustom textColorMenu = new JMenuCustom("Color");

        String[] textColors = {
                "Black",
                "Blue",
                "Red",
                "Green"
        };
        // Create a radio button group where selecting a value will set the text color, with default value being Black
        textColorMenu.addRadioButtonGroup(textColors, this::setTextColor, "Black");

        final JMenuCustom textFontMenu = new JMenuCustom("Font");

        final String[] fonts = {
                Font.SERIF,
                Font.MONOSPACED,
                Font.SANS_SERIF
        };
        // Create a radio button group where selecting a value will set the font, with default value being Serif
        textFontMenu.addRadioButtonGroup(fonts, this::setFontName, Font.SERIF);
        textFontMenu.addSeparator();
        // Add checkboxes for setting bold and italic styles
        textFontMenu.addCheckBox("Bold", this::setBold, false);
        textFontMenu.addCheckBox("Italic", this::setItalic, false);

        final JMenuCustom textBackgroundMenu = new JMenuCustom("Background");

        final String[] backgroundColors = {
            "White",
            "Cyan",
            "Yellow",
            "Light_Gray"
        };
        // Radio button group that sets the text area's background color, with default being White
        textBackgroundMenu.addRadioButtonGroup(backgroundColors, this::setTextBackgroundColor, "White");

        // Add all submenus into textMenu
        textMenu.add(textColorMenu);
        textMenu.addSeparator();
        textMenu.add(textFontMenu);
        textMenu.addSeparator();
        textMenu.add(textBackgroundMenu);

        // Add all menus to menuBar
        this.menuBar = new JMenuBar();
        menuBar.add(drawMenu);
        menuBar.add(textMenu);

        this.shapeOptions = new String[] {
                "Circle",
                "Rectangle",
                "Square"
        };

        // Picking one of three options will set the current shape
        this.shapePicker = new JComboBox<>(shapeOptions);
        this.shapePicker.addActionListener(event -> this.setShape());
        // Clicking a button will open a color picker dialog for setting shape color
        this.colorPicker = new JButton("Pick Color");
        this.colorPicker.addActionListener(event-> this.openColorPicker());
        // Ticking the checkbox will fill the shape being drawn
        this.filledCheckBox = new JCheckBox("Filled");
        this.filledCheckBox.addActionListener(event -> this.shapePanel.repaint());
        this.filledCheckBox.setBackground(Color.WHITE);

        // Create final part of GUI that contains the widgets for setting shape, shape color, and whether to fill shape
        final JPanel buttonPanel = new JPanel();
        buttonPanel.add(this.shapePicker);
        buttonPanel.add(this.colorPicker);
        buttonPanel.add(this.filledCheckBox);
        buttonPanel.setBackground(Color.GRAY);

        final JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.add(this.shapeText, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        bottomPanel.setFocusable(false);

        this.setShapeColor(Color.BLACK);
        this.setShape();
        this.shapePanel.add(bottomPanel, BorderLayout.SOUTH);
    }
    // Set bold state and update text upon calling
    private void setBold(final boolean isSelected) {
        this.isBold = isSelected;
        this.shapeText.setFont(this.getCurrentFont());
    }
    // Set italic state and update font upon calling
    private void setItalic(final boolean isSelected) {
        this.isItalic = isSelected;
        this.shapeText.setFont(this.getCurrentFont());
    }
    // Should return either 1 or 0
    private int getBold() {
        return this.isBold ? Font.BOLD : Font.PLAIN;
    }
    // Should return either 2 or 0
    private int getItalic() {
        return this.isItalic ? Font.ITALIC : Font.PLAIN;
    }
    // Attempt to get color code from string and use it to update text color
    private void setTextColor(final String color) {
        this.shapeText.setForeground(this.getColorCode(color));
    }
    // Attempt to get color code from string and use it to update text background color
    private void setTextBackgroundColor(final String textBackgroundColor) {
        this.shapeText.setBackground(this.getColorCode(textBackgroundColor));
    }
    // Set font name and update the current text
    private void setFontName(final String fontName) {
        this.fontName = fontName;
        this.shapeText.setFont(this.getCurrentFont());
    }
    private String getSelectedShape() {
        final int shapeIndex = this.shapePicker.getSelectedIndex();

        return this.shapeOptions[shapeIndex];
    }
    private void setShapeColor(final Color shapeColor) {
        this.shapeColor = shapeColor;
        // Manually call repaint() so new panel can be updated with new color
        this.shapePanel.repaint();
    }
    private void openColorPicker() {
        // Returns null if no color was picked
        final Color newColor = showDialog(this, "Choose a color", this.shapeColor);

        if (newColor != null) {
            // Making sure that a new color is selected prevents unnecessary repaints
            if (!newColor.equals(this.shapeColor)) {
                this.setShapeColor(newColor);
            }
        }
    }
    // Sets the coordinates from a mouse press event
    private void setMousePressedCoordinates(final int x, final int y) {
        this.mousePressedX = x;
        this.mousePressedY = y;
    }
    // Sets the mouse coordinates from a mouse release event
    private void setMouseReleasedCoordinates(final int x, final int y) {
        this.mouseReleasedX = x;
        this.mouseReleasedY = y;
    }
    // Since mouse can be released from positions higher than when initially pressed, use a method to return the
    // top-left X and Y values so shapes can be calculated and drawn correctly
    private int getTopLeftX() {
        return this.mousePressedX < this.mouseReleasedX ? this.mousePressedX : this.mouseReleasedX;
    }
    private int getTopLeftY() {
        return this.mousePressedY < this.mouseReleasedY ? this.mousePressedY : this.mouseReleasedY;
    }
    // Get the bottom-right X and Y values for getting width and height
    private int getBottomRightX() {
        return this.mousePressedX > this.mouseReleasedX ? this.mousePressedX : this.mouseReleasedX;
    }
    private int getBottomRightY() {
        return this.mousePressedY > this.mouseReleasedY ? this.mousePressedY : this.mouseReleasedY;
    }
    private int getShapeWidth() {
        return this.getBottomRightX() - this.getTopLeftX();
    }
    private int getShapeHeight() {
        return this.getBottomRightY() - this.getTopLeftY();
    }
    // Overload setShape() for easier shape updating and repainting
    private void setShape() {
        this.setShape(this.getSelectedShape());
    }
    private void setShape(final String shape) {
        final int shapeIndex = Arrays.asList(this.shapeOptions).indexOf(shape);
        // Make sure that shapePicker is updated to display the updated selected shape
        this.shapePicker.setSelectedIndex(shapeIndex);

        final String text;

        switch (this.getSelectedShape()) {
            case "Circle":
                final Circle circle = new Circle(this.getShapeWidth() / 2.0);
                text = format("Radius is %.1f\nArea is %.2f\nPerimeter is %.2f",
                              circle.getRadius(),
                              circle.getArea(),
                              circle.getPerimeter());
                break;
            case "Rectangle":
                final Rectangle rectangle = new Rectangle(this.getShapeWidth(), this.getShapeHeight());
                text = format("Width is %.1f\nHeight is %.2f\nArea is %.2f\nPerimeter is %.2f",
                              rectangle.getWidth(),
                              rectangle.getHeight(),
                              rectangle.getArea(),
                              rectangle.getPerimeter());
                break;
            case "Square":
                final Rectangle square = new Rectangle(this.getShapeWidth(), this.getShapeWidth());
                text = format("Width is %.1f\nArea is %.2f\nPerimeter is %.2f",
                              square.getWidth(),
                              square.getArea(),
                              square.getPerimeter());
                break;
            // Since the program is using only three shapes, this condition should never be reached
            default:
                text = "";
        }
        this.shapeText.setText(text);
        // Once text has been updated, display the appropriate shape
        this.repaint();
    }
    private Font getCurrentFont() {
        // Since Font.BOLD, Font.ITALIC, and Font.PLAIN return integers and Font() takes an integer for its second
        // argument, add getBold() and getItalic() together to get the value representing the desired style
        final int style = this.getBold() + this.getItalic();

        return new Font(this.fontName, style, 20);
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
            // Considering the program is only using the above colors, the condition below should never be reached
            default:
                colorCode = Color.GRAY;
        }
        return colorCode;
    }
    // Creates the main window and displays it in the middle of the screen
    public void init() {
        this.setTitle("Drawing shapes and Displaying All Info");
        this.add(this.shapePanel);
        this.setJMenuBar(this.menuBar);
        this.setSize(500, 500);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    // Main method where program will execute
    public static void main(final String[] args) {
        ShapeGUI shapeGUI = new ShapeGUI();
        shapeGUI.init();
    }
}