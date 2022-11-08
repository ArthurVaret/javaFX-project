package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import models.Accessory;
import models.Cloth;
import models.Product;
import models.Shoe;

import service.DBManager;

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
    private TextArea txtError;
    @FXML
    private TextField txtPromo;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private ComboBox<Integer> cbSize;
    @FXML
    private ColorPicker cpColour;
    @FXML
    private ListView<Product> listViewProducts;
    @FXML
    private Button btnCreate;
    @FXML
    private Button btnModify;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnSave;
    @FXML
    private Button btnCancel;
    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }

    private ArrayList<Integer> shoeSizeList;
    private ArrayList<Integer> clothSizeList;
    private ObservableList<Integer> observableShoeSize;
    private ObservableList<Integer> observableClothSize;
    private DBManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        manager = new DBManager();
        initializeSizeObservable();

        initializeSizeComboBox();
        initializeTypeComboBox();
        initializeProductListView();
        listViewProducts.getSelectionModel().selectedItemProperty().addListener(e->displayProductDetails((Product) listViewProducts.getSelectionModel().getSelectedItem()));
    }

    public void initializeSizeObservable() {
        shoeSizeList = new ArrayList<Integer>();
        clothSizeList = new ArrayList<Integer>();
        // Tableau des tailles
        for (int i = 34; i <= 54; i+=2){
            shoeSizeList.add(i);
        }
        for (int i = 36; i <= 50; i++){
            clothSizeList.add(i);
        }
        observableShoeSize = FXCollections.observableArrayList(shoeSizeList);
        observableClothSize = FXCollections.observableArrayList(clothSizeList);
    }

    public void initializeSizeComboBox() {
        List<Integer> list = new ArrayList<Integer>();
        ObservableList<Integer> size = FXCollections.observableArrayList(list);
        cbSize.setItems(size);
    }
    public void initializeTypeComboBox(){
        List<String> list = new ArrayList<String>();
        list.add("Cloth");
        list.add("Shoe");
        list.add("Accessory");
        ObservableList<String> type = FXCollections.observableArrayList(list);
        cbType.setItems(type);
    }
    public void initializeProductListView() {
//        List<Product> products = new ArrayList<>();
//        products.add(new Shoe(1, "Air Force 1",100,7,42));
//        products.add(new Cloth(2, "T-Shirt",32,13,46));
//        products.add(new Accessory(3, "Rolex",3000,2));

        List <Product> products = manager.loadProducts();
        if (products != null) {
            ObservableList<Product> students = FXCollections.observableArrayList(products);
            listViewProducts.setItems(students);
        }
    }

    private void displayProductDetails(Product selectedProduct) {
        if(selectedProduct != null){
            btnDelete.setVisible(true);
            btnModify.setVisible(true);
            txtName.setText(selectedProduct.getName());
            txtStock.setText(String.valueOf(selectedProduct.getNbItems()));
            txtPrice.setText(String.valueOf(selectedProduct.getPrice()));
            txtCost.setText(String.valueOf(selectedProduct.getPrice()));

            switch (selectedProduct.getType()) {
                case "cloth" -> {
                    Cloth cloth = (Cloth) selectedProduct;
                    cbSize.setItems(observableClothSize);
                    cbSize.setValue(cloth.getSize());
                    cbType.setValue("Cloth");
                }
                case "shoe" -> {
                    Shoe shoe = (Shoe) selectedProduct;
                    cbSize.setItems(observableShoeSize);
                    cbSize.setValue(shoe.getSize());
                    cbType.setValue("Shoe");
                }
                case "accessory" -> {
                    cbSize.hide();
                    cbSize.setValue(null);
                    cbType.setValue("Accessory");
                }
                default -> {
                }
            }
        }
        else {
            btnDelete.setVisible(false);
            btnModify.setVisible(false);
        }
    }

    public void onNew() {
        listViewProducts.getSelectionModel().clearSelection();
        txtName.setText(null);
        txtStock.setText(null);
        txtPrice.setText(null);
        txtCost.setText(null);
        txtPromo.setText(null);
        cbType.setValue(null);
        cbSize.setValue(null);
        cpColour.setValue(null);
        btnCancel.setVisible(true);
        btnSave.setVisible(true);
        btnDelete.setVisible(false);
        btnModify.setVisible(false);
    }
    public void onCancel(){
        listViewProducts.getSelectionModel().clearSelection();
        txtName.setText(null);
        txtStock.setText(null);
        txtPrice.setText(null);
        txtCost.setText(null);
        txtPromo.setText(null);
        cbType.setValue(null);
        cbSize.setValue(null);
        cpColour.setValue(null);
        btnCancel.setVisible(false);
        btnSave.setVisible(false);
        btnDelete.setVisible(true);
        btnModify.setVisible(true);
    }
    public void onSave(){
        Product product = null;
        switch (cbType.getValue()) {
            case "cloth" -> {
                product = new Cloth(1,txtName.getText(), Double.parseDouble(txtPrice.getText()),Integer.parseInt(txtStock.getText()), cbSize.getValue());
            }
            case "shoe" -> {
                product = new Shoe(1,txtName.getText(),Double.parseDouble(txtPrice.getText()),Integer.parseInt(txtStock.getText()), cbSize.getValue());
            }
            case "accessory" -> {
                product = new Accessory(1,txtName.getText(),Double.parseDouble(txtPrice.getText()),Integer.parseInt(txtStock.getText()));
            }
        }

        ObservableList<Product> products = FXCollections.observableArrayList(listViewProducts.getItems());
        products.add(product);
        listViewProducts.setItems(products);
    }
    public void onModify(){

    }
    public void onDelete(){

    }
}