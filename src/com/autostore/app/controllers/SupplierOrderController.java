package com.autostore.app.controllers;

import com.autostore.app.Inventory.Inventory;
import com.autostore.app.Inventory.SearchInventory;
import com.autostore.app.accounting.Accounting;
import com.autostore.app.supplier.SubmitOrder;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.net.URL;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class SupplierOrderController implements Initializable {

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

        @FXML
        private BorderPane pane;
        private int supplierID;
        private double subTotal;
        private double discount;
        private double tax;
        private double total;
        private static double TAX_RATE = .06;
        private DecimalFormat format = new DecimalFormat(",###.##");

        @Override
        public void initialize(URL location, ResourceBundle resources) {

            discountCB.getItems().setAll("No Discount", "5%", "10%", "15%", "20%", "25%", "30%", "35%", "40%", "45%", "50%", "55%", "60%",
                    "65%", "70%", "75%");
            discountCB.getSelectionModel().selectFirst();

            initInventoryTable();
            loadInventory();
            setInvoiceListViewCustomCell();
            initAddItemMouseEvent();
            initRemoveItemMouseEvent();

            subTotal = 0;
            discount = 0;
            tax = 0;
            total = 0;

            removeItemButton.setOnAction(event -> {
                Inventory removeItem = itemListView.getSelectionModel().getSelectedItem();
                removeItem(removeItem);
            });
            submitButton.setOnAction(event -> submitOrder());
            discountCB.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> setDiscount());
        }

        private void submitOrder() {

            ArrayList<Inventory> itemsOrdered = new ArrayList<>(itemListView.getItems());

            if(!itemsOrdered.isEmpty()) {

                SubmitOrder order = new SubmitOrder();

                if(order.add(tax, subTotal, discount, total, supplierID)) {

                    order.addOrderToPurchaseLine(itemsOrdered);

                    Accounting updateBalance = new Accounting();
                    updateBalance.addToBalance(total);

                    DialogController.showDialog("Order Submitted", "Your order has been successfully submitted",
                            new Image(DialogController.SUCCESS_ICON));

                    Stage stage = (Stage) pane.getScene().getWindow();
                    stage.close();

                } else {
                    DialogController.showDialog("Order Not Submitted", "Your order could not be submitted",
                            new Image(DialogController.ERROR_ICON));
                }
            } else {
                DialogController.showDialog("Order Empty", "Your cart is empty.", new Image(DialogController.INFO_ICON));
            }
        }

        private void updateQuantity(Inventory item) {

            if(item != null) {

                if(item.getQuantityToOrder() < item.getStockQuantity()) {
                    item.setQuantityToOrder(item.getQuantityToOrder() + 1);
                    subTotal += item.getUnitPrice();
                    tax = TAX_RATE * subTotal;
                    total = ((subTotal + tax) - discount);
                    setDiscount();
                    setOrderAmounts();
                } else {
                    DialogController.showDialog("Not Enough Stock", "You may not exceed stock limit.",
                            new Image(DialogController.ERROR_ICON));
                }
            }
        }

        private void setDiscount() {

            String discountValue = discountCB.getSelectionModel().getSelectedItem();

            if(discountValue.equals("No Discount")) {
                discount = 0.0;
            } else if(Double.parseDouble(discountValue.substring(0, (discountValue.indexOf('%')))) < 9) {
                discount = subTotal * Double.parseDouble(".0" + discountValue.substring(0, (discountValue.indexOf('%'))));
            } else {
                discount = subTotal * Double.parseDouble("." + discountValue.substring(0, (discountValue.indexOf('%'))));
            }

            tax = TAX_RATE * subTotal;
            total = ((subTotal + tax) - discount);
            setOrderAmounts();
        }

        private void removeItem(Inventory Item) {

            if(Item != null) {

                subTotal -= (Item.getUnitPrice() * Item.getQuantityToOrder());
                tax = TAX_RATE * subTotal;
                total = ((subTotal + tax) - discount);
                setDiscount();
                setOrderAmounts();

                itemListView.getItems().remove(Item);
            }
        }

        private void removeItemQuantity(Inventory item) {

            if(item != null) {

                if(item.getQuantityToOrder() > 1) {
                    item.setQuantityToOrder(item.getQuantityToOrder() - 1);
                    subTotal -= item.getUnitPrice();
                    tax = TAX_RATE * subTotal;
                    total = ((subTotal + tax) - discount);
                    setDiscount();
                    setOrderAmounts();

                    itemListView.refresh();
                } else {
                    removeItem(item);
                }
            }
        }

        private void initRemoveItemMouseEvent() {

            itemListView.setOnMouseClicked(event ->  {

                if(event.getClickCount() == 2) {

                    Inventory item = itemListView.getSelectionModel().getSelectedItem();

                    if(item != null) {
                        removeItemQuantity(item);
                    }
                }
            });
        }

        private void initAddItemMouseEvent() {

            inventoryTable.setOnMouseClicked(event -> {

                if(event.getClickCount() == 2) {

                    if(!inventoryTable.getItems().isEmpty()) {

                        Inventory item = inventoryTable.getSelectionModel().getSelectedItem();

                        if(item != null) {

                            boolean itemExists = false;

                            for(Inventory checkItem: itemListView.getItems()) {

                                if(item.getInventoryID() == checkItem.getInventoryID()) {
                                    updateQuantity(checkItem);
                                    itemExists = true;
                                }
                            }

                            if(!itemExists) {

                                Inventory model = new Inventory();
                                model.setPartName(item.getPartName());
                                model.setInventoryID(item.getInventoryID());
                                model.setDescription(item.getDescription());
                                model.setUnitPrice(item.getUnitPrice());
                                model.setStockQuantity(item.getStockQuantity());
                                model.setQuantityToOrder(1);

                                subTotal += item.getUnitPrice();
                                tax = TAX_RATE * subTotal;
                                total = ((subTotal + tax) - discount);

                                itemListView.getItems().add(model);

                                setDiscount();
                                setOrderAmounts();
                            }
                        }
                        itemListView.refresh();
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
                                        "Unit Price: $" + item.getUnitPrice() + "\n" +
                                        "Quantity Selected: " + item.getQuantityToOrder());
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

        void setSupplierID(int supplierID) {
            this.supplierID = supplierID;
        }
        void setHeaderLabel(String supplierName){headerLabel.setText("Supplier: " + supplierName);}
        private void setOrderAmounts() {

            subTotalLabel.setText("Sub-Total: $" + format.format(subTotal));
            taxLabel.setText("Tax: $" + format.format(tax));
            totalLabel.setText("Total: $" + format.format(total));

        }
}

