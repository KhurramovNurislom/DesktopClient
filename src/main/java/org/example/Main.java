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




    public static void main(String[] args) {
        launch(args);
    }
}