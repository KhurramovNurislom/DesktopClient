package org.example.controllers;

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

import java.io.File;
import java.net.URL;
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
//        AddedKeysList();

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
            @Override
            public void handle(ActionEvent event) {
                id_btnSign.setDisable(true);

                /** Imzo index*/
                String[] temp = id_tfFilePath.getText().split(",");


                if (!id_tfFilePath.getText().isEmpty() && new File(id_tfFilePath.getText()).isFile()) {


                    /**  Fayllar tanlanganda imzo qo'yish */

//                    for (int i = 0; i < temp.length; i++) {
//
////                        System.out.println("orginal file => " + temp[i]);
//
//                        try {
//                            /** PDF document ga qrcode ga link upload qilish */
//                            PDFWorkerMethod(temp[i], signLink());
//
////                            System.out.println("qrcode pasted file => " + new PDFWorker().ReadSignLink(Main.getSignedFileInfo().getFilePath()));
//
//                            /** Imzolangan QRCode qo'yilgan faylni qrcode dagi link ka upload qilish */
//                            UpLoadSignedFile(Main.getSignedFileInfo().getFilePath());
//
//
//                            /** File ga imzo qo'yilmoqda */
//                            sign = GenerateDigitalSignature.generateDigitalSignature(Main.getKeys().getData().getKalits().
//                                            getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getAttributes().getPrivkey(),
//                                    new FileInputStream(Main.getSignedFileInfo().getFilePath()));
//
////                            System.out.println("sign : " + sign);
//                            /** Messages fayl va imzo haqidagi ma'lumotlar yuklanadi */
//                            new Requestes().ResponseMessage(Main.getLoginData().getUser().getId(), sign, Main.getKeys().getData().getKalits().getData()[id_cbSignes.getSelectionModel().getSelectedIndex()].getId(), null, null);
//
//                            PaneSingerInfo();
//
//                        } catch (IOException | NoSuchAlgorithmException | KeyManagementException e) {
//                            System.err.println(e.getMessage());
//                        }
//                    }
//

                } else {


                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setHeaderText("Fayl tanlanmagan");
                    alert.setContentText("Imzolanadigan faylni tanlang");
                    alert.show();


                }

                id_ivCheckSign.setVisible(true);
                id_ivCheckSign.setImage(new Image("check.png"));
                id_lblVerification.setVisible(true);
                id_lblVerification.setText("Fayl imzolandi");

                id_btnSign.setDisable(false);


            }
        });
    }

    private void UploadSign(String sign) {

        /** Bu yerga imzoni kerakli linkka yuklovchi request yozilishi kerak */

    }

//    private String signLink() throws IOException, NoSuchAlgorithmException, KeyManagementException {
//        new Requestes().RequestSignLink();
//        return Main.getUrl() + "/api/imzo/link/fayl/" + Main.getHash().getHash();
//    }


//    private void AddedKeysList() {
//
//
//        /** Foydalanuvchining kalitlarini serverdan olib beradi */
//        try {
//            new Requestes().RequestKeys();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
//        /** Serverdan olingan foydalanuvchining kalitlarini keyListga yuklaydi */
//        for (int i = 0; i < Main.getKeys().getData().getKalits().getData().length; i++) {
//            keysList.add(Main.getKeys().getData().getKalits().getData()[i].getAttributes().getNomi() + "\n" + Main.getKeys().getData().getKalits().getData()[i].getAttributes().getCreatedAt());
//        }
//    }

//    public void UpLoadSignedFile(String filePath) throws IOException, NoSuchAlgorithmException, KeyManagementException {
////        System.out.println(filePath);
//        new Requestes().RequestUpload(filePath);
//    }


//    private void PaneSingerInfo() {
//
//        id_tfLogin.setText(Main.getLoginData().getUser().getUsername());
//        id_tfEmail.setText(Main.getLoginData().getUser().getEmail());
//        id_ivUserImage.setImage(new Image(Main.getUrl() + Main.getaUsersMe().getData().getUsersPermissionsUser().getData().getAttributes().getRasm().getData().getAttributes().getUrl()));
//
//        id_tfSignedFileName.setText(Main.getSignedFileInfo().getName());
//        id_tfSignedFilePath.setText(Main.getSignedFileInfo().getFilePath());
//        try {
//            id_tfSignedFileVolume.setText(new DecimalFormat("#.##").format((double) Files.size(Path.of(Main.getSignedFileInfo().getFilePath())) / (1024 * 1024)) + " Mb");
//            id_tfFileSignedTime.setText(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date(Files.readAttributes(Path.of(Main.getSignedFileInfo().getFilePath()), BasicFileAttributes.class).creationTime().toMillis())));
//        } catch (IOException e) {
//            System.err.println("Failed to get file creation time: " + e.getMessage());
//        }
//    }

    /**
     * imzoni pdf file ga qrcode shaklida yuklash va yangi pdf hosil qilish
     */
//    private void PDFWorkerMethod(String filePathPDF, String signText) {
//        /** QRCode qo'yish */
//        try {
//            new PDFWorker().PasteSignLink(filePathPDF, signText);
//        } catch (IOException | WriterException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
