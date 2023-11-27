//package org.example.signature;
//
//
//import org.example.utils.ByteEncode;
//import org.example.utils.ReadKeys;
//
//import java.io.BufferedInputStream;
//import java.io.InputStream;
//import java.security.PrivateKey;
//import java.security.Signature;
//
//public class GenerateDigitalSignature {
//
//    public static String generateDigitalSignature(String privkey,InputStream is) {
//
//
//
//        ReadKeys readKeys = new ReadKeys();
//        /** Generate a DSA signature */    try {
//
//            PrivateKey privateKey = readKeys.readPrivateKey(privkey);        /* Create a Signature object and initialize it with the private key */
//            Signature dsa = Signature.getInstance("SHA1withDSA", "SUN");        dsa.initSign(privateKey);
//            /* Update and sign the data */        BufferedInputStream bufin = new BufferedInputStream(is);
//            byte[] buffer = new byte[1024];        int len;
//            while (bufin.available() != 0) {            len = bufin.read(buffer);
//                dsa.update(buffer, 0, len);        }
//            bufin.close();        byte[] realSig = dsa.sign();
//            return ByteEncode.encodeHexString(realSig);
//        } catch (Exception e) {        System.err.println("Caught exception " + e.toString());
//            return null;    }
//    }
//}
//
//
//// 302c021451689a2fd328ff355cbf45a7ae3ebb79213dff66021462560b34ef3287f9d30953047ea48527801bb945
//// 302c02142034204692a1220352bd654f4ca1f756eaf902f6021433a3d31892ccfc1f0a1347115f4ad73675899527
