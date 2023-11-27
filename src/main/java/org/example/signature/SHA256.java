package org.example.signature;//package com.example.webclient.signature;
//
//import javax.xml.bind.DatatypeConverter;
//import java.nio.charset.StandardCharsets;
//import java.security.MessageDigest;
//import java.security.NoSuchAlgorithmException;
//
//
//public class SHA256 {
//    public String heshSha256(String text) throws NoSuchAlgorithmException {
//        MessageDigest md = MessageDigest.getInstance("SHA-256");
//        byte[] digest = md.digest(text.getBytes(StandardCharsets.UTF_8));
//        String sha256 = DatatypeConverter.printHexBinary(digest).toLowerCase();
//
//        return sha256;
//    }
//}
//
//
//
