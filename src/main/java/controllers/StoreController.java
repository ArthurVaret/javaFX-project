package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.text.ParseException;
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
    private ArrayList<Integer> shoeSizeList;
    private ArrayList<Integer> clothSizeList;
    private ArrayList<String> typeList;
    private ObservableList<Integer> observableShoeSize;
    private ObservableList<Integer> observableClothSize;
    private ObservableList<String> observableType;
    private DBManager manager;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        manager = new DBManager();
        initializeObservable();

        initializeComboBox();
        initializeProductListView();
        listViewProducts.getSelectionModel().selectedItemProperty().addListener(e->displayProductDetails((Product) listViewProducts.getSelectionModel().getSelectedItem()));
    }

    public void initializeObservable() {
        shoeSizeList = new ArrayList<>();
        clothSizeList = new ArrayList<>();
        typeList = new ArrayList<>();
        // Tableau des tailles
        for (int i = 34; i <= 54; i+=2){
            shoeSizeList.add(i);
        }
        for (int i = 36; i <= 50; i++){
            clothSizeList.add(i);
        }
        // Les types
        typeList.add("Cloth");
        typeList.add("Shoe");
        typeList.add("Accessory");

        observableType = FXCollections.observableArrayList(typeList);
        observableShoeSize = FXCollections.observableArrayList(shoeSizeList);
        observableClothSize = FXCollections.observableArrayList(clothSizeList);
    }

    public void initializeComboBox() {
        cbSize.setItems(observableShoeSize);
        cbType.setItems(observableType);
    }

    public void initializeProductListView() {
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
        } else {
            btnDelete.setVisible(false);
            btnModify.setVisible(false);
        }
    }

    public void clearFields() {
        listViewProducts.getSelectionModel().clearSelection();
        txtName.setText(null);
        txtStock.setText(null);
        txtPrice.setText(null);
        txtCost.setText(null);
        txtPromo.setText(null);
        cbType.setValue(null);
        cbSize.setValue(null);
        cpColour.setValue(null);
    }

    public void onNew() {
        clearFields();
        btnCancel.setVisible(true);
        btnSave.setVisible(true);
        btnDelete.setVisible(false);
        btnModify.setVisible(false);
    }
    public void onCancel(){
        clearFields();
        btnCancel.setVisible(false);
        btnSave.setVisible(false);
        btnDelete.setVisible(true);
        btnModify.setVisible(true);
    }
    public void onSave(){

        if (cbType.getValue() == null ||
            cbSize.getValue() == null ||
            txtName.getText().isEmpty() ||
            txtPrice.getText().isEmpty() ||
            txtStock.getText().isEmpty()) {
            txtError.setText("At least one field is not correct");
            return;
        }

        String type = cbType.getValue();
        String name = txtName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int stock = Integer.parseInt(txtStock.getText());
        int size = cbSize.getValue();

        Product product = null;
        int biggestId = -1;
        // getting the biggest id
        for (Product p:listViewProducts.getItems()) {
            biggestId = Math.max(p.getId(),biggestId);
        }

        switch (type) {
            case "cloth" -> {
                product = new Cloth(biggestId+1,name,price,stock,size);
            }
            case "shoe" -> {
                product = new Shoe(biggestId+1,name,price,stock,size);
            }
            case "accessory" -> {
                product = new Accessory(biggestId+1,name,price,stock);
            }
        }

        txtError.setText(manager.addProduct(product));

        ObservableList<Product> products = FXCollections.observableArrayList(listViewProducts.getItems());
        products.add(product);
        listViewProducts.setItems(products);
    }
    public void onModify(){

    }
    public void onDelete(){

    }
}