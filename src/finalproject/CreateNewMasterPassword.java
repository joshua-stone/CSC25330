/*
    Program: CreateNewMasterPassword.java
    Written by: Joshua Stone
    Description: A window that opens when a valid password file can't be detected
    Challenges: Making sure button enables only if passwords match
    Time Spent: 2 hours

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/02/17     Joshua Stone    Initial commit
    12/05/17     Joshua Stone    Add a field for password input
    12/05/17     Joshua Stone    Add a second field to verify
    12/05/17     Joshua Stone    Add buttons for enter and cancel
    12/05/17     Joshua Stone    Use BorderLayout for structuring window
    12/05/17     Joshua Stone    Execute password checking method with each keypress
    12/05/17     Joshua Stone    Leave Enter button disabled until password match
*/

package finalproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class CreateNewMasterPassword extends JFrame {
    private static final long serialVersionUID = 1L;
    private JButton okButton;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;
    // Called after user agrees to make a new session, as they'll need a password for protecting data
    public CreateNewMasterPassword() {
        JPanel inputPanel = new JPanel(new GridLayout(2,2));
        this.passwordField = new JPasswordField(10);
        this.passwordConfirmField = new JPasswordField(10);

        // Make sure both passwords match so the user doesn't mistype
        passwordField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkPassword();
            }
        });
        passwordConfirmField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                checkPassword();
            }
        });
        JLabel label1 = new JLabel("Enter master password:");
        JLabel label2 = new JLabel("Confirm master password:");
        // Put widgets into their own panel
        inputPanel.add(label1);
        inputPanel.add(passwordField);
        inputPanel.add(label2);
        inputPanel.add(passwordConfirmField);

        // Start a new session with master password
        this.okButton = new JButton("Enter");
        this.okButton.addActionListener(event -> this.initMainWindow());
        this.okButton.setEnabled(false);
        // Make an Ok and Cancel button
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> this.cancel());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.okButton);
        buttonPanel.add(cancelButton);
        JPanel masterPasswordPanel = new JPanel(new BorderLayout());
        // Borders help improve the appearance of the window
        masterPasswordPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        masterPasswordPanel.add(inputPanel, BorderLayout.CENTER);
        masterPasswordPanel.add(buttonPanel, BorderLayout.SOUTH);
        // Set up the rest of the main window
        this.setTitle("Set Master Password");
        this.add(masterPasswordPanel);
        this.pack();
        this.setResizable(false);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    // Retrieves the current value of the password field
    String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }
    // Simple check for both password input fields to make sure they aren't empty and match
    private void checkPassword() {
        char[] pass1 = this.passwordField.getPassword();
        char[] pass2 = this.passwordConfirmField.getPassword();
        if (Arrays.equals(pass1, pass2) && pass1.length > 0) {
            this.okButton.setEnabled(true);
        } else {
            this.okButton.setEnabled(false);
        }
    }
    // Closes the program if selected
    protected void cancel() {
        this.dispose();
    }
    // Once a password has been made, close this window and start the main session window
    protected void initMainWindow() {
        this.dispose();
        new PasswordManagerMainWindow(this.getPassword());
    }
}
