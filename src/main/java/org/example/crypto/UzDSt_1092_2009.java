package org.example.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.example.utils.Requests;
import org.paynet.util.encoders.Hex;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Arrays;

public class UzDSt_1092_2009 {
    public KeyPair generateKeyPair() {
        return generationKeyPair();
    }

    public String signGenerate(String privKey, String path) {
        System.out.println(path);

        try {
            return ByteEncode.encodeHexString(signGeneration(readPrivateKey(privKey), Files.readAllBytes(Paths.get(path))));
        } catch (IOException e) {
            System.err.println("exception : UzDSt_1092_2009().signGenerate() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    public boolean verifySignature(String pubKey, String path, String sign) {
        System.out.println(path);
        try {
            return verification(readPublicKey(pubKey), Files.readAllBytes(Paths.get(path)), ByteEncode.decodeHexString(sign));
        } catch (IOException e) {
            System.err.println("exception : UzDSt_1092_2009().verifySignature() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    private KeyPair generationKeyPair() {
        try {
            KeyPairGenerator g = KeyPairGenerator.getInstance("ECGOST3410", "BC");
            g.initialize(new ECNamedCurveGenParameterSpec("GostR3410-2001-CryptoPro-A"), new SecureRandom());
            return g.generateKeyPair();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            System.err.println("exception: UzDSt_1092_2009().generationKeyPair() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    private byte[] signGeneration(PrivateKey privateKey, byte[] message) {
        try {
            Signature sgr = Signature.getInstance("ECGOST3410", "BC");
            sgr.initSign(privateKey, new SecureRandom());
            sgr.update(message);
            return sgr.sign();
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            System.err.println("exception: UzDSt_1092_2009().signGeneration() => " + e.getCause());
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
            System.err.println("exception: UzDSt_1092_2009().verification() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    private PrivateKey readPrivateKey(String privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("ECGOST3410", "BC");
            PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(ByteEncode.decodeHexString(privateKey));
            return keyFactory.generatePrivate(privateKeySpec);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
            System.err.println("exception : UzDSt_1092_2009().readPrivateKey() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    private PublicKey readPublicKey(String publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("ECGOST3410", "BC");
            X509EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(ByteEncode.decodeHexString(publicKey));
            return keyFactory.generatePublic(publicKeySpec);
        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeySpecException e) {
            System.err.println("exception : UzDSt_1092_2009().readPublicKey() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }
}