package org.example.pfx;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.security.cert.CertificateException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

public class ReadAliesInPFX {

    public String[] readAliesInPFX(String[] listPath) {
        Security.addProvider(new BouncyCastleProvider());
        List<String> listAlies = new ArrayList<>();
        try {
            for (String pfxFile : listPath) {
                KeyStore keyStore = KeyStore.getInstance("PKCS12");
                FileInputStream fis = new FileInputStream(pfxFile);
                keyStore.load(fis, null);
                Enumeration<String> aliases = keyStore.aliases();
                while (aliases.hasMoreElements()) {
                    String alias = aliases.nextElement();
                    listAlies.add(alias);
                }
            }
        } catch (KeyStoreException | CertificateException | IOException | NoSuchAlgorithmException e) {
            System.out.println("exception : ReadAliesInPFX().readAliesInPFX() => " + e.getMessage());
            throw new RuntimeException(e);
        }
        String[] arrList = new String[listAlies.size()];

        for (int i = 0; i < listAlies.size(); i++) {
            arrList[i] = listAlies.get(i);
        }

        return arrList;
    }
}

