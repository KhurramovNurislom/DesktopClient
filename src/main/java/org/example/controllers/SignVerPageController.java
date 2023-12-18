package org.example.controllers;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.Initializable;
import javafx.scene.ImageCursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Main;
import org.example.crypto.UzDSt_1092_2009;
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
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

public class SignVerPageController implements Initializable {
    @FXML
    public JFXButton id_btnChangeFile;
    @FXML
    public TextField id_tfFilePath;
    @FXML
    public Label id_lblImzoEgasi;
    @FXML
    public JFXButton id_btnSignVerification;
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
    @FXML
    public Pane id_pnShadow;

    private FileChooser fileChooser;
    private List<File> fileList;
    private String link;
    private String[] temp;
    private boolean boolPane = true;
    Duration duration = Duration.seconds(0.1);

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        shadow();

        id_lblVerification.setVisible(false);

        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All files", "*.*"),
                new FileChooser.ExtensionFilter("pdf files", "*.pdf"));
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
                temp = id_tfFilePath.getText().replaceAll(", ", ",").split(",");
                /** QRCodeni o'qish */
                for (String s : temp) {
                    link = new PDFWorker().ReadSignLink(s);
                    new Requests().RequestGetSignedFilesInfo(link);
                }
                id_btnChangeFile.setDisable(false);
            }
        });


        id_btnSignVerification.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                for (String s : temp) {
                    boolean boolSignVerify = new UzDSt_1092_2009().verifySignature(Main.getVerification().getPubkey(), s, Main.getVerification().getFayl().getImzo());
                    if (boolSignVerify) {
                        id_lblVerification.setVisible(true);
                        id_lblVerification.setText("Imzo tasdiqlandi");
                        id_lblVerification.setTextFill(Color.GREEN);
                        id_ivCheckSign.setImage(new Image("/images/signVerPage/accept.png"));
                        boolPane = false;
                        shadow();
                        id_tfLogin.setText(Main.getVerification().getUser().getUsername());
                        id_tfEmail.setText(Main.getVerification().getUser().getEmail());
                        new Requests().RequestUsers();
                        for (int j = 0; j < Main.getAUsers().getData().getUsersPermissionsUsers().getData().length; j++) {
                            if (Main.getVerification().getUser().getId() == Main.getAUsers().getData().getUsersPermissionsUsers().getData()[j].getId()) {
                                id_ivUserImage.setImage(new Image(Main.getUrl() + Main.getAUsers().getData().getUsersPermissionsUsers().getData()[j].getAttributes().getRasm().getData().getAttributes().getUrl()));
                            } else {
                                if (Main.getVerification().getUser().getId() == Main.getLoginData().getUser().getId()) {
                                    id_ivUserImage.setImage(new Image(Main.getUrl() + Main.getAUsersMe().getData().getUsersPermissionsUser().getData().getAttributes().getRasm().getData().getAttributes().getUrl()));
                                }
                            }
                        }
                        id_tfSignedFileName.setText(Main.getVerification().getFayl().getName());
                        id_tfSignedFilePath.setText(s);
                        try {
                            id_tfSignedFileVolume.setText(new DecimalFormat("#.##").format((double) Files.size(Path.of(id_tfSignedFilePath.getText())) / (1024 * 1024)) + " Mb");
                            id_tfFileSignedTime.setText(new SimpleDateFormat("HH:mm:ss dd.MM.yyyy").format(new Date(Files.readAttributes(Path.of(id_tfSignedFilePath.getText()),
                                    BasicFileAttributes.class).creationTime().toMillis())));
                        } catch (IOException e) {
                            System.err.println("exception : SignVerPageController(btnSignVerification) => " + e.getCause());
                        }

                    } else {
                        id_lblVerification.setText("Imzo tasdiqlanmadi");
                        id_lblVerification.setTextFill(Color.RED);
                        id_ivCheckSign.setImage(new Image("/images/signVerPage/warning.png"));
                    }
                }
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
}



