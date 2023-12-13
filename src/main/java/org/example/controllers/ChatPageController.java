package org.example.controllers;


import com.jfoenix.controls.JFXButton;
import com.sun.jna.platform.unix.X11;
import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Main;
import org.example.modules.userMessages.Fayllar;
import org.example.utils.Requests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DecimalFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;


public class ChatPageController implements Initializable {
    @FXML
    public TextField id_tfFindAbonent;
    @FXML
    public VBox id_vbAbonents;
    @FXML
    public Label id_lblAbonentName;
    @FXML
    public JFXButton id_btnIconSearch;
    @FXML
    public JFXButton id_btnUserInfo;
    @FXML
    public JFXButton id_btnChatSetting;
    @FXML
    public JFXButton id_btnAddDoc;
    @FXML
    public JFXButton id_btnSendMessage;
    @FXML
    public TextField id_tfMessage;
    @FXML
    public ScrollPane id_spAbonents;
    @FXML
    public ScrollPane id_spMessages;
    @FXML
    public VBox id_vbMessages;
    @FXML
    public Pagination id_Pagination;
    @FXML
    public BorderPane id_bpChat;
    @FXML
    public HBox id_hbPagination;
    @FXML
    public Pane id_pnMessageInfo;
    @FXML
    public JFXButton id_btnPaneClose;
    @FXML
    public Circle id_crAbonent;
    @FXML
    public Pane id_pnShadow;
    @FXML
    public Label id_lblPaneInfoLabel;
    @FXML
    public Pane id_pnDocInfo;
    @FXML
    public JFXButton id_btnDocInfoClose;
    @FXML
    public Pane id_pnDocTitle;
    @FXML
    public VBox id_vbDocSend;
    @FXML
    public JFXButton id_btnSendDoc;
    @FXML
    public JFXButton id_btnPlusDoc;
    @FXML
    public Pane id_pnDocSend;
    @FXML
    public VBox id_vbFiles;
    @FXML
    public ScrollPane id_spFiles;
    @FXML
    public VBox id_vbFilesMessagesPane;
    @FXML
    private HBox hBox;
    @FXML
    private Label lbl;
    @FXML
    private Label lblSize;
    @FXML
    private VBox vBox;
    @FXML
    private JFXButton btnRef;
    @FXML
    private JFXButton btnDel;

    private FileChooser fileChooser;
    private List<File> fileList;
    private final List<File> fileListTemp = new ArrayList<>();
    private File fileTemp;

    private int start = 0;
    private int limit = 7;
    private int tempUser = 0;


    TranslateTransition slide = new TranslateTransition();

    /***** SendDocument ga kerak o'zgaruvchilar ****/


    /*******************************************************/
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        FulledLeftPane();
        id_btnChatSetting.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Chat settings bosildi");
            }
        });
        id_btnIconSearch.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("IconSearch bosildi");
            }
        });
        id_btnAddDoc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("AddDoc bosildi");
//                AddDocs();
            }
        });
        id_btnUserInfo.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("UserInfo bosildi");
            }
        });
        id_btnSendMessage.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                System.out.println("SendMessage bosildi");
//                handleSendMessages();
            }
        });
        id_vbAbonents.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                id_spAbonents.setVvalue((Double) newValue);
            }
        });
        id_vbMessages.heightProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                id_spMessages.setVvalue((Double) newValue);
            }
        });
        id_tfFindAbonent.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

                ObservableList<Integer> filterList = FXCollections.observableArrayList();

                id_tfFindAbonent.textProperty().addListener((observable, oldValue, newValue) -> {
                    for (int i = 0; i < Main.getAUsers().getData().getUsersPermissionsUsers().getData().length; i++) {
                        if (Main.getAUsers().getData().getUsersPermissionsUsers().getData()[i].getAttributes().getUsername().toLowerCase().contains(newValue.toLowerCase())) {
                            filterList.add(Main.getAUsers().getData().getUsersPermissionsUsers().getData()[i].getId());
                        }
                    }

                    int[] filterArr = new int[filterList.size()];

                    for (int i = 0; i < filterList.size(); i++) {
                        filterArr[i] = filterList.get(i);
                    }

                    filterList.clear();
                    fulledList(filterArr);
                });
            }
        });
        id_btnSendDoc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                SendDocuments();
            }
        });

        id_btnPlusDoc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
