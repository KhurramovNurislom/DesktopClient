package org.example.utils;

import org.example.Main;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

import java.io.IOException;
import java.net.URL;

public class FXMLLoaderMade {
    private Pane view;

    public Pane getPane(String fileName) throws IOException {
        URL fileURL = Main.class.getResource("/fxml/" + fileName + ".fxml");
        if (fileURL == null) {
            throw new java.io.FileNotFoundException("FXML file topilmadi");
        }
        view = FXMLLoader.load(fileURL);
        return view;
    }
}
