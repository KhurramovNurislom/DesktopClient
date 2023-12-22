package org.example.pfx;

import org.example.Main;
import org.example.modules.AliesKey.AliesKey;
import org.example.modules.AliesKey.AliesKeys;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class AliesKeysReader {


    public void AliesCorrect() {

        String[] arr = new ReadAliesInPFX().readAliesInPFX(keys());
        AliesKey[] aliesKey = new AliesKey[arr.length];
        String[] textArr;

        for (int i = 0; i < arr.length; i++) {
            textArr = arr[i].split(",");
            System.out.println(textArr.length);
//            aliesKey[i].setId(i);

//            for (int j = 0; j < textArr.length; j++) {
//                System.out.println(textArr[j]);
//                String[] temp;
//                temp = textArr[j].split("=");
//                if (temp[0].equals("1.2.860.3.16.1.2")) {
//                    temp[0] = "jshshir";
//                }
//
//                if (temp[0].equals("cn")) {
//                    aliesKey[i].setCn(temp[1]);
//                } else if (temp[0].equals("name")) {
//                    aliesKey[i].setName(temp[1]);
//                } else if (temp[0].equals("surname")) {
//                    aliesKey[i].setSurname(temp[3]);
//                } else if (temp[0].equals("l")) {
//                    aliesKey[i].setL(temp[4]);
//                } else if (temp[0].equals("st")) {
//                    aliesKey[i].setSt(temp[5]);
//                } else if (temp[0].equals("c")) {
//                    aliesKey[i].setC(temp[6]);
//                } else if (temp[0].equals("o")) {
//                    aliesKey[i].setO(temp[7]);
//                } else if (temp[0].equals("uid")) {
//                    aliesKey[i].setUid(temp[8]);
//                } else if (temp[0].equals("jshshir")) {
//                    aliesKey[i].setJshshir(temp[9]);
//                } else if (temp[0].equals("serialnumber")) {
//                    aliesKey[i].setSerialnumber(temp[10]);
//                } else if (temp[0].equals("validfrom")) {
//                    aliesKey[i].setValidfrom(temp[11]);
//                } else if (temp[0].equals("validto")) {
//                    aliesKey[i].setValidto(temp[12]);
//                }
//            }

            System.out.println("\n");
        }

//        for (int i = 0; i < aliesKey.length; i++) {
//            System.out.println(aliesKey[i].toString());
//        }


//        AliesKeys aliesKeys = new AliesKeys();
//        aliesKeys.setAliesKeyList(aliesKey);
//        Main.setAliesKeys(aliesKeys);

//        for (int i = 0; i < text.size(); i++) {
//            System.out.println(text.get(i));
//
//            textArr = text.get(i).split(",");
//
//
//        }
//
//        for (int i = 0; i < 12; i++) {
//            System.out.println("kerak => " + textArr[i]);
//        }


//            for (int j = 0; j < 12; j++) {
//                String[] temp = new String[2];
//                temp = text.get(j).split("=");
//                textArr[j] = temp[1];
//            }

//            for (String s : textArr) {
//                System.out.println("kerak => " + s);
//            }


//
//
//
//
//
//
//
//
//
//
//
//
//


//        }
//        AliesKeys aliesKeys = new AliesKeys();
//        aliesKeys.setAliesKeyList(aliesKey);
//        Main.setAliesKeys(aliesKeys);
    }

    private String[] keys() {
        File folder = new File("C:\\DSKEYS");
        List<String> list = new ArrayList<>();
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile() && file.getName().toLowerCase().endsWith(".pfx")) {
                        list.add(file.getAbsolutePath());
                    }
                }
            } else {
                System.out.println("Papka bo'sh");
            }
        } else {
            System.out.println("Papka mavjud emas yoki direktoriya emas");
        }

        String[] arrList = new String[list.size()];
        for (int i = 0; i < arrList.length; i++) {
            arrList[i] = list.get(i);
        }
        return arrList;
    }

}