//                AddDocuments();
            }
        });
    }

    private void FulledLeftPane() {
        int[] arr = new int[Main.getAUsers().getData().getUsersPermissionsUsers().getData().length];
        for (int i = 0; i < Main.getAUsers().getData().getUsersPermissionsUsers().getData().length; i++) {
            arr[i] = Main.getAUsers().getData().getUsersPermissionsUsers().getData()[i].getId();
        }
        fulledList(arr);
    }

    private void fulledList(int[] arr) {

        /** Kontaktlar hosil qilinadi */
        Circle circle;
        JFXButton[] btnArr = new JFXButton[arr.length];
        JFXButton btn;

        for (int i = 0; i < arr.length; i++) {

            for (int j = 0; j < Main.getAUsers().getData().getUsersPermissionsUsers().getData().length; j++) {

                if (arr[i] == Main.getAUsers().getData().getUsersPermissionsUsers().getData()[j].getId()) {

                    circle = new Circle(30);
                    circle.setStroke(Color.LIGHTBLUE);
                    circle.setFill(new ImagePattern(new Image(Main.getUrl() + Main.getAUsers().getData().getUsersPermissionsUsers().getData()[j].getAttributes().getRasm().getData().getAttributes().getUrl())));

                    btn = new JFXButton(Main.getAUsers().getData().getUsersPermissionsUsers().getData()[j].getAttributes().getUsername());
                    btn.setId(String.valueOf(arr[i]));
                    btn.setPrefSize(240, 60);
                    btn.setGraphic(circle);
                    btn.setAlignment(Pos.CENTER_LEFT);

                    btnArr[i] = btn;

                    final int finalI = Integer.parseInt(btn.getId());
                    btnArr[i].setOnAction(new EventHandler<ActionEvent>() {
                        @Override
                        public void handle(ActionEvent event) {
                            /** Abonent Name degan labelni to'ldiradi */
                            for (int k = 0; k < Main.getAUsers().getData().getUsersPermissionsUsers().getData().length; k++) {
                                if (finalI == Main.getAUsers().getData().getUsersPermissionsUsers().getData()[k].getId()) {
                                    id_crAbonent.setFill(new ImagePattern(new Image(Main.getUrl() + Main.getAUsers().getData().getUsersPermissionsUsers().getData()[k].getAttributes().getRasm().getData().getAttributes().getUrl())));
                                    id_lblAbonentName.setText(Main.getAUsers().getData().getUsersPermissionsUsers().getData()[k].getAttributes().getUsername());
                                    tempUser = Main.getAUsers().getData().getUsersPermissionsUsers().getData()[k].getId();
                                    FulledMessagesPane(tempUser);
                                    /** Paginatsiyani sozlaydi */
                                    HandlePagination();
                                }
                            }
                        }
                    });
                }
            }
        }
        id_vbAbonents.getChildren().clear();
        id_vbAbonents.getChildren().addAll(btnArr);
    }


