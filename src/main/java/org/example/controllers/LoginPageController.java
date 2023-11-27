package org.example.controllers;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.example.Main;
import org.example.utils.Requestes;
import org.example.utils.SSLClient;
import org.example.utils.SceneChooser;

import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ResourceBundle;

public class LoginPageController implements Initializable {

    @FXML
    public TextField id_tfLogin;
    @FXML
    public PasswordField id_pfPassword;
    @FXML
    public JFXButton id_btnTizimgaKirish;
    @FXML
    public JFXButton id_btnRoyhatdanOtish;
    @FXML
    public JFXButton id_btnEyes;
    @FXML
    public ImageView id_ivHiddenEyes;
    @FXML
    public TextField id_tfPassword;

    public boolean eyebool = false;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_tfPassword.setVisible(false);
        id_btnEyes.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eyebool = !eyebool;
                if (eyebool) {
                    id_ivHiddenEyes.setImage(new Image("/images/loginPage/eye-crossed.png"));
                    id_tfPassword.setVisible(true);
                    id_pfPassword.setVisible(false);
                    id_tfPassword.setText(id_pfPassword.getText());
                } else {
                    id_ivHiddenEyes.setImage(new Image("/images/loginPage/eye.png"));
                    id_tfPassword.setVisible(false);
                    id_pfPassword.setVisible(true);
                    id_pfPassword.setText(id_tfPassword.getText());
                }
            }
        });

        id_btnTizimgaKirish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                try {
                    Main.setClient(new SSLClient().httpsClient());
                } catch (NoSuchAlgorithmException | KeyManagementException e) {
                    System.err.println("error: LoginPageController(btnTizimgaKirish) => " + e.getMessage());
                }

                /** Serverga request jo'natib login parolni oladi va kiritilgan login parol bilan solishtiradi */
                try {
                    if (new Requestes().RequestLogin(id_tfLogin.getText(), id_pfPassword.getText())) {
                        SceneChooser.changeScene(event, "/fxml/MainPage.fxml", "Asosiy oyna...");
                    } else {
                        System.out.println("login yoki parol xato...");
                    }
                } catch (Exception e) {
                    System.err.println("error: LoginPageController(btnTizimgaKirish) => " + e.getMessage());
                }

                /** Foydalanuvchining kalitlarini olib beradi */
//                try {
//                    new Requestes().RequestKeys();
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }

            }
        });

        id_btnRoyhatdanOtish.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneChooser.changeScene(event, "/fxml/RegPage.fxml", "Ro'yhatdan o'tish oynasi");
            }
        });
    }
}
