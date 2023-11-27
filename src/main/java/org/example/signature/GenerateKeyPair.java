//package org.example.signature;
//
//import com.example.webclient.utils.ByteEncode;
//
//import java.security.*;
//import java.security.spec.PKCS8EncodedKeySpec;
//import java.security.spec.X509EncodedKeySpec;
//
//public class GenerateKeyPair {
//    public void generateKeyPair(String keyName, String secretPassword) {
//        try {
//
//            /* Generate a key pair */
//            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("DSA", "SUN");
//            SecureRandom random = SecureRandom.getInstance("SHA1PRNG", "SUN");
//
//            keyGen.initialize(1024, random);
//
//            KeyPair pair = keyGen.generateKeyPair();
//
//            PrivateKey privateKey = pair.getPrivate();
//            PublicKey publicKey = pair.getPublic();
//
//            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey.getEncoded());
//            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey.getEncoded());
//
////            String asd = new AES().worker(keyName, secretPassword, "shifr", ByteEncode.encodeHexString(pkcs8EncodedKeySpec.getEncoded()));
////
////            System.out.println("asd: " + asd);
//
//
//            Main.setPublicKey(ByteEncode.encodeHexString(x509EncodedKeySpec.getEncoded()));
//            Main.setPrivateKey(ByteEncode.encodeHexString(pkcs8EncodedKeySpec.getEncoded()));
//
////            System.out.println("publicKey asd :" + Main.getPublicKey());
////
////            System.out.println("privateKey asd :" + Main.getPrivateKey());
//
//
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (NoSuchProviderException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}
