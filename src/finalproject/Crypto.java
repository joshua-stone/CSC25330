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
            // Failure to read could be caused by incorrect file permissions
            System.out.println("Failed to read file");
            System.exit(-1);
        }
        return data;
    }
    // Method for decrypting file in memory a password
    public static byte[] fileDecrypt(final byte[] input, final String password) throws BadPaddingException, IOException {
            final ByteArrayInputStream infile = new ByteArrayInputStream(input);
            final byte[] salt = new byte[Crypto.saltPad];
            final byte[] iv = new byte[Crypto.ivPad];
            // Read the first bytes of the byte array based on the known size of the salt pad and iv pad
            final int saltSize = infile.read(salt);
            final int ivSize = infile.read(iv);
            // Finding that the salt and iv pads are too small means the file doesn't contain valid data
            if (saltSize + ivSize < Crypto.saltPad + Crypto.ivPad) {
                System.out.println("File is too small");
                System.exit(-1);
            }
            // Get ready to decrypt the file with a secret key and read it into memory
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
    // Takes a byte array representing use data, and encrypts it using a password and stores it to the disk
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
            // Write salt, iv, and encrypted stream to file, in that order
            outFile.write(salt);
            outFile.write(iv);
            outFile.write(encrypted);

        } catch (BadPaddingException | IllegalBlockSizeException | InvalidKeyException | NoSuchAlgorithmException | NoSuchPaddingException | ShortBufferException e) {
            // There are many ways for an exception to be caused, but not making the program quit could help the user fix the problem
            // before attempting to save again
            System.out.println("Error in encrypting.");
        }
    }
    // Generates a key for decrypting a file
    private static SecretKey getSecret(final String password, final byte[] salt) {
        SecretKey secret = null;

        try {
            final SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
            // Use the password and random salt with key size and number of iterations to create a secret key
            final KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, Crypto.iterations, Crypto.keyLength);
            final SecretKey secretKey = factory.generateSecret(keySpec);
            secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            // Close the program if there's a failure, as the program absolutely needs a key to decrypt data
            System.out.println("Couldn't generate secret key");
            System.exit(-1);
        }
        return secret;
    }
    // Generates a random salt as a source of uniqueness
    private static byte[] getSalt() {
        final byte[] salt = new byte[saltPad];
        // Read secureRandom into salt until reaches the capacity set by saltPad
        final SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        return salt;
    }
    // Generate an initialization vector for increased uniqueness
    // https://crypto.stackexchange.com/questions/732/why-use-an-initialization-vector-iv
    private static byte[] getIV(final Cipher cipher) {
        byte[] iv = new byte[ivPad];

        try {
            final AlgorithmParameters params = cipher.getParameters();
            // IVs are essentially the starting values for an input to a cryptographic cipher, therefore random values
            // make guessing more difficult
            iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        } catch (InvalidParameterSpecException e) {
            // Close if IV can't  be generated as it's required for strong encryption
            System.out.println("Couldn't generate IV");
            System.exit(-1);
        }
        return iv;
    }
    // A simple password generator that produces a string of random numbers and letters of a specified length
    public static String randomString(final int length) {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        // Strong passwords require a good source of entropy to make cracking difficult
        final SecureRandom rng = new SecureRandom();
        char[] text = new char[length];
        // Essentially picks a random letter from characters and adds it to text until it's full
        for (int i = 0; i < length; i++) {
            text[i] = characters.charAt(rng.nextInt(characters.length()));
        }
        return String.valueOf(text);
    }
}