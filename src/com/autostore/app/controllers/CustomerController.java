package com.autostore.app.controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;
import com.autostore.app.customer.*;
import com.autostore.app.model.InvoiceListViewModel;
import com.autostore.app.model.SearchByCBModel;
import com.autostore.app.model.CustomerTableModel;
import com.autostore.app.model.InvoiceTableModel;
import com.autostore.app.utils.ApplicationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.util.Callback;

public class CustomerController implements Initializable {

    @FXML
    private Tab informationTab;

    @FXML
    private TextField firstNameTF;

    @FXML
    private TextField lastNameTF;

    @FXML
    private TextField addressTF;

    @FXML
    private TextField emailTF;

    @FXML
    private TextField phoneTF;

    @FXML
    private TextField cityTF;

    @FXML
    private TextField stateTF;

    @FXML
    private TextField zipCodeTF;

    @FXML
    private Button addButton;

    @FXML
    private Button updateButton;

    @FXML
	private Button clearButton;

    @FXML
    private TableView<CustomerTableModel> customerTable;

    @FXML
    private TableColumn<CustomerTableModel, String> firstNameColumn;

    @FXML
    private TableColumn<CustomerTableModel, String> lastNameColumn;

    @FXML
    private TableColumn<CustomerTableModel, String> addressColumn;

    @FXML
    private TableColumn<CustomerTableModel, String> emailColumn;

    @FXML
    private TableColumn<CustomerTableModel, String> phoneColumn;

    @FXML
    private TableColumn<CustomerTableModel, String> cityColumn;

    @FXML
    private TableColumn<CustomerTableModel, String> stateColumn;

    @FXML
    private TableColumn<CustomerTableModel, String> zipCodeColumn;

    @FXML
    private Tab purchaseHistoryTab;

	@FXML
	private ListView<InvoiceListViewModel> invoiceSumListView;

	@FXML
	private Label invoiceDiscountLabel;

	@FXML
	private Label invoiceSubTotalLabel;

	@FXML
	private Label invoiceTaxesLabel;

	@FXML
	private Label invoiceTotalLabel;

    @FXML
    private TableView<InvoiceTableModel> invoiceTable;

    @FXML
    private TableColumn<InvoiceTableModel, Integer> invoiceIDColumn;

    @FXML
    private TableColumn<InvoiceTableModel, Date> orderDateColumn;

    @FXML
    private TableColumn<InvoiceTableModel, Double> discountColumn;

    @FXML
    private TableColumn<InvoiceTableModel, Double> subTotalColumn;

    @FXML
    private TableColumn<InvoiceTableModel, Double> taxColumn;

    @FXML
    private TableColumn<InvoiceTableModel, Double> totalColumn;

    @FXML
    private TextField searchTF;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<String> searchTypeComboBox;
    
    @FXML
    private ComboBox<SearchByCBModel> searchByComboBox;

	@FXML
	private Tab newOrderTab;
    private TextField[] textFields;
    private CustomerTableModel custSelectedTableRow;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		textFields = new TextField[]{firstNameTF, lastNameTF, addressTF, emailTF, phoneTF, cityTF, stateTF, zipCodeTF};
		
		searchTypeComboBox.getItems().setAll("Relative", "Absolute");
		searchTypeComboBox.getSelectionModel().selectFirst();
		
		initCustomerTable();
		initInvoiceTable();
		initSearchByComboBox();
        fillCustomerDataForm();
        fillInvoiceSummaryList();
        setInvoiceListViewCustomCell();
		
