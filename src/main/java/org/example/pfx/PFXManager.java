package org.example.pfx;

import lombok.Getter;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

// PFX faylni o'qish
@Getter
public class PFXManager {

    private Long number = 1L;
    private String pfxFilePath = "C:\\Users\\user\\Desktop\\PFX_file/" + getFileName() + ".pfx";

    public PFXManager() {
    }

    public String getFileName() {
        if (this.number.toString().length() == 1) {
            return "DS00000" + number;
        } else if (this.number.toString().length() == 2) {
            return "DS0000" + number;
        } else if (this.number.toString().length() == 3) {
            return "DS000" + number;
        } else if (this.number.toString().length() == 4) {
            return "DS00" + number;
        } else if (this.number.toString().length() == 5) {
            return "DS0" + number;
        } else if (this.number.toString().length() == 6) {
            return "DS" + number;
        } else return null;
    }

    public void main() throws Exception {
        Security.addProvider(new BouncyCastleProvider());

        SecureRandom random = new SecureRandom();
        ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("GostR3410-2001-CryptoPro-A");
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECGOST3410");
        keyPairGenerator.initialize(spec, random);
        KeyPair p = keyPairGenerator.generateKeyPair();

        PrivateKey sKey = p.getPrivate();
        PublicKey vKey = p.getPublic();

        PFXManager main = new PFXManager();
//        main.certificate();
//        try {
//            main.fileRead();
//        } catch (UnrecoverableKeyException ex) {
//            throw new RuntimeException(ex);
//        } catch (KeyStoreException ex) {
//            throw new RuntimeException(ex);
//        } catch (NoSuchAlgorithmException ex) {
//            throw new RuntimeException(ex);
//        }
//        main.fileEdit();
//        main.setNumber();
        main.generateCertificate();
        main.keyStoreTest(sKey, vKey);
    }


    private void fileEdit() {

        char[] password = "24061996".toCharArray();
        try (FileInputStream fis = new FileInputStream(pfxFilePath)) {

            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, password);

        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException e) {

            throw new RuntimeException(e);
        }

    }

    private void fileRead() {

        // PFX faylni o'qish

        char[] password = "24061996".toCharArray();

        try (FileInputStream fis = new FileInputStream("C:\\DSKEYS\\DS4997124990002_Fazolat_Mukimova_24061996.pfx")) {

            KeyStore keyStore = KeyStore.getInstance("PKCS12");

            keyStore.load(fis, password);

        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            throw new RuntimeException(e);
        }
    }

    public void setNumber() {
        this.number += 1;
    }

    private void certificate() {
        // Security.addProvider(new BouncyCastleProvider());

        String pfxFile = "C:\\DSKEYS\\DS4997124990002_Fazolat_Mukimova_24061996.pfx";
        char[] password = "24061996".toCharArray();

        // PFX faylni o'qish

        try (FileInputStream fis = new FileInputStream(pfxFile)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, password);

            // PFX fayl ichidagi kalit aliaslarini olish
            Enumeration<String> aliases = keyStore.aliases();

            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println("Alias: " + alias);
                //--------------------------------------------------------------------//
                X509Certificate cert = null;
                try {
                    cert = (X509Certificate) keyStore.getCertificate(alias);
                } catch (KeyStoreException e) {

                    throw new RuntimeException(e);
                }
                // save your cert inside the keystore
                try {
                    keyStore.setCertificateEntry("YourCertAlias", cert);
                } catch (KeyStoreException e) {

                    throw new RuntimeException(e);
                }
                // create the outputstream to store the keystore
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream(pfxFilePath);
                } catch (FileNotFoundException e) {

                    throw new RuntimeException(e);
                }
                // store the keystore protected with password
                try {
                    keyStore.store(fos, password);
                } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {

                    throw new RuntimeException(e);
                }
            }


        } catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {

            throw new RuntimeException(e);
        }


    }


    public void generateCertificate() {

        Security.addProvider(new BouncyCastleProvider());
        try {
            SecureRandom random = new SecureRandom();
            ECNamedCurveParameterSpec spec = ECNamedCurveTable.getParameterSpec("GostR3410-2001-CryptoPro-A");
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("ECGOST3410");
            keyPairGenerator.initialize(spec, random);
            KeyPair p = keyPairGenerator.generateKeyPair();

            PrivateKey sKey = p.getPrivate();

            KeyStore ks = KeyStore.getInstance("PKCS12");

            ks.load(null, null);
            CreateCertificate signedCertificate = new CreateCertificate();
            X509Certificate cert = signedCertificate.managerCer();

            ks.setKeyEntry("Anvarov Sharofiddin Sobitali o'g'li 1997.08.08 Farg'ona viloyati Quva tumani", sKey, "gost".toCharArray(), new Certificate[]{cert});

            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            ks.store(bOut, "gost".toCharArray());

            ks = KeyStore.getInstance("PKCS12");

            ks.load(new ByteArrayInputStream(bOut.toByteArray()), "gost".toCharArray());

            Enumeration<String> aliases = ks.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.out.println("Alias: " + alias);
                //--------------------------------------------------------------------//
                try {
                    cert = (X509Certificate) ks.getCertificate(alias);
                } catch (KeyStoreException e) {
                    throw new RuntimeException(e);
                }
                // save your cert inside the keystore
                try {
                    ks.setCertificateEntry("YourCertAlias", cert);
                } catch (KeyStoreException e) {
                    throw new RuntimeException(e);
                }
                // create the outputstream to store the keystore
                FileOutputStream fos = null;
                try {
                    fos = new FileOutputStream("C:\\DSKEYS\\PFX_file/" + getFileName() + ".pfx");
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                }
                // store the keystore protected with password
                try {
                    ks.store(fos, "gost".toCharArray());
                } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
                    throw new RuntimeException(e);
                }
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }

    private void keyStoreTest(PrivateKey sKey, PublicKey vKey) {
        //
        // keystore test
        //
        KeyStore ks = null;
        try {
            ks = KeyStore.getInstance("PKCS12");
            ks.load(null, null);
            CreateCertificate signedCertificate = new CreateCertificate();
            X509Certificate cert = signedCertificate.managerCer();

            ks.setKeyEntry("gost", sKey, "gost".toCharArray(), new Certificate[]{cert});

            ByteArrayOutputStream bOut = new ByteArrayOutputStream();

            ks.store(bOut, "gost".toCharArray());

            ks = KeyStore.getInstance("PKCS12");

            ks.load(new ByteArrayInputStream(bOut.toByteArray()), "gost".toCharArray());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

}