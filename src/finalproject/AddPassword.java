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

public class AddPassword extends JFrame {
    private static final long serialVersionUID = 1L;
    private final PasswordManagerMainWindow passwordManagerMainWindow;
    private final JTextField labelField;
    private final JTextField userField;
    private final JPasswordField passField;

    public AddPassword(final PasswordManagerMainWindow passwordManagerMainWindow) {
        // Taking a reference to a parent window means more control over widgets
        this.passwordManagerMainWindow = passwordManagerMainWindow;
        this.passwordManagerMainWindow.setEnabled(false);
        final JPanel inputPanel = new JPanel(new GridLayout(6,1));

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

        buttonRow.add(saveButton);
        buttonRow.add(cancelButton);
        buttonRow.add(generatePasswordButton);
        inputPanel.add(labelText);
        inputPanel.add(labelField);
        inputPanel.add(userText);
        inputPanel.add(userField);
        inputPanel.add(passText);
        inputPanel.add(passField);

        final JPanel root = new JPanel(new BorderLayout());
        root.add(inputPanel, BorderLayout.CENTER);
        root.add(buttonRow, BorderLayout.SOUTH);
        root.setBorder(new EmptyBorder(10, 10, 10, 10));

        this.setTitle("New");
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        this.setLocationRelativeTo(this.passwordManagerMainWindow);
        this.add(root);
        this.pack();
        this.setVisible(true);
    }
    private void generatePassword() {
        this.passField.setText(Crypto.randomString(16));
    }
    String getLabel() {
        return this.labelField.getText();
    }
    String getUsername() {
        return this.userField.getText();
    }
    String getPassword() {
        return String.valueOf(this.passField.getPassword());
    }
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
class UpdatePassword extends SwingWorker<Integer, Integer> {
    private final PasswordManagerMainWindow passwordManagerMainWindow;
    private final String label;
    private final String username;
    private final String password;

    public UpdatePassword(final PasswordManagerMainWindow passwordManagerMainWindow, final String label, final String username, final String password) {
        this.passwordManagerMainWindow = passwordManagerMainWindow;
        this.label = label;
        this.username = username;
        this.password = password;
    }
    public Integer doInBackground() {
        // JLists need to be updated in a worker thread to prevent exceptions
        this.passwordManagerMainWindow.labelList.add(this.label);
        this.passwordManagerMainWindow.userList.add(this.username);
        this.passwordManagerMainWindow.passList.add(this.password);
        this.passwordManagerMainWindow.labels.setListData(this.passwordManagerMainWindow.labelList.toArray(new String[0]));

        return 0;
    }
    public void done() {
        // With JList updated, re-enable parent window and clear inputs
        this.passwordManagerMainWindow.setEnabled(true);
        this.passwordManagerMainWindow.setEnabledFields(false);
        this.passwordManagerMainWindow.clearFields();
    }
}
