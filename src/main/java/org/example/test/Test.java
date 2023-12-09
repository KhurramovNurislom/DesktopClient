package org.example.test;

import java.io.FileNotFoundException;

public class Test {
    public static void main(String[] args) throws FileNotFoundException {

        testString();
        System.out.println("\n/******************************************************************************************************************/");
        testFile();


//        System.out.println(Arrays.toString(bytes));
//        String text = ByteEncode.encodeHexString(bytes);
//        System.out.println(text);
//        byte[] bytes2 = ByteEncode.decodeHexString(text);
//        System.out.println(Arrays.toString(bytes2));
//        String text2 = ByteEncode.encodeHexString(bytes2);
//        System.out.println(text2);

//        PrivateKey privateKey = new UzDSt_1092_2009().readPrivateKey("3045020100301c06062a8503020213301206072a85030202230106072a850302021e0104220420ea51c81999af95d884338f442e2442c443bb73bca5f848b3325dacc3d62fe1ce");
//        PublicKey publicKey = new UzDSt_1092_2009().readPublicKey("3063301c06062a8503020213301206072a85030202230106072a850302021e01034300044014f7aee7e006ae9b9a7c64bcc95b9ad2538718473a7f80d6d8ff77c6d61e8b8fe3da6f815d4652b0aa9aea1d87343a47a44ac1a3371092075b5eb60a9016d5ac");

//        String text = "Nurislom";
//        String path = "D:\\Tekshirishga\\testDesktop\\test.pdf";
//        System.out.println("\n\n\n\n");
//        String sign = new UzDSt_1092_2009().signGenerate("3045020100301c06062a8503020213301206072a85030202230106072a850302021e0104220420ea51c81999af95d884338f442e2442c443bb73bca5f848b3325dacc3d62fe1ce",
//                path);
//        System.out.println(sign);
//        boolean bool = new UzDSt_1092_2009().verifySignature(sign,
//                path,
//                "3063301c06062a8503020213301206072a85030202230106072a850302021e01034300044014f7aee7e006ae9b9a7c64bcc95b9ad2538718473a7f80d6d8ff77c6d61e8b8fe3da6f815d4652b0aa9aea1d87343a47a44ac1a3371092075b5eb60a9016d5ac");
//
//        System.out.println(bool);

////        System.out.println(textFile);
//
//        byte[] bytes = textFile.getBytes();
//
//        byte[] signByte = new UzDSt_1092_2009().signGeneration(privateKey, bytes);
//
//        System.out.println("\n\n\n\n");
//
//        String sign = new ByteEncode().encodeHexString(signByte);
//
//        byte[] signByte2 = ByteEncode.decodeHexString(sign);
//
//        boolean boolSignVerify = new UzDSt_1092_2009().verification(publicKey, textFile, signByte2);
//
//        System.out.println("Natija => " + boolSignVerify);

    }

    private static void testFile() {

        String path = "D:\\Tekshirishga\\testDesktop\\test.pdf";

        String sign = new UzDSt_1092_2009File().signGenerate("3045020100301c06062a8503020213301206072a85030202230106072a850302021e0104220420ea51c81999af95d884338f442e2442c443bb73bca5f848b3325dacc3d62fe1ce",
                path);

        System.out.println("sign => " + sign);

        boolean bool = new UzDSt_1092_2009File().verifySignature(sign,
                path,
                "3063301c06062a8503020213301206072a85030202230106072a850302021e01034300044014f7aee7e006ae9b9a7c64bcc95b9ad2538718473a7f80d6d8ff77c6d61e8b8fe3da6f815d4652b0aa9aea1d87343a47a44ac1a3371092075b5eb60a9016d5ac");

        System.out.println("\n" + bool);

    }


    private static void testString() {

        String path = "D:\\Tekshirishga\\testDesktop\\test.pdf";

        String sign = new org.example.test.UzDSt_1092_2009File().signGenerate("3045020100301c06062a8503020213301206072a85030202230106072a850302021e0104220420ea51c81999af95d884338f442e2442c443bb73bca5f848b3325dacc3d62fe1ce",
                path);

        System.out.println("\n");

        boolean bool = new org.example.test.UzDSt_1092_2009File().verifySignature(sign,
                path,
                "3063301c06062a8503020213301206072a85030202230106072a850302021e01034300044014f7aee7e006ae9b9a7c64bcc95b9ad2538718473a7f80d6d8ff77c6d61e8b8fe3da6f815d4652b0aa9aea1d87343a47a44ac1a3371092075b5eb60a9016d5ac");

        System.out.println(bool);

    }


}
