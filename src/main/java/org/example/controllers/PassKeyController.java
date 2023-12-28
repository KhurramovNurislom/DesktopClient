package org.example.controllers;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.example.Main;

import java.net.URL;
import java.util.ResourceBundle;

public class PassKeyController implements Initializable {
    public Label id_lblKeyPath;
    public TextField id_tfPass;
    public PasswordField id_pfPass;
    public ImageView id_ivEye;
    public Label id_lblTitle;
    public ImageView id_ivClose;
    public Pane id_pnPane;
    private boolean eyeBool = true;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        hiddenEyes();
        id_ivClose.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                id_ivClose.setImage(new Image("/images/passKeys/close.gif"));
            }
        });

        id_ivEye.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                eyeBool = !eyeBool;
                if (eyeBool) {
                    id_ivEye.setImage(new Image("/images/passKeys/visible.png"));
                    id_tfPass.setVisible(true);
                    id_pfPass.setVisible(false);
                } else {
                    id_ivEye.setImage(new Image("/images/passKeys/show.png"));
                    id_tfPass.setVisible(false);
                    id_pfPass.setVisible(true);
                }
            }
        });

        id_lblKeyPath.setText(Main.getListPaths().get(Main.getIndexPFXFilePath()));

        id_tfPass.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Yangi belgi qo'shilganida uzunlikni ko'rsatish
                System.out.println("Belgi Uzunligi: " + newValue.length());
            }
        });

        id_ivClose.setOnMouseExited(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                id_ivClose.setImage(new Image("/images/passKeys/close_red.png"));
            }
        });

        id_ivClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                Main.showPassStage(false);
            }
        });

    }


    private void hiddenEyes() {


        id_tfPass.setText(id_pfPass.getText());
        id_pfPass.textProperty().addListener((observable, oldValue, newValue) -> {
            id_tfPass.setText(newValue);
        });
        id_tfPass.textProperty().addListener((observable, oldValue, newValue) -> {
            id_pfPass.setText(newValue);
        });
    }
}
