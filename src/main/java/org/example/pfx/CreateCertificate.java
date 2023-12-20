package org.example.pfx;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;
import org.bouncycastle.jce.X509Principal;
import org.bouncycastle.jce.spec.ECNamedCurveGenParameterSpec;
import org.bouncycastle.x509.X509V3CertificateGenerator;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.*;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Date;

@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)

public class CreateCertificate {

    String CERTIFICATE_ALIAS = "YOUR_CERTIFICATE_NAME";
    String CERTIFICATE_DN = "CN=cn, O=o, L=L, ST=il, C= c";
    String CERTIFICATE_NAME;
    String PASSWORD;

    public X509Certificate managerCer(String CERTIFICATE_NAME, String PASSWORD, KeyPair keyPair) {
        this.CERTIFICATE_NAME = "C:/DSKEYS/CER/" + CERTIFICATE_NAME + ".cer";
        this.PASSWORD = PASSWORD;
        System.out.println("Your certificate => " + CERTIFICATE_NAME);
        System.out.println("Password => " + PASSWORD);
        System.out.println("kerak => " + keyPair.toString());
        return createCertificate(keyPair);
    }

    @SuppressWarnings("deprecation")
    private X509Certificate createCertificate(KeyPair keyPair) {
        try {
            /** GENERATE THE X509 CERTIFICATE */
            X509V3CertificateGenerator v3CertGen = new X509V3CertificateGenerator();
            v3CertGen.setSerialNumber(BigInteger.valueOf(System.currentTimeMillis()));
            v3CertGen.setIssuerDN(new X509Principal(CERTIFICATE_DN));
            v3CertGen.setNotBefore(new Date(System.currentTimeMillis() - 1000L * 60 * 60 * 24));
            v3CertGen.setNotAfter(new Date(System.currentTimeMillis() + (1000L * 60 * 60 * 24 * 365 * 10)));
            v3CertGen.setSubjectDN(new X509Principal(CERTIFICATE_DN));
            v3CertGen.setPublicKey(keyPair.getPublic());
            v3CertGen.setSignatureAlgorithm("GOST3411withECGOST3410");
            X509Certificate cert = v3CertGen.generateX509Certificate(keyPair.getPrivate());
            saveCert(cert, keyPair.getPrivate());
            return cert;
        } catch (SignatureException | InvalidKeyException e) {
            System.err.println("exception: CreateCertificate().createCertificate() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void saveCert(X509Certificate cert, PrivateKey key) {
        File file = new File(CERTIFICATE_NAME);

        System.out.println("kerak => " + file.getAbsoluteFile());
        try (FileOutputStream os = new FileOutputStream(file)) {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            keyStore.load(null, null);
            keyStore.setKeyEntry(CERTIFICATE_ALIAS, key, PASSWORD.toCharArray(), new java.security.cert.Certificate[]{cert});
            keyStore.store(os, PASSWORD.toCharArray());
            os.write(cert.getEncoded());
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            System.out.println("exception: CreateCertificate().saveCert() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}