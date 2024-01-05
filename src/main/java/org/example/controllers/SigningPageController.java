package org.example.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.Getter;
import lombok.Setter;
import org.example.Main;
import org.example.crypto.UzDSt_1092_2009;
import org.example.modules.AliesKey.AliesKeys;
import org.example.pfx.AliesKeysReader;
import org.example.utils.FXMLLoaderMade;
import org.example.utils.FXMLLoaderWithController;
import org.example.utils.PDFWorker;
import org.example.utils.Requests;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class SigningPageController implements Initializable {

    public JFXButton id_btnChangeFile;
    public JFXButton id_btnSign;
    public TextField id_tfFilePath;
    public JFXComboBox<Pane> id_cbSignes;
    public ImageView id_ivUserImage;
    public TextField id_tfLogin;
    public TextField id_tfEmail;
    public TextField id_tfSignedFileName;
    public TextField id_tfSignedFilePath;
    public TextField id_tfSignedFileVolume;
    public TextField id_tfFileSignedTime;
    public Pane id_pnShadow;
    public Label id_lblKeysName;
    public Pane id_pnAllShadow;
    public JFXButton id_btnKeyFileChooser;
    public Pane id_pnSign;
    public ImageView id_ivLogo;
    public Pane id_pnFund;
    private final FileChooser fileChooser = new FileChooser();
    private final FileChooser fileChooserKey = new FileChooser();
    public Pane id_pnPass;
    private List<File> fileList;
    private File file;
    private final ObservableList<Pane> keysList = FXCollections.observableArrayList();
    private final boolean boolPane = true;

    /**
     * elements of PasswordPage
     */
    public Label id_lblKeyPath;
    public TextField id_tfPass;
    public PasswordField id_pfPass;
    public ImageView id_ivEye;
    public Label id_lblTitle;
    public ImageView id_ivClose;
    public Pane id_pnPane;
    public JFXButton id_btnExit;
    public Pane id_pnTitle;
    public JFXButton id_btnOk;
    public JFXButton id_btnEye;
    private boolean eyeBool = false;

    private int seconds = 60;

    private String passOld = "";

    private Stage passStage;

    private double x = 0, y = 0;

    Duration duration = Duration.seconds(0.1);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        /** FileChooserlarga pdf va pfx file */
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pdf files", "*.pdf"), new FileChooser.ExtensionFilter("all files", "*.*"));
        fileChooserKey.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PFX files", "*.pfx"), new FileChooser.ExtensionFilter("all files", "*.*"));

        /** Imzolash uchun file tanlanadi */
        id_btnChangeFile.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnChangeFile.setDisable(true);
                fileList = fileChooser.showOpenMultipleDialog(new Stage());
                if (fileList != null) {
                    fileList.forEach(selectedFiles -> {
                        id_tfFilePath.setText(fileList.toString().replaceAll("\\[", "").replaceAll("]", ""));
                    });
                }
                id_btnChangeFile.setDisable(false);
            }
        });

        /** KeyTanlashga toolTip */
        Tooltip tooltip = new Tooltip("Yopiq kalitni tanlash");
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setFont(Font.font(14));
        id_btnKeyFileChooser.setTooltip(tooltip);

        /** C:\\DSKEYS papkani ichidagi pfx filelarning aliasini o'qiydi */
        new AliesKeysReader().AliesCorrect();

        /** C:\\DSKEYS papkani ichidagi pfx file bor yoki yo'qligini tekshiradi */
        if (Main.getAliesKeys().getAliesKeyList().length == 0) {
            id_lblKeysName.setText("C:\\DSKEYS papkada kalit mavjud emas, kalitni tanlang");
            id_lblKeysName.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    /** Kalit tanlash uchun fileChooserni ochib yuboradi */
                    KeyFileChooser();
                }
            });
            id_cbSignes.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {

                    /** Kalit tanlash uchun fileChooserni ochib yuboradi */
                    KeyFileChooser();
                }
            });
        } else {

            /** PFX filelar bo'lsa ComboBoxga ma'lumotlarini yuklaydi */
            for (int i = 0; i < Main.getAliesKeys().getAliesKeyList().length; i++) {
                KeyInfoInPFXController keyInfoInPFXController = new KeyInfoInPFXController();
                keyInfoInPFXController.setK(i);
                keysList.add(new FXMLLoaderWithController().getPane("KeyInfoInPFX", keyInfoInPFXController));
            }
            id_cbSignes.setItems(keysList);

            /** Tanlangan sifatida birinchi fileni belgilaydi */
            id_lblKeysName.setText(Main.getListPaths().get(0));
            id_lblKeysName.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    id_cbSignes.show();
                }
            });

            /** ComboBoxdan select qilingan file yo'lini labelga chiqaradi (imzolashda labeldagi yo'li ko'rsatilgan kalitdan foydalaniladi)*/
            id_cbSignes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    id_lblKeysName.setText(Main.getListPaths().get(id_cbSignes.getSelectionModel().getSelectedIndex()));
                    Main.setIndexPFXFilePath(id_cbSignes.getSelectionModel().getSelectedIndex());
                }
            });
        }

        /** PFX file DSKEYS papkada bo'lmasa foydalanuvchi fileChooser orqali kalitni ko'rsatadi*/
        id_btnKeyFileChooser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                /** Kalit tanlash uchun fileChooserni ochib yuboradi */
                KeyFileChooser();
            }
        });

        /** Imzolash jarayoni boshlandi*/
        id_btnSign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnSign.setDisable(true);

                /** Imzolash uchun file, uning pdf ekanligi tekshiriladi va yopiq kalit tanlanganligi, uning pfx file ekanligi tekshiriladi*/
