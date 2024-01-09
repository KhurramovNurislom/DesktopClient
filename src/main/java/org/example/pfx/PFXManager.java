package org.example.pfx;

import javafx.application.Platform;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.bouncycastle.jce.ECNamedCurveTable;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.jce.spec.ECNamedCurveParameterSpec;
import org.example.Main;

import java.io.*;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.InstantSource;
import java.time.LocalTime;
import java.util.Date;
import java.util.Enumeration;
import java.util.Locale;

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


        PrivateKey sKey = p.getPrivate();
        PublicKey vKey = p.getPublic();

//        PFXManager pfxManager = new PFXManager();
//        pfxManager.certificate();
//        pfxManager.fileRead();
//        pfxManager.fileEdit();
//        pfxManager.setNumber();
//        pfxManager.generateCertificate();
//        pfxManager.keyStore(sKey, vKey);

    }

    public void createPfxFile(KeyPair p, String cerName, String password) {
        generateCertificate(p, cerName, password);
    }

    public String readPfxFile(String pathPfxFile, String passString) {
        return certificate(pathPfxFile, passString);
    }

//    public void editPfxFile(String pathPfxFile, String passString) {
//        fileEdit(pathPfxFile, passString);
//    }

//    private void fileEdit(String pathPfxFile, String passString) {
//        try (FileInputStream fis = new FileInputStream(pathPfxFile)) {
//            KeyStore keyStore = KeyStore.getInstance("PKCS12");
//            keyStore.load(fis, passString.toCharArray());
//        } catch (CertificateException | NoSuchAlgorithmException | IOException | KeyStoreException e) {
//
//            throw new RuntimeException(e);
//        }
//    }

//    private void fileRead(String pathPfxFile, String passString) {
//        // PFX faylni o'qish
//        try (FileInputStream fis = new FileInputStream(pathPfxFile)) {
//            KeyStore keyStore = KeyStore.getInstance("PKCS12");
//            keyStore.load(fis, passString.toCharArray());
//        } catch (IOException | CertificateException | NoSuchAlgorithmException | KeyStoreException e) {
//            System.err.println("exception: PFXManager().fileRead() => " + e.getMessage());
//            throw new RuntimeException(e);
//        }
//    }

    public void setNumber() {
        this.number += 1;
    }

    private String certificate(String pfxFile, String password) {

        System.out.println(pfxFile);
        System.out.println(password);

        // PFX faylni o'qish
        try (FileInputStream fis = new FileInputStream(pfxFile)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(fis, password.toCharArray());
            // PFX fayl ichidagi kalit aliaslarini olish
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String alias = aliases.nextElement();
//                System.err.println("Alias: " + alias);
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
                    keyStore.store(fos, password.toCharArray());
                } catch (KeyStoreException | NoSuchAlgorithmException | CertificateException | IOException e) {
                    System.err.println("exception: PFXManager(). keyStore.store() => " + e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        } catch (NoSuchAlgorithmException | CertificateException | IOException | KeyStoreException e) {
            System.err.println("exception: PFXManager().certificate() => " + e.getMessage());
            throw new RuntimeException(e);
        }
        return null;

    }

    private void generateCertificate(KeyPair p, String cerName, String password) {

        try {
            PrivateKey sKey = p.getPrivate();
            KeyStore ks = KeyStore.getInstance("PKCS12");
            ks.load(null, null);
            CreateCertificate signedCertificate = new CreateCertificate();
            X509Certificate cert = signedCertificate.managerCer(cerName, password, p);


//            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");


//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy.MM.dd kk:mm:ss");

//                    String timeNow = simpleDateFormat.format("2024-01-09T04:01:37.167Z");

//            System.out.println(timeNow);


            String outputDateFormat = "yyyy.MM.dd kk:mm:ss";

            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
            SimpleDateFormat outputFormat = new SimpleDateFormat(outputDateFormat);

            ks.setKeyEntry("cn=xurramov nurislom parda o‘g‘li,name=nurislom,surname=xurramov,l=юнусобод тумани,st=тошкент ш.,c=uz,o=не указано,uid=" +
                            Main.getKeysGen().getData().getId() + ",1.2.860.3.16.1.2=32406966220011,serialnumber=77c7d9a1,validfrom="
                            + outputFormat.format(inputFormat.parse(Main.getKeysGen().getData().getAttributes().getCreatedAt())) + ",validto="
                            + outputFormat.format(inputFormat.parse(Main.getKeysGen().getData().getAttributes().getPublishedAt())),
                    sKey, password.toCharArray(), new Certificate[]{cert});
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

//    private void keyStore(KeyPair keyPair, String cerName, String pass) {
//        try {
//            KeyStore ks = KeyStore.getInstance("PKCS12");
//            ks.load(null, null);
//            CreateCertificate signedCertificate = new CreateCertificate();
//            X509Certificate cert = signedCertificate.managerCer(cerName, pass, keyPair);
//            ks.setKeyEntry("gost", keyPair.getPrivate(), pass.toCharArray(), new Certificate[]{cert});
//            ByteArrayOutputStream bOut = new ByteArrayOutputStream();
//            ks.store(bOut, pass.toCharArray());
//            ks = KeyStore.getInstance("PKCS12");
//            ks.load(new ByteArrayInputStream(bOut.toByteArray()), pass.toCharArray());
//        } catch (Exception e) {
//            System.err.println("exception: PFXManager().generateCertificate() => " + e.getMessage());
//        }
//    }

}