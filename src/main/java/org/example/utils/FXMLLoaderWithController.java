package org.example.utils;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
//            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(String.valueOf(fileURL)));


            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setController(object);

//
//            Pane pane = new Pane();

            return fxmlLoader.load(fileURL);
        } catch (IOException e) {
            System.err.println("exception : FXMLLoaderMade().getPane() => " + e.getCause());
            throw new RuntimeException(e);
        }
    }
}
