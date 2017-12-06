/*
    Program: PasswordManagerMainWindow.java
    Written by: Joshua Stone
    Description: The main window of the program after logging in with a master password
    Challenges: Having UI update correctly when adding and removing passwords
    Time Spent: 4 hours

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/02/17     Joshua Stone    Initial commit
    12/02/17     Joshua Stone    Create constructor for PasswordManagerMainWindow
    12/02/17     Joshua Stone    Use Properties() for parsing file data
    12/02/17     Joshua Stone    Have constructor take either a password
    12/05/17     Joshua Stone    Have a second constructor for previous usages
    12/05/17     Joshua Stone    Parse property file buy storing values in three array lists
    12/05/17     Joshua Stone    Present labels as a JList
    12/05/17     Joshua Stone    Implement updating JList with Add window
    12/05/17     Joshua Stone    Have a swing worker for adding and removing to JList without multithreading issues
    12/05/17     Joshua Stone    Have an update() function to save state while typing
    12/05/17     Joshua Stone    Have save() to save and encrypt session data
    12/05/17     Joshua Stone    Have fields disabled until an item is selected
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

    public PasswordManagerMainWindow(final String masterPassword) {
        this(masterPassword, new byte[0]);
    }
    public PasswordManagerMainWindow(final String masterPassword, final byte[] decryptedBlob) {
        this.masterPassword = masterPassword;
        InputStream input = new ByteArrayInputStream(decryptedBlob);
        Properties passwordStore = new Properties();

        // Attempt to load password store into a property object, or fail and close program prematurely
        try {
            passwordStore.load(input);
        } catch (IOException e) {
            System.out.println("Failed to load password file.");
            System.exit(-1);
        }
        this.labelList = new ArrayList<>();
        this.userList = new ArrayList<>();
        this.passList = new ArrayList<>();

        int index = 0;

        // Since the .properties format lacks many features, a flat structure is used with rows of key-value pairs
        // being enumerated
        while (true) {
            try {
                final String label = passwordStore.getProperty(String.format("label_%s", index));
                final String user = passwordStore.getProperty(String.format("user_%s", index));
                final String pass = passwordStore.getProperty(String.format("pass_%s", index));

                if (label == null || user == null || pass == null) {
                    break;
                } else {
                    this.labelList.add(label);
                    this.userList.add(user);
                    this.passList.add(pass);
                    index++;
                }

            } catch (Exception e) {
                System.out.println("Incorrect parsing");
            }
        }
        this.labels = new JList<>(this.labelList.toArray(new String[0]));
        this.labels.addListSelectionListener(event -> this.setCredentials());
        this.usernameField = new JTextField(10);
        // Detect key presses and update values in input fields as needed
        this.usernameField.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent e) {
                update();
            }
        });

        this.passwordField = new JPasswordField(10);
        // Swing doesn't allow copy and pasting by default, so enable it so passwords can be used elsewhere
        this.passwordField.putClientProperty("JPasswordField.cutCopyAllowed", true);

        JPanel buttonRow = new JPanel(new FlowLayout());

        // Delete will remove the currently selected label along with its username and password
        this.deleteButton = new JButton("Delete");
        this.deleteButton.addActionListener(event -> this.delete());
        // Open a window for adding a label, username, and password
        JButton addButton = new JButton("Add");
        addButton.addActionListener(event -> this.add());
        // Upon clicking save, serialize the session data, encrypt, and write to file
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener(event -> this.save());

        buttonRow.add(addButton);
        buttonRow.add(this.deleteButton);
        buttonRow.add(saveButton);
        // A JList doesn't have a selected item by default, so an exception would be raised if anything was entered in
        // an input field
        this.setEnabledFields(false);
        // Scrollpane makes cycling through passwords easier
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
        this.add(root);
        this.setSize(500, 150);
        this.setTitle("PasswordManager");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    int getIndex() {
        return this.labels.getSelectedIndex();
    }
    private void setCredentials() {
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
            Crypto.fileEncrypt(converted, "passwordmanager.properties", this.masterPassword);
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
        // JLists aren't thread-safe, so use a worker thread to update safely
        this.passwordManagerMainWindow.labelList.remove(this.index);
        this.passwordManagerMainWindow.userList.remove(this.index);
        this.passwordManagerMainWindow.passList.remove(this.index);
        this.passwordManagerMainWindow.labels.setListData(this.passwordManagerMainWindow.labelList.toArray(new String[0]));

        return 0;
    }
    public void done() {
        this.passwordManagerMainWindow.setEnabled(true);
        this.passwordManagerMainWindow.setEnabledFields(false);
        this.passwordManagerMainWindow.clearFields();
    }
}