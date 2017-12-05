/*
    Program: MasterPasswordLogin.java
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
import java.io.IOException;

public class MasterPasswordLogin extends JFrame {
    final byte[] infile;
    final JPasswordField passwordField;
    final JButton passwordButton;
    MasterPasswordLogin(final String passwordFileName) {
        this.infile = Crypto.readFile(passwordFileName);
        JPanel inputPanel = new JPanel(new GridLayout(1,2));
        this.passwordField = new JPasswordField(10);
        JLabel label1 = new JLabel("Enter password: ");

        inputPanel.add(label1);
        inputPanel.add(passwordField);

        this.passwordButton = new JButton("Enter");
        passwordButton.addActionListener(event -> {
            new ButtonEvent(this, infile, getPassword()).execute();
        });


        this.passwordButton.setEnabled(false);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> this.dispose());

        JButton newButton = new JButton("New");
        newButton.addActionListener(event -> {
            this.dispose();
            new CreateNewMasterPassword();
        });
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

        //byte[] test = Crypto.readFile(passwordFileName);
    }
    private String getPassword() {
        return String.valueOf(this.passwordField.getPassword());
    }
}
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
        } catch (IOException e) {
            try {
                Thread.sleep(timeout);
            } catch(InterruptedException interrupted) {

            }
            this.isCorrect = false;

        }
        return 0;
    }
    protected void done() {
        if (this.isCorrect) {
            this.masterPasswordLogin.dispose();
            new PasswordManagerMainWindow(this.password, this.decryptedBlob);
        } else {
            JOptionPane.showMessageDialog(null, "Password not valid. Try again.");
            this.button.setEnabled(true);
        }
    }
}