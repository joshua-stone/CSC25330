/*
    Program: MasterPasswordLogin.java
    Written by: Joshua Stone
    Description: A window that prompts for a master password to unlock the password manager
    Challenges: Making sure password can decrypt file and multithread properly
    Time Spent: 2 hours

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/02/17     Joshua Stone    Initial commit
    12/05/17     Joshua Stone    Add class constructor
    12/05/17     Joshua Stone    Add password input field
    12/05/17     Joshua Stone    Add buttons for password, cancel, and save
    12/05/17     Joshua Stone    Implement a swing worker for checking password
    12/05/17     Joshua Stone    Disable GUI while checking password
    12/05/17     Joshua Stone    Clear password if login attempt fails
    12/05/17     Joshua Stone    Add serialVersionUID for linting
*/
package finalproject;

import javax.crypto.BadPaddingException;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.IOException;

public class MasterPasswordLogin extends JFrame {
    private static final long serialVersionUID = 1L;
    final byte[] infile;
    final JPasswordField passwordField;
    final JButton passwordButton;
    MasterPasswordLogin(final String passwordFileName) {
        this.infile = Crypto.readFile(passwordFileName);
        JPanel inputPanel = new JPanel(new GridLayout(1,2));
        this.passwordField = new JPasswordField(10);
        JLabel label1 = new JLabel("Enter password: ");

        inputPanel.add(label1);
        inputPanel.add(this.passwordField);

        this.passwordButton = new JButton("Enter");
        // Pressing Enter will dispatch decryption to a swing worker
        passwordButton.addActionListener(event -> {
            new ButtonEvent(this, infile, getPassword()).execute();
        });

        this.passwordButton.setEnabled(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> this.dispose());

        // Provide the option to start a brand new password store
        JButton newButton = new JButton("New");
        newButton.addActionListener(event -> {
            this.dispose();
            new CreateNewMasterPassword();
        });
        // Make sure empty passwords can't be entered
        this.passwordField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                char[] pass1 = passwordField.getPassword();
                if (pass1.length > 0) {
                    passwordButton.setEnabled(true);
                } else {
                    passwordButton.setEnabled(false);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(this.passwordButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(newButton);

        JPanel root = new JPanel(new BorderLayout());

        root.add(inputPanel, BorderLayout.CENTER);
        root.add(buttonPanel, BorderLayout.SOUTH);
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setTitle("PasswordManager");
        this.add(root);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    private String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }
}
// A worker that'll try to unlock a password file
class ButtonEvent extends SwingWorker<Integer, Integer>  {
    private MasterPasswordLogin masterPasswordLogin;
    final private static long timeout = 2000;
    final JButton button;
    final byte[] encryptedBlob;
    final String password;
    byte[] decryptedBlob;
    boolean isCorrect;

    ButtonEvent(MasterPasswordLogin masterPasswordLogin, final byte[] encryptedBlob, final String password) {
        this.masterPasswordLogin = masterPasswordLogin;
        this.button = this.masterPasswordLogin.passwordButton;
        this.encryptedBlob = encryptedBlob;
        this.password = password;
    }
    protected Integer doInBackground() {
        this.button.setEnabled(false);
        try {
            this.decryptedBlob = Crypto.fileDecrypt(this.encryptedBlob, this.password);
            this.isCorrect = true;
        } catch (IOException | BadPaddingException e1) {
            try {
                // If unlocking failed, pause for a moment to slow down attempts at brute forcing
                Thread.sleep(ButtonEvent.timeout);
            } catch(InterruptedException interrupted) {
                System.out.println("Thread terminated prematurely");
            }
            this.isCorrect = false;
        }

        return 0;
    }
    protected void done() {
        if (this.isCorrect) {
            // Remove the login window when done, and start the main session
            this.masterPasswordLogin.dispose();
            new PasswordManagerMainWindow(this.password, this.decryptedBlob);
        } else {
            // If password fails, reset password field and re-enable button
            JOptionPane.showMessageDialog(null, "Password not valid. Try again.");
            this.masterPasswordLogin.passwordField.setText("");
            this.button.setEnabled(true);
        }
    }
}