package org.example.test;

import com.google.zxing.ChecksumException;
import com.google.zxing.FormatException;
import com.google.zxing.NotFoundException;
import org.example.utils.PDFWorker;

import java.io.IOException;

public class QRTEST {
    public static void main(String[] args) {
        PDFWorker pdfWorker = new PDFWorker();

        String link = pdfWorker.ReadSignLink("D:\\Tekshirishga\\testDesktop\\test 17_05_19 09_12_2023.pdf");
        System.out.println(link);
    }
}
