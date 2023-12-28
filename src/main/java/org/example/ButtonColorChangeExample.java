package org.example;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ContextMenu;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ButtonColorChangeExample extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Label Matnini Nusxa Olish");

        Label myLabel = new Label("Salom, Dunyo!");

        // ContextMenu yaratish
        ContextMenu contextMenu = new ContextMenu();

        // Nusxa olish uchun MenuItem
        MenuItem copyMenuItem = new MenuItem("Nusxa Olish");
        copyMenuItem.setOnAction(e -> copyLabelText(myLabel));
        contextMenu.getItems().add(copyMenuItem);

        // Labelga ContextMenu ni bog'lash
        myLabel.setContextMenu(contextMenu);

        VBox root = new VBox(10, myLabel);
        Scene scene = new Scene(root, 300, 200);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void copyLabelText(Label label) {
        // Label matnini olish
        String labelText = label.getText();

        // Clipboard uchun matnni sozlash
        ClipboardContent content = new ClipboardContent();
        content.putString(labelText);

        // Clipboard ga matnni joylash
        Clipboard.getSystemClipboard().setContent(content);
    }
}
