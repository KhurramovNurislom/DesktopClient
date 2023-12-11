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

public class UzDSt_1092_2009File {

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

    public String signGenerate(String privKey, String filePath) {

        System.out.println("privKey => " + privKey);
        System.out.println(filePath);

        try {

            byte[] faylBytes = Files.readAllBytes(Paths.get(filePath));

            byte[] signBytes = signGeneration(readPrivateKey(privKey), faylBytes);
            System.out.println("signByte => " + Arrays.toString(signBytes));

            String sign = ByteEncode.encodeHexString(signBytes);

            System.out.println("sign => " + sign);

            return sign;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public boolean verifySignature(String sign, String path, String pubKey) {

        try {

            System.out.println("sign2 => " + sign);

            byte[] signBytes = ByteEncode.decodeHexString(sign);

            System.out.println("signBytes => " + Arrays.toString(signBytes));

            return verification(readPublicKey(pubKey), Files.readAllBytes(Paths.get(path)), signBytes);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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

    private boolean verification(PublicKey publicKey, byte[] message, byte[] sign) {

        try {

            Signature sgr = Signature.getInstance("ECGOST3410", "BC");

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
            throw new RuntimeException(e);
        }
    }

}