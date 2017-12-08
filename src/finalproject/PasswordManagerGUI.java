/*
    Program: PasswordManagerStartup.java
    Written by: Joshua Stone
    Description: A class which starts other parts of the PasswordManager program
    Challenges: Finding how to reference other windows and start them based on different conditions
    Time Spent: 1 hour

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/02/17     Joshua Stone    Initial commit
    12/02/17     Joshua Stone    Add PasswordManagerGUI class
    12/02/17     Joshua Stone    Create startup dialog
    12/02/17     Joshua Stone    Add reading from password file
    12/02/17     Joshua Stone    Use checks for file existence at startup
    12/02/17     Joshua Stone    Use dialog to ask if user wants to make a new password
    12/02/17     Joshua Stone    Dispatch master password creation to a different window
    12/02/17     Joshua Stone    Have main window for logging in with a master password

*/

package finalproject;

import javax.swing.*;
import java.io.File;

// PasswordManagerGUI bootstraps the rest of the program depending on the presence of a passwordmanager.properties file
public class PasswordManagerGUI {
    private String passwordFileName = "passwordmanager.properties";

    public PasswordManagerGUI() {
        // Get a reference to a password file
        final File passwordFile = new File(this.passwordFileName);
        // Check if the password file exists
        if (passwordFile.exists()) {
            // Attempt to restore a previous session if a password file is detected
            new MasterPasswordLogin(this.passwordFileName);
        } else {
            // Else, prompt to make a new password
            final int value = JOptionPane.showConfirmDialog(null,
                    "Create a master password?",
                    "Password file not found",
                    JOptionPane.OK_CANCEL_OPTION);
            // If Close or Cancel are pressed, close the program
            if (value != JOptionPane.YES_OPTION) {
                System.exit(0);
                // Else, start next part of the program
            }  else {
                new CreateNewMasterPassword();
            }
        }
    }
    // Start the program
    public static void main(final String[] args) {
        new PasswordManagerGUI();
    }
}