//    /** Xabar jo'natish qismi */
//    private void handleSendMessages() {
////        new Requests().ResponseMessage(Main.getLoginData().getUser().getId(), sign, Main.getKeys().getData().getKalits().getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getId(), null, null);
//    }
//
//
//    /*********************************************/
//

    /**
     * Messageslar oynasini to'ldirish
     */
    private void FulledMessagesPane(int userId) {

        System.out.println("id => " + userId);

//        ResponseUserMessages(String.valueOf(userId), String.valueOf(Main.getLoginData().getUser().getId()), start, limit);

        new Requests().RequestUserMessages(String.valueOf(userId), String.valueOf(Main.getLoginData().getUser().getId()), start, limit);

        System.out.println(Main.getUserMessages().toString());

//        Label label;
//        HBox hb;
//
//        id_vbMessages.getChildren().clear();
//
//        for (int i = Main.getUserMessages().getData().getMessages().getData().length - 1; i >= 0; i--) {
//
//
//            for (Fayllar a : Main.getUserMessages().getData().getMessages().getData()[i].getAttributes().getFayllar()) {
//
//                hb = new HBox();
//                hb.setPrefSize(700, 60);
//
//                /**************/
//                HBox hBox = new HBox();
//                hBox.setMaxSize(500, 60);
//
//                /**************/
//                label = new Label();
//                label.setMaxSize(500, 60);
//                label.setGraphic(hBox);
//                label.setAlignment(Pos.CENTER);
//                label.setPadding(new Insets(5, 5, 5, 5));
//
//                /***** Doc rasmi *********/
//                ImageView iv = new ImageView(new Image("docs.png"));
//                iv.setFitWidth(50);
//                iv.setFitHeight(50);
//
//                /** O'rta */
//                VBox vb1 = new VBox();
//                vb1.setPadding(new Insets(0, 5, 0, 5));
//                vb1.setAlignment(Pos.CENTER_LEFT);
//
//                /** O'rta tepa */
//                HBox hbTop1 = new HBox();
//                hbTop1.setPrefHeight(25);
//
//                Label lblFileName = new Label();
//                lblFileName.setText(a.getFayl().getData().getAttributes().getName());
//                lblFileName.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16");
//                lblFileName.setMaxWidth(340);
//
//                hbTop1.getChildren().add(lblFileName);
//
//                /** O'rta past */
//                HBox hbBottom1 = new HBox();
//                hbBottom1.setPrefHeight(25);
//                hbBottom1.setAlignment(Pos.BOTTOM_LEFT);
//
//                Label lblFileSize = new Label();
//                lblFileSize.setText(a.getFayl().getData().getAttributes().getSize() + " kByte");
//                lblFileSize.setStyle("-fx-text-fill: #182c66; -fx-font-size: 12");
//
//
//                hbBottom1.getChildren().add(lblFileSize);
//
//                vb1.getChildren().addAll(hbTop1, hbBottom1);
//
//                /** o'ng chekka **/
//                VBox vb2 = new VBox();
//                vb2.setPadding(new Insets(0, 5, 0, 5));
//                vb2.setAlignment(Pos.CENTER_RIGHT);
//
//                /** O'ng chekka tepa*/
//                HBox hbTop2 = new HBox();
//                hbTop2.setPrefHeight(25);
//
//                JFXButton btnInfo = new JFXButton("Batafsil");
//                btnInfo.setPrefSize(100, 20);
//                btnInfo.setId("id_btn" + a.getFayl().getData().getId());
//
//                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.4), id_pnShadow);
//
//                btnInfo.setOnAction(new EventHandler<ActionEvent>() {
//                    @Override
//                    public void handle(ActionEvent event) {
//
//                        id_pnMessageInfo.setTranslateX(700);
//                        slide.setDuration(Duration.seconds(0.4));
//                        slide.setNode(id_pnMessageInfo);
//
//                        /** Bu narsa har bitta messagega beriladi */
//                        slide.setOnFinished((ActionEvent e) -> {
//                            id_pnShadow.setVisible(true);
//                        });
//
//                        id_pnShadow.setVisible(true);
//                        fadeTransition.setFromValue(0.0);
//                        fadeTransition.setToValue(0.4);
//                        fadeTransition.play();
//                        slide.setToX(200);
//                        slide.play();
//
//                        id_btnPaneClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
//                            @Override
//                            public void handle(MouseEvent event) {
//
//                                slide.setOnFinished((ActionEvent e) -> {
//                                    id_pnShadow.setVisible(false);
//                                });
//
//                                fadeTransition.setFromValue(0.4);
//                                fadeTransition.setToValue(0.0);
//                                fadeTransition.play();
//
//                                slide.setToX(700);
//                                slide.play();
//                                id_pnMessageInfo.setTranslateX(200);
//                            }
//                        });
//                        id_lblPaneInfoLabel.setText(btnInfo.getId());
//                    }
//                });
//                ImageView ivBtn = new ImageView(new Image("eye.png"));
//                ivBtn.setFitWidth(15);
//                ivBtn.setFitHeight(15);
//                btnInfo.setGraphic(ivBtn);
//                hbTop2.getChildren().add(btnInfo);
//                /** O'ng chekka tepa*/
//                HBox hbBottom2 = new HBox();
//                hbBottom2.setPrefHeight(25);
//                hbBottom2.setAlignment(Pos.BOTTOM_RIGHT);
//                hbBottom2.setSpacing(5);
//                Image img = new Image("check2.png");
//                ImageView ivCheck = new ImageView(img);
//                ivCheck.setFitHeight(15);
//                ivCheck.setFitWidth(22);
//                Label lblTime = new Label();
//                lblTime.setText(dateToString(a.getFayl().getData().getAttributes().getUpdatedAt()));
//                lblTime.setPrefSize(73, 20);
//                lblTime.setAlignment(Pos.BOTTOM_LEFT);
//                lblTime.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 12");
//                hbBottom2.getChildren().addAll(lblTime, ivCheck);
//                vb2.getChildren().addAll(hbTop2, hbBottom2);
//                /** Umumiy */
//                hBox.getChildren().addAll(iv, vb1, vb2);
//                if (Main.getUserMessages().getData().getMessages().getData()[i].getAttributes().getFrom_user().getData().getId().equals(String.valueOf(Main.getLoginData().getUser().getId()))) {
//                    label.setStyle("-fx-background-radius: 18 18 0 18; -fx-background-color: #578bcf; -fx-text-fill: #ffffff; -fx-font-size: 14");
//                    hb.setAlignment(Pos.CENTER_RIGHT);
//                } else {
//                    label.setStyle("-fx-background-radius: 18 18 18 0; -fx-background-color: #578ba0; -fx-text-fill: #ffffff; -fx-font-size: 14");
//                    hb.setAlignment(Pos.CENTER_LEFT);
//                }
//                hb.getChildren().add(label);
//                id_vbMessages.getChildren().add(hb);
//            }
//            String ifXabar = Main.getUserMessages().getData().getMessages().getData()[i].getAttributes().getXabar();
//            if (ifXabar != null && !ifXabar.isEmpty()) {
////                System.out.println(Main.getUserMessages().getData().getMessages().getData()[i].getAttributes().getXabar());
//                label = new Label(Main.getUserMessages().getData().getMessages().getData()[i].getAttributes().getXabar());
//                label.setWrapText(true);
//                hb = new HBox();
//                hb.setPrefSize(700, 30);
//                hb.setPadding(new Insets(0, 10, 0, 10));
//                hb.getChildren().add(label);
//                if (Main.getUserMessages().getData().getMessages().getData()[i].getAttributes().getFrom_user().getData().getId().equals(String.valueOf(Main.getLoginData().getUser().getId()))) {
//                    label.setStyle("-fx-background-radius: 18 18 0 18; -fx-background-color: #578bcf; -fx-text-fill: #ffffff");
//                    hb.setAlignment(Pos.CENTER_RIGHT);
//                } else {
//                    label.setStyle("-fx-background-radius: 18 18 18 0; -fx-background-color: #578ba0; -fx-text-fill: #ffffff");
//                    hb.setAlignment(Pos.CENTER_LEFT);
//                }
//                label.setFont(new Font(14));
//                label.prefWidth(30);
//                label.maxHeight(500);
//                label.setPadding(new Insets(5, 10, 5, 10));
//                id_vbMessages.getChildren().add(hb);
//            }
//        }
    }

    private void HandlePagination() {
        id_hbPagination.setVisible(true);
        id_Pagination.setPageCount(Main.getUserMessages().getData().getMessages().getMeta().getPagination().getPageCount());
        id_Pagination.setPageFactory(pageIndex -> {
            start = pageIndex;
            FulledMessagesPane(tempUser);
            return new Pane();
        });
    }

    private String dateToString(String givenTime) {
        DateTimeFormatter givenFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        LocalDateTime dateTime = LocalDateTime.parse(givenTime, givenFormatter);
        DateTimeFormatter newFormatter = DateTimeFormatter.ofPattern("HH:mm dd.MM");
        return dateTime.format(newFormatter);
    }

    private void AddDocs() {
        if (tempUser != 0) {
            fileChooser = new FileChooser();
            fileList = fileChooser.showOpenMultipleDialog(new Stage());
            if (fileList != null) {
                PaneShadow();
                id_vbFilesMessagesPane.getChildren().clear();
                fileListTemp.clear();
                fileListTemp.addAll(fileList);
                documentSendPane();
            }
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Assalomu aleykum...");
            alert.setContentText("Iltimos, muloqot uchun abonentlar ro'yhatidan foydalanuvchi tanlang...");
            alert.setHeaderText("Muloqot uchun foydalanuvchi tanlanmagan");
            alert.show();
        }
    }

    /**
     * Fayl jo'natish uchun tanlanganda slide style qilib beradi
     */
    private void PaneShadow() {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.4), id_pnShadow);
        fadeTransition.setFromValue(0.0);
        fadeTransition.setToValue(0.4);
        fadeTransition.play();
        id_pnShadow.setVisible(true);
        id_vbDocSend.setVisible(true);
        id_btnDocInfoClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        id_pnShadow.setVisible(false);
                    }
                });
                fadeTransition.setFromValue(0.4);
                fadeTransition.setToValue(0.0);
                fadeTransition.play();
                id_vbDocSend.setVisible(false);
            }
        });
    }

    /**
     * Paperni posganda shu method ishlaydi
     */
    private void documentSendPane() {
        if (fileListTemp.isEmpty()) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.4), id_pnShadow);
            fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    id_pnShadow.setVisible(false);
                }
            });
            fadeTransition.setFromValue(0.4);
            fadeTransition.setToValue(0.0);
            fadeTransition.play();
            id_vbDocSend.setVisible(false);
        }

        id_vbFilesMessagesPane.getChildren().clear();
        if (fileListTemp.size() < 6) {
            id_vbFilesMessagesPane.setPrefHeight(50);
            id_vbFilesMessagesPane.setPadding(new Insets(0, 0, 0, 1));
            for (int i = 0; i < fileListTemp.size(); i++) {
                id_vbFilesMessagesPane.getChildren().add(gethBox(i));
            }
        } else {
            id_vbFilesMessagesPane.setPadding(new Insets(0, 0, 0, 0));
            id_vbFilesMessagesPane.setPrefHeight(310);
            ScrollPane sp = new ScrollPane();
            sp.setPrefWidth(500);
            sp.setPrefHeight(310);
            VBox vb = new VBox();
            vb.setPrefWidth(485);
            vb.setStyle("-fx-background-color:  #b4e0df");
            for (int i = 0; i < fileListTemp.size(); i++) {
                vb.getChildren().add(gethBox(i));
            }
            sp.setContent(vb);
            id_vbFilesMessagesPane.getChildren().add(sp);
        }
    }

    private HBox gethBox(int i) {
        final int finalI = i;
        hBox = new HBox();
        hBox.setPrefSize(485, 50);
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.setPadding(new Insets(5, 0, 5, 10));

        ImageView imgViewRefresh = new ImageView(new Image("/images/chatPage/refresh.png"));
        ImageView imgViewDoc = new ImageView(new Image("/images/chatPage/google-docs.png"));
        ImageView imgViewDel = new ImageView(new Image("/images/chatPage/trash.png"));

        imgViewDoc.setFitWidth(40);
        imgViewDoc.setFitHeight(40);
        imgViewDoc.setLayoutX(10);

        imgViewRefresh.setFitWidth(20);
        imgViewRefresh.setFitHeight(20);

        imgViewDel.setFitWidth(25);
        imgViewDel.setFitHeight(25);

        btnRef = new JFXButton();
        btnRef.setPrefSize(40, 40);
        btnRef.setGraphic(imgViewRefresh);

        btnDel = new JFXButton();
        btnDel.setPrefSize(40, 40);
        btnDel.setGraphic(imgViewDel);
        btnDel.setPadding(new Insets(0, 10, 0, 0));
        btnRef.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnRefresh(finalI);
                System.out.println("Refresh bosildi");
            }
        });
        btnDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                btnDelete(finalI);
                System.out.println("Delete bosiladi");
            }
        });
        lbl = new Label(fileListTemp.get(i).toString());
        DecimalFormat decimalFormat = new DecimalFormat("#.##");
        float fileSize = (float) new File(fileListTemp.get(i).toString()).length() / (1024 * 1024);
        lblSize = new Label(decimalFormat.format(fileSize) + " Mb");
        lbl.setPrefSize(335, 20);
        lbl.setStyle("-fx-text-fill: #10427B; -fx-font-size: 16px");
        lblSize.setStyle("-fx-text-fill: #0A315D; -fx-font-size: 12px; -fx-font-style: Italic");
        hBox.setPadding(new Insets(10, 0, 10, 0));
        hBox.setSpacing(10);
        vBox = new VBox();
        vBox.getChildren().addAll(lbl, lblSize);
        hBox.getChildren().addAll(imgViewDoc, vBox, btnRef, btnDel);
        return hBox;
    }


    private void btnRefresh(int n) {
        fileChooser = new FileChooser();
        fileTemp = fileChooser.showOpenDialog(new Stage());
        if (fileTemp != null) {
            fileListTemp.set(n, fileTemp);
            documentSendPane();
        }
    }

    private void btnDelete(int n) {
        fileListTemp.remove(n);
        documentSendPane();
    }

    //    private void SendDocuments() {
