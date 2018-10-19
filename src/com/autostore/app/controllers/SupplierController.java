package com.autostore.app.controllers;

import com.autostore.app.model.CustomerInvoiceModel;
import com.autostore.app.model.SearchByCBModel;
import com.autostore.app.supplier.*;
import com.autostore.app.supplier.SearchSupplier;
import com.autostore.app.utils.ApplicationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.util.Callback;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;


public class SupplierController implements Initializable {

    @FXML
    private Tab informationTab;

    @FXML
    private TextField nameTF;

    @FXML
    private TextField addressTF;

    @FXML
    private TextField cityTF;

    @FXML
    private TextField stateTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField phoneTF;

    @FXML
    private TextField contactNameTF;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableView<Supplier> supplierTable;

    @FXML
    private TableColumn<Supplier, String> nameColumn;

    @FXML
    private TableColumn<Supplier, String> addressColumn;

    @FXML
    private TableColumn<Supplier, String> cityColumn;

    @FXML
    private TableColumn<Supplier, String> stateColumn;

    @FXML
    private TableColumn<Supplier, String> emailColumn;

    @FXML
    private TableColumn<Supplier, String> phoneColumn;

    @FXML
    private TableColumn<Supplier, String> contactNameColumn;

    @FXML
    private Tab purchaseHistoryTab;

    @FXML
    private ListView<CustomerInvoiceModel> invoiceSumListView;

    @FXML
    private Label discountLabel;

    @FXML
    private Label subTotalLabel;

    @FXML
    private Label taxesLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private TableView<SupplierInvoice> purchaseHistoryTable;

    @FXML
    private TableColumn<SupplierInvoice, Integer> purchaseIDColumn;

    @FXML
    private TableColumn<SupplierInvoice, Date> orderDateColumn;

    @FXML
    private TableColumn<SupplierInvoice, Double> discountColumn;

    @FXML
    private TableColumn<SupplierInvoice, Double> subTotalColumn;

    @FXML
    private TableColumn<SupplierInvoice, Double> taxColumn;

    @FXML
    private TableColumn<SupplierInvoice, Double> totalColumn;

    @FXML
    private TextField searchTF;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> searchTypeComboBox;

    @FXML
    private ComboBox<SearchByCBModel> searchByComboBox;

    private TextField[] textFields;
    private Supplier suppSelectedTableRow;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        textFields = new TextField[]{nameTF, addressTF, emailTF, phoneTF, cityTF, stateTF, contactNameTF};

        searchTypeComboBox.getItems().setAll("Absolute", "Relative");
        searchTypeComboBox.getSelectionModel().selectLast();

        initSupplierTable();
        initSearchComboBox();
        initPurchaseHistoryTable();
        fillSupplierDataForm();
        // fillInvoiceSummaryList();

