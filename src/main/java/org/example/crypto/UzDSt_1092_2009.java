package org.example.crypto;

import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.paynet.util.encoders.Hex;

import java.math.BigInteger;
import java.security.*;

public class UzDSt_1092_2009 {
    public void keyPair(){

        Security.addProvider(new BouncyCastleProvider());

        String text = "Nurislom";
        KeyPair keyPair = null;


        try {
            keyPair = generationTest2();

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


    private static KeyPair generationTest2() {

        try {

            KeyPairGenerator g = KeyPairGenerator.getInstance("ECGOST3410", "BC");
            g.initialize(new ECNamedCurveGenParameterSpec("GostR3410-2001-CryptoPro-A"), new SecureRandom());
            KeyPair p = g.generateKeyPair();

            return p;

//            PrivateKey sKey = p.getPrivate();
//            PublicKey vKey = p.getPublic();
//
//
//            System.out.println(new String(sKey.getEncoded()));
//            System.out.println(new String(vKey.getEncoded()));
//
//
//            System.out.println(new BigInteger(sKey.getEncoded()));
//            System.out.println(new BigInteger(vKey.getEncoded()));
//
//            System.out.println();


        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidAlgorithmParameterException e) {
            throw new RuntimeException(e);
        }


    }


    private static byte[] signGenerate(PrivateKey privateKey, String text) {

        byte[] message = text.getBytes();
        Signature sgr = null;
        byte[] sigBytes = null;
        try {
            sgr = Signature.getInstance("ECGOST3410", "BC");

            sgr.initSign(privateKey, new SecureRandom());

            sgr.update(message);

            sigBytes = sgr.sign();


//            if (!sgr.verify(sigBytes)) {
//                System.out.println(false);
//
//            } else System.out.println(true);

//            BigInteger[] sig = decode(sigBytes);

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            System.err.println("sign error => " + e.getMessage());
            throw new RuntimeException(e);
        }
        return sigBytes;

    }


    private static void verification(PublicKey publicKey, String text, byte[] sign) {

        try {
            Signature sgr = Signature.getInstance("ECGOST3410", "BC");
            byte[] message = text.getBytes();
            sgr.initVerify(publicKey);
            sgr.update(message);

            if (!sgr.verify(sign))
                System.out.println("Imzo haqiqiy emas...");
            else System.out.println("Imzo haqiqiy...");

        } catch (NoSuchAlgorithmException | NoSuchProviderException | InvalidKeyException | SignatureException e) {
            throw new RuntimeException(e);
        }
    }

    /**********************************************************************************************************************/
    private static void generationTest()
            throws Exception {
//        Signature s = Signature.getInstance("GOST3410", "BC");
        KeyPairGenerator g = KeyPairGenerator.getInstance("GOST3410", "BC");
        byte[] data = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0};


        KeyPair p = g.generateKeyPair();

        PrivateKey sKey = p.getPrivate();
        PublicKey vKey = p.getPublic();

//        s.initSign(sKey);
//
//        s.update(data);

//        byte[] sigBytes = s.sign();
//
//        s = Signature.getInstance("GOST3410", "BC");
//
//        s.initVerify(vKey);
//
//        s.update(data);
//
//        if (!s.verify(sigBytes)) {
//            fail("GOST3410 verification failed");
//        }
//
//        //
//        // default initialisation test
//        //
//        s = Signature.getInstance("GOST3410", "BC");
//        g = KeyPairGenerator.getInstance("GOST3410", "BC");
//
//        p = g.generateKeyPair();
//
//        sKey = p.getPrivate();
//        vKey = p.getPublic();
//
//        s.initSign(sKey);
//
//        s.update(data);
//
//        sigBytes = s.sign();
//
//        s = Signature.getInstance("GOST3410", "BC");
//
//        s.initVerify(vKey);
//
//        s.update(data);
//
//        if (!s.verify(sigBytes)) {
//            fail("GOST3410 verification failed");
//        }

        //
        // encoded test
        //
//        KeyFactory f = KeyFactory.getInstance("GOST3410", "BC");
//
//        X509EncodedKeySpec x509s = new X509EncodedKeySpec(vKey.getEncoded());

/************      Kerakli pasti             **********/

        //
        // ECGOST3410 generation test
        //
//        s = Signature.getInstance("ECGOST3410", "BC");
        g = KeyPairGenerator.getInstance("ECGOST3410", "BC");

//        BigInteger mod_p = new BigInteger("57896044618658097711785492504343953926634992332820282019728792003956564821041"); //p
//
//        ECCurve curve = new ECCurve.Fp(
//            mod_p, // p
//            new BigInteger("7"), // a
//            new BigInteger("43308876546767276905765904595650931995942111794451039583252968842033849580414")); // b
//
//        ECParameterSpec ecSpec = new ECParameterSpec(
//                curve,
//                    new ECPoint.Fp(curve,
//                                   new ECFieldElement.Fp(mod_p,new BigInteger("2")), // x
//                                   new ECFieldElement.Fp(mod_p,new BigInteger("4018974056539037503335449422937059775635739389905545080690979365213431566280"))), // y
//                    new BigInteger("57896044618658097711785492504343953927082934583725450622380973592137631069619")); // q

        g.initialize(new ECNamedCurveGenParameterSpec("GostR3410-2001-CryptoPro-A"), new SecureRandom());

        p = g.generateKeyPair();

        sKey = p.getPrivate();
        vKey = p.getPublic();

        System.out.println(new String(sKey.getEncoded()));
        System.out.println(vKey.getEncoded().toString());

//        s.initSign(sKey);
//
//        s.update(data);
//
////        sigBytes = s.sign();
//
//        s = Signature.getInstance("ECGOST3410", "BC");
//
//        s.initVerify(vKey);
//
//        s.update(data);

//        if (!s.verify(sigBytes)) {
//            fail("ECGOST3410 verification failed");
//        }
/***
 //
 // encoded test
 //       */
//        f = KeyFactory.getInstance("ECGOST3410", "BC");
//
//        x509s = new X509EncodedKeySpec(vKey.getEncoded());
//        ECPublicKey eck1 = (ECPublicKey) f.generatePublic(x509s);
//
//        if (!eck1.getQ().equals(((ECPublicKey) vKey).getQ())) {
//            fail("public number not decoded properly");
//        }
//
//        if (!eck1.getParameters().equals(((ECPublicKey) vKey).getParameters())) {
//            fail("public parameters not decoded properly");
//        }
//
//        pkcs8 = new PKCS8EncodedKeySpec(sKey.getEncoded());
//        ECPrivateKey eck2 = (ECPrivateKey) f.generatePrivate(pkcs8);
//
//        if (!eck2.getD().equals(((ECPrivateKey) sKey).getD())) {
//            fail("private number not decoded properly");
//        }
//
//        if (!eck2.getParameters().equals(((ECPrivateKey) sKey).getParameters())) {
//            fail("private number not decoded properly");
//        }
//
//        eck2 = (ECPrivateKey) serializeDeserialize(sKey);
//        if (!eck2.getD().equals(((ECPrivateKey) sKey).getD())) {
//            fail("private number not decoded properly");
//        }
//
//        if (!eck2.getParameters().equals(((ECPrivateKey) sKey).getParameters())) {
//            fail("private number not decoded properly");
//        }
//
//        checkEquals(eck2, sKey);
//
//        if (!(eck2 instanceof PKCS12BagAttributeCarrier)) {
//            fail("private key not implementing PKCS12 attribute carrier");
//        }
//
//        eck1 = (ECPublicKey) serializeDeserialize(vKey);
//
//        if (!eck1.getQ().equals(((ECPublicKey) vKey).getQ())) {
//            fail("public number not decoded properly");
//        }
//
//        if (!eck1.getParameters().equals(((ECPublicKey) vKey).getParameters())) {
//            fail("public parameters not decoded properly");
//        }
//
//        checkEquals(eck1, vKey);
    }


}