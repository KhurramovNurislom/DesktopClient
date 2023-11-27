package org.example.signature;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        // write your code here
        System.out.println("Assalomu aleykum\n");

        System.out.println("1 => kalitlar generatsiya qilish;");
        System.out.println("2 => imzo qo'yish;");
        System.out.println("3 => imzoni tekshirish;");

        System.out.print("Amaliyotni tanlang: ");

        switch (new Scanner(System.in).nextLine()) {
            case "1":
//                new GenerateKeyPair().generateKeyPair(login);
                break;

            case "2":
//                new GenerateDigitalSignature().generateDigitalSignature();
                break;

            case "3":
//                new VerifyDigitalSignature().verifyDigitalSignature();
                break;

            default:
                System.out.println("Kechirasiz siz kiritgan amaliyot mavjud emas, qaytadan urinib ko'ring\n" +
                        "-------------------------------------------------------------------------------" +
                        "\n");
                main(args);
        }
    }
}
