package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.File;

public class PDFViewer extends Application {

    private static final String PDF_FILE_PATH = "D:\\Tekshirishga\\testDesktop\\test.pdf";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("PDF Viewer");

        WebView webView = new WebView();
        WebEngine webEngine = webView.getEngine();

        // Convert file path to URI to handle spaces and special characters
        File file = new File(PDF_FILE_PATH);
        String fileURI = file.toURI().toString();

        // Load PDF file into WebView
        webEngine.load("http://docs.google.com/gview?embedded=true&url=" + fileURI);

        // Create the scene
        Scene scene = new Scene(webView, 800, 600);
        primaryStage.setScene(scene);

        // Show the stage
        primaryStage.show();
    }
}