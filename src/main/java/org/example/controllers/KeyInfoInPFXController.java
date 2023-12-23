package org.example.controllers;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;
import org.example.Main;

import java.net.URL;
import java.util.ResourceBundle;


public class KeyInfoInPFXController implements Initializable {
    public Label id_lblName;
    @Getter
    @Setter
    private int k;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_lblName.setText(Main.getAliesKeys().getAliesKeyList()[k].getName());
    }
}
