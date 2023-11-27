//package org.example.controllers;
//
//import com.example.webclient.Main;
//import com.example.webclient.services.Requestes;
//import com.example.webclient.signature.GenerateKeyPair;
//import com.jfoenix.controls.JFXButton;
//import com.jfoenix.controls.JFXListView;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
//import javafx.fxml.Initializable;
//import javafx.scene.control.Alert;
//import javafx.scene.control.ScrollPane;
//import javafx.scene.control.TextField;
//import javafx.scene.input.MouseEvent;
//import javafx.scene.layout.Pane;
//
//import java.io.IOException;
//import java.net.URL;
//import java.util.ResourceBundle;
//
//public class KeysGenPageController implements Initializable {
//
//    public TextField id_tfPassword;
//    public TextField id_tfKeyName;
//    public TextField id_tfPassword2;
//    public JFXButton id_btnGenerate;
//    public JFXListView<String> id_lvList;
//    public JFXButton id_btnDel;
//    public ScrollPane id_ScrollPane;
//    public Pane id_PaneTest;
//
//    private int items;
//
//    private static final int ITEMS_PER_PAGE = 10;
//    private int pageIndex;
//
//
//    @Override
//    public void initialize(URL location, ResourceBundle resources) {
//
//
//        addPrivateKeys();
//
//
//        id_lvList.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
////                System.out.println(Main.getKeys().getData().getKalits().getData()[id_lvList.getSelectionModel().getSelectedIndex()].getId());
////                System.out.println(Main.getKeys().getData().getKalits().getData()[id_lvList.getSelectionModel().getSelectedIndex()].getAttributes().getNomi());
//                items = Main.getKeys().getData().getKalits().getData()[id_lvList.getSelectionModel().getSelectedIndex()].getId();
//                Main.setActiveKey(Main.getKeys().getData().getKalits().getData()[id_lvList.getSelectionModel().getSelectedIndex()].getAttributes().getNomi());
//            }
//        });
//
//
//        id_btnGenerate.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                id_btnGenerate.setDisable(true);
//                if (!id_tfKeyName.getText().isEmpty() && !id_tfPassword.getText().isEmpty() && !id_tfPassword2.getText().isEmpty() && id_tfPassword.getText().equals(id_tfPassword2.getText())) {
//
//                    new GenerateKeyPair().generateKeyPair(id_tfKeyName.getText(), id_tfPassword.getText());
//
//                    try {
//                        new Requestes().RequestkeysGen(Main.getPrivateKey(), Main.getPublicKey(), id_tfKeyName.getText());
//                    } catch (IOException e) {
//                        throw new RuntimeException(e);
//                    }
//
//                } else {
//                    Alert alert = new Alert(Alert.AlertType.ERROR);
//                    alert.setTitle("Xatolik!");
//                    alert.setHeaderText("Maydonlarda xatolik mavjud");
//                    alert.setContentText("Maydonlarda xatolik mavjud \n yoki parol tasdiqlanmadi");
//                    alert.show();
//                }
//
//                id_tfKeyName.clear();
//                id_tfPassword.clear();
//                id_tfPassword2.clear();
//                id_btnGenerate.setDisable(false);
//                addPrivateKeys();
//            }
//        });
//
//        id_btnDel.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//
//                try {
//                    new Requestes().RequestKeyDel(items);
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//                addPrivateKeys();
//            }
//        });
//    }
//
//    private void addPrivateKeys() {
//
//        try {
//            new Requestes().RequestKeys();
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//
////        ImageView ivPrivKey = new ImageView(new Image("privKey.png"));
////        ivPrivKey.setFitWidth(30);
////        ivPrivKey.setFitHeight(30);
//
//        ObservableList<String> items = FXCollections.observableArrayList();
//
////        System.out.println("O'lcham: " + Main.getKeys().getData().getKalits().getData().length);
//        for (int i = 0; i < Main.getKeys().getData().getKalits().getData().length; i++) {
////            ImageView ivDel = new ImageView(new Image(new File("del.png").toURI().toString()));
////            ivDel.setFitHeight(30);
////            ivDel.setFitWidth(30);
////            JFXButton btn = new JFXButton();
////            btn.setGraphic(ivPrivKey);
////            btn.setText(Main.getKeys().getData().getKalits().getData()[i].getAttributes().getNomi());
////            btn.setGraphic(ivDel);
//            items.add(Main.getKeys().getData().getKalits().getData()[i].getAttributes().getNomi());
////            System.out.println("kerak: " + Main.getKeys().getData().getKalits().getData()[i].getAttributes().getNomi());
//        }
//
////        for (int i = 0; i < 100; i++) {
////            items.add("Imzo_" + i);
////
////
////        }
//
//        id_lvList.setItems(items);
//
//    }
//
//
////        private void FulledMessagePane() {
////
////            try {
////                new Requestes().RequestUserMessages();
////            } catch (IOException e) {
////                throw new RuntimeException(e);
////            }
////
////            for (int i = 0; i < Main.getUserMessages().getData().getMessages().getData().length; i++) {
////                items.add(Main.getUserMessages().getData().getMessages().getData()[i].getAttributes().getMavzu() + "\n" +
////                        Main.getUserMessages().getData().getMessages().getData()[i].getAttributes().getXabar());
////            }
////
//////            if (items.size() % ITEMS_PER_PAGE == 0) {
//////                id_Pagination.setPageCount((items.size() / ITEMS_PER_PAGE));
//////            } else {
//////                id_Pagination.setPageCount((items.size() / ITEMS_PER_PAGE) + 1);
//////            }
//////
//////            id_Pagination.setPageFactory(pageIndex -> {
//////                this.pageIndex = pageIndex;
//////                int fromIndex = pageIndex * ITEMS_PER_PAGE;
//////                int toIndex = Math.min(fromIndex + ITEMS_PER_PAGE, items.size());
//////                id_lvList.setItems(FXCollections.observableArrayList(items.subList(fromIndex, toIndex)));
//////                return new BorderPane(id_lvList, null, null, new Label("Page " + (pageIndex + 1)), null);
//////            });
////        }
//
//    private void Test() {
//
//
//    }
//
//}
