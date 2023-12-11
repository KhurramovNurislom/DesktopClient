package org.example.utils;

import org.example.Main;
import org.example.modules.SignedFileInfo;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class PDFWorker {

      public void PasteSignLink(String filePathPDF, String signText) {
        File file = new File(filePathPDF);
        generateQRCodeImage(signText, 100, 100, file.getParentFile() + File.separator + "signImage.png", file);
    }

    public String ReadSignLink(String inputPDFFile) {
        File file = new File(inputPDFFile);
        return ImageExtraction(file);
    }

    private void generateQRCodeImage(String sign, int width, int height, String filePath, File filePDF) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, Object> hints = new HashMap<>();
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        try {
            BitMatrix bitMatrix = qrCodeWriter.encode(sign, BarcodeFormat.QR_CODE, width, height, hints);
            Path path = FileSystems.getDefault().getPath(filePath);
            MatrixToImageWriter.writeToPath(bitMatrix, "JPG", path);
            File fileImage = new File(filePDF.getParentFile() + File.separator + "signImage.png");
            AddImage(filePDF, fileImage);
            fileImage.delete();
        } catch (WriterException | IOException e) {
            System.err.println("exception :  PDFWorker().generateQRCodeImage() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    private void AddImage(File filePDF, File fileImage) {
        try (PDDocument document = PDDocument.load(filePDF)) {
            for (int i = 0; i < document.getPages().getCount(); i++) {
                PDPage page = document.getPage(i); // Rasmni qo'shish kerak bo'lgan sahifa (0 - birinchi sahifa)
                PDRectangle mediaBox = page.getMediaBox();
                float xPosition = mediaBox.getWidth() - 120; // Rasmning x koordinatasi
                float yPosition = 15; // Rasmning y koordinatasi
                PDImageXObject image = PDImageXObject.createFromFile(fileImage.getPath(), document);
                try (PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true, true)) {
                    contentStream.drawImage(image, xPosition, yPosition, image.getWidth() / 2, image.getHeight() / 2);
                }
            }
            /** O'zgartirilgan PDF faylini saqlash */
            String fileNameWithoutExtension = filePDF.getName().substring(0, filePDF.getName().lastIndexOf("."));
            String pathSignedFile = filePDF.getParentFile() + File.separator + fileNameWithoutExtension + " "
                    + new SimpleDateFormat("HH_mm_ss dd_MM_yyyy").format(new Date()) + ".pdf";
            SignedFileInfo signedFileInfo = new SignedFileInfo();
            signedFileInfo.setName(String.valueOf(Path.of(pathSignedFile).getFileName()));
            signedFileInfo.setFilePath(pathSignedFile);
            signedFileInfo.setCreatedAt(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date()));
            Main.setSignedFileInfo(signedFileInfo);
            document.save(pathSignedFile);
        } catch (IOException e) {
            System.err.println("PDF faylini ochishda xatolik: " + e.getMessage());
        }
    }

    private String ImageExtraction(File file) {
        String sign = null;
        try (PDDocument document = PDDocument.load(file)) {
            int pageIndex = document.getNumberOfPages() - 1;
            PDPage page = document.getPage(pageIndex);
            PDResources resources = page.getResources();
            for (COSName cosName : resources.getXObjectNames()) {
                PDXObject xObject = resources.getXObject(cosName);
                if (xObject instanceof PDImageXObject imageXObject) {
                    BufferedImage image = imageXObject.getImage();
                    sign = ReadQRCode(image);
                }
            }
        } catch (Exception e) {
            System.err.println("PDFWorker().ImageExtraction() => " + e.getCause());
        }
        return sign;
    }

    private String ReadQRCode(BufferedImage barcBufferedImage) {
        LuminanceSource source = new BufferedImageLuminanceSource(barcBufferedImage);
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Reader reader = new MultiFormatReader();
        try {
            Result result = reader.decode(bitmap);
            return result.toString();
        } catch (NotFoundException | ChecksumException | FormatException e) {
            System.err.println("exception : PDFWorker().ReadQRCode() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }

    private void ConvertDocxToPDF(String filePath) {
    }
}