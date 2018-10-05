package com.autostore.app.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class InventoryController {

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
    private Button removeButton;

    @FXML
    private Button updateButton;

    @FXML
    private TableView<?> inventoryTable;

    @FXML
    private TableColumn<?, ?> partColumn;

    @FXML
    private TableColumn<?, ?> stockQtyColumn;

    @FXML
    private TableColumn<?, ?> addressColumn;

    @FXML
    private TableColumn<?, ?> descriptionColumn;

    @FXML
    private TextField searchTF;

    @FXML
    private Button searchButton;

    @FXML
    private ComboBox<?> searchTypeComboBox;

    @FXML
    private ComboBox<?> searchByComboBox;

}