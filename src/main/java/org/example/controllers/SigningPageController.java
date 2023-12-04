package org.example.controllers;

import com.google.zxing.WriterException;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
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
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.SneakyThrows;
import org.example.Main;
import org.example.crypto.UzDSt_1092_2009;
import org.example.utils.PDFWorker;
import org.example.utils.Requests;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SigningPageController implements Initializable {

    @FXML
    public JFXButton id_btnChangeFile;
    @FXML
    public JFXButton id_btnSign;
    @FXML
    public JFXComboBox id_cbSignes;
    @FXML
    public TextField id_tfFilePath;
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
    public ImageView id_ivCheckSign;
    @FXML
    public Label id_lblVerification;

    private final FileChooser fileChooser = new FileChooser();
    private List<File> fileList;
    private final ObservableList<String> keysList = FXCollections.observableArrayList();
    private String sign;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id_ivCheckSign.setVisible(false);
        id_lblVerification.setVisible(false);

        /** kalitlar ro'yhatini to'ldirish*/
        AddedKeysList();

        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pdf files", "*.pdf"),
                new FileChooser.ExtensionFilter("all files", "*.*"));

        id_cbSignes.setItems(keysList);

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
            @SneakyThrows
            @Override
            public void handle(ActionEvent event) {
                id_btnSign.setDisable(true);

                String t = id_tfFilePath.getText();
                t = t.replaceAll(", ", ",");

                /** Imzolanadigan pdf filelarni ajratib oladi */
                String[] temp = t.split(",");

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
                                    new FileInputStream(Main.getSignedFileInfo().getFilePath()));

                            /** Messages fayl va imzo haqidagi ma'lumotlar yuklanadi */
                            new Requests().ResponseMessage(Main.getLoginData().getUser().getId(), sign, Main.getKeys().getData().getKalits().getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getId(), "myMessage", "myMessageSing");

                            PaneSingerInfo();

                        }
                    }
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Fayl tanlanmagan");
                    alert.setContentText("Imzolanadigan faylni tanlang");
                    alert.show();
                }

//                        try {
//                            /** PDF document ga qrcode ga link upload qilish */
//                            new PDFWorker().PasteSignLink(temp[i], signLink());
//
//                            /** Imzolangan QRCode qo'yilgan faylni qrcode dagi link ka upload qilish */
//                            new Requests().RequestUpload(Main.getSignedFileInfo().getFilePath());
//
//
////                            System.out.println("qrcode pasted file => " + new PDFWorker().ReadSignLink(Main.getSignedFileInfo().getFilePath()));
//
//
////                            UpLoadSignedFile(Main.getSignedFileInfo().getFilePath());
//
//
//                            /** File ga imzo qo'yilmoqda */
////                            sign = GenerateDigitalSignature.generateDigitalSignature(Main.getKeys().getData().getKalits().
////                                            getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getAttributes().getPrivkey(),
////                                    new FileInputStream(Main.getSignedFileInfo().getFilePath()));
//
////                            System.out.println("sign : " + sign);
////                            /** Messages fayl va imzo haqidagi ma'lumotlar yuklanadi */
////                            new Requests().ResponseMessage(Main.getLoginData().getUser().getId(), sign, Main.getKeys().getData().getKalits().getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getId(), null, null);
//
//                            PaneSingerInfo();
//
//                        } catch (IOException | NoSuchAlgorithmException | KeyManagementException | WriterException e) {
//                            System.err.println("exception : SigningPageController(btnSign) => " + e.getMessage());
//                            throw new RuntimeException(e);
//                        }
//                    }
//
//                } else {
//                System.out.println("asdasd");
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setHeaderText("Fayl tanlanmagan");
//                    alert.setContentText("Imzolanadigan faylni tanlang");
//                    alert.show();
//                }
                id_ivCheckSign.setVisible(true);
                id_ivCheckSign.setImage(new Image("/images/signedPage/check.png"));
                id_lblVerification.setVisible(true);
                id_lblVerification.setText("Fayl imzolandi");
                id_btnSign.setDisable(false);
            }
        });
    }

    private void UploadSign(String sign) {

        /** Bu yerga imzoni kerakli linkka yuklovchi request yozilishi kerak */

    }

    private String signLink() {
        new Requests().RequestSignLink();
        return Main.getUrl() + "/api/imzo/link/fayl/" + Main.getHash().getHash();
    }


    private void AddedKeysList() {
        /** Foydalanuvchining kalitlarini serverdan olib beradi */ // shu qismini qayta ko'rib chiqish kerak, dasturni qotiryabdi !!!
        try {
            new Requests().RequestKeys();
        } catch (IOException e) {
            System.err.println("SigningPageController().AddedKeysList() => " + e.getMessage());
            throw new RuntimeException(e);
        }
        /** Serverdan olingan foydalanuvchining kalitlarini keyListga yuklaydi */
        for (int i = 0; i < Main.getKeys().getData().getKalits().getData().length; i++) {
            keysList.add(Main.getKeys().getData().getKalits().getData()[i].getAttributes().getNomi() + "\n" + Main.getKeys().getData().getKalits().getData()[i].getAttributes().getCreatedAt());
        }
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
            System.err.println("exception : SigningPageController().PaneSingerInfo() => " + e.getMessage());
        }
    }

}
