package org.example.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.example.utils.Requests;
import org.paynet.util.encoders.Hex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

public class UzDSt_1092_2009 {

    public void generateKeyPair(String password) {

        Security.addProvider(new BouncyCastleProvider());

        KeyPair keyPair = generationKeyPair();

//        System.out.println("\n PrivateKey => " + Hex.toHexString(keyPair.getPrivate().getEncoded()));
//
//        System.out.println("\n PublicKey => " + Hex.toHexString(keyPair.getPublic().getEncoded()));

        /** Bazaga kalitlarni yuklash */
        try {
            new Requests().RequestkeysGen(Hex.toHexString(keyPair.getPrivate().getEncoded()), Hex.toHexString(keyPair.getPublic().getEncoded()), "keyName");
        } catch (IOException e) {
            System.err.println("exception: UzDSt_1092_2009().generateKeyPair() => " + e.getMessage());
            throw new RuntimeException(e);
        }

        /** PFX file integratsiya */
//        PFX pfx = new PFX();
//        pfx.pfx();

//        Main.setKeyPair(keyPair);
//        PFX pfx = new PFX();
//        try {
//            pfx.generateCertificate();
//        } catch (Exception e) {
//            System.err.println("exception : UzDSt_1092_2009().generateKeyPair() => " + e.getMessage());
//            throw new RuntimeException(e);
//        }
        /** /PFX file integratsiya */


    }

    public String signGenerate(String privKey, InputStream is) {

        System.out.println(privKey);

        try {
            byte[] array = is.readAllBytes();
            byte[] bytes = signGeneration(readPrivateKey(privKey), array);
            String sign = new ByteEncode().encodeHexString(bytes);
            System.out.println("Sign => " + sign);
            return sign;
        } catch (IOException e) {
            System.err.println("exception : UzDSt_1092_2009().signGenerate() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private KeyPair generationKeyPair() {
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECGOST3410", "BC");
            g.initialize(new ECNamedCurveGenParameterSpec("GostR3410-2001-CryptoPro-A"), new SecureRandom());
            KeyPair p = g.generateKeyPair();
            return p;
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            System.err.println("exception: UzDSt_1092_2009().generationKeyPair() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private byte[] signGeneration(PrivateKey privateKey, byte[] message) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            Signature sgr = Signature.getInstance("ECGOST3410", "BC");
            sgr.initSign(privateKey, new SecureRandom());
            sgr.update(message);
            return sgr.sign();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            System.err.println("exception: UzDSt_1092_2009().signGenerate() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void verification(PublicKey publicKey, String text, byte[] sign) {

        try {
            Signature sgr = Signature.getInstance("ECGOST3410", "BC");
            byte[] message = text.getBytes();
            sgr.initVerify(publicKey);
            sgr.update(message);

            if (!sgr.verify(sign))
                System.out.println("Imzo haqiqiy emas...");
            else System.out.println("Imzo haqiqiy...");

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            System.err.println("exception: UzDSt_1092_2009().verification() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private PrivateKey readPrivateKey(String privateKey) {
        Security.addProvider(new BouncyCastleProvider());
        byte[] encodedPrivateKey = ByteEncode.decodeHexString(privateKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("ECGOST3410", "BC");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(encodedPrivateKey);
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
            System.err.println("exception : UzDSt_1092_2009().readPrivateKey() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private PublicKey readPublicKey(String publicKey) {
        Security.addProvider(new BouncyCastleProvider());
        byte[] encodedPublicKey = ByteEncode.decodeHexString(publicKey);
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("ECGOST3410", "BC");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(encodedPublicKey);
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
            System.err.println("exception : UzDSt_1092_2009().readPublicKey() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}