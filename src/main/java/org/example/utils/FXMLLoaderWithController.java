package org.example.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import org.example.Main;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;

public class FXMLLoaderWithController {
    public Pane getPane(String fileName, Object object) {
        URL fileURL = Main.class.getResource("/fxml/" + fileName + ".fxml");
        if (fileURL == null) {
            try {
                throw new FileNotFoundException("FXML file topilmadi");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(fileURL);
            fxmlLoader.setController(object);
            return fxmlLoader.load();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
