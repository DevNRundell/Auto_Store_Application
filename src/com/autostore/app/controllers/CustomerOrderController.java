package com.autostore.app.controllers;

import com.autostore.app.Inventory.Inventory;
import com.autostore.app.Inventory.SearchInventory;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import java.net.URL;
import java.text.DecimalFormat;
import java.util.ResourceBundle;

public class CustomerOrderController implements Initializable {

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
    private ListView<Inventory> itemListView;

    @FXML
    private Label subTotalLabel;

    @FXML
    private ComboBox<String> discountCB;

    @FXML
    private Label taxLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private Button submitButton;

    @FXML
    private Button removeItemButton;

    @FXML
    private Label headerLabel;
    private int customerID;
    private double subTotal;
    private double discount;
    private double TAX_RATE = .06;
    private double tax;
    private double total;
    private DecimalFormat format = new DecimalFormat(",###.##");

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        discountCB.getItems().setAll("5%", "10%", "15%", "20%", "25%", "30%", "35%", "40%", "45%", "50%", "55%", "60%",
                                    "65%", "70%", "75%");

        initInventoryTable();
        loadInventory();
        setInvoiceListViewCustomCell();
        initAddItemMouseEvent();
        initDiscountCBListener();

        subTotal = 0;
        discount = 0;
        total = 0;

        removeItemButton.setOnAction(event -> removeItem());

    }

    private void initDiscountCBListener() {

        discountCB.selectionModelProperty().addListener((observable, oldValue, newValue) -> {
            discount = subTotal + (subTotal * Integer.parseInt(discountCB.getSelectionModel().getSelectedItem().substring(0, '%' - 1)));
            setOrderAmounts();

            //doesnt work fix
        });
    }

    private void removeItem() {

        Inventory removeItem = itemListView.getSelectionModel().getSelectedItem();

        if(removeItem != null) {

            subTotal -= removeItem.getUnitPrice();
            tax = (TAX_RATE * subTotal);
            total = ((subTotal + tax) - discount);
            setOrderAmounts();

            itemListView.getItems().remove(removeItem);
        }
    }

    private void initAddItemMouseEvent() {

        inventoryTable.setOnMouseClicked(event -> {

            if(event.getClickCount() == 2) {

                if(!inventoryTable.getItems().isEmpty()) {

                    Inventory item = inventoryTable.getSelectionModel().getSelectedItem();

                    if(item != null) {

                        Inventory model = new Inventory();
                        model.setPartName(item.getPartName());
                        model.setInventoryID(item.getInventoryID());
                        model.setDescription(item.getDescription());
                        model.setUnitPrice(item.getUnitPrice());

                        subTotal += item.getUnitPrice();
                        tax = (TAX_RATE * subTotal);
                        total = ((subTotal + tax) - discount);

                        itemListView.getItems().add(model);

                        setOrderAmounts();
                    }
                }
            }
        });
    }

    private void setInvoiceListViewCustomCell() {

        itemListView.setCellFactory(new Callback<>() {

            @Override
            public ListCell<Inventory> call(ListView<Inventory> param) {
                return new ListCell<>() {

                    @Override
                    protected void updateItem(Inventory item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText("Item: " + item.getPartName() + "\n" +
                                    "Description: " + item.getDescription() + "\n" +
                                    "Unit Price: $" + item.getUnitPrice());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    private void loadInventory() {
        SearchInventory searchInventory = new SearchInventory();
        searchInventory.searchAllInventory();

        if(searchInventory.getInventoryData() != null) {
            inventoryTable.setItems(searchInventory.getInventoryData());
        }
    }

    private void initInventoryTable() {

        partColumn.setCellValueFactory(new PropertyValueFactory<>("partName"));
        stockQtyColumn.setCellValueFactory(new PropertyValueFactory<>("stockQuantity"));
        unitPriceColumn.setCellValueFactory(new PropertyValueFactory<>("unitPrice"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));

    }

    public void setCustomerID(int customerID) {
        this.customerID = customerID;
    }
    public void setHeaderLabel(String customerName){headerLabel.setText("Customer: " + customerName);}
    private void setOrderAmounts() {

        subTotalLabel.setText("Sub-Total: $" + format.format(subTotal));
        taxLabel.setText("Tax: $" + format.format(tax));
        totalLabel.setText("Total: $" + format.format(total));

    }
}
