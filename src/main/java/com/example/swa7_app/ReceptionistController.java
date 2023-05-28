package com.example.swa7_app;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.converter.IntegerStringConverter;

import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.UnaryOperator;

public class ReceptionistController  implements Initializable {


    @FXML
    private ComboBox<?> CheukOut;
    @FXML
    private AnchorPane checkout_form,services_form,guests_form;

    @FXML
    private Button close_btn;


    @FXML
    private Button minimize_btn;

    @FXML
    private Button checkin_btn;

    @FXML
    private Label username;

    @FXML
    private Button rooms_btn;

    @FXML
    private Button guests_btn;

    @FXML
    private Button services_btn;

    @FXML
    private Button logout_btn;

    @FXML
    private Button checkout_btn;

    @FXML
    private AnchorPane dashboard_form;

    @FXML
    private TextField firestnameinput;

    @FXML
    private TextField lastnameinput;

    @FXML
    private TextField passinput;

    @FXML
    private TextField phoneinput;

    @FXML
    private TextField emailinput;

    @FXML
    private TextField nationalityinput;

    @FXML
    private TextField addressinput;

    @FXML
    private ComboBox<?> roomtype;

    @FXML
    private ComboBox<?> roomnumber;

    @FXML
    private DatePicker checkindata;

    @FXML
    private DatePicker chekoutdata;

    @FXML
    private TextField creditCardField;

    @FXML
    private TextField cardHolderField;

    @FXML
    private TextField exp_Mth_Field;

    @FXML
    private TextField exp_Year_Field;

    @FXML
    private TextField CVV_Field;

    @FXML
    private Button Reset;

    @FXML
    private Button confirm;

    @FXML
    private AnchorPane rooms_form;
    @FXML
    private AnchorPane checkin_form;

    @FXML
    private TextField availableRooms_roomNumber;

    @FXML
    private ComboBox<?> availableRooms_roomStatus;

    @FXML
    private TextField availableRooms_roomPrice;

    @FXML
    private Button availableRooms_addBtn;

    @FXML
    private Button availableRooms_updateBtn;

    @FXML
    private Button availableRooms_clearBtn;

    @FXML
    private Button availableRooms_deleteBtn;

    @FXML
    private ComboBox<?> availableRooms_roomType;

    @FXML
    private TableView<roomData> availableRooms_tableView;

    @FXML
    private TableColumn<roomData,String> availableRooms_col_roomNumbr;

    @FXML
    private TableColumn<roomData,String> availableRooms_col_roomType;

    @FXML
    private TableColumn<roomData,String> availableRooms_col_roomStatus;

    @FXML
    private TableColumn<roomData,String> availableRooms_col_roomPrice;

    @FXML
    private TextField availableRooms_search;



    @FXML
    private Button Add_btn;

    @FXML
    private Button Update_btn;

    @FXML
    private Button Delete_btn;


    @FXML
    private TableView<guestData> guest_tableView;

    @FXML
    private TableColumn<guestData,String> guest_Room;

    @FXML
    private TableColumn<guestData,String> guest_firstName;

    @FXML
    private TableColumn<guestData,String> guest_lastName;

    @FXML
    private TableColumn<guestData,String> guest_customerPhone;

    @FXML
    private TableColumn<guestData,String> guest_checkedIn;

    @FXML
    private TableColumn<guestData,String> guest_checkedOut;

    public void setText(String text) {
        username.setText(text);
    }




    @FXML
    void closepage(ActionEvent event) {
        System.exit(0);
    }



    @FXML
    void logout(ActionEvent event) {

    }

    @FXML
    void minimize(ActionEvent event) {
        Stage stage=(Stage)rooms_form.getScene().getWindow();
        stage.setIconified(true);
    }

    @FXML
    void switchForm(ActionEvent event) {
        if(event.getSource()==checkin_btn){
            checkin_form.setVisible(true);
            checkout_form.setVisible(false);
            rooms_form.setVisible(false);
            guests_form.setVisible(false);
            services_form.setVisible(false);

        }else if(event.getSource()==checkout_btn){
            checkin_form.setVisible(false);
            checkout_form.setVisible(true);
            rooms_form.setVisible(false);
            guests_form.setVisible(false);
            services_form.setVisible(false);

        }else if(event.getSource()==rooms_btn){
            checkin_form.setVisible(false);
            checkout_form.setVisible(false);
            rooms_form.setVisible(true);
            guests_form.setVisible(false);
            services_form.setVisible(false);
            availableRoomsShowData();
        }else if(event.getSource()==guests_btn){
            checkin_form.setVisible(false);
            checkout_form.setVisible(false);
            rooms_form.setVisible(false);
            guests_form.setVisible(true);
            services_form.setVisible(false);
            guestShowData();

        }else if(event.getSource()==services_btn){
            checkin_form.setVisible(false);
            checkout_form.setVisible(false);
            rooms_form.setVisible(false);
            guests_form.setVisible(false);
            services_form.setVisible(true);

        }
    }
    ///       room number combo box //////

