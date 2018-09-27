package com.autostore.app.utils;

import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class WindowUtils {
	
	public static void closeWindow(Pane pane, Button button) {
		
		button.setOnAction(event -> {
			
			Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
			stage.close();
			
		});
	}
}
