package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import models.Accessory;
import models.Clothe;
import models.Product;
import models.Shoe;

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

        List<Product> hardProducts = new ArrayList<>();
        hardProducts.add(new Shoe(1, "AirForceOne",100,7,42));
        hardProducts.add(new Clothe(2, "T-Shirt",32,13,3));
        ObservableList<Product> students = FXCollections.observableArrayList(hardProducts);
        listViewProducts.setItems(students);


        listViewProducts.getSelectionModel().selectedItemProperty().addListener(e->displayProductDetails((Product) listViewProducts.getSelectionModel().getSelectedItem()));

    }
    private void displayProductDetails(Product selectedProduct) {
        if(selectedProduct!=null){
            txtName.setText(selectedProduct.getName());
            txtStock.setText(String.valueOf(selectedProduct.getNbItems()));
        }
    }

}