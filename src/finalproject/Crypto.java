package finalproject;

import java.io.*;
import java.io.RandomAccessFile;
import java.security.*;
import java.security.spec.InvalidParameterSpecException;
import java.security.spec.KeySpec;
import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

public class Crypto {
    private static final int saltOffset = 0;
    private static final int ivOffset = 16;
    private static final int saltPad = 16;
    private static final int ivPad = 16;
    private static final int bufferSize = 64;
    private static final int iterations = 65536;
    private static final int keyLength = 256;

    public static byte[] readFile(final String inputFile) {
        byte[] data;

        try(RandomAccessFile input = new RandomAccessFile(inputFile, "r")) {
            data = new byte[(int)input.length()];
            input.readFully(data);
        } catch (Exception e) {
            data = null;
        }
        return data;
    }
    public static byte[] fileDecrypt(final byte[] input, final String password) throws IOException {
            ByteArrayInputStream infile = new ByteArrayInputStream(input);
            byte[] salt = new byte[saltPad];
            byte[] iv = new byte[ivPad];

            infile.read(salt);
            infile.read(iv);

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            SecretKey secret = getSecret(password, salt);

            Cipher cipher = null;
            try {
                cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
            } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidAlgorithmParameterException | InvalidKeyException e) {
                System.out.println("Invalid algorithm available");
                System.exit(2);
            }
            byte[] in = new byte[bufferSize];
            int read;
            while ((read = infile.read(in)) != -1) {
                outputStream.write(cipher.update(in, 0, read));
            }
            try {
                byte[] output = cipher.doFinal();
                outputStream.write(output);
                outputStream.flush();
            } catch (BadPaddingException | IllegalBlockSizeException e) {
                throw new IOException();
            }

            return outputStream.toByteArray();
    }
    public static void fileEncrypt(final byte[] inputStream, final String outputFile, final String password) {
        try (FileOutputStream outFile = new FileOutputStream(outputFile)) {

            byte[] salt = getSalt();

            SecretKey secret = getSecret(password, salt);

            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);

            byte[] iv = getIV(cipher);
            byte[] encrypted = new byte[cipher.getOutputSize(inputStream.length)];

            int enc_len = cipher.update(inputStream, 0, inputStream.length, encrypted, 0);

            cipher.doFinal(encrypted, enc_len);

            outFile.write(salt);
            outFile.write(iv);
            outFile.write(encrypted);

        } catch (Exception e) {

        }
    }
    private static SecretKey getSecret(final String password, final byte[] salt) {
        SecretKey secret;
        try {
            SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");

            KeySpec keySpec = new PBEKeySpec(password.toCharArray(), salt, iterations, keyLength);
            SecretKey secretKey = factory.generateSecret(keySpec);
            secret = new SecretKeySpec(secretKey.getEncoded(), "AES");
        } catch (Exception e) {
            secret = null;
        }
        return secret;
    }
    private static byte[] getSalt() {
        final byte[] salt = new byte[saltPad];

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(salt);

        return salt;
    }
    private static byte[] getIV(final Cipher cipher) {
        byte[] iv;
        try {
            AlgorithmParameters params = cipher.getParameters();

            iv = params.getParameterSpec(IvParameterSpec.class).getIV();
        } catch (InvalidParameterSpecException e) {
            iv = null;
        }
        return iv;
    }
}