package org.example.pfx;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Enumeration;

/**
 * PFX faylni o'qish
 */
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class PFXManager {

//    String CERTIFICATE_NAME;
//    String password;
//    Long number = 1L;

    Long number = 1L;
    int soni = 1;
    String pfxFilePath = "C:\\DSKEYS\\" + getFileName() + ".pfx";


    String getFileName() {
        if (this.number.toString().length() == 1) {
            return "DS" + number + "00000" + soni;
        } else if (this.number.toString().length() == 2) {
            return "DS" + number + "0000" + soni;
        } else if (this.number.toString().length() == 3) {
            return "DS" + number + "000" + soni;
        } else if (this.number.toString().length() == 4) {
            return "DS" + number + "00" + soni;
        } else if (this.number.toString().length() == 5) {
            return "DS" + number + "0" + soni;
        } else if (this.number.toString().length() == 6) {
            return "DS" + number + soni;
        } else return null;
    }

    public void pfxManager(KeyPair p, String password) {

        Security.addProvider(new BouncyCastleProvider());

        PrivateKey sKey = p.getPrivate();
        PublicKey vKey = p.getPublic();

        PFXManager pfxManager = new PFXManager();
        pfxManager.certificate();
//        pfxManager.fileRead();
//        pfxManager.fileEdit();
        pfxManager.setNumber();
//        pfxManager.generateCertificate();
//        pfxManager.keyStore(sKey, vKey);

    }

    public void createPfxFile(KeyPair p, String cerName, String password) {
        generateCertificate(p, cerName, password);
    }

    public void readPfxFile(String pathPfxFile, String passString) {
        fileRead(pathPfxFile, passString);
    }

    public void editPfxFile(String pathPfxFile, String passString) {
        fileEdit(pathPfxFile, passString);
    }

    private void fileEdit(String pathPfxFile, String passString) {
        try (FileInputStream fis = new FileInputStream(pathPfxFile)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, passString.toCharArray());
        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException e) {

            throw new RuntimeException(e);
        }

    }

    private void fileRead(String pathPfxFile, String passString) {
        // PFX faylni o'qish
        char[] password = passString.toCharArray();
        try (FileInputStream fis = new FileInputStream(pathPfxFile)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, password);
        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
            System.err.println("exception: PFXManager().fileRead() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public void setNumber() {
        this.number += 1;
    }

    private void certificate() {

        Security.addProvider(new BouncyCastleProvider());
        String pfxFile = "C:/DSKEYS/DS4997124990002_Fazolat_Mukimova_24061996.pfx";

        char[] password = "24061996".toCharArray();

        // PFX faylni o'qish

        try (FileInputStream fis = new FileInputStream(pfxFile)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, password);
            // PFX fayl ichidagi kalit aliaslarini olish
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
                System.err.println("Alias: " + alias);
                //--------------------------------------------------------------------//
                try {
                    X509Certificate cert = (X509Certificate) keyStore.getCertificate(alias);
                    // save your cert inside the keystore
                    keyStore.setCertificateEntry("YourCertAlias", cert);
                } catch (KeyStoreException e) {
                    System.err.println("exception: PFXManager(). keyStore.store() => " + e.getMessage());
                    throw new RuntimeException(e);
                }
                try {
                    // create the outputstream to store the keystore
                    FileOutputStream fos = new FileOutputStream(pfxFilePath);
                    // store the keystore protected with password
                    keyStore.store(fos, password);
                } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
                    System.err.println("exception: PFXManager(). keyStore.store() => " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        } catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
            System.err.println("exception: PFXManager().certificate() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void generateCertificate(KeyPair p, String cerName, String password) {

        Security.addProvider(new BouncyCastleProvider());

        try {
            PrivateKey sKey = p.getPrivate();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(null, null);
            CreateCertificate signedCertificate = new CreateCertificate();
            X509Certificate cert = signedCertificate.managerCer(cerName, password, p);
            ks.setKeyEntry("alias", sKey, password.toCharArray(), new Certificate[]{cert});
            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
            ks.store(bOut, password.toCharArray());

            ks = KeyStore.getInstance("PKCS12");

            ks.load(new ByteArrayInputStream(bOut.toByteArray()), password.toCharArray());

            Enumeration<String> aliases = ks.aliases();

            while (aliases.hasMoreElements()) {

                String alias = aliases.nextElement();
                System.err.println("Alias: " + alias);
                //--------------------------------------------------------------------//
                try {

                    cert = (X509Certificate) ks.getCertificate(alias);

                    // save your cert inside the keystore
                    ks.setCertificateEntry("YourCertAlias", cert);

                    // create a folder
                    new File("C:\\DSKEYS").mkdir();

                    // create the OutputStream to store the keystore
                    FileOutputStream fos = new FileOutputStream("C:\\DSKEYS\\" + getFileName() + ".pfx");

                    // store the keystore protected with password
                    ks.store(fos, password.toCharArray());

                } catch (KeyStoreException | IOException | NoSuchAlgorithmException | CertificateException e) {
                    System.err.println("exception: PFXManager().fileRead() => " + e.getMessage());
                }
            }

        } catch (Exception e) {
            System.err.println("exception: PFXManager().generateCertificate() => " + e.getMessage());
        }


    }

    private void keyStore(KeyPair keyPair, String cerName, String pass) {

        try {
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(null, null);
            CreateCertificate signedCertificate = new CreateCertificate();
            X509Certificate cert = signedCertificate.managerCer(cerName, pass, keyPair);

            ks.setKeyEntry("gost", keyPair.getPrivate(), pass.toCharArray(), new Certificate[]{cert});

            ByteArrayOutputStream bOut = new ByteArrayOutputStream();

            ks.store(bOut, pass.toCharArray());

            ks = KeyStore.getInstance("PKCS12");

            ks.load(new ByteArrayInputStream(bOut.toByteArray()), pass.toCharArray());

        } catch (Exception e) {
            System.err.println("exception: PFXManager().generateCertificate() => " + e.getMessage());
        }

    }

}