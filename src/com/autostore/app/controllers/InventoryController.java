package com.autostore.app.controllers;

import com.autostore.app.Inventory.AddInventory;
import com.autostore.app.Inventory.Inventory;
import com.autostore.app.Inventory.SearchInventory;
import com.autostore.app.Inventory.UpdateInventory;
import com.autostore.app.model.SearchByCBModel;
import com.autostore.app.utils.ApplicationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.util.Callback;
import java.net.URL;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {

    @FXML
    private TextField partNameTF;

    @FXML
    private TextField stockQtyTF;

    @FXML
    private TextField unitPriceTF;

    @FXML
    private TextArea descriptionTA;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableView<Inventory> inventoryTable;

    @FXML
    private TableColumn<Inventory, String> partColumn;

    @FXML
    private TableColumn<Inventory, Integer> stockQtyColumn;

    @FXML
    private TableColumn<Inventory, Double> unitPriceColumn;

    @FXML
    private TableColumn<Inventory, String> descriptionColumn;

    @FXML
    private TextField searchTF;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> searchTypeComboBox;

    @FXML
    private ComboBox<SearchByCBModel> searchByComboBox;
    private TextField[] textFields;
    private TextArea[] textAreas;
    private Inventory selectedRow;
    private int partID;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        textFields = new TextField[]{partNameTF, stockQtyTF, unitPriceTF};
        textAreas = new TextArea[] {descriptionTA};
        searchTypeComboBox.getItems().setAll("Relative", "Absolute");
        searchTypeComboBox.getSelectionModel().selectFirst();

        initSearchByComboBox();
        initInventoryTable();
        fillInventoryDataForm();

        searchButton.setOnAction(event -> searchInventory());
        clearButton.setOnAction(event -> clearForm());
        addButton.setOnAction(event -> addInventory());
        updateButton.setOnAction(event -> updateInventoryItem());

    }

    private void clearForm() {
        ApplicationUtils.setTextAreaEmpty(textAreas);
        ApplicationUtils.setTextFieldsEmpty(textFields);
        updateButton.setDisable(true);
        addButton.setDisable(false);
        selectedRow = null;
        partID = 0;
    }

    private void addInventory() {

        if(!ApplicationUtils.isTextFieldEmpty(textFields)) {

            AddInventory addInventory = new AddInventory();
            addInventory.setPartName(partNameTF.getText().trim());
            addInventory.setStockQuantity(Integer.parseInt(stockQtyTF.getText().trim()));
            addInventory.setUnitPrice(Double.parseDouble(unitPriceTF.getText().trim()));
            addInventory.setDescription(descriptionTA.getText().trim());

            if(addInventory.add()) {
                DialogController.showDialog("Add Successful", "Part: " + addInventory.getPartName() +
                        " was successfully added.", new Image(DialogController.SUCCESS_ICON));
                clearForm();
            } else {
                DialogController.showDialog("Add Failed", "Part: " + addInventory.getPartName() +
                        " could not be added, please try again.", new Image(DialogController.ERROR_ICON));
            }
        }
    }

    private void updateInventoryItem() {

        if(selectedRow != null) {

            Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to make these changes?", ButtonType.YES, ButtonType.NO);
            updateAlert.showAndWait();

            if (updateAlert.getResult() == ButtonType.YES) {

                UpdateInventory updateInventory = new UpdateInventory();

                partID = selectedRow.getInventoryID();

                updateInventory.setInventoryID(partID);
                updateInventory.setPartName(partNameTF.getText().trim());
                updateInventory.setStockQuantity(Integer.parseInt(stockQtyTF.getText().trim()));
                updateInventory.setUnitPrice(Double.parseDouble(unitPriceTF.getText().trim()));
                updateInventory.setDescription(descriptionTA.getText().trim());

                if (updateInventory.updateItem()) {
                    DialogController.showDialog("Update Successful", "Part: " + updateInventory.getPartName() +
                            " was successfully updated.", new Image(DialogController.SUCCESS_ICON));
                    clearForm();
                } else {
                    DialogController.showDialog("Update Failed", "Part: " + updateInventory.getPartName() +
                            " failed to update.", new Image(DialogController.ERROR_ICON));
                }
                inventoryTable.refresh();
            }
        }
    }

    private void searchInventory() {

        ApplicationUtils.setTextFieldsEmpty(textFields);
        ApplicationUtils.setTextAreaEmpty(textAreas);
        String query, searchValue;

        String searchByValue = searchByComboBox.getSelectionModel().getSelectedItem().getSqlValue();

        if(searchTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
            searchValue = "%" + searchTF.getText().trim() + "%";
            query = "select * from store_inventory where " + searchByValue + " like ?";
        } else {
            searchValue = searchTF.getText().trim();
            query = "select * from store_inventory where " + searchByValue + " = ?";
        }

        SearchInventory searchInventory = new SearchInventory();
        searchInventory.searchInventoryData(query, searchValue);

        if(!searchInventory.getInventoryData().isEmpty()) {
            inventoryTable.setItems(searchInventory.getInventoryData());
        } else {
            inventoryTable.getItems().clear();
        }

        updateButton.setDisable(true);
    }

    private void fillInventoryDataForm() {

        inventoryTable.setOnMouseClicked(event -> {

            if(!inventoryTable.getItems().isEmpty()) {

                if(event.getClickCount() == 1) {

                    selectedRow = inventoryTable.getSelectionModel().getSelectedItem();

                    if(selectedRow != null) {

                        partID = selectedRow.getInventoryID();
                        partNameTF.setText(selectedRow.getPartName());
                        stockQtyTF.setText(String.valueOf(selectedRow.getStockQuantity()));
                        unitPriceTF.setText(String.valueOf(selectedRow.getUnitPrice()));
                        descriptionTA.setText(selectedRow.getDescription());

                        addButton.setDisable(true);
                        updateButton.setDisable(false);
                    }
                }
            }
        });
    }

    private void initInventoryTable() {

        partColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        stockQtyColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void initSearchByComboBox() {

        ObservableList<SearchByCBModel> searchByValues = FXCollections.observableArrayList(
                new SearchByCBModel("Part ID", "inventory_id"),
                new SearchByCBModel("Part Name", "name"));

        searchByComboBox.getItems().addAll(searchByValues);
        searchByComboBox.getSelectionModel().selectFirst();
        searchByComboBox.setCellFactory(new Callback<>(){

            @Override
            public ListCell<SearchByCBModel> call(ListView<SearchByCBModel> p) {

                return new ListCell<>(){

                    @Override
                    protected void updateItem(SearchByCBModel t, boolean bln) {
                        super.updateItem(t, bln);

                        if(t != null){
                            setText(t.getSearchValue());
                        }else{
                            setText(null);
                        }
                    }
                };
            }
        });
    }
}


















































































