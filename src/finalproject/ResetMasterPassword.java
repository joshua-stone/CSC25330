/*
    Program: ResetMasterPassword.java
    Written by: Joshua Stone
    Description: A subclass of CreateNewMasterPassword for resetting a password during a session
    Challenges: Making sure events execute actions correctly
    Time Spent: 1 hour

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/06/17     Joshua Stone    Initial commit
    12/06/17     Joshua Stone    Inherit from CreateNewMasterPassword
    12/06/17     Joshua Stone    Make constructor call superclass along with disabling parent window
    12/06/17     Joshua Stone    Override initMainWindow()
    12/06/17     Joshua Stone    Override cancel()
    12/06/17     Joshua Stone    Add serialVersionUID to pass linting
*/

package finalproject;

// Create a password reset window above the parent window
public class ResetMasterPassword extends CreateNewMasterPassword {
    private static final long serialVersionUID = 1L;
    private final PasswordManagerMainWindow passwordManagerMainWindow;

    public ResetMasterPassword(final PasswordManagerMainWindow passwordManagerMainWindow) {
        // Does mostly what CreateMasterPassword does, except it takes a reference to a main window and disables it for
        // safer password resetting
        super();
        this.passwordManagerMainWindow = passwordManagerMainWindow;
        this.setLocationRelativeTo(this.passwordManagerMainWindow);
        // Disable main window to prevent data entry that could interfere with the password reset
        this.passwordManagerMainWindow.setEnabled(false);
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
    }
    // Set the new password, close this window, and re-enable the main window
    @Override
    protected void initMainWindow() {
        this.passwordManagerMainWindow.setEnabled(true);
        this.dispose();
        this.passwordManagerMainWindow.setMasterPassword(this.getPassword());
    }
    // Instead of closing the whole program, close this window and leave main window running
    @Override
    protected void cancel() {
        this.passwordManagerMainWindow.setEnabled(true);
        this.dispose();
    }
}