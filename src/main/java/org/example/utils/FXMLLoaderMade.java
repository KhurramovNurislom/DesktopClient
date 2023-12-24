package org.example.utils;

import org.example.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FXMLLoaderMade {
    public Pane getPane(String fileName) {
        URL fileURL = Main.class.getResource("/fxml/" + fileName + ".fxml");
        if (fileURL == null) {
            try {
                throw new java.io.FileNotFoundException("FXML file topilmadi");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            return FXMLLoader.load(fileURL);
        } catch (IOException e) {
            System.err.println("exception : FXMLLoaderMade().getPane() => " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
