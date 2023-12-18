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
import javafx.scene.input.MouseEvent;
import org.example.Main;
import org.example.utils.Requests;
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
    public ImageView id_btnEyes;
    @FXML
    public ImageView id_ivHiddenEyes;
    @FXML
    public TextField id_tfPassword;

    public boolean eyebool = false;

    Requests requests = new Requests();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        id_tfPassword.setVisible(false);
        id_ivHiddenEyes.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {

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

                Main.setClient(new SSLClient().httpsClient());

                /** Serverga request jo'natib login parolni oladi va kiritilgan login parol bilan solishtiradi */
                try {
                    if (requests.RequestLogin(id_tfLogin.getText(), id_pfPassword.getText())) {
                        Thread thread = new Thread() {
                            @Override
                            public void run() {
                                /** Login bilan kirgan user haqida */
                                requests.ResponseUsersMe();
                                /** Userlarni serverdan oladi */
                                requests.RequestUsers();
                                /** Foydalanuvchining kalitlarini olib beradi */
                                requests.RequestKeys();
                            }
                        };
                        thread.start();

                        SceneChooser.changeScene(event, "/fxml/MainPage.fxml", "Asosiy oyna...");

                    } else {
                        System.out.println("login yoki parol xato...");
                    }
                } catch (Exception e) {
                    System.err.println("exception: LoginPageController(btnTizimgaKirish) => " + e.getCause());
                }

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
