package com.autostore.app.controllers;

import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import com.autostore.app.customer.SearchCustomer;
import com.autostore.app.customer.UpdateCustomer;
import com.autostore.app.model.SearchByCBModel;
import com.autostore.app.model.CustomerTableModel;
import com.autostore.app.model.InvoiceListModel;
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
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button updateButton;

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
    private Tab purchaseHistoryTab;

    @FXML
    private ListView<InvoiceListModel> invoiceSumListView;

    @FXML
    private Label discountLabel;

    @FXML
    private Label subTotalLabel;

    @FXML
    private Label taxesLabel;

    @FXML
    private Label totalLabel;

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
    private TextField[] textFields;
    private CustomerTableModel selectedTableRow;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		textFields = new TextField[]{firstNameTF, lastNameTF, addressTF, emailTF, phoneTF, cityTF, stateTF};
		
		searchTypeComboBox.getItems().setAll("Absolute Search", "Relative Search");
		searchTypeComboBox.getSelectionModel().selectFirst();
		
		initCustomerTable();
		initInvoiceTable();
		fillCustomerDataForm();
		initSearchByComboBox();
		
		searchButton.setOnAction(event -> searchCustomer());
		updateButton.setOnAction(event -> updateCustomer());
	}
	
	private void fillCustomerDataForm() {
		
		customerTable.setOnMouseClicked(event -> {
				
			if(!customerTable.getItems().isEmpty()) {
				
				if(event.getClickCount() == 1) {
					
					selectedTableRow = customerTable.getSelectionModel().getSelectedItem();

					if(selectedTableRow != null) {

                        firstNameTF.setText(selectedTableRow.getFirstName());
                        lastNameTF.setText(selectedTableRow.getLastName());
                        addressTF.setText(selectedTableRow.getAddress());
                        emailTF.setText(selectedTableRow.getEmail());
                        phoneTF.setText(selectedTableRow.getPhone());
                        cityTF.setText(selectedTableRow.getCity());
                        stateTF.setText(selectedTableRow.getState());

                        searchInvoice(selectedTableRow.getCustomer_id());
                    }
				}
			}
			
			updateButton.setDisable(false);
		});
	}
	
	private void updateCustomer() {
		
		Alert updateAlert = new Alert(AlertType.CONFIRMATION, "Are you sure you want to make these changes?", ButtonType.YES, ButtonType.NO);
		updateAlert.showAndWait();
		
		if(updateAlert.getResult() == ButtonType.YES) {
					
			UpdateCustomer updateCustomer = new UpdateCustomer();
			
			updateCustomer.setCustomerID(selectedTableRow.getCustomer_id());
			updateCustomer.setFirstName(firstNameTF.getText().trim());
			updateCustomer.setLastName(lastNameTF.getText().trim());
			updateCustomer.setAddress(addressTF.getText().trim());
			updateCustomer.setEmail(emailTF.getText().trim());
			updateCustomer.setPhone(phoneTF.getText().trim());
			updateCustomer.setCity(cityTF.getText().trim());
			updateCustomer.setState(stateTF.getText().trim());
			
			if(updateCustomer.update()) {
                DialogController.showDialog("Update Successful", "Customer: " + updateCustomer.getFirstName() +
                        " was successfully updated.", new Image(DialogController.SUCCESS_ICON));
                ApplicationUtils.setTextFieldsEmpty(textFields);
			} else {
                DialogController.showDialog("Update Failed", "Customer: " + updateCustomer.getFirstName() +
                        " failed to update.", new Image(DialogController.ERROR_ICON));
            }
			
			customerTable.refresh();
			
		}
	}
	
	private void searchInvoice(int customerID) {
		
		
		
		
	}

	private void searchCustomer() {
		
		ApplicationUtils.setTextFieldsEmpty(textFields);
		String query, searchValue;
		
		String searchByValue = searchByComboBox.getSelectionModel().getSelectedItem().getSqlValue();
			
		SearchCustomer customer = new SearchCustomer();

		if(searchTypeComboBox.getSelectionModel().getSelectedIndex() == 0) {
			searchValue = searchTF.getText().trim();
			query = "select * from customer where " + searchByValue + " = ?";
		} else {
			searchValue = "%" + searchTF.getText().trim() + "%";
			query = "select * from customer where " + searchByValue + " like ?";
		}
			
		customer.searchCustomerData(query, searchValue);
				
		if(!customer.getCustomerData().isEmpty()) {
			customerTable.setItems(customer.getCustomerData());
		} else {
			customerTable.getItems().clear();
		}
		
		updateButton.setDisable(true);
	}
	
	private void initCustomerTable() {
		
		firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));
		lastNameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));
		addressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
		emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
		phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
		cityColumn.setCellValueFactory(new PropertyValueFactory<>("city"));
		stateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

	}
	
	private void initInvoiceTable() {
		
		invoiceIDColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceID"));
		orderDateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
		discountColumn.setCellValueFactory(new PropertyValueFactory<>("discount"));
		subTotalColumn.setCellValueFactory(new PropertyValueFactory<>("subTotal"));
		taxColumn.setCellValueFactory(new PropertyValueFactory<>("tax"));
		totalColumn.setCellValueFactory(new PropertyValueFactory<>("total"));
	}
	
	private void initSearchByComboBox() {
		
		ObservableList<SearchByCBModel> searchByValues = FXCollections.observableArrayList(
				new SearchByCBModel("First Name", "first_name"),
				new SearchByCBModel("Last Name", "last_name"),
				new SearchByCBModel("Address", "address"),
				new SearchByCBModel("Email", "email"),
				new SearchByCBModel("Phone", "phone"),
				new SearchByCBModel("City", "city"),
				new SearchByCBModel("State", "state"));

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



































