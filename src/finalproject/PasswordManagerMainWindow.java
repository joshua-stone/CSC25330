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
    12/05/17     Joshua Stone    Add serialVersionUID for linting
    12/06/17     Joshua Stone    Refactor constructor to take different arguments
    12/07/17     Joshua Stone    Add input validation for save()
    12/07/17     Joshua Stone    Add a password reset and session reset option
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
    private static final long serialVersionUID = 1L;
    private JTextField usernameField;
    private JPasswordField passwordField;
    protected ArrayList<String> labelList;
    protected ArrayList<String> userList;
    protected ArrayList<String> passList;
    protected JList<String> labelPicker;
    private String masterPassword;
    private JButton deleteButton;
    // Called when a new session is made and there's no previous password file
    public PasswordManagerMainWindow(final String masterPassword) {
        this(masterPassword, new byte[0]);
    }
    // Called after a file has been decrypted
    public PasswordManagerMainWindow(final String masterPassword, final byte[] decryptedBlob) {
        InputStream input = new ByteArrayInputStream(decryptedBlob);
        Properties passwordStore = new Properties();
        // Attempt to load password store into a property object, or fail and close program prematurely
        try {
            passwordStore.load(input);
        } catch (IOException e) {
            System.out.println("Failed to load password file.");
            System.exit(-1);
        }
        ArrayList<String> labelList = new ArrayList<>();
        ArrayList<String> userList = new ArrayList<>();
        ArrayList<String> passList = new ArrayList<>();

        int index = 0;
        // This loop is for parsing passwordStore into three separate arraylists to represent the internal state of the password store
        while (true) {
            try {
                final String label = passwordStore.getProperty(String.format("label_%s", index));
                final String user = passwordStore.getProperty(String.format("user_%s", index));
                final String pass = passwordStore.getProperty(String.format("pass_%s", index));
                // Since the application forbids saving with any field empty, stop adding elements to the array lists
                if (label != null && user != null && pass != null) {
                    labelList.add(label);
                    userList.add(user);
                    passList.add(pass);
                    index++;
                } else {
                    break;
                }

            } catch (Exception e) {
                System.out.println("Incorrect parsing");
            }
        }
        // Finally start the main window with the session data
        new PasswordManagerMainWindow(masterPassword, labelList, userList, passList);
    }
    // Main window of the program, which should be called by PasswordManagerMainWindow(String, byte[])
    public PasswordManagerMainWindow(final String masterPassword, final ArrayList<String> labelList, final ArrayList<String> userList, final ArrayList<String> passList) {
        this.masterPassword = masterPassword;
        this.labelList = labelList;
        this.userList = userList;
        this.passList = passList;

        this.labelPicker = new JList<>(this.labelList.toArray(new String[0]));
        this.labelPicker.addListSelectionListener(event -> this.setCredentials());
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
        // Make a row of buttons
        buttonRow.add(addButton);
        buttonRow.add(this.deleteButton);
        buttonRow.add(saveButton);
        // A JList doesn't have a selected item by default, so an exception would be raised if anything was entered in
        // an input field
        this.setEnabledFields(false);
        // ScrollPane makes cycling through passwords easier
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setSize(220, 20);
        scrollPane.setViewportView(this.labelPicker);
        // Add a JList, two text fields, and three labels for a panel
        final JPanel topPanel = new JPanel(new GridLayout(2, 3));
        topPanel.add(new JLabel("Label:"));
        topPanel.add(new JLabel("Username:"));
        topPanel.add(new JLabel("Password:"));
        topPanel.add(scrollPane);
        topPanel.add(this.usernameField);
        topPanel.add(this.passwordField);
        // Put panels into a root panel to add to the frame
        JPanel root = new JPanel(new BorderLayout());
        root.add(topPanel, BorderLayout.CENTER);
        root.add(buttonRow, BorderLayout.SOUTH);
        root.setBorder(new EmptyBorder(10, 10, 10, 10));
        // Sets the menu bar for resetting a master password
        JMenuBar menuBar = new JMenuBar();
        JMenu optionMenu = new JMenu("Options");
        JMenuItem startNewSession = new JMenuItem("New session");
        // Creates a new session while keeping the master password
        startNewSession.addActionListener(event -> {
            this.dispose();
            new PasswordManagerMainWindow(this.masterPassword);
        });
        // Has the option to reset the master password while keeping the current session
        JMenuItem resetMasterPassword = new JMenuItem("Reset master password");
        resetMasterPassword.addActionListener( event -> this.resetMasterPassword());

        optionMenu.add(startNewSession);
        optionMenu.add(resetMasterPassword);
        menuBar.add(optionMenu);
        // Sets up the rest of the GUI
        this.setJMenuBar(menuBar);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(root);
        this.setSize(500, 150);
        this.setTitle("PasswordManager");
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
    // Gets the index of the selected item in labelPicker
    int getIndex() {
        return this.labelPicker.getSelectedIndex();
    }
    // Sets up the username and password fields associated with a label
    private void setCredentials() {
        this.setEnabledFields(true);
        String userText = this.userList.get(this.getIndex());
        this.usernameField.setText(userText);

        String passText = this.passList.get(this.getIndex());
        this.passwordField.setText(passText);
    }
    // Saves the most recent change to a username or password so it doesn't go away when selecting other labels
    private void update() {
        String userText = this.usernameField.getText();

        this.userList.set(this.getIndex(), userText);
    }
    // Calls a swing worker for adding a label, username, and password
    private void add() {
        new AddPassword(this);
    }
    // Sets states for username field, password field, and delete button
    void setEnabledFields(final boolean state) {
        this.usernameField.setEnabled(state);
        this.passwordField.setEnabled(state);
        this.deleteButton.setEnabled(state);
    }
    // Called every time an AddPassword window is closed
    void clearFields() {
        this.usernameField.setText("");
        this.passwordField.setText("");
    }
    // Calls a swing worker for removing a selected label
    private void delete() {
        new RemovePassword(this).execute();
    }
    // Saves an encrypted session to the disk
    private void save() {
        // Makes sure that all lists don't contain empty strings and aren't empty
        if (!this.userList.contains("") && !this.userList.isEmpty() && !this.passList.contains("") && !this.passList.isEmpty()) {
            Properties properties = new Properties();
            // Simply enumerate from 0 to however many rows there are for keys and add to properties
            for (int index = 0; index < this.labelList.size(); index++) {
                final String labelValue = this.labelList.get(index);
                final String userValue = this.userList.get(index);
                final String passValue = this.userList.get(index);
                // label_0, label_1, label_2 ..
                final String labelKey = String.format("label_%s", index);
                final String userKey = String.format("user_%s", index);
                final String passKey = String.format("pass_%s", index);

                properties.setProperty(labelKey, labelValue);
                properties.setProperty(userKey, userValue);
                properties.setProperty(passKey, passValue);
            }
            // Once properties has its key values, serialize it, encrypt it, and write it to the disk
            try {
                byte[] converted = Serialize.convertProperties(properties);
                Crypto.fileEncrypt(converted, "passwordmanager.properties", this.masterPassword);
            } catch (IOException e) {
                JOptionPane.showMessageDialog(this, "Error: Couldn't save file");
            }
            // Prompt the user to correct their data inputs if needed
        } else {
            JOptionPane.showMessageDialog(this, "Error: All fields must be filled before saving");
        }
    }
    // Have ResetMasterPassword() be able to set the master password
    void setMasterPassword(final String masterPassword) {
        this.masterPassword = masterPassword;
    }
    // Calling this will make a new window for resetting the master password
    private void resetMasterPassword() {
        new ResetMasterPassword(this);
    }
}
// Swing worker for ensuring labelPicker is updated without causing exceptions
class RemovePassword extends SwingWorker<Integer, Integer>  {
    private final PasswordManagerMainWindow passwordManagerMainWindow;
    private final int index;
    public RemovePassword(final PasswordManagerMainWindow passwordManagerMainWindow) {
        this.passwordManagerMainWindow = passwordManagerMainWindow;
        this.index = this.passwordManagerMainWindow.getIndex();
    }
    // Removes item from labelPicker in the background along with the associated username and password by using an index
    public Integer doInBackground() {
        // JLists aren't thread-safe, so use a worker thread to update safely
        this.passwordManagerMainWindow.labelList.remove(this.index);
        this.passwordManagerMainWindow.userList.remove(this.index);
        this.passwordManagerMainWindow.passList.remove(this.index);
        this.passwordManagerMainWindow.labelPicker.setListData(this.passwordManagerMainWindow.labelList.toArray(new String[0]));

        return 0;
    }
    // Finally re-enable the main window
    public void done() {
        this.passwordManagerMainWindow.setEnabledFields(false);
        this.passwordManagerMainWindow.clearFields();
    }
}