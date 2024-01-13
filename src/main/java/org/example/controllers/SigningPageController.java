package org.example.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import javafx.animation.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.Main;
import org.example.crypto.UzDSt_1092_2009;
import org.example.pfx.AliesKeysReader;
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

    private List<File> fileList;
    private File file;
    private final ObservableList<Pane> keysList = FXCollections.observableArrayList();
    private final boolean boolPane = true;

    private KeyInfoInPFXController keyInfoInPFXController = null;


    Duration duration = Duration.seconds(0.1);

    static double x = 0, y = 0;
    static Stage passStage;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        Main.setPaneShadow(id_pnAllShadow);
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("pdf files", "*.pdf"), new FileChooser.ExtensionFilter("all files", "*.*"));
        fileChooserKey.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PFX files", "*.pfx"), new FileChooser.ExtensionFilter("all files", "*.*"));

        Tooltip tooltip = new Tooltip("Yopiq kalitni tanlash");
        tooltip.setShowDelay(Duration.millis(100));
        tooltip.setFont(Font.font(14));
        id_btnKeyFileChooser.setTooltip(tooltip);


        new AliesKeysReader().AliesCorrect();
        if (Main.getAliesKeys().getAliesKeyList().length == 0) {
            id_lblKeysName.setText("C:\\DSKEYS papkada kalit mavjud emas, kalitni tanlang");
            id_lblKeysName.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    KeyFileChooser();
                }
            });
            id_cbSignes.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    KeyFileChooser();
                }
            });
        } else {


            for (int i = 0; i < Main.getAliesKeys().getAliesKeyList().length; i++) {
                keyInfoInPFXController = new KeyInfoInPFXController();
                keyInfoInPFXController.setK(i);
                keysList.add(new FXMLLoaderWithController().getPane("KeyInfoInPFX", keyInfoInPFXController));
            }


            id_cbSignes.setItems(keysList);
            id_lblKeysName.setText(Main.getListPaths().get(0));
            id_lblKeysName.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    id_cbSignes.show();
                }
            });
            id_cbSignes.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    id_lblKeysName.setText(Main.getListPaths().get(id_cbSignes.getSelectionModel().getSelectedIndex()));
                    Main.setIndexPFXFilePath(id_cbSignes.getSelectionModel().getSelectedIndex());
                }
            });


        }
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
        id_btnKeyFileChooser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                KeyFileChooser();
            }
        });

        id_btnSign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                id_btnSign.setDisable(true);
                btnSignHandle(id_tfFilePath.getText(), id_lblKeysName.getText());
                id_btnSign.setDisable(false);
            }
        });
    }

    private String signedFile() {

        return null;
    }

    private void KeyFileChooser() {
        file = fileChooserKey.showOpenDialog(new Stage());
        if (file != null) {
            id_lblKeysName.setText(file.getAbsolutePath());
            new AliesKeysReader().AliesCorrectFile(id_lblKeysName.getText());
            keyInfoInPFXController = new KeyInfoInPFXController();
            keyInfoInPFXController.setBool(false);
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

    private void btnSignHandle(String filePaths, String pfxPath) {

        if (new File(pfxPath).isFile() &&
                new File(filePaths).isFile() &&
                filePaths.endsWith(".pdf")) {

            Main.setPaneShadow(id_pnAllShadow);
            Main.showPassStage(true);
            Main.setKeyFilePath(pfxPath);

        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("title");
            alert.setHeaderText("HeaderText");
            alert.setContentText("ContentText");
            alert.show();
        }

        /** Timer ishlaydi, btn bosilganda 60 s kutadi keyin davom ettiradi*/

        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {

                if (Main.isPassVerify()) {
                    timer.cancel();
                    String sign = "";
                    /** Imzolanadigan pdf filelarni ajratib oladi */
                    String[] temp = filePaths.replaceAll(", ", ",").split(",");
                    if (!filePaths.isEmpty()) {
                        /**  Fayllar tanlanganda imzo qo'yish */
                        for (String s : temp) {
                            if (new File(s).isFile() && new File(s).getName().toLowerCase().endsWith(".pdf")) {
                                /** PDF document ga link yuklangan qrcode yuklash */
                                new PDFWorker().PasteSignLink(s, signLink());
                                /** Imzolangan, QRCode qo'yilgan faylni qrcode dagi link ka yuklash */
                                new Requests().RequestUpload(Main.getSignedFileInfo().getFilePath());
                                sign = new UzDSt_1092_2009().signGenerate(Main.getPrivateKey(), pfxPath);
                                /** Messages fayl va imzo haqidagi ma'lumotlar yuklanadi */
                                new Requests().ResponseMessage(Main.getLoginData().getUser().getId(), sign, Integer.parseInt((Main.getAliesKey().getUid())), null, null);
                                PaneSingerInfo();
                            }
                        }
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setHeaderText("Fayl tanlanmagan");
                        alert.setContentText("Imzolanadigan faylni tanlang");
                        alert.show();
                    }

//                shadow();

                    Main.setPassVerify(false);
                }
            }
        };
        timer.schedule(task, 0, 100);
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("Timer To'xtatdi......");
                timer.cancel();
            }
        }, 60000);
    }

    private void PaneSingerInfo() {


        TranslateTransition slide = new TranslateTransition();
        FadeTransition fTr1 = new FadeTransition(Duration.seconds(0.5), id_ivLogo);
        FadeTransition fTr2 = new FadeTransition(Duration.seconds(0.5), id_pnShadow);
        slide.setNode(id_pnSign);
//        id_pnSign.setTranslateY(0);
        slide.setDuration(Duration.seconds(0.4));
        slide.setOnFinished((ActionEvent e) -> {

            id_ivLogo.setVisible(false);
            id_pnShadow.setVisible(true);
            fTr2.setFromValue(0.0);
            fTr2.setToValue(1.0);
            fTr2.play();

        });

//        id_pnShadow.setVisible(true);
        fTr1.setFromValue(1.0);
        fTr1.setToValue(0.0);
        fTr1.play();

        slide.setToY(-350);
        slide.play();


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

}
