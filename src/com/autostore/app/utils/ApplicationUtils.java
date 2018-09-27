package com.autostore.app.utils;

import javafx.scene.control.TextField;

public class ApplicationUtils {
	
	public static void setTextFieldsEmpty(TextField[] textFields) {
		for(TextField textField :textFields) {
			textField.clear();
		}
	}

}
