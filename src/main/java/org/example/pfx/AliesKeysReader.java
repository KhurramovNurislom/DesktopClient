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
        AliesKey ak;
        for (int i = 0; i < arr.length; i++) {
            ak = new AliesKey();
            textArr = arr[i].split(",");
            ak.setId(i);
            for (String s : textArr) {
                String[] temp;
                temp = s.split("=");
                if (temp[0].equals("1.2.860.3.16.1.2")) {
                    temp[0] = "jshshir";
                }
                switch (temp[0]) {
                    case "cn" -> ak.setCn(temp[1]);
                    case "name" -> ak.setName(temp[1]);
                    case "surname" -> ak.setSurname(temp[1]);
                    case "l" -> ak.setL(temp[1]);
                    case "st" -> ak.setSt(temp[1]);
                    case "c" -> ak.setC(temp[1]);
                    case "o" -> ak.setO(temp[1]);
                    case "uid" -> ak.setUid(temp[1]);
                    case "jshshir" -> ak.setJshshir(temp[1]);
                    case "serialnumber" -> ak.setSerialnumber(temp[1]);
                    case "validfrom" -> ak.setValidfrom(temp[1]);
                    case "validto" -> ak.setValidto(temp[1]);
                }
            }
            aliesKey[i] = ak;
        }
        AliesKeys aliesKeys = new AliesKeys();
        aliesKeys.setAliesKeyList(aliesKey);
        Main.setAliesKeys(aliesKeys);
        System.out.println(aliesKeys);
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
        Main.setListPaths(list);
        return arrList;
    }
}