//    }
//
    private void AddDocuments() {
        fileTemp = fileChooser.showOpenDialog(new Stage());
        if (fileTemp != null) {
            fileListTemp.add(fileTemp);
        }
        documentSendPane();
    }

    /**
     btnInfo.setOnAction(new EventHandler<ActionEvent>() {
    @Override public void handle(ActionEvent event) {

    id_pnMessageInfo.setTranslateX(700);
    slide.setDuration(Duration.seconds(0.4));
    slide.setNode(id_pnMessageInfo);

    slide.setOnFinished((ActionEvent e) -> {
    id_pnShadow.setVisible(true);
    });

    id_pnShadow.setVisible(true);
    fadeTransition.setFromValue(0.0);
    fadeTransition.setToValue(0.4);
    fadeTransition.play();

    slide.setToX(200);
    slide.play();

    id_btnPaneClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
    @Override public void handle(MouseEvent event) {

    slide.setOnFinished((ActionEvent e) -> {
    id_pnShadow.setVisible(false);
    });

    fadeTransition.setFromValue(0.4);
    fadeTransition.setToValue(0.0);
    fadeTransition.play();

    slide.setToX(700);
    slide.play();
    id_pnMessageInfo.setTranslateX(200);}
    });

    id_lblPaneInfoLabel.setText(btnInfo.getId());}
    });
     */


