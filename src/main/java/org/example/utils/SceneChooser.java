package org.example.utils;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.Main;

import java.io.IOException;
import java.util.Objects;

public class SceneChooser {
    //this is for change Scene
    public static void changeScene(ActionEvent event, String fxmlFile, String title) {

//        System.out.println(fxmlFile);
//        System.out.println(event);
        Parent root = null;
        try {
            if (event != null) {
                FXMLLoader loader = new FXMLLoader(SceneChooser.class.getResource(fxmlFile));
                root = loader.load();
            } else {
                root = FXMLLoader.load(Objects.requireNonNull(SceneChooser.class.getResource(fxmlFile)));
            }
        } catch (IOException e) {
            System.err.println("exception: SceneChooser => " + e.getMessage());
        }

        assert event != null;
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setTitle(title);
        assert root != null;
        Scene scene = new Scene(root);
        scene.getStylesheets().add
                (Objects.requireNonNull(Main.class.getResource("/css/Style.css")).toExternalForm());
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
