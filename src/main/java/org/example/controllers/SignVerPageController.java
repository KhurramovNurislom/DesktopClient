//package org.example.controllers;
//
//import com.example.webclient.Main;
//import com.example.webclient.services.PDFWorker;
//import com.example.webclient.services.Requestes;
//import com.example.webclient.signature.VerifyDigitalSignature;
//import com.google.zxing.ChecksumException;
//import com.google.zxing.FormatException;
//import com.google.zxing.NotFoundException;
//import com.jfoenix.controls.JFXButton;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Label;
//import javafx.scene.control.TextField;
//import javafx.scene.image.Image;
//import javafx.scene.image.ImageView;
//import javafx.scene.paint.Color;
//import javafx.stage.FileChooser;
//import javafx.stage.Stage;
//
//import java.io.File;
//import java.io.IOException;
//import java.net.URL;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.attribute.BasicFileAttributes;
//import java.text.DecimalFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//import java.util.List;
//import java.util.ResourceBundle;
//
//public class SignVerPageController implements Initializable {
//    public JFXButton id_btnChangeFile;
//    public TextField id_tfFilePath;
//    public Label id_lblImzoEgasi;
//    public JFXButton id_btnSignVerification;
//    public ImageView id_ivUserImage;
//    public TextField id_tfLogin;
//    public TextField id_tfEmail;
//    public TextField id_tfSignedFileName;
//    public TextField id_tfSignedFilePath;
//    public TextField id_tfSignedFileVolume;
//    public TextField id_tfFileSignedTime;
//    public ImageView id_ivCheckSign;
//    public Label id_lblVerification;
//    private FileChooser fileChooser;
//    private List<File> fileList;
//    private String link;
//    private String[] temp;
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//        id_lblVerification.setVisible(false);
//
//        fileChooser = new FileChooser();
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("All files", "*.*"),
//                new FileChooser.ExtensionFilter("pdf files", "*.pdf"));
//
//        id_btnChangeFile.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                id_btnChangeFile.setDisable(true);
//
//                fileList = fileChooser.showOpenMultipleDialog(new Stage());
//                if (fileList != null) {
//                    fileList.stream().forEach(selectedFiles -> {
//                        id_tfFilePath.setText(fileList.toString().replaceAll("\\[", "").replaceAll("]", ""));
//                    });
//                }
//                temp = id_tfFilePath.getText().split(",");
//                for (int i = 0; i < temp.length; i++) {
//                    System.out.println(temp[i]);
//
//                    /**
//                     * QRCodeni o'qish
//                     */
//                    try {
//                        link = new PDFWorker().ReadSignLink(temp[i]);
//                        System.out.println("Link => " + link);
//                        new Requestes().RequestGetSignerFilesInfo(link);
//
//                    } catch (ChecksumException | NotFoundException | IOException | FormatException e) {
//                        throw new RuntimeException(e);
//                    }
//                }
//                id_btnChangeFile.setDisable(false);
//            }
//        });
//
//        id_btnSignVerification.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                /**      Test ishchi   */
//                for (int i = 0; i < temp.length; i++) {
//                    boolean boolSignVerify = VerifyDigitalSignature.verifyDigitalSignature(Main.getVerification().getFayl().getImzo(), temp[i], Main.getVerification().getPubkey());
//
//                    if (boolSignVerify) {
//                        id_lblVerification.setVisible(true);
//                        id_lblVerification.setText("Imzo tasdiqlandi");
//                        id_lblVerification.setTextFill(Color.GREEN);
//                        id_ivCheckSign.setImage(new Image("accept.png"));
//
//
//
//                        id_tfLogin.setText(Main.getVerification().getUser().getUsername());
//                        id_tfEmail.setText(Main.getVerification().getUser().getEmail());
//                        System.out.println(Main.getUrl());
//                        try {
//                            new Requestes().RequestUsers();
//                        } catch (IOException e) {
//                            throw new RuntimeException(e);
//                        }
//                        String rasmUrl;
//                        for (int j = 0; j < Main.getaUsers().getData().getUsersPermissionsUsers().getData().length; j++) {
//                            if (Main.getVerification().getUser().getId() == Main.getaUsers().getData().getUsersPermissionsUsers().getData()[j].getId()) {
//                                id_ivUserImage.setImage(new Image(Main.getUrl() + Main.getaUsers().getData().getUsersPermissionsUsers().getData()[j].getAttributes().getRasm().getData().getAttributes().getUrl()));
//                            } else {
//                                if (Main.getVerification().getUser().getId() == Main.getLoginData().getUser().getId()) {
//                                    id_ivUserImage.setImage(new Image(Main.getUrl() + Main.getaUsersMe().getData().getUsersPermissionsUser().getData().getAttributes().getRasm().getData().getAttributes().getUrl()));
//                                }
//                            }
//                        }
//
//                        id_tfSignedFileName.setText(Main.getVerification().getFayl().getName());
//                        id_tfSignedFilePath.setText(temp[i]);
//                        try {
//                            id_tfSignedFileVolume.setText(new DecimalFormat("#.##").format((double) Files.size(Path.of(id_tfSignedFilePath.getText())) / (1024 * 1024)) + " Mb");
//                            id_tfFileSignedTime.setText(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date(Files.readAttributes(Path.of(id_tfSignedFilePath.getText()), BasicFileAttributes.class).creationTime().toMillis())));
//                        } catch (IOException e) {
//                            System.err.println("Failed to get file creation time: " + e.getMessage());
//                        }
//
//                    } else {
//                        id_lblVerification.setVisible(true);
//                        id_lblVerification.setText("Imzo tasdiqlanmadi");
//                        id_lblVerification.setTextFill(Color.RED);
//
//                        id_ivCheckSign.setImage(new Image("warning.png"));
//
//
//                    }
//                }
//
//                /*****************************************************/
//            }
//        });
//    }
//
//    private String PDFWorker() {
//
//
//        return "asdasdasdasd";
//    }
//
//    private void PaneSingerInfo() {
//
//
//    }
//}
//
//
//
