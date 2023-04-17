package com.clickpay.utils;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

public class CryptoUtil {

    public static final String CLASSPATH = "D:\\Intellij Projects\\Clickpay\\clickpay\\src\\main\\resources\\";
    private static final String KEY = "OKqngubowwD2ghOb1j/zI+t7jTgwgfc+";
    private static String ALGORITHM = null;
    private static String TRANSFORMATION = null;

    public static void setData(String data) {
        TRANSFORMATION = data;
        ALGORITHM = data;
    }

    public static void start(String[] args) {
        CryptoUtil.setData(args[0]);
		/*CryptoUtil.encrypt("OKqngubowwD2ghOb1j/zI+t7jTgwgfc+",
				new File("D:\\Intellij Projects\\Clickpay\\clickpay\\src\\main\\resources\\application-qasim.properties"),
				new File("D:\\Intellij Projects\\Clickpay\\clickpay\\src\\main\\resources\\app.properties")
		);*/

        File file = new File(CLASSPATH + "application-qasim.properties");
        try {
            file.createNewFile();
        } catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }

        decrypt(args[1], new File(CLASSPATH + "app.properties"), file);
    }

    private static void encrypt(String key, File inputFile, File outputFile) {
        doCrypto(Cipher.ENCRYPT_MODE, key, inputFile, outputFile);
    }

    private static void decrypt(String key, File inputFile, File outputFile) {
        doCrypto(Cipher.DECRYPT_MODE, key, inputFile, outputFile);
    }

    private static void doCrypto(int cipherMode, String key, File inputFile,
                                 File outputFile) {
        try {
            Key secretKey = new SecretKeySpec(key.getBytes(), ALGORITHM);
            Cipher cipher = Cipher.getInstance(TRANSFORMATION);
            cipher.init(cipherMode, secretKey);

            FileInputStream inputStream = new FileInputStream(inputFile);
            byte[] inputBytes = new byte[(int) inputFile.length()];
            inputStream.read(inputBytes);

            byte[] outputBytes = cipher.doFinal(inputBytes);

            FileOutputStream outputStream = new FileOutputStream(outputFile);
            outputStream.write(outputBytes);

            inputStream.close();
            outputStream.close();

        } catch (NoSuchPaddingException | NoSuchAlgorithmException
                 | InvalidKeyException | BadPaddingException
                 | IllegalBlockSizeException | IOException ex) {
            throw new RuntimeException("Error encrypting/decrypting file", ex);
        }
    }
}