    public void roomNumberList() {
        String room_TYPE = (String) roomtype.getSelectionModel().getSelectedItem();
        String listNumber = "SELECT distinct roomNumber FROM rooms WHERE status = 'Available' AND type = ?";
        connect = DatabaseConnection.getConnection();
        try {
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listNumber);
            prepare.setString(1, room_TYPE);
            result = prepare.executeQuery();
            while (result.next()) {
                listData.add(result.getString("roomNumber"));
            }
            roomnumber.setItems(listData);
        }catch(Exception e){
            e.printStackTrace();
        }

    }
/////////////////////////////////////////
    ///       room type combo box //////

    public void roomTypeList() {
        String listType = "SELECT distinct type FROM rooms WHERE status = 'Available'";
        connect = DatabaseConnection.getConnection();
        try{
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listType);
            result = prepare.executeQuery();
            while (result.next()){
                listData.add(result.getString("type"));
            }
            roomtype.setItems(listData);
            roomtype.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                roomNumberList();
            });
        }catch(Exception e){
            e.printStackTrace();
        }
        //roomNumberList();
    }
/////////////////////////////////////////
    ///       room table //////
    public ObservableList<roomData>availableRoomListData() {
        ObservableList<roomData> listdata = FXCollections.observableArrayList();
        String sql = "SELECT * FROM rooms";

        connect = DatabaseConnection.getConnection();
        try {
            roomData roomD;
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                roomD = new roomData(result.getInt("roomNumber"),
                        result.getString("type"),
                        result.getString("status"),
                        result.getDouble("price"));
                listdata.add(roomD);
            }
        } catch (SQLException e) {
            e.printStackTrace();}
        return listdata;}
