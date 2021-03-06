package com.autostore.app.controllers;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;

public class MainApplicationController implements Initializable {

    @FXML
    private Button customerButton;

    @FXML
    private Button supplierButton;

    @FXML
    private Button inventoryButton;

    @FXML
    private Pane contentPane;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		//Preload Customer Window
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/autostore/app/fxml/customer_window.fxml"));
			Region child = loader.load();
			contentPane.getChildren().add(child);
			child.prefHeightProperty().bind(contentPane.heightProperty());
			child.prefWidthProperty().bind(contentPane.widthProperty());

		} catch(Exception e) {
			e.printStackTrace();
		}

		customerButton.setOnAction(event -> {
			
			try {
				
				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/autostore/app/fxml/customer_window.fxml"));
				Region child = loader.load();
				contentPane.getChildren().add(child);
				child.prefHeightProperty().bind(contentPane.heightProperty());
				child.prefWidthProperty().bind(contentPane.widthProperty());

			} catch(Exception e) {
					e.printStackTrace();
			}
			
		});
		
		supplierButton.setOnAction(event -> {
			try {

				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/autostore/app/fxml/supplier_window.fxml"));
				Region child = loader.load();
				contentPane.getChildren().add(child);
				child.prefHeightProperty().bind(contentPane.heightProperty());
				child.prefWidthProperty().bind(contentPane.widthProperty());

			} catch(Exception e) {
				e.printStackTrace();
			}
		});
		
		inventoryButton.setOnAction(event -> {
			try {

				FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/autostore/app/fxml/inventory_window.fxml"));
				Region child = loader.load();
				contentPane.getChildren().add(child);
				child.prefHeightProperty().bind(contentPane.heightProperty());
				child.prefWidthProperty().bind(contentPane.widthProperty());

			} catch(Exception e) {
				e.printStackTrace();
			}
		});
	}
}