//                if (new File(id_lblKeysName.getText()).isFile() && new File(id_lblKeysName.getText()).getAbsolutePath().endsWith(".pfx") && new File(id_tfFilePath.getText()).isFile() && id_tfFilePath.getText().endsWith(".pdf")) {

                showPassStage();

//                } else {
                /** Xatolik bo'lsa alert e'lon qilish*/
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("title");
//                    alert.setHeaderText("HeaderText");
//                    alert.setContentText("ContentText");
//                    alert.show();
//                }


                /** Imzolanadigan pdf filelarni ajratib oladi */
//                String[] temp = id_tfFilePath.getText().replaceAll(", ", ",").split(",");
//                if (!id_tfFilePath.getText().isEmpty()) {
//                    /**  Fayllar tanlanganda imzo qo'yish */
//                    for (String s : temp) {
//                        if (new File(s).isFile() && new File(s).getName().toLowerCase().endsWith(".pdf")) {
//                            /** PDF document ga link yuklangan qrcode yuklash */
//                            new PDFWorker().PasteSignLink(s, signLink());
//                            /** Imzolangan, QRCode qo'yilgan faylni qrcode dagi link ka yuklash */
//                            new Requests().RequestUpload(Main.getSignedFileInfo().getFilePath());
//                            sign = new UzDSt_1092_2009().signGenerate(Main.getKeys().getData().getKalits().
//                                            getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getAttributes().getPrivkey(),
//                                    Main.getSignedFileInfo().getFilePath());
//                            /** Messages fayl va imzo haqidagi ma'lumotlar yuklanadi */
//                            new Requests().ResponseMessage(Main.getLoginData().getUser().getId(),
//                                    sign,
//                                    Main.getKeys().getData().getKalits().getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getId(),
//                                    null,
//                                    null);
//                            PaneSingerInfo();
//                        }
//                    }
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setHeaderText("Fayl tanlanmagan");
//                    alert.setContentText("Imzolanadigan faylni tanlang");
//                    alert.show();
//                }
////                id_ivCheckSign.setVisible(true);
////                id_ivCheckSign.setImage(new Image("/images/signedPage/check.png"));
//                boolPane = false;
//                shadow();
//                id_lblVerification.setVisible(true);
//                id_lblVerification.setText("Fayl imzolandi");

                id_btnSign.setDisable(false);
            }
        });


