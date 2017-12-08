/*
    Program: AddPassword.java
    Written by: Joshua Stone
    Description: A window that spawns from the main window for creating a new username and password
    Challenges: Making sure data updates correctly when using Swing
    Time Spent: 3 hours

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/02/17     Joshua Stone    Initial commit
    12/05/17     Joshua Stone    Create a swing worker for updating a JList properly
    12/05/17     Joshua Stone    Implement constructor that takes reference to the main window
    12/05/17     Joshua Stone    Add window for adding a new user and password
    12/05/17     Joshua Stone    Have main window disabled to keep other data from being entered
    12/05/17     Joshua Stone    Add a random password generator
    12/05/17     Joshua Stone    Make sure all three fields have characters typed in
    12/05/17     Joshua Stone    Add serialVersionUID for linting
*/
package finalproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
// Creates a dialog for adding a label, username, and password
public class AddPassword extends JFrame {
    // Serial version ID for linting
    private static final long serialVersionUID = 1L;
    private final PasswordManagerMainWindow passwordManagerMainWindow;
    private final JTextField labelField;
    private final JTextField userField;
    private final JPasswordField passField;
    // Simply takes a reference to a main window so it can add a label, username, and password
    public AddPassword(final PasswordManagerMainWindow passwordManagerMainWindow) {
        // Taking a reference to a parent window means more control over widgets
        this.passwordManagerMainWindow = passwordManagerMainWindow;
        this.passwordManagerMainWindow.setEnabled(false);
        final JPanel inputPanel = new JPanel(new GridLayout(6,1));
        // Create the label, username, and password fields.
        final JLabel labelText = new JLabel("Label:");
        this.labelField = new JTextField(20);
        final JLabel userText = new JLabel("Username:");
        this.userField = new JTextField(20);
        final JLabel passText = new JLabel("Password:");
        this.passField = new JPasswordField(20);
        // Allow copying and pasting for convenience
        this.passField.putClientProperty("JPasswordField.cutCopyAllowed", true);

        final JButton saveButton = new JButton("Okay");
        // Make sure none of the fields are empty
        saveButton.addActionListener(event -> this.checkCredentials());
        final JButton cancelButton = new JButton("Cancel");
        // Simply go back to parent window and re-enable if cancelled
        cancelButton.addActionListener(event -> {
            this.passwordManagerMainWindow.setEnabled(true);
            this.dispose();
        });
        JButton generatePasswordButton = new JButton("Generate");
        // Fill the password field with a randomly generated string
        generatePasswordButton.addActionListener(event -> this.generatePassword());
        final JPanel buttonRow = new JPanel();
        // Add the widgets to panels
        buttonRow.add(saveButton);
        buttonRow.add(cancelButton);
        buttonRow.add(generatePasswordButton);
        inputPanel.add(labelText);
        inputPanel.add(labelField);
        inputPanel.add(userText);
        inputPanel.add(userField);
        inputPanel.add(passText);
        inputPanel.add(passField);
        // Place widgets in a root pane
        final JPanel root = new JPanel(new BorderLayout());
        root.add(inputPanel, BorderLayout.CENTER);
        root.add(buttonRow, BorderLayout.SOUTH);
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Put together the rest of the window
        this.setTitle("New");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(this.passwordManagerMainWindow);
        this.add(root);
        this.pack();
        this.setVisible(true);
    }
    // Calls a password generator which puts its result in the password field
    private void generatePassword() {
        this.passField.setText(Crypto.randomString(16));
    }
    // Returns the value in the label field
    private String getLabel() {
        return this.labelField.getText();
    }
    // Returns the value in the username field
    private String getUsername() {
        return this.userField.getText();
    }
    // Returns the value in the password field
    private String getPassword() {
        return String.valueOf(this.passField.getPassword());
    }
    // Simple check to make sure none of the input fields are empty so data storage doesn't break
    private void checkCredentials() {
        if (this.getLabel().length() == 0 || this.getUsername().length() == 0 || this.getPassword().length() == 0) {
            JOptionPane.showMessageDialog(this, "Error: One or more fields are empty");
        } else {
            // If successful, tear down window, re-enable parent window, and update data
            new UpdatePassword(this.passwordManagerMainWindow, this.getLabel(), this.getUsername(), this.getPassword()).execute();
            this.dispose();
        }
    }
}
//  Swing worker for updating labelPicker
class UpdatePassword extends SwingWorker<Integer, Integer> {
    private final PasswordManagerMainWindow passwordManagerMainWindow;
    private final String label;
    private final String username;
    private final String password;

    // Have the swing worker take a reference to the main window, and give it a label, username, and password so it can update
    public UpdatePassword(final PasswordManagerMainWindow passwordManagerMainWindow, final String label, final String username, final String password) {
        this.passwordManagerMainWindow = passwordManagerMainWindow;
        this.label = label;
        this.username = username;
        this.password = password;
    }
    // Runs an update on labelPicker in the background
    public Integer doInBackground() {
        // labelPicker need to be updated in a worker thread to prevent exceptions
        this.passwordManagerMainWindow.labelList.add(this.label);
        this.passwordManagerMainWindow.userList.add(this.username);
        this.passwordManagerMainWindow.passList.add(this.password);
        this.passwordManagerMainWindow.labelPicker.setListData(this.passwordManagerMainWindow.labelList.toArray(new String[0]));

        return 0;
    }
    // Frees up the main window once it's safe to use the UI again
    public void done() {
        // With labelPicker updated, clear input fields to create a neutral state and re-enable the main window
        this.passwordManagerMainWindow.setEnabled(true);
        this.passwordManagerMainWindow.setEnabledFields(false);
        this.passwordManagerMainWindow.clearFields();
    }
}
