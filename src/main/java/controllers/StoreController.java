package controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import models.*;

import service.DBManager;

public class StoreController implements Initializable {
    @FXML
    private Label lblSize;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtStock;
    @FXML
    private TextField txtPrice;
    @FXML
    private TextField txtCost;
    @FXML
    public TextField txtNumber;
    @FXML
    private Label txtMessage;
    @FXML
    private TextField txtPromo;
    @FXML
    private ComboBox<String> cbType;
    @FXML
    private ComboBox<Integer> cbSize;
    @FXML
    private ListView<Product> listViewProducts;
    @FXML
    public ListView<Order> listViewOrder;
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
    public Button btnSell;
    @FXML
    public Button btnBuy;
    private ArrayList<Integer> shoeSizeList;
    private ArrayList<Integer> clothSizeList;
    private ArrayList<Product> productList;
    private ObservableList<Integer> observableShoeSize;
    private ObservableList<Integer> observableClothSize;
    private ObservableList<String> observableType;
    private ObservableList<Product> observableProduct;
    private DBManager manager;
    private Product selected;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        manager = new DBManager();
        initializeObservable();

        initializeComboBox();
        initializeProductListView();
        listViewProducts.getSelectionModel().selectedItemProperty().addListener(e->displayProductDetails(listViewProducts.getSelectionModel().getSelectedItem()));
        cbType.getSelectionModel().selectedItemProperty().addListener(e->displaySizesIfNotAccessory(cbType.getSelectionModel().getSelectedItem()));
    }

    public void initializeObservable() {
        shoeSizeList = new ArrayList<>();
        clothSizeList = new ArrayList<>();
        ArrayList<String> typeList = new ArrayList<>();
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

        cbSize.setPromptText("Choose a size");
        cbType.setPromptText("Choose a type");
    }
    public void initializeProductListView() {
        productList = manager.loadProducts();
        if (productList != null) {
            observableProduct = FXCollections.observableArrayList(productList);
            listViewProducts.setItems(observableProduct);
        }
    }

    private void displayProductDetails(Product selectedProduct) {
        if(selectedProduct != null){
            selected = selectedProduct;

            // showing buttons
            if (!btnDelete.isVisible()) btnDelete.setVisible(true);
            if (!btnModify.isVisible()) btnModify.setVisible(true);

            // Filling fields
            txtName.setText(selected.getName());
            txtStock.setText(String.valueOf(selected.getStock()));
            txtPrice.setText(String.valueOf(selected.getPrice()));
            txtCost.setText(String.valueOf(selected.getPrice()));

            switch (selected.getType()) {
                case "Cloth" -> {
                    Cloth cloth = (Cloth) selected;
                    cbSize.setItems(observableClothSize);
                    cbSize.setValue(cloth.getSize());
                    showSizeCb();
                    cbType.setValue("Cloth");
                }
                case "shoe" -> {
                    Shoe shoe = (Shoe) selected;
                    cbSize.setItems(observableShoeSize);
                    cbSize.setValue(shoe.getSize());
                    showSizeCb();
                    cbType.setValue("Shoe");
                }
                case "Accessory" -> {
                    hideSizeCb();
                    cbSize.setValue(null);
                    cbType.setValue("Accessory");
                }
                default -> {
                }
            }
        } else {
            if (btnDelete.isVisible()) btnDelete.setVisible(false);
            if (btnModify.isVisible()) btnModify.setVisible(false);
        }
    }
    private void displaySizesIfNotAccessory(String selectedType) {
        if (!selectedType.isEmpty()) {
            if (selectedType.equals("Accessory")) hideSizeCb();
            else showSizeCb();
        }
    }
    private void showSizeCb() {
        if (!lblSize.isVisible()) lblSize.setVisible(true);
        if (!cbSize.isVisible()) cbSize.setVisible(true);
    }
    private void hideSizeCb() {
        if (lblSize.isVisible()) lblSize.setVisible(false);
        if (cbSize.isVisible()) cbSize.setVisible(false);
    }

    private void clearFields() {
        listViewProducts.getSelectionModel().clearSelection();
        txtName.setText("");
        txtStock.setText("");
        txtPrice.setText("");
        txtCost.setText("");
        txtPromo.setText("");
        cbType.setValue("");
        cbSize.setValue(0);
        cbSize.setPromptText("Choose a size");
        cbType.setPromptText("Choose a type");
    }
    private void disableProductList() {
        if (!listViewProducts.isMouseTransparent()) listViewProducts.setMouseTransparent(true);
        if (listViewProducts.isFocusTraversable()) listViewProducts.setFocusTraversable(false);
    }
    private void enableProductList() {
        if (listViewProducts.isMouseTransparent()) listViewProducts.setMouseTransparent(false);
        if (!listViewProducts.isFocusTraversable()) listViewProducts.setFocusTraversable(true);
    }

    private void createMode(){
        disableProductList();
        if (!btnCancel.isVisible()) btnCancel.setVisible(true);
        if (!btnSave.isVisible())   btnSave.setVisible(true);
        if (btnCreate.isVisible())  btnCreate.setVisible(false);
        if (btnDelete.isVisible())  btnDelete.setVisible(false);
        if (btnModify.isVisible())  btnModify.setVisible(false);
    }

    private void normalMode(){
        enableProductList();
        if (btnCancel.isVisible())  btnCancel.setVisible(false);
        if (btnSave.isVisible())    btnSave.setVisible(false);
        if (!btnCreate.isVisible()) btnCreate.setVisible(true);
        if (!btnDelete.isVisible()) btnDelete.setVisible(true);
        if (!btnModify.isVisible()) btnModify.setVisible(true);
    }

    private void addProductInList(Product p) {
        productList.add(p);
        observableProduct.add(p);
        listViewProducts.setItems(observableProduct);
    }
    private void deleteProductInList(Product p) {
        productList.remove(p);
        observableProduct.remove(p);
        listViewProducts.setItems(observableProduct); // refresh listView
    }

    private void updateProductInList(int i, Product p){
        productList.set(i, p);
        observableProduct.set(i, p);
        listViewProducts.setItems(observableProduct);
    }

    private boolean isInputsInvalid() {
        if (cbType.getValue() == null || cbType.getValue().isEmpty()) return false;
        if (cbSize.getValue() == null) return false;

        // check for size depending on product's type
        boolean sizeValid = true;
        int size = cbSize.getValue();
        String selectedType = cbType.getValue();
        if (selectedType.equals("Shoe")) sizeValid = shoeSizeList.contains(size);
        if (selectedType.equals("Accessory")) sizeValid = clothSizeList.contains(size);

        return
            sizeValid &&
            !txtName.getText().isEmpty() &&
            !txtPrice.getText().isEmpty() &&
            !txtStock.getText().isEmpty()
            ;
    }

    public void onNew() {
        clearFields();
        createMode();
    }
    public void onCancel(){
        clearFields();
        normalMode();
    }
    public void onSave(){
        // check inputs
        if (isInputsInvalid()) {
            error("Please enter correct fields");
            return;
        }

        // storing inputs
        String type = cbType.getValue();
        String name = txtName.getText();
        double price = Double.parseDouble(txtPrice.getText());
        int stock = Integer.parseInt(txtStock.getText());
        int size = cbSize.getValue();

        Product product = null;

        // getting the biggest id
        int biggestId = -1;
        for (Product p:listViewProducts.getItems()) {
            biggestId = Math.max(p.getId(),biggestId);
        }

        switch (type) {
            case "Cloth" ->     product = new Cloth(biggestId+1,name,price,stock,size);
            case "Shoe" ->      product = new Shoe(biggestId+1,name,price,stock,size);
            case "Accessory" -> product = new Accessory(biggestId+1,name,price,stock);
        }

        if (product == null) {
            error("Couldn't understand the described product, please check");
            return;
        }

        if (manager.addProduct(product)){
//            addProductInList(product); // refresh listView
            message("Product added ! :)");
            onCancel();
            initializeProductListView();
        } else error("Oh no, there is a bug :(");
    }
    public void onModify(){
        if (selected != null) {
            // check for fields
            if (!isInputsInvalid()) {
                error("Please enter correct fields");
                return;
            }
            // check if we changed any property
            String type = cbType.getValue();
            String name = txtName.getText();
            double price = Double.parseDouble(txtPrice.getText());
            int stock = Integer.parseInt(txtStock.getText());
            int size = cbSize.getValue();

            Product product = null;

            switch (type) {
                case "Cloth" ->     product = new Cloth(selected.getId(),name,price,stock,size);
                case "Shoe" ->      product = new Shoe(selected.getId(),name,price,stock,size);
                case "Accessory" -> product = new Accessory(selected.getId(),name,price,stock);
            }

            if (product == null) {
                error("Couldn't understand the described product, please check");
                return;
            }

            int index = productList.indexOf(selected);
            if (index == -1 && selected == productList.get(index)) {
                System.out.println("Index : " + index);
                error("Change a property if you want to update !");
                return;
            }

            if (manager.updateProduct(product)) {
//                updateProductInList(index, product);
                normalMode();
                message("Product updated !");
                initializeProductListView();
            } else error("Oh no, there is a bug :(");
        } else error("No product selected");
    }
    public void onDelete(){
//        System.out.println(listViewProducts.getSelectionModel().getSelectedItem().getId());
        if (selected != null) {
            int id = selected.getId();
            if (manager.deleteProduct(id)){
                message("Product deleted !");
                deleteProductInList(selected);
            }
            else error("Oh no, there is a bug :(");
        }
    }
    public void onBuy(){

    }
    public void onSell(){

    }
    private void message(String m){
        txtMessage.setText(m);
        txtMessage.setStyle("-fx-text-fill: green;");
    }
    private void error(String m){
        txtMessage.setText(m);
        txtMessage.setStyle("-fx-text-fill: red;");
    }
}