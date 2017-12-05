/*
    Program: CreateNewMasterPassword.java
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
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Arrays;

public class CreateNewMasterPassword extends JFrame {
    private JButton okButton;
    private JPasswordField passwordField;
    private JPasswordField passwordConfirmField;

    public CreateNewMasterPassword() {
        JPanel inputPanel = new JPanel(new GridLayout(2,2));
        this.passwordField = new JPasswordField(10);
        this.passwordConfirmField = new JPasswordField(10);

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

        inputPanel.add(label1);
        inputPanel.add(passwordField);
        inputPanel.add(label2);
        inputPanel.add(passwordConfirmField);

        this.okButton = new JButton("Enter");
        this.okButton.addActionListener(event -> {
            this.dispose();
            new PasswordManagerMainWindow(this.getPassword());
        });
        this.okButton.setEnabled(false);
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> this.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.okButton);
        buttonPanel.add(cancelButton);
        JPanel masterPasswordPanel = new JPanel(new BorderLayout());
        masterPasswordPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        masterPasswordPanel.add(inputPanel, BorderLayout.CENTER);
        masterPasswordPanel.add(buttonPanel, BorderLayout.SOUTH);
        this.setTitle("PasswordManager");
        this.add(masterPasswordPanel);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    private String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }
    private void checkPassword() {
        char[] pass1 = this.passwordField.getPassword();
        char[] pass2 = this.passwordConfirmField.getPassword();
        if (Arrays.equals(pass1, pass2) && pass1.length > 0) {
            this.okButton.setEnabled(true);
        } else {
            this.okButton.setEnabled(false);
        }
    }
}
