package org.example;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;

import java.io.File;
import java.io.IOException;

public class DocumentViewer extends Application {

    private static final int DEFAULT_PAGE = 0;



    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Document Viewer");

        BorderPane root = new BorderPane();
        Button openButton = new Button("Open PDF");

        openButton.setOnAction(e -> openPDF(primaryStage));

        root.setTop(openButton);

        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void openPDF(Stage primaryStage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File selectedFile = fileChooser.showOpenDialog(primaryStage);

        if (selectedFile != null) {
            displayPDF(selectedFile, primaryStage);
        }
    }

    private void displayPDF(File file, Stage primaryStage) {
        try {
            PDDocument document = PDDocument.load(file);
            PDFRenderer pdfRenderer = new PDFRenderer(document);

            javafx.scene.image.Image page = SwingFXUtils.toFXImage(pdfRenderer.renderImage(DEFAULT_PAGE), null);
            ImageView imageView = new ImageView(page);

            BorderPane root = new BorderPane(imageView);
            Scene scene = new Scene(root, 800, 600);

            primaryStage.setScene(scene);
            primaryStage.setTitle("Document Viewer - " + file.getName());
            primaryStage.show();

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
}