//        /********** PasswordPage***********/
//
//        timer();
//        hiddenEyes();
//
//        Tooltip tooltip1 = new Tooltip("matn ustiga bi1 marta bosib va nusxa oling");
//        tooltip.setShowDelay(Duration.millis(100));
//        id_lblKeyPath.setTooltip(tooltip1);
//        id_btnOk.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//
////                new PFXManager().readPfxFile(Main.getKeyFilePath(), id_pfPass.getText());
////
////                if (true) {
//
//
////                } else {
////
////                }
//
//            }
//        });
//        id_lblKeyPath.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                ClipboardContent clipboardContent = new ClipboardContent();
//                clipboardContent.putString(id_lblKeyPath.getText());
//                Clipboard.getSystemClipboard().setContent(clipboardContent);
//            }
//        });
//
//        id_btnEye.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//                eyeBool = !eyeBool;
//                if (eyeBool) {
//                    id_ivEye.setImage(new Image("/images/passKeys/visible.png"));
//                    id_tfPass.setVisible(true);
//                    id_pfPass.setVisible(false);
//                } else {
//                    id_ivEye.setImage(new Image("/images/passKeys/show.png"));
//                    id_tfPass.setVisible(false);
//                    id_pfPass.setVisible(true);
//                }
//            }
//        });
//
//        id_btnExit.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent actionEvent) {
//
//            }
//        });
//
//        id_ivClose.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent e) {
//
//            }
//        });
//
//
//        id_lblKeyPath.setText(Main.getListPaths().get(Main.getIndexPFXFilePath()));
//
//
//        id_tfPass.textProperty().addListener(new ChangeListener<String>() {
//            @Override
//            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
//                if (newValue.length() < 8) {
//                    id_tfPass.setStyle("-fx-text-fill:  RED");
//                    id_pfPass.setStyle("-fx-text-fill:  RED");
//                } else if (newValue.length() < 17) {
//                    passOld = newValue;
//                    id_tfPass.setStyle("-fx-text-fill:  #0F2A62");
//                    id_pfPass.setStyle("-fx-text-fill:  #0F2A62");
//                } else {
//                    id_tfPass.setText(passOld);
//                    id_pfPass.setText(passOld);
//                    id_tfPass.setStyle("-fx-text-fill:  RED");
//                    id_pfPass.setStyle("-fx-text-fill:  RED");
//                }
//            }
//        });
//
//        id_ivClose.setOnMouseEntered(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                id_ivClose.setImage(new Image("/images/passKeys/close.gif"));
//            }
//        });
//
//        id_ivClose.setOnMouseExited(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent mouseEvent) {
//                id_ivClose.setImage(new Image("/images/passKeys/close_red.png"));
//            }
//        });

    }

    private void KeyFileChooser() {
        file = fileChooserKey.showOpenDialog(new Stage());
        if (file != null) {
            id_lblKeysName.setText(file.getAbsolutePath());
        }
    }


    private void shadow() {

        if (boolPane) {

            id_pnShadow.setVisible(true);

            // Panega kursor kirganda
            id_pnShadow.setOnMouseEntered(e -> {

                Timeline timeline = new Timeline(new KeyFrame(duration, new KeyValue(id_pnShadow.opacityProperty(), 0.0)), new KeyFrame(Duration.ZERO, new KeyValue(id_pnShadow.opacityProperty(), 0.6)));
                timeline.play();

            });

            // Panedan kursor chiqqanda
            id_pnShadow.setOnMouseExited(e -> {

                Timeline timeline = new Timeline(new KeyFrame(duration, new KeyValue(id_pnShadow.opacityProperty(), 0.6)), new KeyFrame(Duration.ZERO, new KeyValue(id_pnShadow.opacityProperty(), 0.0)));
                timeline.play();

            });

        } else {

            Timeline timeline = new Timeline(new KeyFrame(duration, new KeyValue(id_pnShadow.opacityProperty(), 0.0)), new KeyFrame(Duration.ZERO, new KeyValue(id_pnShadow.opacityProperty(), 0.6)));
            timeline.play();

            id_pnShadow.setVisible(false);
        }
    }


    private String signLink() {
        new Requests().RequestSignLink();
        return Main.getUrl() + "/api/imzo/link/fayl/" + Main.getHash().getHash();
    }


    private void PaneSingerInfo() {
        id_tfLogin.setText(Main.getLoginData().getUser().getUsername());
        id_tfEmail.setText(Main.getLoginData().getUser().getEmail());
        id_ivUserImage.setImage(new Image(Main.getUrl() + Main.getAUsersMe().getData().getUsersPermissionsUser().getData().getAttributes().getRasm().getData().getAttributes().getUrl()));
        id_tfSignedFileName.setText(Main.getSignedFileInfo().getName());
        id_tfSignedFilePath.setText(Main.getSignedFileInfo().getFilePath());
        try {
            id_tfSignedFileVolume.setText(new DecimalFormat("#.##").format((double) Files.size(Path.of(Main.getSignedFileInfo().getFilePath())) / (1024 * 1024)) + " Mb");
            id_tfFileSignedTime.setText(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date(Files.readAttributes(Path.of(Main.getSignedFileInfo().getFilePath()), BasicFileAttributes.class).creationTime().toMillis())));
        } catch (IOException e) {
            System.err.println("exception : SigningPageController().PaneSingerInfo => " + e.getCause());
        }
    }

