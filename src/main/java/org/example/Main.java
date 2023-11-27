package org.example;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import okhttp3.OkHttpClient;
import org.example.moduls.login.LoginData;
import org.example.moduls.login.UserData;
import org.example.utils.SSLClient;


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

    @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("/fxml/LoginPage.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        stage.setTitle("Kirish oynasi...");
        stage.getIcons().add(new Image("/images/icon.png"));
        stage.setScene(scene);
//        stage.setResizable(false);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}