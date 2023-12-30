package org.example.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import org.example.Main;
import org.example.crypto.UzDSt_1092_2009;
import org.example.pfx.PFXManager;
import org.example.utils.Requests;
import org.paynet.util.encoders.Hex;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.security.KeyPair;
import java.util.ResourceBundle;

public class KeysGenPageController implements Initializable {

    @FXML
    public TableView id_tvApplication;
    @FXML
    public TableColumn id_tcNumber;
    @FXML
    public TableColumn id_tcServiceName;
    @FXML
    public TableColumn id_tcDate;
    @FXML
    public TableColumn id_tcStatus;
    @FXML
    public TableColumn id_tcComment;
    @FXML
    public TableColumn id_tcDownloads;
    @FXML
    private JFXButton id_btnDel;

    @FXML
    private JFXButton id_btnGenerate;

    @FXML
    private ImageView id_ivHiddenEyes;

    @FXML
    private ImageView id_ivHiddenEyesVer;

    @FXML
    private JFXListView<String> id_lvList;

    @FXML
    private PasswordField id_pfPass;

    @FXML
    private PasswordField id_pfPassVer;

    @FXML
    private TextField id_tfCerName;

    @FXML
    private TextField id_tfPass;

    @FXML
    private TextField id_tfPassVer;

    private int items;

    //    private static final int ITEMS_PER_PAGE = 10;
    public boolean eyeBool = false;

    public boolean eyeBoolVer = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        addPrivateKeys();

        hiddenEyes();

        id_ivHiddenEyesVer.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                eyeBoolVer = !eyeBoolVer;
                if (eyeBoolVer) {
                    id_ivHiddenEyesVer.setImage(new Image("/images/keysGenPage/visible.png"));
                    id_tfPassVer.setVisible(true);
                    id_pfPassVer.setVisible(false);
                } else {
                    id_ivHiddenEyesVer.setImage(new Image("/images/keysGenPage/show.png"));
                    id_tfPassVer.setVisible(false);
                    id_pfPassVer.setVisible(true);
                }
            }
        });


        id_lvList.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                items = Main.getKeys().getData().getKalits().getData()[id_lvList.getSelectionModel().getSelectedIndex()].getId();
            }
        });


        id_btnGenerate.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                id_btnGenerate.setDisable(true);

                if (!id_tfCerName.getText().isEmpty() && !id_pfPass.getText().isEmpty() &&
                        !id_pfPassVer.getText().isEmpty() && id_pfPass.getText().equals(id_pfPassVer.getText())) {

                    UzDSt_1092_2009 uzDSt10922009 = new UzDSt_1092_2009();
                    KeyPair keyPair = uzDSt10922009.generateKeyPair();

                    new Requests().RequestkeysGen("Hex.toHexString(keyPair.getPrivate().getEncoded())",
                            Hex.toHexString(keyPair.getPublic().getEncoded()), id_tfCerName.getText());
                    PFXManager pfxManager = new PFXManager();
                    pfxManager.setNumber((long) Main.getKeysGen().getData().getId());
                    pfxManager.setSoni(Main.getKeys().getData().getKalits().getData().length + 1);
                    pfxManager.createPfxFile(keyPair, id_tfCerName.getText(), id_tfPass.getText());

                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Xatolik!");
                    alert.setHeaderText("Maydonlarda xatolik mavjud");
                    alert.setContentText("Maydonlarda xatolik mavjud \n yoki parol tasdiqlanmadi");
                    alert.show();
                }

                id_tfCerName.clear();
                id_tfPass.clear();
                id_pfPass.clear();
                id_tfPassVer.clear();
                id_pfPassVer.clear();
                id_btnGenerate.setDisable(false);
                addPrivateKeys();

                id_btnGenerate.setDisable(false);
            }
        });

        id_btnDel.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new Requests().RequestKeyDel(items);
                addPrivateKeys();
            }
        });
    }

    private void hiddenEyes() {

        id_tfPass.setText(id_pfPass.getText());

        id_pfPass.textProperty().addListener((observable, oldValue, newValue) -> {
            id_tfPass.setText(newValue);
        });

        id_tfPass.textProperty().addListener((observable, oldValue, newValue) -> {
            id_pfPass.setText(newValue);
        });

        id_tfPassVer.setText(id_pfPassVer.getText());

        id_pfPassVer.textProperty().addListener((observable, oldValue, newValue) -> {
            id_tfPassVer.setText(newValue);
        });

        id_tfPassVer.textProperty().addListener((observable, oldValue, newValue) -> {
            id_pfPassVer.setText(newValue);
        });

        id_ivHiddenEyes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                eyeBool = !eyeBool;
                if (eyeBool) {
                    id_ivHiddenEyes.setImage(new Image("/images/keysGenPage/visible.png"));
                    id_tfPass.setVisible(true);
                    id_pfPass.setVisible(false);
                } else {
                    id_ivHiddenEyes.setImage(new Image("/images/keysGenPage/show.png"));
                    id_tfPass.setVisible(false);
                    id_pfPass.setVisible(true);
                }
            }
        });
    }

    private void addPrivateKeys() {

        new Requests().RequestKeys();

//       ImageView ivPrivKey = new ImageView(new Image("\\images\\keysGenPage\\key.png"));
//       ivPrivKey.setFitWidth(30);
//       ivPrivKey.setFitHeight(30);

        ObservableList<String> items = FXCollections.observableArrayList();

        for (int i = 0; i < Main.getKeys().getData().getKalits().getData().length; i++) {
//            ImageView ivDel = new ImageView(new Image(new File("\\images\\keyGenPage\\del.png").toURI().toString()));
//            ivDel.setFitHeight(30);
//            ivDel.setFitWidth(30);
//            JFXButton btn = new JFXButton();
//            btn.setGraphic(ivPrivKey);
//            btn.setText(Main.getKeys().getData().getKalits().getData()[i].getAttributes().getNomi());
//            btn.setGraphic(ivDel);
            items.add(Main.getKeys().getData().getKalits().getData()[i].getAttributes().getNomi());
//            System.out.println("kerak: " + Main.getKeys().getData().getKalits().getData()[i].getAttributes().getNomi());
        }
        id_lvList.setItems(items);
    }
}










