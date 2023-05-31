package com.example.swa7_app;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private Button CancelButton;
    @FXML
    private Button loginButton;
    @FXML
    private Button SignupButton;
    @FXML
    private Label loginMessageLabel;
    @FXML
    private ImageView brandingImageView;
    @FXML
    private ImageView lockImageView;
    @FXML
    private TextField usernameTextField;
    @FXML
    private PasswordField enterPasswordField;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File  brandingFile = new File("Graphics/bg.png");
        Image brandingImage = new Image(brandingFile.toURI().toString());
        brandingImageView.setImage(brandingImage);
        File  lockFile = new File("Graphics/enter.png");
        Image lockImage = new Image(lockFile.toURI().toString());
        lockImageView.setImage(lockImage);
    }
    public void loginButtonOnAction(ActionEvent event){

        if(usernameTextField.getText().isBlank() == false && enterPasswordField.getText().isBlank() == false){
            loginMessageLabel.setText("You try to login");
            loginMessageLabel.setAlignment(Pos.CENTER);
            validateLogin();
        }else{
            loginMessageLabel.setText("Please enter username and password");
            loginMessageLabel.setAlignment(Pos.CENTER);
        }
    }
    public void SignupButtonOnAciton(ActionEvent event) throws IOException {
        ((Node)event.getSource()).getScene().getWindow().hide();
        //((Node)(event.getSource())).getScene().getWindow().hide();
        //Parent root = FXMLLoader.load(getClass().getResource("register.fxml"));
        FXMLLoader loader = new FXMLLoader(getClass().getResource("register.fxml"));
        Parent root = loader.load();

        Stage sstage = new Stage();
        sstage.setScene(new Scene(root));
        sstage.setTitle("Add Employee");
        sstage.initStyle(StageStyle.UNDECORATED);
        Image image = new Image("file:icon.png");
        sstage.getIcons().add(image);

        sstage.show();
    }

    public void cancelButtonOnAction(ActionEvent event){
        Stage stage = (Stage) CancelButton.getScene().getWindow();
        stage.close();
        Platform.exit();
    }

    public void validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String verifyLogin = "SELECT count(1) FROM user_account WHERE username = '" + usernameTextField.getText() + "' and password = '" + enterPasswordField.getText() +"';";

        try{

            Statement statement = connectDB.createStatement();
            ResultSet queryResult = statement.executeQuery(verifyLogin);

            while(queryResult.next()){
                if(queryResult.getInt(1) == 1){
                    loginMessageLabel.setText("Congratulations!");
                    loginMessageLabel.setAlignment(Pos.CENTER);

                    String checkAdmin = "SELECT account_id,firstname,lastname FROM user_account WHERE username = '" + usernameTextField.getText() + "' and password = '" + enterPasswordField.getText() +"';";
                    queryResult = statement.executeQuery(checkAdmin);
                    FXMLLoader loader = null ;
                    Parent root = null ;
                    while(queryResult.next()) {
                        if (queryResult.getInt(1) == 1) {
                            loader = new FXMLLoader(getClass().getResource("Admin.fxml"));//to connect to the admin #start
                            root = loader.load();
                        } else {
                            int receptionistID = queryResult.getInt(1);
                            String firstname = queryResult.getString(2);
                            String lastname = queryResult.getString(3);
                            String inputText = firstname + " " + lastname;
                            loader = new FXMLLoader(getClass().getResource("Receptionist.fxml"));//to connect to the receptionist #start
                            root = loader.load();
                            ReceptionistController changeUserName = loader.getController();
                            changeUserName.setText(inputText,receptionistID);
                        }
                        //Parent root = loader.load();
                        //Parent root = loader.load();
                        Stage sstage = new Stage();
                        sstage.setScene(new Scene(root));
                        sstage.setTitle("Admin Panel");
                        Image image = new Image("file:icon.png");
                        sstage.getIcons().add(image);
                        sstage.initStyle(StageStyle.UNDECORATED);
                        sstage.show();
                        loginButton.getScene().getWindow().hide(); // #end
                    }

                }else{
                    loginMessageLabel.setText("Invalid login. Please try again.");
                    loginMessageLabel.setAlignment(Pos.CENTER);
                }
            }

        }catch(Exception e){
            e.printStackTrace();
            e.getCause();
        }
    }



}