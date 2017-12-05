/*
    Program: PasswordManager.java
    Written by: Joshua Stone
    Description:
    Challenges:
    Time Spent:

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    11/17/17     Joshua Stone    Initial commit
*/

package finalproject;

import java.io.ByteArrayOutputStream;

import java.io.FileInputStream;
import java.util.Properties;

public class PasswordManager {

    public PasswordManager() {
        Properties config = new Properties();
        try (FileInputStream inputFile = new FileInputStream("password.xml")) {
            config.load(inputFile);
        } catch (Exception e) {
            System.out.println("Config file failed.");
        }
        int index = 0;
        while (true) {
            try {
                //config.getProperty(String.format("%s", index));
                final String label = config.getProperty(String.format("label_%s", index));
                final String user = config.getProperty(String.format("user_%s", index));
                final String pass = config.getProperty(String.format("pass_%s", index));

                if (label == null || user == null || pass == null) {
                    break;
                } else {
                    System.out.println(label + user + pass + index);
                    index++;
                }

            } catch (Exception e) {

            }

        }
    }
    public static void main(final String[] args) {
        byte[] test = Crypto.readFile("/home/jstone/IdeaProjects/CSC25330/encryptedfile.des");

        //ByteArrayOutputStream o = Crypto.fileDecrypter(test, "javapapers");
        System.out.println(test.length);
        //System.out.println(o);
        //ByteArrayOutputStream f1 = Crypto.fileDecrypt("/home/jstone/IdeaProjects/CSC25330/encryptedfile.des", "javapapers");
        //System.out.print(f1);

        //Crypto.fileEncrypt(f1.toByteArray(), "test", "javapapers");
        //ByteArrayOutputStream f2 = Crypto.fileDecrypt("/home/jstone/IdeaProjects/CSC25330/test", "javapapers");
        //System.out.print(f2);
    }
}