//////////////////////////////////
    private ObservableList<roomData>roomDataList;


    public void availableRoomsShowData(){
        roomDataList=availableRoomListData();
        availableRooms_col_roomNumbr.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        availableRooms_col_roomType.setCellValueFactory(new  PropertyValueFactory<>("roomType") );
        availableRooms_col_roomStatus.setCellValueFactory(new  PropertyValueFactory<>("status"));
        availableRooms_col_roomPrice.setCellValueFactory(new  PropertyValueFactory<>("price"));

        availableRooms_tableView.setItems(roomDataList);
    }

        //////////////////////////////////////////////
    private Connection connect;
    private PreparedStatement prepare;
    private Statement statement;
    private ResultSet result;



    @FXML
    void confirmbtn(ActionEvent event) {
        String insertcustomerdata = "insert into customer (firstName,lastName,total,phoneNumber,email,checkin,checkout) " +
                "values(?,?,?,?,?,?,?)" ;
        String insertguestdata = "insert into guest (room,firstName,lastName,total,phoneNumber,email,checkin,checkout) " +
                "values(?,?,?,?,?,?,?,?)" ;
        String updateroomdata = "update rooms set status='Not Available' where roomNumber= ?";
        connect = DatabaseConnection.getConnection();
        DatabaseConnection connectnow = new DatabaseConnection();
        Connection connection = connectnow.getConnection();
        try
        {
            String firstName = firestnameinput.getText();
            String lastName = lastnameinput.getText();
            String phoneNumber = phoneinput.getText();
            String email = emailinput.getText();
            String Checkin = String.valueOf(checkindata.getValue());
            String Checkout = String.valueOf(chekoutdata.getValue());
            //String roomNum = roomnumber.getSelectionModel().getSelectedItem().toString();
            int total = 100;

            Alert alter;
            if(firstName== null || lastName== null || phoneNumber == null || email ==null ||Checkin==null||Checkout==null ||
                    nationalityinput==null ||roomtype==null || roomnumber==null || addressinput==null ||passinput ==null ){
                alter = new Alert(Alert.AlertType.ERROR);
                alter.setTitle("Error message");
                alter.setHeaderText(null);
                alter.setContentText("please complete information");
                alter.showAndWait();
            }else {


                alter = new Alert(Alert.AlertType.CONFIRMATION);
                alter.setTitle("CONFIRMATION message");
                alter.setHeaderText(null);
                alter.setContentText("Are you sure ?");

                Optional<ButtonType> option =alter.showAndWait();

                if(option.get().equals(ButtonType.OK)){
                    prepare = connect.prepareStatement(insertcustomerdata);
                    prepare.setString(1,firstName);
                    prepare.setString(2,lastName);
                    prepare.setString(3, String.valueOf(total));
                    prepare.setString(4,phoneNumber);
                    prepare.setString(5,email);
                    prepare.setString(6,Checkin);
                    prepare.setString(7, Checkout );
                    prepare.executeUpdate();
                    String room_num = (String)roomnumber.getSelectionModel().getSelectedItem();
                    prepare = connect.prepareStatement(insertguestdata);
                    prepare.setString(1,room_num);
                    prepare.setString(2,firstName);
                    prepare.setString(3,lastName);
                    prepare.setString(4, String.valueOf(total));
                    prepare.setString(5,phoneNumber);
                    prepare.setString(6,email);
                    prepare.setString(7,Checkin);
                    prepare.setString(8, Checkout );
                    prepare.executeUpdate();
                    prepare = connect.prepareStatement(updateroomdata);
                    prepare.setString(1,room_num);
                    prepare.executeUpdate();

                    alter.setTitle("information message");
                    alter.setHeaderText(null);
                    alter.setContentText("successfully checked-in");
                    alter.showAndWait();
                    reset();

                }else {
                    return;
                }
            }

            checkout();
        }catch(Exception e){
            e.printStackTrace();
        };
    }
    /////////////////////////
    public void checkout() {
        String listNumber = "SELECT room FROM guest ";
        connect = DatabaseConnection.getConnection();
        try {
            ObservableList listData = FXCollections.observableArrayList();
            prepare = connect.prepareStatement(listNumber);
            result = prepare.executeQuery();
            while (result.next()) {
                listData.add(result.getString("room"));
            }
            CheukOut.setItems(listData);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @FXML
    void CheckOut_btn(ActionEvent event) {
        String roomNUM = (String) CheukOut.getSelectionModel().getSelectedItem();
        String deleteData = "DELETE FROM guest WHERE room = ?";
        String updateRoom = "UPDATE rooms SET status = 'Available' WHERE roomNumber = ?";
        connect = DatabaseConnection.getConnection();
        Alert alert;
        try {
            alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation Message");
            alert.setHeaderText(null);
            alert.setContentText("Are you sure you want to Checkout Room #" + roomNUM + "?");

            Optional<ButtonType> option = alert.showAndWait();

            if (option.get().equals(ButtonType.OK)) {
                prepare = connect.prepareStatement(deleteData);
                prepare.setString(1, roomNUM);
                prepare.executeUpdate();

                prepare = connect.prepareStatement(updateRoom);
                prepare.setString(1, roomNUM);
                prepare.executeUpdate();

                alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Message");
                alert.setHeaderText(null);
                alert.setContentText("Successfully Checked Out!");
                alert.showAndWait();
                checkout();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
//////////////////////////////
    public ObservableList<guestData> guestListData() {
        ObservableList<guestData> listDatacust = FXCollections.observableArrayList();
        String sql = "SELECT idcustomer,room,firstName,lastName,phoneNumber,checkin,checkout FROM guest";
        connect = DatabaseConnection.getConnection();
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            guestData custD;

            while (result.next()) {
                custD = new guestData(result.getInt("idcustomer"),
                        result.getInt("room"),
                        result.getString("firstName"),
                        result.getString("lastName"),
                        result.getString("phoneNumber"),
                        result.getDate("checkin"),
                        result.getDate("checkout"));
                listDatacust.add(custD);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }return listDatacust;
    }

    private ObservableList<guestData>listguestData;
    public void guestShowData(){
        listguestData=guestListData();
        guest_Room.setCellValueFactory(new PropertyValueFactory<>("room"));
        guest_firstName.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        guest_lastName.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        guest_customerPhone.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));
        guest_checkedIn.setCellValueFactory(new PropertyValueFactory<>("checkin"));
        guest_checkedOut.setCellValueFactory(new PropertyValueFactory<>("checkout"));
        guest_tableView.setItems(listguestData);

    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            guestShowData();
            roomTypeList();
            roomNumberList();
            checkout();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }




    @FXML
    void resett(ActionEvent event) {
        reset();
    }
    void reset(){
        roomtype.setValue(null);
        roomnumber.setValue(null);
        checkindata.setValue(null);
        chekoutdata.setValue(null);
        firestnameinput.setText(null);
        lastnameinput.setText(null);
        passinput.setText(null);
        phoneinput.setText(null);
        addressinput.setText(null);
        nationalityinput.setText(null);
        emailinput.setText(null);
    }



}