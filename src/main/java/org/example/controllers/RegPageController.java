package org.example.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.example.modules.login.UserData;
import org.example.utils.SceneChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;
import java.util.List;
import java.util.ResourceBundle;


public class RegPageController implements Initializable {

    @FXML
    public TextField id_tfLogin;
    @FXML
    public PasswordField id_pfPassword;
    @FXML
    public PasswordField id_pfPasswordCheck;
    @FXML
    public TextField id_tfName;
    @FXML
    public TextField id_tfSurname;
    @FXML
    public TextField id_tfFatherName;
    @FXML
    public DatePicker id_dpBirthDay;
    @FXML
    public TextField id_tfPassportSeries;
    @FXML
    public TextField id_tfImagePath;
    @FXML
    public JFXButton id_btnImageLoad;
    @FXML
    public ImageView id_iwImage;
    @FXML
    public TextField id_tfPrivatePhoneNumber;
    @FXML
    public TextField id_tfWorkPhoneNumber;
    @FXML
    public JFXButton id_btnClearFields;
    @FXML
    public JFXButton id_btnCreateUser;
    @FXML
    public JFXButton id_btnExit;

    private FileChooser fileChooser;

    private List<File> fileList;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        fileChooser = new FileChooser();

        id_iwImage.setImage(new Image("/images/regPage/person.png"));


        id_btnImageLoad.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                id_btnImageLoad.setDisable(true);

                fileList = fileChooser.showOpenMultipleDialog(new Stage());

