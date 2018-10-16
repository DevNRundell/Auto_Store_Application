package com.autostore.app.controllers;

import com.autostore.app.customer.SearchCustomerInvoice;
import com.autostore.app.model.SearchByCBModel;
import com.autostore.app.model.SupplierTableModel;
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
import java.util.ResourceBundle;


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
    private Button removeButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableView<SupplierTableModel> supplierTable;

    @FXML
    private TableColumn<SupplierTableModel, String> nameColumn;

    @FXML
    private TableColumn<SupplierTableModel, String> addressColumn;

    @FXML
    private TableColumn<SupplierTableModel, String> cityColumn;

    @FXML
    private TableColumn<SupplierTableModel, String> stateColumn;

    @FXML
    private TableColumn<SupplierTableModel, String> emailColumn;

    @FXML
    private TableColumn<SupplierTableModel, String> phoneColumn;

    @FXML
    private TableColumn<SupplierTableModel, String> contactNameColumn;

    @FXML
    private Tab purchaseHistoryTab;

    @FXML
    private ListView<?> invoiceSumListView;

    @FXML
    private Label discountLabel;

    @FXML
    private Label subTotalLabel;

    @FXML
    private Label taxesLabel;

    @FXML
    private Label totalLabel;

    @FXML
    private TableView<?> purchaseHistoryTable;

    @FXML
    private TableColumn<?, ?> purchaseIDColumn;

    @FXML
    private TableColumn<?, ?> orderDateColumn;

    @FXML
    private TableColumn<?, ?> discountColumn;

    @FXML
    private TableColumn<?, ?> subTotalColumn;

    @FXML
    private TableColumn<?, ?> taxColumn;

    @FXML
    private TableColumn<?, ?> totalColumn;

    @FXML
    private TextField searchTF;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> searchTypeComboBox;

    @FXML
    private ComboBox<SearchByCBModel> searchByComboBox;

    private TextField[] textFields;
    private SupplierTableModel suppSelectedTableRow;

    @Override
    public void initialize(URL arg0, ResourceBundle arg1) {

        textFields = new TextField[]{nameTF, addressTF, emailTF, phoneTF, cityTF, stateTF, contactNameTF};

        searchTypeComboBox.getItems().setAll("Absolute", "Relative");
        searchTypeComboBox.getSelectionModel().selectFirst();

        initSupplierTable();
        initSearchComboBox();
        //initInvoiceTable();
        fillSupplierDataForm();
        // fillInvoiceSummaryList();

        searchButton.setOnAction(event -> searchSupplier());
        clearButton.setOnAction(event -> clearForm());
        //addButton.setOnAction(event -> addSupplier());


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
        removeButton.setDisable(true);
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

                        // searchInvoice(suppSelectedTableRow.getSupplierID());

                        addButton.setDisable(true);
                        updateButton.setDisable(false);
                        //clearInvoiceHistoryForm();
                    }
                }
            }
        });
    }
}

//    private void addSupplier() {
//
//        if(!ApplicationUtils.isTextFieldEmpty(textFields)) {
//
//            AddSupplier addSupplier = new AddSupplier();
//            addSupplier.setName(nameTF.getText().trim());
//            addSupplier.setAddress(addressTF.getText().trim());
//            addSupplier.setEmail(emailTF.getText().trim().toLowerCase());
//            addSupplier.setPhone(phoneTF.getText().trim());
//            addSupplier.setCity(cityTF.getText().trim());
//            addSupplier.setState(stateTF.getText().trim());
//            addSupplier.setContactName(contactNameTF.getText().trim());
//
//            if(addSupplier.add()) {
//
//                DialogController.showDialog("Add Successful", "Supplier: " + addSupplier.getName() +
//                        " was successfully added.", new Image(DialogController.SUCCESS_ICON));
//                ApplicationUtils.setTextFieldsEmpty(textFields);
//            } else {
//                DialogController.showDialog("Add Failed", "Supplier: " + addSupplier.getName() +
//                        " could not be added, please try again.", new Image(DialogController.ERROR_ICON));
//            }
//        }
//    }

