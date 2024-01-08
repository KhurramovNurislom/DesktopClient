package org.example;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import okhttp3.OkHttpClient;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.example.modules.AliesKey.AliesKeys;
import org.example.modules.Hash;
import org.example.modules.SignedFileInfo;
import org.example.modules.keys.Keys;
import org.example.modules.keysGen.KeysGen;
import org.example.modules.login.LoginData;
import org.example.modules.login.UserData;
import org.example.modules.upload.Upload;
import org.example.modules.userMessages.UserMessages;
import org.example.modules.users.AUsers;
import org.example.modules.usersMe.AUsersMe;
import org.example.modules.verificationInfo.VerificationInfo;
import org.example.utils.FXMLLoaderMade;
import org.example.utils.SSLClient;

import java.security.KeyPair;
import java.security.Security;
import java.util.*;


@FieldDefaults(level = AccessLevel.PRIVATE)

public class Main extends Application {

    @Getter
    @Setter
    static SSLClient sslClient;
    @Getter
    @Setter
    static String url = "https://imzo.texnokun.uz/server"; //"http://192.168.1.15:1337/"; //
    @Getter
    @Setter
    static OkHttpClient client;
    @Getter
    @Setter
    static LoginData loginData;
    @Getter
    @Setter
    static UserData userData;
    @Getter
    @Setter
    static AUsersMe aUsersMe;
    @Getter
    @Setter
    static KeysGen keysGen;
    @Getter
    @Setter
    static Hash hash;
    @Getter
    @Setter
    static SignedFileInfo signedFileInfo;
    @Getter
    @Setter
    static Upload upload;
    @Getter
    @Setter
    static Keys keys;
    @Getter
    @Setter
    static VerificationInfo verification;
    @Getter
    @Setter
    static AUsers aUsers;
    @Getter
    @Setter
    static UserMessages userMessages;
    @Getter
    @Setter
    static AliesKeys aliesKeys;
    @Getter
    @Setter
    static List<String> listPaths;
    @Getter
    @Setter
    static int indexPFXFilePath;
    @Getter
    @Setter
    static Pane pane;
    @Getter
    @Setter
    static boolean passVerify = false;
    @Getter
    @Setter
    static String keyFilePath;
    @Getter
    @Setter
    static Pane paneShadow;
    static double x = 0, y = 0;
    static Stage passStage;


    @Override
    public void start(Stage stage) throws Exception {

        Security.addProvider(new BouncyCastleProvider());

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Kirish oynasi...");
        stage.getIcons().add(new Image("/images/icon.png"));

        stage.setScene(scene);
        scene.getStylesheets().add
                (Objects.requireNonNull(Main.class.getResource("/css/Style.css")).toExternalForm());
        stage.setResizable(false);
        stage.show();
    }

    public static void showPassStage(boolean bool) {
        new Thread() {
            @Override
            public void run() {
                allShadow(bool);
            }
        }.start();

        if (bool) {
            passStage = new Stage();
            Scene scene = new Scene(new FXMLLoaderMade().getPane("PasswordKey"));
            pane.setOnMousePressed(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    x = event.getSceneX();
                    y = event.getSceneY();
                }
            });
            pane.setOnMouseDragged(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event) {
                    passStage.setX(event.getScreenX() - x);
                    passStage.setY(event.getScreenY() - y);
                }
            });
            passStage.setScene(scene);
//            passStage.initModality(Modality.APPLICATION_MODAL);
            passStage.initStyle(StageStyle.TRANSPARENT);
            passStage.centerOnScreen();
            passStage.show();

        } else {
            allShadow(false);
            passStage.close();
        }

    }


    public static void allShadow(boolean bool) {

        if (bool) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), paneShadow);
            paneShadow.setVisible(true);
            fadeTransition.setFromValue(0.0);
            fadeTransition.setToValue(0.6);
            fadeTransition.play();
        } else {
            new Thread() {
                @Override
                public void run() {
                    FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.3), paneShadow);
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
                        paneShadow.setVisible(false);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }

            }.start();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}