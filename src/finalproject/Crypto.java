/*
    Program: PasswordManagerStartup.java
    Written by: Joshua Stone
    Description: A collection of cryptographic methods for secure file storage
    Challenges: Finding a way to get data from a file and decrypt and encrypt in a reliable manner
    Time Spent: 3 hours

    Revision History:
    Date:        By:             Action:
    ---------------------------------------------------
    12/02/17     Joshua Stone    Initial commit
    12/05/17     Joshua Stone    Use AES encryption for storing the dumped password file
    12/05/17     Joshua Stone    Make sure a salt and initialization vector are used
    12/05/17     Joshua Stone    Create simple password generator method using letters and numbers
    12/05/17     Joshua Stone    Implement methods that encrypt a file and can decrypt as well
    12/05/17     Joshua Stone    Stabilize final file format
    12/05/17     Joshua Stone    Add check for signs of corruption

*/
package finalproject;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;

public class Crypto {
    private static final int saltPad = 16;
    private static final int ivPad = 16;
    private static final int bufferSize = 64;
    private static final int iterations = 65536;
    private static final int keyLength = 128;
    private static final String algorithm = "AES/CBC/PKCS5Padding";
    // A method for reading a file into memory for further processing
    public static byte[] readFile(final String inputFile) {
        byte[] data = null;

        try(final RandomAccessFile input = new RandomAccessFile(inputFile, "r")) {
            // Create a byte array of the same length as the file and read every byte into it
            data = new byte[(int)input.length()];
            input.readFully(data);
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
            System.exit(-1);
        } catch (IOException e) {
            System.out.println("Failed to read file");
            System.exit(-1);
        }
        return data;
    }
    public static byte[] fileDecrypt(final byte[] input, final String password) throws BadPaddingException, IOException {
            final ByteArrayInputStream infile = new ByteArrayInputStream(input);
            final byte[] salt = new byte[Crypto.saltPad];
            final byte[] iv = new byte[Crypto.ivPad];

            // Read the first bytes of the byte array based on the size of the salt pad and iv pad
            final int saltSize = infile.read(salt);
            final int ivSize = infile.read(iv);

            if (saltSize + ivSize < saltSize + Crypto.ivPad) {
                System.out.println("Salt and iv pads are too small");
                System.exit(-1);
            }
            final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            final SecretKey secret = getSecret(password, salt);

            Cipher cipher = null;
            try {
                // https://docs.oracle.com/javase/9/docs/api/javax/crypto/Cipher.html
                cipher = Cipher.getInstance(Crypto.algorithm);
                cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
                // Many different exceptions can be caused from attempt to decrypt, so just fail if any occur
                System.out.println("Invalid algorithm available");
                System.exit(2);
            }
            // File should be read in chunks
            final byte[] chunk = new byte[Crypto.bufferSize];

            int read;
            // Returns the number of bytes read into chunks
            while ((read = infile.read(chunk)) != -1) {
                // Write out multi-part decryption to stream, with no offset
                outputStream.write(cipher.update(chunk, 0, read));
            }
            // Finalize multi-part decryption
            try {
                final byte[] output = cipher.doFinal();
                outputStream.write(output);
                outputStream.flush();
            } catch (BadPaddingException e) {
                throw new BadPaddingException();
            } catch (IllegalBlockSizeException e) {
                throw new IOException();
            }
            return outputStream.toByteArray();
    }
    public static void fileEncrypt(final byte[] inputStream, final String outputFile, final String password) throws IOException {
        try (final FileOutputStream outFile = new FileOutputStream(outputFile)) {

            final byte[] salt = getSalt();

            final SecretKey secret = getSecret(password, salt);
            // Use the same cipher for encryption and decryption
            final Cipher cipher = Cipher.getInstance(Crypto.algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, secret);

            final byte[] iv = getIV(cipher);
            final byte[] encrypted = new byte[cipher.getOutputSize(inputStream.length)];
            // Essentially do the opposite of fileDecrypt by turning plaintext into encrypted blocks
            int enc_len = cipher.update(inputStream, 0, inputStream.length, encrypted, 0);
            // Finalize encryption
            cipher.doFinal(encrypted, enc_len);
            // Write salt, iv, and encrypted stream to file
            outFile.write(salt);
            outFile.write(iv);
            outFile.write(encrypted);

        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | ShortBufferException e) {
            System.out.println("Error in encrypting.");
        }
    }
    // Generates a secret key
    private static SecretKey getSecret(final String password, final byte[] salt) {
        SecretKey secret = null;

        try {
            final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            // Use the password and random salt with key size and number of iterations to generate a secret
            final KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, Crypto.iterations, Crypto.keyLength);
            final SecretKey secretKey = factory.generateSecret(keySpec);
            secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            System.out.println("Couldn't generate secret key");
            System.exit(-1);
        }
        return secret;
    }
    // Generates a random salt as a source of uniqueness
    private static byte[] getSalt() {
        final byte[] salt = new byte[saltPad];

        final SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        return salt;
    }
    // Generate an initialization vector for increased uniqueness
    private static byte[] getIV(final Cipher cipher) {
        byte[] iv = null;
        try {
            final AlgorithmParameters params = cipher.getParameters();

            iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        } catch (InvalidParameterSpecException e) {
            System.out.println("Couldn't generate IV");
            System.exit(-1);
        }
        return iv;
    }
    // A simple password generator that produces a string of random numbers and letters of a specified length
    public static String randomString(final int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

        final SecureRandom rng = new SecureRandom();
        char[] text = new char[length];
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return String.valueOf(text);
    }
}