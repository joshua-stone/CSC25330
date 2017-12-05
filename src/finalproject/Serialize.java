package finalproject;

import java.io.*;
import java.util.Properties;

public class Serialize {
    public static byte[] convertProperties(final Properties properties) throws IOException {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

        properties.store(outputStream, null);

        return outputStream.toByteArray();
    }
}