        searchButton.setOnAction(event -> searchSupplier());
        clearButton.setOnAction(event -> clearForm());
        addButton.setOnAction(event -> addSupplier());
        updateButton.setOnAction(event -> updateSupplier());


    }

    private void initSupplierTable() {

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
        cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
        stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));

    }

    private void clearForm() {
        ApplicationUtils.setTextFieldsEmpty(textFields);
        updateButton.setDisable(true);
        addButton.setDisable(false);
        suppSelectedTableRow = null;
    }

    private void initSearchComboBox() {

        ObservableList<SearchByCBModel> searchValues = FXCollections.observableArrayList(
                new SearchByCBModel("Name", "name"),
                new SearchByCBModel("Email", "email"),
                new SearchByCBModel("Phone", "phone"));

        searchByComboBox.getItems().addAll(searchValues);
        searchByComboBox.getSelectionModel().selectFirst();
        searchByComboBox.setCellFactory(new Callback<>() {

            @Override
            public ListCell<SearchByCBModel> call(ListView<SearchByCBModel> p) {

                return new ListCell<>() {

                    @Override
                    protected void updateItem(SearchByCBModel t, boolean bln) {
                        super.updateItem(t, bln);

                        if (t != null) {
                            setText(t.getSearchValue());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }

    private void searchSupplier() {

        ApplicationUtils.setTextFieldsEmpty(textFields);
        String query, searchValue;

        String searchByValue = searchByComboBox.getSelectionModel().getSelectedItem().getSqlValue();

        SearchSupplier supplier = new SearchSupplier();

        if (searchTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
            searchValue = searchTF.getText().trim();
            query = "select * from supplier where " + searchByValue + " = ?";
        } else {
            searchValue = "%" + searchTF.getText().trim() + "%";
            query = "select * from supplier where " + searchByValue + " like ?";
        }

        supplier.searchSupplierData(query, searchValue);

        if (!supplier.getSupplierData().isEmpty()) {
            supplierTable.setItems(supplier.getSupplierData());
        } else {
            supplierTable.getItems().clear();
        }

        updateButton.setDisable(true);
    }

    private void initPurchaseHistoryTable() {
        purchaseIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
        orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
        subTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
        taxColumn.setCellValueFactory(new PropertyValueFactory<>("tax"));
        totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
    }

    private void fillSupplierDataForm() {

        supplierTable.setOnMouseClicked(event -> {

            if (!supplierTable.getItems().isEmpty()) {

                if (event.getClickCount() == 1) {

                    suppSelectedTableRow = supplierTable.getSelectionModel().getSelectedItem();

                    if (suppSelectedTableRow != null) {

                        nameTF.setText(suppSelectedTableRow.getName());
                        addressTF.setText(suppSelectedTableRow.getAddress());
                        emailTF.setText(suppSelectedTableRow.getEmail());
                        phoneTF.setText(suppSelectedTableRow.getPhone());
                        cityTF.setText(suppSelectedTableRow.getCity());
                        stateTF.setText(suppSelectedTableRow.getState());
                        contactNameTF.setText(suppSelectedTableRow.getContactName());

                        searchInvoice(suppSelectedTableRow.getSupplierID());

                        addButton.setDisable(true);
                        updateButton.setDisable(false);
                        clearInvoiceHistoryForm();
                    }
                }
            }
        });
    }

    private void searchInvoice(int supplierID) {

        SearchSupplierInvoice invoice = new SearchSupplierInvoice();
        invoice.searchInvoiceData(supplierID);

        if(!invoice.getSupplierData().isEmpty()) {
            purchaseHistoryTable.setItems(invoice.getSupplierData());
        } else {
            purchaseHistoryTable.getItems().clear();
        }

    }

    private void clearInvoiceHistoryForm() {
        discountLabel.setText("Discount:");
        subTotalLabel.setText("Sub-Total:");
        taxesLabel.setText("Tax:");
        totalLabel.setText("Total:");
        invoiceSumListView.getItems().clear();
    }

    private void addSupplier() {

        if (!ApplicationUtils.isTextFieldEmpty(textFields)) {

            AddSupplier addSupplier = new AddSupplier();
            addSupplier.setName(nameTF.getText().trim());
            addSupplier.setAddress(addressTF.getText().trim());
            addSupplier.setEmail(emailTF.getText().trim().toLowerCase());
            addSupplier.setPhone(phoneTF.getText().trim());
            addSupplier.setCity(cityTF.getText().trim());
            addSupplier.setState(stateTF.getText().trim());
            addSupplier.setContactName(contactNameTF.getText().trim());

            if (addSupplier.add()) {

                DialogController.showDialog("Add Successful", "Supplier: " + addSupplier.getName() +
                        " was successfully added.", new Image(DialogController.SUCCESS_ICON));
                ApplicationUtils.setTextFieldsEmpty(textFields);
            } else {
                DialogController.showDialog("Add Failed", "Supplier: " + addSupplier.getName() +
                        " could not be added, please try again.", new Image(DialogController.ERROR_ICON));
            }
        }
    }

    private void updateSupplier() {

        if(suppSelectedTableRow != null) {

            Alert updateAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to make these changes?", ButtonType.YES, ButtonType.NO);
            updateAlert.showAndWait();

            if (updateAlert.getResult() == ButtonType.YES) {

                UpdateSupplier updateSupplier = new UpdateSupplier();

                int temp = suppSelectedTableRow.getSupplierID();
                updateSupplier.setSupplierID(suppSelectedTableRow.getSupplierID());
                updateSupplier.setName(nameTF.getText().trim());
                updateSupplier.setAddress(addressTF.getText().trim());
                updateSupplier.setEmail(emailTF.getText().trim().toLowerCase());
                updateSupplier.setPhone(phoneTF.getText().trim());
                updateSupplier.setCity(cityTF.getText().trim());
                updateSupplier.setState(stateTF.getText().trim());
                updateSupplier.setContactName(contactNameTF.getText().trim());

                if (updateSupplier.update()) {
                    DialogController.showDialog("Update Successful", "Supplier: " + updateSupplier.getName() +
                            " was successfully updated.", new Image(DialogController.SUCCESS_ICON));
                    ApplicationUtils.setTextFieldsEmpty(textFields);
                    updateButton.setDisable(true);
                } else {
                    DialogController.showDialog("Update Failed", "Supplier: " + updateSupplier.getName() +
                            " failed to update.", new Image(DialogController.ERROR_ICON));
                }
                supplierTable.refresh();
            }
        }
    }

}

