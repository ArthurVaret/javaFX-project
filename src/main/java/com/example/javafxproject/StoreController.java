package com.example.javafxproject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StoreController implements Initializable {
    @FXML
    private Label welcomeText;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtCost;
    @FXML
    private TextField txtPromo;
    @FXML
    private ComboBox cbType;
    @FXML
    private ComboBox cbSize;
    @FXML
    private ColorPicker cpColour;
    @FXML
    private ListView listViewProducts;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        List<String> svalues = new ArrayList<String>();
        svalues.add("XS");
        svalues.add("S");
        svalues.add("M");
        svalues.add("L");
        svalues.add("XL");
        ObservableList<String> size = FXCollections.observableArrayList(svalues);
        cbSize.setItems(size);

        List<String> tvalues = new ArrayList<String>();
        tvalues.add("Clothes");
        tvalues.add("Shoes");
        tvalues.add("Accessories");
        ObservableList<String> type = FXCollections.observableArrayList(tvalues);
        cbType.setItems(type);

        List<product> hardProducts = new ArrayList<>();
        hardProducts.add(new Shoes("AirForceOne",100,7,42));
        hardProducts.add(new Clothes("t-shirt",32,13,3));
        ObservableList<product> students=
                FXCollections.observableArrayList(hardProducts);
        listViewProducts.setItems(students);


        listViewProducts.getSelectionModel().selectedItemProperty().addListener(e->displayProductDetails((product) listViewProducts.getSelectionModel().getSelectedItem()));

    }
    private void displayProductDetails(product selectedProduct) {
        if(selectedProduct!=null){
            txtName.setText(selectedProduct.getName());
            txtStock.setText(String.valueOf(selectedProduct.getNbItems()));
        }
    }

}