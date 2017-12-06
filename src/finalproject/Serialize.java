/*
    Program: Serialize.java
    Written by: Joshua Stone
    Description: Small class storing a property object in a format that can be written to the disk
    Challenges: Finding a format that's supported by Java and works with the password storage
    Time Spent: 20 minutes

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/02/17     Joshua Stone    Initial commit
*/

package finalproject;

import java.io.*;
import java.util.Properties;

public class Serialize {
    public static byte[] convertProperties(final Properties properties) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        // Use built-in store() to dump to a stream
        properties.store(outputStream, null);
        // Now put stream into a byte array
        return outputStream.toByteArray();
    }
}