//    private void AddedKeysList() {
//        /** Foydalanuvchining kalitlarini serverdan olib beradi */
//        new Requests().RequestKeys();
//        /** Serverdan olingan foydalanuvchining kalitlarini keyListga yuklaydi */
//        for (int i = 0; i < Main.getKeys().getData().getKalits().getData().length; i++) {
//            keysList.add(Main.getKeys().getData().getKalits().getData()[i].getAttributes().getNomi() + "\n"
//                    + Main.getKeys().getData().getKalits().getData()[i].getAttributes().getCreatedAt());
//        }
//    }


//    private void passShadow(boolean bool) {
//
//        if (bool) {
////            id_pnAllShadow.setVisible(true);
//            fadeTransition.setFromValue(0.0);
//            fadeTransition.setToValue(0.5);
//            fadeTransition.play();
//        } else {
//            fadeTransition.setFromValue(0.5);
//            fadeTransition.setToValue(0.0);
//            fadeTransition.play();
//            id_pnAllShadow.setVisible(false);
//        }
//
//
//    }


    private void showPassStage() {

        id_pnPass.setVisible(true);

        id_pnTitle.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    x = event.getSceneX();
                    y = event.getSceneY();
                }
            });

        id_pnPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    id_pnPane.setLayoutX(event.getScreenX() - x);
                    id_pnPane.setLayoutY(event.getScreenY() - y);
                }
            });

//        if (bool) {
//        Stage asd = new Stage();
//        Scene scene = new Scene(new FXMLLoaderMade().getPane("PasswordKey"));
////            id_pnSign.setOnMousePressed(new EventHandler<MouseEvent>() {
////                @Override
////                public void handle(MouseEvent event) {
////                    x = event.getSceneX();
////                    y = event.getSceneY();
////                }
////            });
////            id_pnPane.setOnMouseDragged(new EventHandler<MouseEvent>() {
////                @Override
////                public void handle(MouseEvent event) {
////                    passStage.setX(event.getScreenX() - x);
////                    passStage.setY(event.getScreenY() - y);
////                }
////            });
//        asd.setScene(scene);
//        asd.initModality(Modality.APPLICATION_MODAL);
//        asd.initStyle(StageStyle.TRANSPARENT);
//        asd.centerOnScreen();
//        System.out.println("asdasdasdasdasdasdasdasdasd");
//        asd.show();

//        } else {
////            allShadow(false);
////            passStage.close();
//        }

    }


    private void allShadow(boolean bool) {

        if (bool) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), id_pnAllShadow);
            id_pnAllShadow.setVisible(true);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(0.6);
            fadeTransition.play();

        } else {
            new Thread() {
                @Override
                public void run() {
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), id_pnAllShadow);
                    fadeTransition.setFromValue(0.6);
                    fadeTransition.setToValue(0.0);
                    fadeTransition.play();
                }
            }.start();
            new Thread() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(300);
                        id_pnAllShadow.setVisible(false);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }.start();
        }
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
        id_lblTitle.setText("Oyna yopilgunicha " + seconds + " sekund");
        if (seconds == 0) {
            passStage.close();
        }
    }


}
