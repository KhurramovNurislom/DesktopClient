package org.example.controllers;


import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import org.example.Main;
import org.example.utils.FXMLLoaderMade;
import org.example.utils.Requests;
import org.example.utils.SceneChooser;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    @FXML
    public BorderPane mainPane;
    @FXML
    public Label id_lblVaqt;
    @FXML
    public JFXButton id_btnChiqish;
    @FXML
    public JFXButton id_btnPowerOff;
    @FXML
    public Circle id_Circle;
    @FXML
    public JFXButton id_btnGetKeyPair;
    @FXML
    public JFXButton id_btnImzoQoyish;
    @FXML
    public JFXButton id_btnImzoniTekshirish;

//    public JFXButton id_btnImzoniBoshqarish;

    @FXML
    public JFXButton id_btnChat;
    //    public JFXButton id_btnYoriqnoma;
    //    public JFXButton id_btnYangilash;
    @FXML
    public Label id_lblUserName;
    @FXML
    public Label id_lblEmail;
    @FXML
    public VBox id_vbLeft;
    @FXML
    public Pane id_pnUserInfo;
    @FXML
    public Label id_lblUserNameMini;
    @FXML
    public Circle id_CircleMini;
    @FXML
    public Label id_lblEmailMini;
    @FXML
    public JFXButton id_btnRefresh;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultPage();
        timeNow();
        pageInfo();

//        id_btnImzoniBoshqarish.setVisible(false);
//        id_btnYangilash.setVisible(false);
//        id_btnYoriqnoma.setVisible(false);

        id_pnUserInfo.setVisible(false);

//        id_lblUserNameMini.setText(Main.getLoginData().getUser().getUsername());
//        id_lblEmailMini.setText(Main.getLoginData().getUser().getEmail());

        id_btnGetKeyPair.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainPane.setRight(id_vbLeft);
                id_pnUserInfo.setVisible(false);
                mainPane.setCenter(new FXMLLoaderMade().getPane("KeysGenPage"));
            }
        });
        id_btnImzoQoyish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainPane.setRight(id_vbLeft);
                id_pnUserInfo.setVisible(false);
                mainPane.setCenter(new FXMLLoaderMade().getPane("SigningPage"));
            }
        });
        id_btnImzoniTekshirish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainPane.setRight(id_vbLeft);
                id_pnUserInfo.setVisible(false);
                mainPane.setCenter(new FXMLLoaderMade().getPane("SignVerPage"));
            }
        });
//        id_btnImzoniBoshqarish.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                id_pnUserInfo.setVisible(false);
//                mainPane.setRight(id_vbLeft);
//                try {
//                    mainPane.setCenter(new FXMLLoaderMade().getPane("ManageSignPage"));
//                } catch (IOException e) {
//                  System.err.println("exception: MainPageController(btnImzoniBoshqarish) => " + e.getMessage());
//                }
//            }
//        });
        id_btnChat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                mainPane.setRight(null);
                mainPane.setCenter(new FXMLLoaderMade().getPane("ChatPage"));
            }
        });
//        id_btnYoriqnoma.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                mainPane.setRight(id_vbLeft);
//                try {
//                    mainPane.setCenter(new FXMLLoaderMade().getPane("GuidePage"));
//                } catch (IOException e) {
//                         System.err.println("exception: MainPageController(btnYoriqnoma) => " + e.getMessage());
//                         throw new RuntimeException(e);
//                }
//            }
//        });

//        id_btnYangilash.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                mainPane.setRight(id_vbLeft);
//                try {
//                    mainPane.setCenter(new FXMLLoaderMade().getPane("UpdatePage"));
//                } catch (IOException e) {
//                     System.err.println("exception: MainPageController(btnYangilanish) => " + e.getMessage());
//                     throw new RuntimeException(e);
//                }
//            }
//        });
        id_btnRefresh.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                defaultPage();
            }
        });
        id_btnChiqish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneChooser.changeScene(event, "/fxml/LoginPage.fxml", "Kirish oynasi...");
            }
        });
        id_btnPowerOff.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
    }

    private void defaultPage() {
        mainPane.setRight(id_vbLeft);
        mainPane.setCenter(new FXMLLoaderMade().getPane("KeysGenPage"));
    }

    private void timeNow() {
        Thread thread = new Thread(() -> {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("kk:mm:ss ");
            while (true) {
                try {
                    Thread.sleep(10);
                } catch (Exception e) {
                    System.err.println("exception: MainPageController().timeNow() => " + e.getMessage());
                }
                final String timeNow = simpleDateFormat.format(new Date());
                Platform.runLater(() -> {
                    id_lblVaqt.setText(timeNow);
                });
            }
        });
        thread.start();
    }

    public void pageInfo() {
        /** Boshqa userlar haqida ma'lumotlar olinadi */
        new Requests().ResponseUsersMe(); /** Login bilan kirgan user haqida */
        id_lblUserName.setText(Main.getLoginData().getUser().getUsername());
        id_lblEmail.setText(Main.getLoginData().getUser().getEmail());
        id_Circle.setStroke(Color.LIGHTBLUE);
        Image image = new Image(Main.getUrl() + Main.getAUsersMe().getData().getUsersPermissionsUser().getData().getAttributes().getRasm().getData().getAttributes().getUrl());
        id_Circle.setFill(new ImagePattern(image));
    }
}
