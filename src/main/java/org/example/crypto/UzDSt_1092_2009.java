package org.example.crypto;

import org.bouncycastle.asn1.pkcs.Pfx;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.paynet.util.encoders.Hex;

import java.math.BigInteger;
import java.security.*;

public class UzDSt_1092_2009 {

    public void generateKeyPair(String password) {

        Security.addProvider(new BouncyCastleProvider());

        KeyPair keyPair = generationKeyPair();

        System.out.println("\n PrivateKey => " + Hex.toHexString(keyPair.getPrivate().getEncoded()));
        System.out.println("\n PublicKey => " + Hex.toHexString(keyPair.getPublic().getEncoded()));

        PFX pfx = new PFX();
        pfx.pfx();

    }



    public void generateKeyPair2() {

        Security.addProvider(new BouncyCastleProvider());

        String text = "Nurislom";


        try {
            KeyPair keyPair = generationKeyPair();

            byte[] sign = signGenerate(keyPair.getPrivate(), text);

            System.out.println("\n\n" + Hex.toHexString(keyPair.getPrivate().getEncoded()));
            System.out.println("\n\n" + Hex.toHexString(keyPair.getPublic().getEncoded()));
            System.out.println("\n\n" + keyPair.getPrivate().toString());
            System.out.println("\n\n" + keyPair.getPublic().toString());
            System.out.println(Hex.toHexString(sign));

            System.out.println("X: " + new BigInteger("2ce004f1422395361b49ad4a10e7336152957804708c04eb5c1f32298d821a87", 16));
            System.out.println("Y: " + new BigInteger("f505d55905d1a7627afa0a3e3e31e741fdc0d57a516032d26070863b4ea4270b", 16));


            verification(keyPair.getPublic(), text, sign);
        } catch (Exception e) {
            System.err.println(e.getMessage());
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

    private byte[] signGenerate(PrivateKey privateKey, String text) {

        byte[] message = text.getBytes();
        Signature sgr = null;
        byte[] sigBytes = null;
        try {
            sgr = Signature.getInstance("ECGOST3410", "BC");

            sgr.initSign(privateKey, new SecureRandom());

            sgr.update(message);

            sigBytes = sgr.sign();


        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            System.err.println("exception: UzDSt_1092_2009().signGenerate() => " + e.getMessage());
            throw new RuntimeException(e);
        }
        return sigBytes;

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

}