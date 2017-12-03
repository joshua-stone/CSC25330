/*
    Program: PasswordManagerMainWindow.java
    Written by: Joshua Stone
    Description:
    Challenges:
    Time Spent:

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    11/17/17     Joshua Stone    Initial commit
    11/17/17     Joshua Stone    Create constructor for PasswordManagerMainWindow
    11/17/17     Joshua Stone    Use Properties() for parsing file data
    11/17/17     Joshua Stone
*/

package finalproject;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PasswordManagerMainWindow {
    private String password;
    private Properties passwordStore;
    public PasswordManagerMainWindow(final String password, final byte[] decryptedBlob) {
        this.password = password;
        InputStream input = new ByteArrayInputStream(decryptedBlob);
        this.passwordStore = new Properties();

        try {
            this.passwordStore.load(input);
        } catch (IOException e) {

        }
            System.out.println(this.passwordStore.getProperty("user_2"));
    }
}
