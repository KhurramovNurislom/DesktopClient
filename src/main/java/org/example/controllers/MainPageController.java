package org.example.controllers;


import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import org.example.utils.SceneChooser;

import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

public class MainPageController implements Initializable {

    public BorderPane mainPane;
    public Label id_lblVaqt;
    public JFXButton id_btnChiqish;
    public JFXButton id_btnPowerOff;
    public Circle id_Circle;
    public JFXButton id_btnGetKeyPair;
    public JFXButton id_btnImzoQoyish;
    public JFXButton id_btnImzoniTekshirish;
    public JFXButton id_btnImzoniBoshqarish;

    public JFXButton id_btnChat;
    public JFXButton id_btnDocs;
    //    public JFXButton id_btnYoriqnoma;
    //    public JFXButton id_btnYangilash;
    public Label id_lblUserName;
    public Label id_lblEmail;
    public VBox id_vbLeft;
    public Pane id_pnUserInfo;
    public Label id_lblUserNameMini;
    public Circle id_CircleMini;
    public Label id_lblEmailMini;
    public JFXButton id_btnRefresh;
    private Object btnTemp;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        defaultPage();
        timeNow();
        pageInfo();
//        btnHover();

//        id_btnImzoniBoshqarish.setVisible(false);
//        id_btnYangilash.setVisible(false);
//        id_btnYoriqnoma.setVisible(false);

        id_pnUserInfo.setVisible(false);

//        id_lblUserNameMini.setText(Main.getLoginData().getUser().getUsername());
//        id_lblEmailMini.setText(Main.getLoginData().getUser().getEmail());

        id_btnGetKeyPair.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clickBtn(id_btnGetKeyPair);
                mainPane.setRight(id_vbLeft);
                id_pnUserInfo.setVisible(false);
                mainPane.setCenter(new FXMLLoaderMade().getPane("KeysGenPage2"));
            }
        });
        id_btnImzoQoyish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clickBtn(id_btnImzoQoyish);
                mainPane.setRight(id_vbLeft);
                id_pnUserInfo.setVisible(false);
                mainPane.setCenter(new FXMLLoaderMade().getPane("SigningPage"));
            }
        });
        id_btnImzoniTekshirish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clickBtn(id_btnImzoniTekshirish);
                mainPane.setRight(id_vbLeft);
                id_pnUserInfo.setVisible(false);
                mainPane.setCenter(new FXMLLoaderMade().getPane("SignVerPage"));
            }
        });

        id_btnImzoniBoshqarish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clickBtn(id_btnImzoniBoshqarish);
                id_pnUserInfo.setVisible(false);
                mainPane.setRight(id_vbLeft);
                mainPane.setCenter(new FXMLLoaderMade().getPane("ManageSignPage"));
            }
        });

        id_btnChat.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                clickBtn(id_btnChat);
                mainPane.setRight(null);
                mainPane.setCenter(new FXMLLoaderMade().getPane("ChatPage"));
            }
        });
        id_btnDocs.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                clickBtn(id_btnDocs);
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
        clickBtn(id_btnImzoQoyish);
        mainPane.setRight(id_vbLeft);
        mainPane.setCenter(new FXMLLoaderMade().getPane(/**"SigningPage"*/"SigningPage"));
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

    private void pageInfo() {
        id_lblUserName.setText(Main.getLoginData().getUser().getUsername());
        id_lblEmail.setText(Main.getLoginData().getUser().getEmail());
        id_Circle.setStroke(Color.LIGHTBLUE);
        Image image = new Image(Main.getUrl() + Main.getAUsersMe().getData().getUsersPermissionsUser().getData().getAttributes().getRasm().getData().getAttributes().getUrl());
        id_Circle.setFill(new ImagePattern(image));
    }

    private void clickBtn(JFXButton btn) {
        fullColor();
        btn.setStyle("-fx-background-color: #375594");
    }

    private void fullColor() {
        id_btnGetKeyPair.setStyle("-fx-background-color: #0F2A62");
        id_btnImzoQoyish.setStyle("-fx-background-color: #0F2A62");
        id_btnImzoniTekshirish.setStyle("-fx-background-color: #0F2A62");
        id_btnImzoniBoshqarish.setStyle("-fx-background-color: #0F2A62");
        id_btnChat.setStyle("-fx-background-color: #0F2A62");
        id_btnDocs.setStyle("-fx-background-color: #0F2A62");
    }


//    private void btnHover(JFXButton btn) {
//        btn.setStyle("-fx-background-color: #375594");
//        //with hoverProperty
//        id_btnGetKeyPair.hoverProperty().addListener(l -> {
//            id_btnGetKeyPair.setStyle("-fx-background-color: #0F2A62;");
//        });
//
//        //or with mouseMoved
//        id_btnGetKeyPair.setOnMouseMoved(m -> {
//            id_btnGetKeyPair.setStyle("-fx-background-color: #375594;");
//        });
//
//
//        //with hoverProperty
//        id_btnImzoQoyish.hoverProperty().addListener(l -> {
//            id_btnImzoQoyish.setStyle("-fx-background-color: #0F2A62;");
//        });
//
//        //or with mouseMoved
//        id_btnImzoQoyish.setOnMouseMoved(m -> {
//            id_btnImzoQoyish.setStyle("-fx-background-color: #375594;");
//        });
//
//
//        //with hoverProperty
//        id_btnImzoniTekshirish.hoverProperty().addListener(l -> {
//            id_btnImzoniTekshirish.setStyle("-fx-background-color: #0F2A62;");
//        });
//
//        //or with mouseMoved
//        id_btnImzoniTekshirish.setOnMouseMoved(m -> {
//            id_btnImzoniTekshirish.setStyle("-fx-background-color: #375594;");
//        });
//
//        //with hoverProperty
//        id_btnImzoniBoshqarish.hoverProperty().addListener(l -> {
//            id_btnImzoniBoshqarish.setStyle("-fx-background-color: #0F2A62;");
//        });
//
//        //or with mouseMoved
//        id_btnImzoniBoshqarish.setOnMouseMoved(m -> {
//            id_btnImzoniBoshqarish.setStyle("-fx-background-color: #375594;");
//        });
//
//        //with hoverProperty
//        id_btnImzoniBoshqarish.hoverProperty().addListener(l -> {
//            id_btnImzoniBoshqarish.setStyle("-fx-background-color: #0F2A62;");
//        });
//
//        //or with mouseMoved
//        id_btnChat.setOnMouseMoved(m -> {
//            id_btnChat.setStyle("-fx-background-color: #375594;");
//        });
//
//        //with hoverProperty
//        id_btnDocs.hoverProperty().addListener(l -> {
//            id_btnDocs.setStyle("-fx-background-color: #0F2A62;");
//        });
//
//        //or with mouseMoved
//        id_btnDocs.setOnMouseMoved(m -> {
//            id_btnDocs.setStyle("-fx-background-color: #375594;");
//        });

//        //with hoverProperty
//        id_btnImzoQoyish.hoverProperty().addListener(l -> {
//            id_btnImzoQoyish.setStyle("-fx-background-color: #0F2A62;");
//        });
//
//        //or with mouseMoved
//        id_btnImzoQoyish.setOnMouseMoved(m -> {
//            id_btnImzoQoyish.setStyle("-fx-background-color: #375594;");
//        });
//    }


}
