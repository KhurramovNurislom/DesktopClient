package org.example.test;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.example.crypto.ByteEncode;
import org.example.utils.Requests;
import org.paynet.util.encoders.Hex;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class UzDSt_1092_2009String {

    public void generateKeyPair(String password) {

        Security.addProvider(new BouncyCastleProvider());

        KeyPair keyPair = generationKeyPair();

        /** Bazaga kalitlarni yuklash */
        new Requests().RequestkeysGen(Hex.toHexString(keyPair.getPrivate().getEncoded()), Hex.toHexString(keyPair.getPublic().getEncoded()), "keyName");

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

    public String signGenerate(String privKey, String text) {

        System.out.println("privKey => " + privKey);
        System.out.println(text);

//        String fileTextHex = FileToString(filePath);

//        System.out.println("fileText => " + fileTextHex);


//        String test = "nurislom";

//        byte[] bytesFile = ByteEncode.decodeHexString(fileText);

        byte[] textBytes = text.getBytes();

//        byte[] bytesFile = ByteEncode.decodeHexString(filePath);

//        System.out.println("asdasdasd => " + bytesFile);

        byte[] signByte = signGeneration(readPrivateKey(privKey), textBytes);

        System.out.println("signByte => " + Arrays.toString(signByte));

        String sign = ByteEncode.encodeHexString(signByte);

        System.out.println("sign => " + sign);

        return sign;
    }

    public boolean verifySignature(String sign, String text, String pubKey) {

        System.out.println("sign => " + sign);

        byte[] signByte = ByteEncode.decodeHexString(sign);

        System.out.println("\nsignByte => " + Arrays.toString(signByte));

        System.out.println("text => " + text);

        return verification(readPublicKey(pubKey), text, signByte);
    }

    private KeyPair generationKeyPair() {
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECGOST3410", "BC");
            g.initialize(new ECNamedCurveGenParameterSpec("GostR3410-2001-CryptoPro-A"), new SecureRandom());
            return g.generateKeyPair();
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

    private boolean verification(PublicKey publicKey, String text, byte[] sign) {
        Security.addProvider(new BouncyCastleProvider());
        try {
            Signature sgr = Signature.getInstance("ECGOST3410", "BC");

            byte[] message = text.getBytes();

            sgr.initVerify(publicKey);
            sgr.update(message);

            if (sgr.verify(sign)) {
                System.out.println("Imzo haqiqiy...");
                return true;
            } else {
                System.out.println("Imzo haqiqiy emas...");
                return false;
            }

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

    private String FileToString(String filePth) {
        try {
            byte[] faylBytes = Files.readAllBytes(Paths.get(filePth));
            // Faylni stringga konvertatsiya qilish
            return ByteEncode.encodeHexString(faylBytes);
        } catch (IOException e) {
            System.err.println("exception : UzDSt_1092_2009().FileToString() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}