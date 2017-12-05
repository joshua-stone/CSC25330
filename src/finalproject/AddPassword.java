/*
    Program: AddPassword.java
    Written by: Joshua Stone
    Description:
    Challenges:
    Time Spent:

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/02/17     Joshua Stone    Initial commit
*/
package finalproject;

import javax.swing.*;
import java.awt.*;

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
        this.passwordManagerMainWindow.labelList.add(this.label);
        this.passwordManagerMainWindow.userList.add(this.username);
        this.passwordManagerMainWindow.passList.add(this.password);
        this.passwordManagerMainWindow.labels.setListData(this.passwordManagerMainWindow.labelList.toArray(new String[0]));

        return 1;
    }
    public void done() {
        this.passwordManagerMainWindow.setEnabled(true);
        this.passwordManagerMainWindow.setEnabledFields(false);
        this.passwordManagerMainWindow.clearFields();
    }
}
public class AddPassword extends JFrame {
    private final PasswordManagerMainWindow passwordManagerMainWindow;
    private final JTextField labelField;
    private final JTextField userField;
    private final JPasswordField passField;

    public AddPassword(final PasswordManagerMainWindow passwordManagerMainWindow) {
        this.passwordManagerMainWindow = passwordManagerMainWindow;
        this.passwordManagerMainWindow.setEnabled(false);
        this.setTitle("Enter a new username and password");
        final JPanel inputPanel = new JPanel(new GridLayout(6,1));

        final JLabel labelText = new JLabel("Label:");
        this.labelField = new JTextField(20);
        final JLabel userText = new JLabel("Username:");
        this.userField = new JTextField(20);
        final JLabel passText = new JLabel("Password:");
        this.passField = new JPasswordField(20);
        this.passField.putClientProperty("JPasswordField.cutCopyAllowed", true);

        final JButton saveButton = new JButton("Okay");
        saveButton.addActionListener(event -> this.checkCredentials());
        final JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> {
            this.passwordManagerMainWindow.setEnabled(true);
            this.dispose();
        });
        JButton generatePasswordButton = new JButton("Generate");
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
            this.passwordManagerMainWindow.setEnabled(true);

            //this.passwordManagerMainWindow.labels.clearSelection();
            new UpdatePassword(this.passwordManagerMainWindow, this.getLabel(), this.getUsername(), this.getPassword()).execute();
            this.dispose();
        }
    }
}
