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
    public Label lblIncome;
    @FXML
    public Label lblCost;
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
        initializeDashboard();
        initializeListener();
        disableOrderFields();
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
    public void initializeDashboard() {
        Double incomeTotal = manager.getDashboardValue("sell");
        Double costTotal = manager.getDashboardValue("buy");
        if (incomeTotal >= 0) lblIncome.setText(incomeTotal.toString());
        if (costTotal >= 0) lblCost.setText(costTotal.toString());
    }
    public void initializeListener() {
        listViewProducts.getSelectionModel().selectedItemProperty().addListener(e->displayProductDetails(listViewProducts.getSelectionModel().getSelectedItem()));
        cbType.getSelectionModel().selectedItemProperty().addListener(e->displaySizesIfNotAccessory(cbType.getSelectionModel().getSelectedItem()));
    }
    private void displayProductDetails(Product selectedProduct) {
        if(selectedProduct != null){
            selected = selectedProduct;

            // showing buttons
            if (!btnDelete.isVisible()) btnDelete.setVisible(true);
            if (!btnModify.isVisible()) btnModify.setVisible(true);
            enableOrderFields();

            // Filling fields
            txtName.setText(selected.getName());
            txtStock.setText(String.valueOf(selected.getStock()));
            txtPrice.setText(String.valueOf(selected.getPrice()));
            txtCost.setText(String.valueOf(selected.getCost()));

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
                    cbSize.setValue(0);
                    cbType.setValue("Accessory");
                }
                default -> {
                }
            }
        } else {
            if (btnDelete.isVisible())      btnDelete.setVisible(false);
            if (btnModify.isVisible())      btnModify.setVisible(false);
            disableOrderFields();
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
    private void enableOrderFields() {
        if (txtNumber.isDisabled()) txtNumber.setDisable(false);
        if (btnBuy.isDisabled())    btnBuy.setDisable(false);
        if (btnSell.isDisabled())   btnSell.setDisable(false);
    }
    private void disableOrderFields() {
        if (!txtNumber.isDisabled())    txtNumber.setDisable(true);
        if (!btnBuy.isDisabled())       btnBuy.setDisable(true);
        if (!btnSell.isDisabled())      btnSell.setDisable(true);
    }

    private void clearFields() {
        listViewProducts.getSelectionModel().clearSelection();
        txtName.clear();
        txtStock.clear();
        txtPrice.clear();
        txtCost.clear();
        txtPromo.clear();
        cbType.setValue("");
        cbSize.setValue(0);
        cbSize.setPromptText("Choose a size");
        cbType.setPromptText("Choose a type");
    }
    private void disableProductList() {
        if (!listViewProducts.isMouseTransparent()) listViewProducts.setMouseTransparent(true);
        if (listViewProducts.isFocusTraversable())  listViewProducts.setFocusTraversable(false);
    }
    private void enableProductList() {
        if (listViewProducts.isMouseTransparent())  listViewProducts.setMouseTransparent(false);
        if (!listViewProducts.isFocusTraversable()) listViewProducts.setFocusTraversable(true);
    }

    private void createMode(){
        disableProductList();
        disableOrderFields();
        if (!btnCancel.isVisible()) btnCancel.setVisible(true);
        if (!btnSave.isVisible())   btnSave.setVisible(true);
        if (btnCreate.isVisible())  btnCreate.setVisible(false);
        if (btnDelete.isVisible())  btnDelete.setVisible(false);
        if (btnModify.isVisible())  btnModify.setVisible(false);
    }

    private void normalMode(){
        enableProductList();
        enableOrderFields();
        if (btnCancel.isVisible())  btnCancel.setVisible(false);
        if (btnSave.isVisible())    btnSave.setVisible(false);
        if (!btnCreate.isVisible()) btnCreate.setVisible(true);
        if (!btnDelete.isVisible()) btnDelete.setVisible(true);
        if (!btnModify.isVisible()) btnModify.setVisible(true);
    }
    private void deleteProductInList(Product p) {
        productList.remove(p);
        observableProduct.remove(p);
        listViewProducts.setItems(observableProduct); // refresh listView
    }
    private boolean checkFields() {
        try {
            if (cbType.getValue() == null || cbType.getValue().isEmpty()) throw new IllegalArgumentException("Product type error");
            if (cbSize.getValue() == null) throw new IllegalArgumentException("Product size error");
            if (txtName.getText().isEmpty()) throw new IllegalArgumentException("Product name empty");
            if (txtPrice.getText().isEmpty()) throw new IllegalArgumentException("Product price empty");
            if (txtCost.getText().isEmpty()) throw new IllegalArgumentException("Product price empty");

            try {
                Double.parseDouble(txtPrice.getText());
                Double.parseDouble(txtCost.getText());
                Integer.parseInt(txtStock.getText());
            } catch (IllegalArgumentException e) {
                error("Number's fields incorrect");
                System.out.println(e.getMessage());
                return false;
            }
            // check for size depending on product's type
            boolean sizeValid = true;
            int size = cbSize.getValue();
            String selectedType = cbType.getValue();
            if (selectedType.equals("Shoe")) sizeValid = shoeSizeList.contains(size);
            else if (selectedType.equals("Cloth")) sizeValid = clothSizeList.contains(size);
            return sizeValid;
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            System.out.println(e.getMessage());
            return false;
        }
    }
    @FXML
    public void onNew() {
        clearFields();
        createMode();
    }
    @FXML
    public void onCancel(){
        clearFields();
        normalMode();
    }
    @FXML
    public void onSave(){
        try {
            // check inputs
            if (checkFields()) {
                // storing inputs
                String type = cbType.getValue();
                String name = txtName.getText();
                double price = Double.parseDouble(txtPrice.getText());
                double cost = Double.parseDouble(txtCost.getText());
                int stock = Integer.parseInt(txtStock.getText());
                int size = cbSize.getValue();

                Product product = null;

                // getting the biggest id
                int biggestId = -1;
                for (Product p:listViewProducts.getItems()) {
                    biggestId = Math.max(p.getId(),biggestId);
                }

                switch (type) {
                    case "Cloth" ->     product = new Cloth(biggestId+1,name,price,cost,stock,size);
                    case "Shoe" ->      product = new Shoe(biggestId+1,name,price,cost,stock,size);
                    case "Accessory" -> product = new Accessory(biggestId+1,name,price,cost,stock);
                }

                if (product == null)
                    throw new IllegalArgumentException("Couldn't understand the described product, please check");

                if (manager.addProduct(product)){
                    message("Product added ! :)");
                    onCancel();
                    initializeProductListView();
                } else
                    throw new IllegalArgumentException("Oh no, there is a bug :(");
            }
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }
    @FXML
    public void onModify(){
        try {
            if (selected != null && checkFields()) {
                // check if we changed any property
                String type = cbType.getValue();
                String name = txtName.getText();
                double price = Double.parseDouble(txtPrice.getText());
                double cost = Double.parseDouble(txtPrice.getText());
                int stock = Integer.parseInt(txtStock.getText());
                int size = cbSize.getValue();

                Product product = null;

                switch (type) {
                    case "Cloth" ->     product = new Cloth(selected.getId(),name,price,cost,stock,size);
                    case "Shoe" ->      product = new Shoe(selected.getId(),name,price,cost,stock,size);
                    case "Accessory" -> product = new Accessory(selected.getId(),name,price,cost,stock);
                }

                if (product == null)
                    throw new IllegalArgumentException("Couldn't understand the described product, please check");

                int index = productList.indexOf(selected);
                if (index == -1 && selected == productList.get(index))
                    throw new IllegalArgumentException("Change a property if you want to update ! Index : " + index);

                if (manager.updateProduct(product)) {
                    normalMode();
                    message("Product updated !");
                    initializeProductListView();
                }
                else
                    error("Oh no, there is a bug :(");
            }
            else
                throw new IllegalArgumentException("No product selected, can't modify");
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            System.out.println(e.getMessage());
        }

    }
    @FXML
    public void onDelete(){
        try {
            if (selected != null) {
                int id = selected.getId();
                if (manager.deleteProduct(id)){
                    message("Product deleted !");
                    deleteProductInList(selected);
                } else
                    throw new IllegalArgumentException("Oh no, there is a bug :(");
            } else
                throw new IllegalArgumentException("No product selected");
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            System.out.println(e.getMessage());
        }

    }
    private void orderAction(String action){
        try {
            if (selected != null) {
                String stringNumber = txtNumber.getText();
                if (stringNumber.isEmpty()) {
                    throw new IllegalArgumentException(String.format("Please write down how many you want to %s !", stringNumber));
                }
                if (!stringNumber.matches("^\\d$")) {
                    throw new IllegalArgumentException("Please write a correct number");
                }
                int amount = Integer.parseInt(stringNumber);
                switch (action) {
                    case "buy" -> {
                        if (manager.orderProduct(selected,action,amount)) {
                            initializeDashboard();
                            selected.setStock(selected.getStock() + amount);
                            txtNumber.clear();
                            message(String.format("Ordered %s product", stringNumber));
                        } else {
                            error("Oh no there is a bug :(");
                        }
                    }
                    case "sell" -> {
                        // check if the amount in input is higher than the selected product
                        if (selected.getStock() - amount < 0)
                            throw new IllegalArgumentException(String.format("Can't sell more, products are limited (%d > %d)",amount,selected.getStock()));

                        if (manager.orderProduct(selected,action,amount)) {
                            initializeDashboard();
                            selected.setStock(selected.getStock() - amount);
                            txtNumber.clear();
                            message(String.format("Sold %s product", stringNumber));
                        }
                        else
                            throw new IllegalArgumentException("Oh no there is a bug :(");
                    }
                    default -> throw new IllegalArgumentException("Can't understand the action");
                }
            } else {
                throw new IllegalArgumentException("No product, can't buy");
            }
        } catch (IllegalArgumentException e) {
            error(e.getMessage());
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public void onBuy(){
        orderAction("buy");
    }
    @FXML
    public void onSell(){
        orderAction("sell");
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