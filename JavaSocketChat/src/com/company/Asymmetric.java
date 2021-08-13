package com.company;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.xml.bind
        .DatatypeConverter;

public class Asymmetric {
    private static final String RSA = "RSA";

    // Generating public & private keys
    // using RSA algorithm.
    public static KeyPair generateRSAKkeyPair() throws Exception {
        SecureRandom secureRandom = new SecureRandom();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);

        keyPairGenerator.initialize(2048, secureRandom);
        return keyPairGenerator.generateKeyPair();
    }

    // Encryption function which converts
    // the plainText into a cipherText
    // using private Key.
    public static byte[] do_RSAEncryption(String plainText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.ENCRYPT_MODE, privateKey);

        return cipher.doFinal(plainText.getBytes());
    }

    // Decryption function which converts
    // the ciphertext back to the
    // original plaintext.
    public static String do_RSADecryption(byte[] cipherText, PublicKey publicKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        byte[] result = cipher.doFinal(cipherText);

        return new String(result);
    }

    public static String encrypt(String plainText, KeyPair keypair) throws Exception {
        byte[] cipherText = do_RSAEncryption(plainText, keypair.getPrivate());
        return DatatypeConverter.printHexBinary(cipherText);
    }

    public static String decryt(String plainText, KeyPair keypair) throws Exception {
        byte[] cipherText = do_RSAEncryption(plainText, keypair.getPrivate());
        return do_RSADecryption(cipherText, keypair.getPublic());
    }

    // Driver code
    public static void main(String args[]) throws Exception {
        KeyPair keypair = generateRSAKkeyPair();

        String plainText = "This is the PlainText " + "I want to Encrypt using RSA.";

        System.out.println("The Encrypted Text is: " + encrypt(plainText, keypair));

        System.out.println("The decrypted text is: " + decryt(plainText, keypair));
    }
}
