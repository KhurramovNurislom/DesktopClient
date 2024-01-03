package org.example.controllers;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import lombok.Getter;
import lombok.Setter;
import org.example.Main;
import java.net.URL;
import java.util.ResourceBundle;

public class KeyInfoInPFXController implements Initializable {

    @FXML
    public Label id_lblSurName;
    @FXML
    public Label id_lblSTIR;
    @FXML
    public Label id_lblJSHSHIR;
    @FXML
    public Label id_lblSerNumber;
    @FXML
    public Label id_lblFromToDate;
    @FXML
    public JFXButton id_btnSign;

    @Getter
    @Setter
    private int k;

    @JsonIgnoreProperties
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (Main.getAliesKeys().getAliesKeyList()[k].getCn().isEmpty()) {
            id_lblSurName.setText("Null");
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xatolik!");
            alert.setHeaderText("Siz tanlagan kalit mazkur tizimga tegishli emas.");
            alert.setContentText("Kechirasiz, kalitingizni qaytadan tekshirib ko'ring...");
        } else {
            id_lblSurName.setText(Main.getAliesKeys().getAliesKeyList()[k].getCn().toUpperCase());
        }
        id_lblJSHSHIR.setText(Main.getAliesKeys().getAliesKeyList()[k].getJshshir());
        id_lblSTIR.setText(Main.getAliesKeys().getAliesKeyList()[k].getUid());
        id_lblSerNumber.setText(Main.getAliesKeys().getAliesKeyList()[k].getSerialnumber());
        id_lblFromToDate.setText(formatDate(Main.getAliesKeys().getAliesKeyList()[k].getValidfrom()) + "  /  " + formatDate(Main.getAliesKeys().getAliesKeyList()[k].getValidto()));
        id_btnSign.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println("Assalom qaleysan => " + k);
            }
        });
    }
    private String formatDate(String date) {
        String day = date.substring(8, 10);
        String month = date.substring(5, 7);
        String year = date.substring(0, 4);
        return day + "." + month + "." + year;
    }
}
