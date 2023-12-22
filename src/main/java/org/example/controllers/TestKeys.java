package org.example.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Main;
import org.example.crypto.UzDSt_1092_2009;
import org.example.modules.AliesKey.AliesKey;
import org.example.modules.AliesKey.AliesKeys;
import org.example.pfx.AliesKeysReader;
import org.example.pfx.ReadAliesInPFX;
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

public class TestKeys implements Initializable {
    @FXML
    public JFXButton id_btnChangeFile;
    @FXML
    public JFXButton id_btnSign;
    @FXML
    public TextField id_tfFilePath;
    @FXML
    public ImageView id_ivCheckSign;
    @FXML
    public Label id_lblVerification;
    @FXML
    public JFXComboBox id_cbSignes;
    @FXML
    public ImageView id_ivUserImage;
    @FXML
    public TextField id_tfLogin;
    @FXML
    public TextField id_tfEmail;
    @FXML
    public TextField id_tfSignedFileName;
    @FXML
    public TextField id_tfSignedFilePath;
    @FXML
    public TextField id_tfSignedFileVolume;
    @FXML
    public TextField id_tfFileSignedTime;
    @FXML
    public Pane id_pnShadow;

    private AliesKeys aliesKeys;

    private final FileChooser fileChooser = new FileChooser();
    public Pane id_pnBackground;
    private List<File> fileList;
    private final ObservableList<String> keysList = FXCollections.observableArrayList();
    private String sign;
    private boolean boolPane = true;
    Duration duration = Duration.seconds(0.1);

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        shadow();

        id_ivCheckSign.setVisible(false);
        id_lblVerification.setVisible(false);

        /** kalitlar ro'yhatini to'ldirish*/
//        AddedKeysList();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pdf files", "*.pdf"),
                new FileChooser.ExtensionFilter("all files", "*.*"));

//        id_cbSignes.setItems(keysList);
//        keys();

//        List<String> list = keys();


        keysList.addAll();

//        for (int i = 0; i < 10; i++) {
//
//
//
//            keysList.add()
//        }
//        test test1 = new test();
//        test1.setText("Assalom aleykum");

//        Pane pane = new Pane(new Label("Assalom Aleykum"));
//        keysList.add(pane);
        id_cbSignes.setItems(keysList);

//        AliesCorrect(keysList);

        new AliesKeysReader().AliesCorrect();

//        System.out.println(Main.getAliesKeys().getAliesKeyList()[0].getName());

//        System.out.println(Main.getAliesKeys().getAliesKeyList()[1].getName());
//        id_cbSignes.setItems();

//        for (int i = 0; i < asd.size(); i++) {
//            System.out.println(asd.get(i));
//        }

        id_cbSignes.setPromptText("Kerakli yopiq kalitni tanlang...");
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

        id_btnSign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnSign.setDisable(true);
                /** Imzolanadigan pdf filelarni ajratib oladi */
                String[] temp = id_tfFilePath.getText().replaceAll(", ", ",").split(",");
                if (!id_tfFilePath.getText().isEmpty()) {
                    /**  Fayllar tanlanganda imzo qo'yish */
                    for (String s : temp) {
                        if (new File(s).isFile() && new File(s).getName().toLowerCase().endsWith(".pdf")) {
                            /** PDF document ga link yuklangan qrcode yuklash */
                            new PDFWorker().PasteSignLink(s, signLink());
                            /** Imzolangan, QRCode qo'yilgan faylni qrcode dagi link ka yuklash */
                            new Requests().RequestUpload(Main.getSignedFileInfo().getFilePath());
                            sign = new UzDSt_1092_2009().signGenerate(Main.getKeys().getData().getKalits().
                                            getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getAttributes().getPrivkey(),
                                    Main.getSignedFileInfo().getFilePath());
                            /** Messages fayl va imzo haqidagi ma'lumotlar yuklanadi */
                            new Requests().ResponseMessage(Main.getLoginData().getUser().getId(),
                                    sign,
                                    Main.getKeys().getData().getKalits().getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getId(),
                                    null,
                                    null);
                            PaneSingerInfo();
                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Fayl tanlanmagan");
                    alert.setContentText("Imzolanadigan faylni tanlang");
                    alert.show();
                }
                id_ivCheckSign.setVisible(true);
                id_ivCheckSign.setImage(new Image("/images/signedPage/check.png"));
                boolPane = false;
                shadow();
                id_lblVerification.setVisible(true);
                id_lblVerification.setText("Fayl imzolandi");
                id_btnSign.setDisable(false);
            }
        });
    }

    private void shadow() {

        if (boolPane) {
            id_pnShadow.setVisible(true);
            // Panega kursor kirganda
            id_pnShadow.setOnMouseEntered(e -> {
                Timeline timeline = new Timeline(
                        new KeyFrame(duration, new KeyValue(id_pnShadow.opacityProperty(), 0.0)),
                        new KeyFrame(Duration.ZERO, new KeyValue(id_pnShadow.opacityProperty(), 0.8)));
                timeline.play();
            });

            // Panedan kursor chiqqanda
            id_pnShadow.setOnMouseExited(e -> {
                Timeline timeline = new Timeline(
                        new KeyFrame(duration, new KeyValue(id_pnShadow.opacityProperty(), 0.8)),
                        new KeyFrame(Duration.ZERO, new KeyValue(id_pnShadow.opacityProperty(), 0.0))
                );
                timeline.play();
            });
        } else {
            Timeline timeline = new Timeline(
                    new KeyFrame(duration, new KeyValue(id_pnShadow.opacityProperty(), 0.0)),
                    new KeyFrame(Duration.ZERO, new KeyValue(id_pnShadow.opacityProperty(), 0.8)));
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
            id_tfFileSignedTime.setText(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date(Files.readAttributes(Path.of(Main.getSignedFileInfo().getFilePath()),
                    BasicFileAttributes.class).creationTime().toMillis())));
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


}
