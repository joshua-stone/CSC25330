/*
    Program: PasswordManagerMainWindow.java
    Written by: Joshua Stone
    Description:
    Challenges:
    Time Spent:

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/02/17     Joshua Stone    Initial commit
    12/02/17     Joshua Stone    Create constructor for PasswordManagerMainWindow
    12/02/17     Joshua Stone    Use Properties() for parsing file data
    12/02/17     Joshua Stone
*/

package finalproject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

public class PasswordManagerMainWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    protected ArrayList<String> labelList;
    protected ArrayList<String> userList;
    protected ArrayList<String> passList;
    protected JList<String> labels;
    private String masterPassword;
    private JButton deleteButton;
    private JButton addButton;
    private Properties passwordStore;

    public PasswordManagerMainWindow(final String masterPassword) {
        this(masterPassword, new byte[0]);
    }
    public PasswordManagerMainWindow(final String masterPassword, final byte[] decryptedBlob) {
        this.masterPassword = masterPassword;
        InputStream input = new ByteArrayInputStream(decryptedBlob);
        this.passwordStore = new Properties();

        try {
            this.passwordStore.load(input);
        } catch (IOException e) {

        }
        this.labelList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.passList = new ArrayList<>();

        int index = 0;

        while (true) {
            try {
                //config.getProperty(String.format("%s", index));
                final String label = this.passwordStore.getProperty(String.format("label_%s", index));
                final String user = this.passwordStore.getProperty(String.format("user_%s", index));
                final String pass = this.passwordStore.getProperty(String.format("pass_%s", index));

                if (label == null || user == null || pass == null) {
                    break;
                } else {
                    this.labelList.add(label);
                    this.userList.add(user);
                    this.passList.add(pass);
                    index++;
                }

            } catch (Exception e) {

            }
        }
        this.labels = new JList<>(this.labelList.toArray(new String[0]));
        this.labels.addListSelectionListener(event -> this.setCredentials());
        this.usernameField = new JTextField(10);
        this.usernameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                update();
            }
        });

        this.passwordField = new JPasswordField(10);
        this.passwordField.putClientProperty("JPasswordField.cutCopyAllowed", true);

        JPanel buttonRow = new JPanel(new FlowLayout());

        this.deleteButton = new JButton("Delete");
        this.deleteButton.addActionListener(event -> this.delete());
        this.addButton = new JButton("Add");
        this.addButton.addActionListener(event -> this.add());

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(event -> this.save());
        buttonRow.add(this.addButton);
        buttonRow.add(this.deleteButton);
        buttonRow.add(saveButton);

        this.setEnabledFields(false);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(220, 20);
        scrollPane.setViewportView(this.labels);
        final JPanel topPanel = new JPanel(new GridLayout(2, 3));

        topPanel.add(new JLabel("Label:"));
        topPanel.add(new JLabel("Username:"));
        topPanel.add(new JLabel("Password:"));
        topPanel.add(scrollPane);
        topPanel.add(this.usernameField);
        topPanel.add(this.passwordField);
        JPanel root = new JPanel(new BorderLayout());
        root.add(topPanel, BorderLayout.CENTER);
        root.add(buttonRow, BorderLayout.SOUTH);
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //this.setCredentials();
        this.add(root);
        this.setSize(500, 150);
        //this.pack();
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    int getIndex() {
        return this.labels.getSelectedIndex();
    }
    void setCredentials() {
        this.setEnabledFields(true);
        String userText = this.userList.get(this.getIndex());
        this.usernameField.setText(userText);

        String passText = this.passList.get(this.getIndex());
        this.passwordField.setText(passText);
    }
    private void update() {
        String userText = this.usernameField.getText();

        this.userList.set(this.getIndex(), userText);
    }
    private void add() {
        new AddPassword(this);
    }
    void setEnabledFields(final boolean state) {
        this.usernameField.setEnabled(state);
        this.passwordField.setEnabled(state);
        this.deleteButton.setEnabled(state);
    }
    void clearFields() {
        this.usernameField.setText("");
        this.passwordField.setText("");
    }
    private void delete() {
        new RemovePassword(this).execute();
    }
    private void save() {
        Properties properties = new Properties();

        for (int index = 0; index < this.labelList.size(); index++) {
            final String labelValue = this.labelList.get(index);
            final String userValue = this.userList.get(index);
            final String passValue = this.userList.get(index);

            final String labelKey = String.format("label_%s", index);
            final String userKey = String.format("user_%s", index);
            final String passKey = String.format("pass_%s", index);

            properties.setProperty(labelKey, labelValue);
            properties.setProperty(userKey, userValue);
            properties.setProperty(passKey, passValue);
        }
        try {
            byte[] converted = Serialize.convertProperties(properties);
            Crypto.fileEncrypt(converted, "test_file", this.masterPassword);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error: Couldn't save file");
        }
    }
}
class RemovePassword extends SwingWorker<Integer, Integer>  {
    private final PasswordManagerMainWindow passwordManagerMainWindow;
    private final int index;
    public RemovePassword(final PasswordManagerMainWindow passwordManagerMainWindow) {
        this.passwordManagerMainWindow = passwordManagerMainWindow;
        this.index = this.passwordManagerMainWindow.getIndex();
    }
    public Integer doInBackground() {
        this.passwordManagerMainWindow.labelList.remove(this.index);
        this.passwordManagerMainWindow.userList.remove(this.index);
        this.passwordManagerMainWindow.passList.remove(this.index);
        this.passwordManagerMainWindow.labels.setListData(this.passwordManagerMainWindow.labelList.toArray(new String[0]));

        return 1;
    }
    public void done() {
        this.passwordManagerMainWindow.setEnabled(true);
        this.passwordManagerMainWindow.setEnabledFields(false);
        this.passwordManagerMainWindow.clearFields();
    }
}