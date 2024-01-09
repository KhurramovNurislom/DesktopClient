package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextFormatter;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTimeTextFieldExample extends Application {

    @Override
    public void start(Stage primaryStage) {
        TextField textField = new TextField();

        // SimpleDateFormat obyekti yaratish
        SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        SimpleDateFormat outputFormat = new SimpleDateFormat("dd.MM.yyyy");

        // TextFormatter ni yaratish
        TextFormatter<Date> textFormatter = new TextFormatter<>(
                new StringConverter<Date>() {
                    @Override
                    public String toString(Date value) {
                        return (value == null) ? "" : outputFormat.format(value);
                    }

                    @Override
                    public Date fromString(String text) {
                        try {
                            // Kiritilgan matnni sanaga o'tkazish
                            return inputFormat.parse(text);
                        } catch (ParseException e) {
                            return null;
                        }
                    }
                },
                null, // Boshlang'ich qiymat
                c -> {
                    if (c.getControlNewText().matches("\\d{4}\\.\\d{2}\\.\\d{2} \\d{2}:\\d{2}:\\d{2}")) {
                        // Agar yangi matn faqat sanaga o'tadigan formatda bo'lsa
                        return c;
                    } else {
                        // Aks holda, tikishni rad etish
                        return null;
                    }
                });

        // TextFormatter ni TextField ga qo'shish
        textField.setTextFormatter(textFormatter);

        primaryStage.setScene(new Scene(textField, 300, 200));
        primaryStage.setTitle("DateTime TextField Example");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