		searchButton.setOnAction(event -> searchCustomer());
		updateButton.setOnAction(event -> updateCustomer());
		clearButton.setOnAction(event -> clearCustomerForm());
		addButton.setOnAction(event -> addCustomer());



	}

	private void clearCustomerForm() {
	    ApplicationUtils.setTextFieldsEmpty(textFields);
	    updateButton.setDisable(true);
	    addButton.setDisable(false);
	    custSelectedTableRow = null;
	    clearInvoiceHistoryForm();
	    invoiceTable.getItems().clear();
    }

    private void clearInvoiceHistoryForm() {
        invoiceDiscountLabel.setText("Discount:");
        invoiceSubTotalLabel.setText("Sub-Total:");
        invoiceTaxesLabel.setText("Tax:");
        invoiceTotalLabel.setText("Total:");
        invoiceSumListView.getItems().clear();
    }

    private void addCustomer() {

        if(!ApplicationUtils.isTextFieldEmpty(textFields)) {

            AddCustomer addCustomer = new AddCustomer();
            addCustomer.setFirstName(firstNameTF.getText().trim());
            addCustomer.setLastName(lastNameTF.getText().trim());
            addCustomer.setAddress(addressTF.getText().trim());
            addCustomer.setEmail(emailTF.getText().trim().toLowerCase());
            addCustomer.setPhone(phoneTF.getText().trim());
            addCustomer.setCity(cityTF.getText().trim());
            addCustomer.setState(stateTF.getText().trim());
            addCustomer.setZipCode(zipCodeTF.getText().trim());

            if(addCustomer.add()) {
                DialogController.showDialog("Add Successful", "Customer: " + addCustomer.getFirstName() +
                                            " was successfully added.", new Image(DialogController.SUCCESS_ICON));
                ApplicationUtils.setTextFieldsEmpty(textFields);
            } else {
                DialogController.showDialog("Add Failed", "Customer: " + addCustomer.getFirstName() +
                                            " could not be added, please try again.", new Image(DialogController.ERROR_ICON));
            }
        }
    }

    private void fillInvoiceSummaryList() {

	    invoiceTable.setOnMouseClicked(event ->  {

	        if(!invoiceTable.getItems().isEmpty()) {

	            if(event.getClickCount() == 1) {

	                InvoiceTableModel invSelectedTableRow = invoiceTable.getSelectionModel().getSelectedItem();

	                if(invSelectedTableRow != null) {

                        CustomerInvoiceData invoiceData = new CustomerInvoiceData();
                        invoiceData.searchInvoiceItemData(invSelectedTableRow.getOrderID());

                        if(invoiceData.getInvoiceData() != null) {

                            invoiceSumListView.setItems(invoiceData.getInvoiceData());

                            invoiceDiscountLabel.setText("Discount: $" + String.valueOf(invSelectedTableRow.getDiscount()));
                            invoiceSubTotalLabel.setText("Sub-Total: $" + String.valueOf(invSelectedTableRow.getSubTotal()));
                            invoiceTaxesLabel.setText("Tax: $" + String.valueOf(invSelectedTableRow.getTax()));
                            invoiceTotalLabel.setText("Total: $" + String.valueOf(invSelectedTableRow.getTotal()));
                        }
                    }
                }
            }
        });
    }

	private void fillCustomerDataForm() {
		
		customerTable.setOnMouseClicked(event -> {
				
			if(!customerTable.getItems().isEmpty()) {
				
				if(event.getClickCount() == 1) {
					
					custSelectedTableRow = customerTable.getSelectionModel().getSelectedItem();

					if(custSelectedTableRow != null) {

                        firstNameTF.setText(custSelectedTableRow.getFirstName());
                        lastNameTF.setText(custSelectedTableRow.getLastName());
                        addressTF.setText(custSelectedTableRow.getAddress());
                        emailTF.setText(custSelectedTableRow.getEmail());
                        phoneTF.setText(custSelectedTableRow.getPhone());
                        cityTF.setText(custSelectedTableRow.getCity());
                        stateTF.setText(custSelectedTableRow.getState());
                        zipCodeTF.setText(custSelectedTableRow.getZipCode());

                        searchInvoice(custSelectedTableRow.getCustomer_id());
                    }
				}
			}
			addButton.setDisable(true);
			updateButton.setDisable(false);
			clearInvoiceHistoryForm();
		});
	}
	
	private void updateCustomer() {

	    if(custSelectedTableRow != null) {

            Alert updateAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to make these changes?", ButtonType.YES, ButtonType.NO);
            updateAlert.showAndWait();

            if (updateAlert.getResult() == ButtonType.YES) {

                UpdateCustomer updateCustomer = new UpdateCustomer();

                updateCustomer.setCustomerID(custSelectedTableRow.getCustomer_id());
                updateCustomer.setFirstName(firstNameTF.getText().trim());
                updateCustomer.setLastName(lastNameTF.getText().trim());
                updateCustomer.setAddress(addressTF.getText().trim());
                updateCustomer.setEmail(emailTF.getText().trim().toLowerCase());
                updateCustomer.setPhone(phoneTF.getText().trim());
                updateCustomer.setCity(cityTF.getText().trim());
                updateCustomer.setState(stateTF.getText().trim());
                updateCustomer.setZipCode(zipCodeTF.getText().trim());

                if (updateCustomer.update()) {
                    DialogController.showDialog("Update Successful", "Customer: " + updateCustomer.getFirstName() +
                            " was successfully updated.", new Image(DialogController.SUCCESS_ICON));
                    ApplicationUtils.setTextFieldsEmpty(textFields);
                    updateButton.setDisable(true);
                } else {
                    DialogController.showDialog("Update Failed", "Customer: " + updateCustomer.getFirstName() +
                            " failed to update.", new Image(DialogController.ERROR_ICON));
                }

                customerTable.refresh();
            }
        }
	}
	
	private void searchInvoice(int customerID) {

        CustomerInvoice invoice = new CustomerInvoice();
        invoice.searchInvoiceData(customerID);

        if(!invoice.getCustomerData().isEmpty()) {
            invoiceTable.setItems(invoice.getCustomerData());
        } else {
            invoiceTable.getItems().clear();
        }
		
	}

	private void searchCustomer() {
		
		ApplicationUtils.setTextFieldsEmpty(textFields);
		String query, searchValue;
		
		String searchByValue = searchByComboBox.getSelectionModel().getSelectedItem().getSqlValue();

		if(searchTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
			searchValue = "%" + searchTF.getText().trim() + "%";
            query = "select * from customer_info where " + searchByValue + " like ?";
		} else {
            searchValue = searchTF.getText().trim();
            query = "select * from customer_info where " + searchByValue + " = ?";
		}

        SearchCustomer customer = new SearchCustomer();
		customer.searchCustomerData(query, searchValue);
				
		if(!customer.getCustomerData().isEmpty()) {
			customerTable.setItems(customer.getCustomerData());
		} else {
			customerTable.getItems().clear();
		}
		
		updateButton.setDisable(true);
        invoiceTable.getItems().clear();
		clearInvoiceHistoryForm();
	}
	
	private void initCustomerTable() {
		
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));
        zipCodeColumn.setCellValueFactory(new PropertyValueFactory<>("zipCode"));

	}
	
	private void initInvoiceTable() {
		invoiceIDColumn.setCellValueFactory(new PropertyValueFactory<>("orderID"));
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
		subTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
		taxColumn.setCellValueFactory(new PropertyValueFactory<>("tax"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
	}

	//Populates searchBy combo-box with string values visible to the user while masking
	//the sql values associated with each string value
	private void initSearchByComboBox() {
		
		ObservableList<SearchByCBModel> searchByValues = FXCollections.observableArrayList(
				new SearchByCBModel("First Name", "first_name"),
				new SearchByCBModel("Last Name", "last_name"),
				new SearchByCBModel("Address", "address"),
				new SearchByCBModel("Email", "email"),
				new SearchByCBModel("Phone", "phone"),
				new SearchByCBModel("City", "city"),
				new SearchByCBModel("State", "state"),
                new SearchByCBModel("Zip Code", "zip_code"));

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

	private void setInvoiceListViewCustomCell() {

        invoiceSumListView.setCellFactory(new Callback<>() {

            @Override
            public ListCell<InvoiceListViewModel> call(ListView<InvoiceListViewModel> param) {
                return new ListCell<>() {

                    @Override
                    protected void updateItem(InvoiceListViewModel item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText("Item: " + item.getName() + "\n" +
                                    "Description: " + item.getDescription() + "\n" +
                                    "Quantity: " + item.getQtyOrdered());
                        } else {
                            setText(null);
                        }
                    }
                };
            }
        });
    }
}



































