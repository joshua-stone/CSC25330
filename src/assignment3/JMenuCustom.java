/*
    Program: JMenuCustom.java
    Written by: Joshua Stone
    Description: A custom class that builds on JMenu to make menu creation simpler
    Challenges: Write an API that can cleanly express menu creation and management while keeping values in sync
    Time Spent: 2 hours

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    11/17/17     Joshua Stone    Initial commit
    11/18/17     Joshua Stone    Add radio group function for font menu
    11/18/17     Joshua Stone    Use Java 8 lambdas and functional interfaces to reuse menu creation code
    11/18/17     Joshua Stone    Implement custom JMenu class for easier menu creation
    12/01/17     Joshua Stone    Add serialVersionUID to pass java linting
*/

package assignment3;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;

// Interface for passing a setter as a parameter in addMenuItem() or addRadioButtonGroup()
interface SetString {
    void setValue(final String value);
}
// Interface for passing a setter as a parameter in addCheckBox()
interface SetBool {
    void setValue(final boolean value);
}
// Swing doesn't seem to have a very expressive API for creating menus, so a custom JMenu is made to reduce boilerplate
// in ShapeGUI constructor
public class JMenuCustom extends JMenu {
    // Set a serial version ID
    private static final long serialVersionUID = 1L;

    public JMenuCustom(final String name) {
        super(name);
    }
    // Simple menu item creation function that'll create an item that calls a function when pressed
    public void addMenuItem(final String item, final SetString function) {
        JMenuItem menuItem = new JMenuItem(item);
        menuItem.addActionListener(event -> function.setValue(item));
        this.add(menuItem);
    }
    // Method for programmatically creating a radio buttons that share a common setter method
    public void addRadioButtonGroup(final String[] items, final SetString function, final String defaultValue) {
        final ButtonGroup radioButtonGroup = new ButtonGroup();

        for (final String item : items) {
            JRadioButtonMenuItem radioButton = new JRadioButtonMenuItem(item);
            // If default value matches any item, then select the radio button and attempt to execute the method so
            // values are kept in sync
            if (item.equals(defaultValue)) {
                radioButton.setSelected(true);
                function.setValue(defaultValue);
            }
            radioButton.addActionListener(event -> function.setValue(item));
            radioButtonGroup.add(radioButton);
            this.add(radioButton);
        }
    }
    // Generate a checkbox that calls a method that takes a boolean based on its current state
    public void addCheckBox(final String item, final SetBool function, final boolean defaultValue) {
        JCheckBox checkbox = new JCheckBox(item);
        checkbox.addActionListener(event -> function.setValue(checkbox.isSelected()));
        // Set checkbox with default value
        checkbox.setSelected(defaultValue);
        // Also use the setter method to set outside value so they're in sync
        function.setValue(defaultValue);
        this.add(checkbox);
    }
}