                if (fileList != null) {

                    fileList.forEach(selectedFiles -> {

                        id_tfImagePath.setText(clearPunct(fileList.get(0).toString()));

                        if (id_tfImagePath.getText().toLowerCase().endsWith(".png") || id_tfImagePath.getText().toLowerCase().endsWith(".jpg")) {
                            ImageLoad();
                            id_tfImagePath.setStyle("-fx-text-fill: black");
                        } else {
                            id_tfImagePath.setStyle("-fx-text-fill: #db0404");

                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("File");
                            alert.setHeaderText("Fayl yuklashda xatolik!");
                            alert.setContentText("Kechirasiz, siz faqat *.jpg va *.png rasmlarni tanlay olasiz");
                            alert.show();

                            id_tfImagePath.clear();
                        }
                    });
                }
                id_btnImageLoad.setDisable(false);
            }
        });

        id_btnClearFields.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                ClearFields();
            }
        });

        id_btnCreateUser.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                if (CheckFields()) {

                    UserData userData = new UserData();

//                    userData.setLoginData(new LoginData(id_tfLogin.getText(), id_pfPassword.getText()));

//                    userData.setName(id_tfName.getText());
//                    userData.setSurname(id_tfSurname.getText());
//                    userData.setFatherName(id_tfFatherName.getText());
//                    userData.setBirthDay(id_dpBirthDay.getValue().toString());
//                    userData.setPassportSeries(id_tfPassportSeries.getText());
//                    userData.setImageData(new ImageData(ImageName(), ImageData()));
//                    userData.setPrivatePhoneNumber(id_tfPrivatePhoneNumber.getText());
//                    userData.setWorkingPhoneNumber(id_tfWorkPhoneNumber.getText());
//                    System.out.println(JsonParser(userData));
//
//
//                    Main.setUserData(userData);


//                    File outputFile = new File("E:\\" + ImageName());
//                    try (FileOutputStream fileOutputStream = new FileOutputStream(outputFile, true)) {
//                        fileOutputStream.write(Base64.getDecoder().decode(ImageData()));
//                    } catch (FileNotFoundException e) {
//                        e.printStackTrace();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }


                    ClearFields();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Xabar...");
                    alert.setHeaderText("Foydalanuvchi yaratildi...");
                    alert.setContentText("Foydalanuvchi muvofaqqiyatli yaratildi...");
                    alert.show();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Xatolik...");
                    alert.setHeaderText("Maydonlarga e'tibor bering...");
                    alert.setContentText("Kechirasiz, maydan to'ldirilmagan...");
                    alert.show();
                }
            }
        });

        id_btnExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                SceneChooser.changeScene(event, "/fxml/LoginPage.fxml", "Kirish oynasi");
            }
        });
    }

    private String clearPunct(String text) {
        return text.replaceAll("\\[", "").replaceAll("]", "");
    }

    private void ClearFields() {
        id_tfLogin.clear();
        id_pfPassword.clear();
        id_pfPasswordCheck.clear();
        id_tfName.clear();
        id_tfSurname.clear();
        id_tfFatherName.clear();
        id_tfPassportSeries.clear();
        id_tfImagePath.clear();
        id_tfPrivatePhoneNumber.clear();
        id_tfWorkPhoneNumber.clear();
        id_dpBirthDay.setValue(null);

        id_tfLogin.setStyle("-fx-prompt-text-fill: black");
        id_pfPassword.setStyle("-fx-prompt-text-fill: black");
        id_pfPasswordCheck.setStyle("-fx-prompt-text-fill: black");
        id_tfName.setStyle("-fx-prompt-text-fill: black");
        id_tfSurname.setStyle("-fx-prompt-text-fill: black");
        id_tfFatherName.setStyle("-fx-prompt-text-fill: black");
        id_tfPassportSeries.setStyle("-fx-prompt-text-fill: black");
        id_tfImagePath.setStyle("-fx-prompt-text-fill: black");
        id_tfPrivatePhoneNumber.setStyle("-fx-prompt-text-fill: black");
        id_tfWorkPhoneNumber.setStyle("-fx-prompt-text-fill: black");

        id_iwImage.setImage(new Image("images/regPage/person.png"));
    }

    private boolean CheckFields() {
        int k = 0;

        if (id_tfLogin.getText().equals("")) {
            k++;
            id_tfLogin.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_tfLogin.setStyle("-fx-prompt-text-fill: black");
        }

        if (id_pfPassword.getText().equals("")) {
            k++;
            id_pfPassword.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_pfPassword.setStyle("-fx-prompt-text-fill: #123132");
        }

        if (id_pfPasswordCheck.getText().equals("")) {
            k++;
            id_pfPasswordCheck.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_pfPasswordCheck.setStyle("-fx-prompt-text-fill: black");
        }

        if (id_tfName.getText().equals("")) {
            k++;
            id_tfName.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_tfName.setStyle("-fx-prompt-text-fill: black");
        }

        if (id_tfSurname.getText().equals("")) {
            k++;
            id_tfSurname.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_tfSurname.setStyle("-fx-prompt-text-fill: black");
        }

        if (id_tfFatherName.getText().equals("")) {
            k++;
            id_tfFatherName.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_tfFatherName.setStyle("-fx-prompt-text-fill: black");
        }


        if (id_dpBirthDay.getValue() == null) {
            k++;
            id_dpBirthDay.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_dpBirthDay.setStyle("-fx-prompt-text-fill: black");
        }


        if (id_tfPassportSeries.getText().equals("")) {
            k++;
            id_tfPassportSeries.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_tfPassportSeries.setStyle("-fx-prompt-text-fill: black");
        }

        if (id_tfImagePath.getText().equals("")) {
            k++;
            id_tfImagePath.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_tfImagePath.setStyle("-fx-prompt-text-fill: black");
        }


        if (id_tfPrivatePhoneNumber.getText().equals("")) {
            k++;
            id_tfPrivatePhoneNumber.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_tfPrivatePhoneNumber.setStyle("-fx-prompt-text-fill: black");
        }

        if (id_tfWorkPhoneNumber.getText().equals("")) {
            k++;
            id_tfWorkPhoneNumber.setStyle("-fx-prompt-text-fill: #db0404");
        } else {
            id_tfWorkPhoneNumber.setStyle("-fx-prompt-text-fill: black");
        }


        if (k == 0) {
            return true;
        } else {
            return false;
        }
    }

    private void ImageLoad() {
        File personImage;
        if (id_tfImagePath.getText().equals("")) {
            personImage = new File("images/regPage/person.png");
            id_iwImage.setImage(new Image(personImage.toURI().toString()));
        } else {
            personImage = new File(id_tfImagePath.getText());
            id_iwImage.setImage(new Image(personImage.toURI().toString()));
        }
    }

    private String ImageName() {
        return new File(id_tfImagePath.getText()).getName();
    }

    private String ImageData() {
        File file = new File(id_tfImagePath.getText());
        try {
            byte[] fileContent = Files.readAllBytes(file.toPath());
            return Base64.getEncoder().encodeToString(fileContent);
        } catch (IOException e) {
            throw new IllegalStateException("could not read file " + file, e);
        }
    }

    private String JsonParser(Object object) {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        String json = "";
        try {
            json = ow.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return json;
    }
}
