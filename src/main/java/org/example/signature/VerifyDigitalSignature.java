//package org.example.signature;
//
//import com.example.webclient.utils.ByteEncode;
//import com.example.webclient.utils.ReadKeys;
//
//import java.io.BufferedInputStream;
//import java.io.FileInputStream;
//import java.security.PublicKey;
//import java.security.Signature;
//
//public class VerifyDigitalSignature {
//
//    public static boolean verifyDigitalSignature(String sign, String path, String pubKeystr) {
//        ReadKeys readKeys = new ReadKeys();
//
//        /** Verify a DSA signature */
//        boolean verifies = false;
//
//        try {
//            PublicKey pubKey = readKeys.readPublicKey(pubKeystr);
//            Signature sig = Signature.getInstance("SHA1withDSA", "SUN");
//            sig.initVerify(pubKey);
//            FileInputStream datafis = new FileInputStream(path);
//            BufferedInputStream bufin = new BufferedInputStream(datafis);
//            byte[] buffer = new byte[1024];
//            int len;
//            while (bufin.available() != 0) {
//                len = bufin.read(buffer);
//                sig.update(buffer, 0, len);
//            }
//            bufin.close();
//            verifies = sig.verify(ByteEncode.decodeHexString(sign));
//
//        } catch (Exception e) {
//            System.err.println("Xatolik Caught exception " + e.toString());
//        }
//        return verifies;
//    }
//}