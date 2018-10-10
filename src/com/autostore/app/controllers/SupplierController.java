package com.autostore.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
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
    private TextField contactTF;

    @FXML
    private Button addButton;

    @FXML
    private Button removeButton;

    @FXML
    private Button updateButton;

    @FXML
    private Button clearButton;

    @FXML
    private TableView<?> supplierTable;

    @FXML
    private TableColumn<?, ?> nameColumn;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> cityColumn;

    @FXML
    private TableColumn<?, ?> stateColumn;

    @FXML
    private TableColumn<?, ?> emailColumn;

    @FXML
    private TableColumn<?, ?> phoneColumn;

    @FXML
    private TableColumn<?, ?> contactColumn;

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
    private ComboBox<?> searchTypeComboBox;

    @FXML
    private ComboBox<?> searchByComboBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {



    }
}