//    /*** Keyinchalikka */
//    private File ConvertWebPtoJpg(Image webpImg) {
//        File output = null;
//        try {
//            // WebP faylni yuklash
//            BufferedImage webpImage = ImageIO.read((ImageInputStream) webpImg);
//
//            // JPEG faylga saqlash
//            output = new File("output.jpg");
//            ImageIO.write(webpImage, "jpg", output);
//
//
//        } catch (IOException e) {
//            System.out.println("Xatolik yuz berdi: " + e.getMessage());
//        }
//        return output;
//    }
//
//    private void asfdaf() {
//
//        try {
//            // Rasmdagi o'lchamni o'zgartirish
//            int x;
//            BufferedImage originalImage = ImageIO.read(new File("input.jpg"));
//            int newWidth = 400;
//            int newHeight = 300;
//            BufferedImage resizedImage = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g = resizedImage.createGraphics();
//            g.drawImage(originalImage, 0, 0, newWidth, newHeight, null);
//            g.dispose();
//
//            // O'lchanganni o'zgartirilgan rasmi saqlash
//            File output = new File("output.jpg");
//            ImageIO.write(resizedImage, "jpg", output);
//        } catch (IOException e) {
//            System.out.println("Xatolik yuz berdi: " + e.getMessage());
//        }

//        try {
//            // Rasmni yuklash
//            BufferedImage originalImage = ImageIO.read(new File("input.jpg"));
//
//            // Kesilgan rasmni yaratish
//            int x = 100; // Boshlang'ich x koordinata
//            int y = 100; // Boshlang'ich y koordinata
//            int width = 200; // Kesiladigan rasmning kengligi
//            int height = 200; // Kesiladigan rasmning balandligi
//            BufferedImage croppedImage = originalImage.getSubimage(x, y, width, height);
//
//            // Kesilgan rasmni saqlash
//            File output = new File("output.jpg");
//            ImageIO.write(croppedImage, "jpg", output);
//        } catch (IOException e) {
//            System.out.println("Xatolik yuz berdi: " + e.getMessage());
//        }
//    }
}