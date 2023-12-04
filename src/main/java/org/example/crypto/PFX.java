//package org.example.crypto;
//
////import org.bouncycastle.jce.provider.BouncyCastleProvider;
//
//
//import lombok.Getter;
//import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
//import org.bouncycastle.jce.provider.BouncyCastleProvider;
//import org.bouncycastle.jce.spec.GOST3410ParameterSpec;
//import org.example.Main;
//import org.example.crypto.TestCertificateGen;
//import org.paynet.util.encoders.Hex;
//
//import java.io.*;
//import java.security.*;
//import java.security.cert.Certificate;
//import java.security.cert.CertificateException;
//import java.security.cert.X509Certificate;
//import java.util.Arrays;
//import java.util.Enumeration;
//
//// PFX faylni o'qish
//@Getter
//public class PFX {
//
//    private Long number = 1L;
//
//    public String getFileName() {
//        if (this.number.toString().length() == 1) {
//            return "DS00000" + number;
//        } else if (this.number.toString().length() == 2) {
//            return "DS0000" + number;
//        } else if (this.number.toString().length() == 3) {
//            return "DS000" + number;
//        } else if (this.number.toString().length() == 4) {
//            return "DS00" + number;
//        } else if (this.number.toString().length() == 5) {
//            return "DS0" + number;
//        } else if (this.number.toString().length() == 6) {
//            return "DS" + number;
//        } else return null;
//    }
//
//    public void pfx() {
//
//        Security.addProvider(new BouncyCastleProvider());
//        try {
//
//            GOST3410ParameterSpec gost3410P = new GOST3410ParameterSpec(CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_B.getId());
//
//            KeyPairGenerator g = KeyPairGenerator.getInstance("GOST3410", "BC");
//            g.initialize(gost3410P, new SecureRandom());
//            KeyPair p = g.generateKeyPair();
//
//            PrivateKey sKey = p.getPrivate();
//            PublicKey vKey = p.getPublic();
//
//            PFX pfx = new PFX();
//            pfx.certificate();
//
//            pfx.fileRead();
////            pfx.fileEdit();
//            pfx.setNumber();
//            pfx.generateCertificate();
//            pfx.keyStoreTest(sKey, vKey);
//        } catch (Exception e) {
//            System.err.println("exception:");
//            throw new RuntimeException(e);
//        }
//
//    }
//
//
//    private void fileEdit() {
//        KeyStore keyStore;
//        {
//            try {
//                keyStore = KeyStore.getInstance("PKCS12");
//            } catch (KeyStoreException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//        char[] password = "24061996".toCharArray();
//
//        try (
//                FileInputStream fis = new FileInputStream("C:\\Users\\programmer\\Desktop\\PFX_file/" + getFileName() + ".pfx")) {
//            keyStore.load(fis, password);
//        } catch (CertificateException | IOException | NoSuchAlgorithmException e) {
//            throw new RuntimeException(e);
//        }
//
//    }
//
//    private void fileRead() {
//        // PFX faylni o'qish
//        KeyStore keyStore = null;
//        try {
//            keyStore = KeyStore.getInstance("PKCS12");
//        } catch (KeyStoreException e) {
//            throw new RuntimeException(e);
//        }
//        char[] password = "24061996".toCharArray();
//
//        try (FileInputStream fis = new FileInputStream("C:\\DSKEYS\\DS4997124990002_Fazolat_Mukimova_24061996.pfx")) {
//            keyStore.load(fis, password);
//        } catch (CertificateException | NoSuchAlgorithmException | IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public void setNumber() {
//        this.number += 1;
//    }
//
//    private void certificate() {
////        Security.addProvider(new BouncyCastleProvider());
//
//        String pfxFile = "C:\\DSKEYS\\DS4997124990002_Fazolat_Mukimova_24061996.pfx";
//        char[] password = "24061996".toCharArray();
//
//        // PFX faylni o'qish
//        KeyStore keyStore = null;
//        try (FileInputStream fis = new FileInputStream(pfxFile)) {
//            keyStore = KeyStore.getInstance("PKCS12");
//            keyStore.load(fis, password);
//        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
//            throw new RuntimeException(e);
//        }
//
//        // PFX fayl ichidagi kalit aliaslarini olish
//        Enumeration<String> aliases = null;
//        try {
//            aliases = keyStore.aliases();
//
//        } catch (KeyStoreException e) {
//            throw new RuntimeException(e);
//        }
//        while (aliases.hasMoreElements()) {
//            String alias = aliases.nextElement();
//            System.out.println("Alias: " + alias);
////--------------------------------------------------------------------//
//            X509Certificate cert = null;
//            try {
//                cert = (X509Certificate) keyStore.getCertificate(alias);
//            } catch (KeyStoreException e) {
//                throw new RuntimeException(e);
//            }
//// save your cert inside the keystore
//            try {
//                keyStore.setCertificateEntry("YourCertAlias", cert);
//            } catch (KeyStoreException e) {
//                throw new RuntimeException(e);
//            }
//// create the outputstream to store the keystore
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream("C:\\Users\\programmer\\Desktop\\PFX_file/" + getFileName() + ".pfx");
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//// store the keystore protected with password
//            try {
//                keyStore.store(fos, password);
//            } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
//                throw new RuntimeException(e);
//            }
//        }
//
//    }
//
//
//    public void generateCertificate() throws Exception {
//
//        Security.addProvider(new BouncyCastleProvider());
//
//        GOST3410ParameterSpec gost3410P = new GOST3410ParameterSpec(CryptoProObjectIdentifiers.gostR3410_94_CryptoPro_B.getId());   //gostR3410_2001_CryptoPro_A
//
//        KeyPairGenerator g = KeyPairGenerator.getInstance("GOST3410", "BC");
//        g.initialize(gost3410P, new SecureRandom());
//        KeyPair p = g.generateKeyPair();
//
//
////        KeyPair p = Main.getKeyPair();
//
//
//        PrivateKey sKey = p.getPrivate();
//        PublicKey vKey = p.getPublic();
//
//        System.out.println("\n\n  PFX privateKey => " + Hex.toHexString(sKey.getEncoded()));
//        System.out.println("\n  PFX publicKey => " + Hex.toHexString(vKey.getEncoded()));
//
//
//        KeyStore ks = KeyStore.getInstance("PKCS12");
//
//        ks.load(null, null);
//
//        X509Certificate cert = TestCertificateGen.createSelfSignedCert("CN=Test", "GOST3411withGOST3410", new KeyPair(vKey, sKey));
//
//        ks.setKeyEntry("Anvarov Sharofiddin Sobitali o'g'li 19970.08.08 Farg'ona viloyati quva tumani", sKey, "gost".toCharArray(), new Certificate[]{cert});
//
//        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
//        ks.store(bOut, "gost".toCharArray());
//
//        ks = KeyStore.getInstance("PKCS12");
//
//        ks.load(new ByteArrayInputStream(bOut.toByteArray()), "gost".toCharArray());
//
//        Enumeration<String> aliases = null;
//        try {
//            aliases = ks.aliases();
//
//        } catch (KeyStoreException e) {
//            throw new RuntimeException(e);
//        }
//
//        while (aliases.hasMoreElements()) {
//            String alias = aliases.nextElement();
//            System.out.println("Alias: " + alias);
////--------------------------------------------------------------------//
//            try {
//                cert = (X509Certificate) ks.getCertificate(alias);
//            } catch (KeyStoreException e) {
//                throw new RuntimeException(e);
//            }
//// save your cert inside the keystore
//            try {
//                ks.setCertificateEntry("YourCertAlias", cert);
//            } catch (KeyStoreException e) {
//                throw new RuntimeException(e);
//            }
//// create the outputstream to store the keystore
//            FileOutputStream fos = null;
//            try {
//                fos = new FileOutputStream("C:\\Users\\user\\Desktop\\" + getFileName() + ".pfx");
//            } catch (FileNotFoundException e) {
//                throw new RuntimeException(e);
//            }
//// store the keystore protected with password
//            try {
//                ks.store(fos, "gost".toCharArray());
//            } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
//                System.err.println("exception: PFX().generateCertificate() => " + e.getMessage());
//                throw new RuntimeException(e);
//            }
//        }
//    }
//
//    private void keyStoreTest(PrivateKey sKey, PublicKey vKey)
//            throws Exception {
//        //
//        // keystore test
//        //
//        KeyStore ks = KeyStore.getInstance("PKCS12");
//
//        ks.load(null, null);
//
//        X509Certificate cert = TestCertificateGen.createSelfSignedCert("CN=Test", "GOST3411withGOST3410", new KeyPair(vKey, sKey));
//
//        ks.setKeyEntry("gost", sKey, "gost".toCharArray(), new Certificate[]{cert});
//
//        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
//
//        ks.store(bOut, "gost".toCharArray());
//
//        ks = KeyStore.getInstance("PKCS12");
//
//        ks.load(new ByteArrayInputStream(bOut.toByteArray()), "gost".toCharArray());
//    }
//
//}