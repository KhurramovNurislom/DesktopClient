package org.example.pfx;

import org.example.crypto.ByteEncode;

import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.PrivateKey;

public class ReadPrivateKey {
    public String readPrivKeyInPFX(String pfxFilePath, String password) {
        PrivateKey privateKey = null;
        try {
            KeyStore keyStore = KeyStore.getInstance("PKCS12");
            FileInputStream fis = new FileInputStream(pfxFilePath);
            keyStore.load(fis, password.toCharArray());

            // Asosiy kalitni olish
            String alias = keyStore.aliases().nextElement();
            privateKey = (PrivateKey) keyStore.getKey(alias, password.toCharArray());

//            // Serifikatni olish (kerak bo'lsa)
//            Certificate cert = keyStore.getCertificate(alias);

            // Boshqa operatsiyalarni amalga oshirishingiz mumkin

//            System.out.println("PrivateKey: " + privateKey);
            assert privateKey != null;
//            System.out.println(ByteEncode.encodeHexString(privateKey.getEncoded()));
            return ByteEncode.encodeHexString(privateKey.getEncoded());
//            System.out.println("Certificate: " + cert);
        } catch (Exception e) {
            return null;

        }

    }
}
