/*
    Program: ShapeGUI.java
    Written by: Joshua Stone
    Description:
    Challenges:
    Time Spent:

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    11/17/17     Joshua Stone    Initial commit
    11/17/17     Joshua Stone    Add PasswordManagerGUI class
    11/17/17     Joshua Stone    Create startup dialog
    11/17/17     Joshua Stone
*/

package finalproject;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

class ButtonEvent extends SwingWorker {
    final private PasswordManagerGUI passwordManagerGUI;
    final private static long timeout = 2000;
    final JButton button;
    final byte[] encryptedBlob;
    final String password;
    ByteArrayOutputStream decryptedBlob;
    boolean isCorrect;

    ButtonEvent(final PasswordManagerGUI passwordManagerGUI, final byte[] encryptedBlob, final JPasswordField password) {
        this.passwordManagerGUI = passwordManagerGUI;
        this.button = this.passwordManagerGUI.passwordButton;
        this.encryptedBlob = encryptedBlob;
        this.password = String.valueOf(password.getPassword());
    }
    @Override
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
    @Override
    public void done() {
        if (this.isCorrect) {
            this.passwordManagerGUI.dispose();
        } else {
            JOptionPane.showMessageDialog(null, "Password not valid. Try again.");
            this.button.setEnabled(true);
        }
    }
}

public class PasswordManagerGUI extends JFrame {
    protected JButton passwordButton;
    private String passwordFileName = "passwords.properties";
    private JButton button;
    public PasswordManagerGUI() {
        File passwordFile = new File(this.passwordFileName);

        if (passwordFile.exists() && passwordFile.canWrite()) {
            this.enterMasterPassword();
        } else {
            final int value = JOptionPane.showConfirmDialog(null,
                    "Create a master password?",
                    "Password file not found",
                    JOptionPane.OK_CANCEL_OPTION);

            if (value != JOptionPane.YES_OPTION) {
                System.exit(0);
            }  else {
                this.createNewPasswordWindow();
            }
        }
    }
    public void createNewPasswordWindow() {
        JPanel inputPanel = new JPanel(new GridLayout(2,2));
        JPasswordField passwordField = new JPasswordField(10);
        JPasswordField passwordConfirmField = new JPasswordField(10);

        passwordField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                char[] pass1 = passwordField.getPassword();
                char[] pass2 = passwordConfirmField.getPassword();
                if (Arrays.equals(pass1, pass2) && pass1.length > 0) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });
        passwordConfirmField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                char[] pass1 = passwordField.getPassword();
                char[] pass2 = passwordConfirmField.getPassword();
                if (Arrays.equals(pass1, pass2) && pass1.length > 0) {
                    button.setEnabled(true);
                } else {
                    button.setEnabled(false);
                }
            }
        });
        JLabel label1 = new JLabel("Enter master password:");
        JLabel label2 = new JLabel("Confirm master password:");

        inputPanel.add(label1);
        inputPanel.add(passwordField);
        inputPanel.add(label2);
        inputPanel.add(passwordConfirmField);

        this.button = new JButton("Enter");
        button.setEnabled(false);
        this.button.addActionListener(event -> System.out.print("test"));
        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(event -> this.dispose());

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(button);
        buttonPanel.add(cancel);
        JPanel root = new JPanel(new BorderLayout());
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        root.add(inputPanel, BorderLayout.CENTER);
        root.add(buttonPanel, BorderLayout.SOUTH);
        this.setTitle("PasswordManager");
        this.add(root);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);
    }
    public void enterMasterPassword() {
        byte[] infile = Crypto.readFile(this.passwordFileName);
        JPanel inputPanel = new JPanel(new GridLayout(1,2));
        JPanel passwordPanel = new JPanel(new GridLayout(2,2));
        JPasswordField password = new JPasswordField(10);


        JLabel label1 = new JLabel("Enter master password:");

        inputPanel.add(label1);
        inputPanel.add(password);

        this.passwordButton = new JButton("Enter");
        passwordButton.addActionListener(event -> {
            new ButtonEvent(this, infile, password).execute();
        });


        this.passwordButton.setEnabled(false);

        JButton cancel = new JButton("Cancel");
        cancel.addActionListener(event -> this.dispose());

        password.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                char[] pass1 = password.getPassword();
                if (pass1.length > 0) {
                    passwordButton.setEnabled(true);
                } else {
                    passwordButton.setEnabled(false);
                }
            }
        });
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(passwordButton);
        buttonPanel.add(cancel);

        JPanel root = new JPanel(new BorderLayout());
        root.add(buttonPanel, BorderLayout.SOUTH);
        root.add(inputPanel, BorderLayout.CENTER);

        this.setTitle("PasswordManager");
        this.add(root);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        byte[] test = Crypto.readFile(this.passwordFileName);
    }

    public static void main(final String[] args) {
        PasswordManagerGUI window = new PasswordManagerGUI();
    }
}
