package org.example.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
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
    public JFXButton id_btnExit;
    public Pane id_pnTitle;
    private boolean eyeBool = true;

    private int seconds = 60;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        timer();
        hiddenEyes();
        Main.setPane(id_pnTitle);
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
                if (newValue.length() < 8 || newValue.length() > 24) {
                    id_tfPass.setStyle("-fx-text-fill:  RED");
                    id_pfPass.setStyle("-fx-text-fill:  RED");
                } else {
                    id_tfPass.setStyle("-fx-text-fill:  #0F2A62");
                    id_pfPass.setStyle("-fx-text-fill:  #0F2A62");
                }


            }
        });

        id_btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Main.showPassStage(false);
            }
        });
        id_ivClose.setOnMouseEntered(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                id_ivClose.setImage(new Image("/images/passKeys/close.gif"));
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

    private void timer() {
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.seconds(1), event -> {
                    seconds--;
                    updateTimerLabel();
                })
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
    }

    private void updateTimerLabel() {
//        int hours = seconds / 3600;
//        int minutes = (seconds % 3600) / 60;
//        int remainingSeconds = seconds % 60;

        id_lblTitle.setText("Oyna yopilgunicha " + seconds + " sekund");

        if (seconds == 0) {
            Main.showPassStage(false);
        }
//        String formattedTime = String.format("%02d:%02d:%02d sekund", hours, minutes, remainingSeconds);
//        id_lblTitle.setText(formattedTime);
//        if (formattedTime.equals("00:00:00 sekund"))
//        if (formattedTime.equals("00:00:00 sekund"))
//            System.exit(-1);

    }

}
