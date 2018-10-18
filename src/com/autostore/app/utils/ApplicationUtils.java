package com.autostore.app.utils;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class ApplicationUtils {
	
	public static void setTextFieldsEmpty(TextField[] textFields) {
		for(TextField textField : textFields) {
			textField.clear();
		}
	}

	public static void setTextAreaEmpty(TextArea[] textAreas) {
		for(TextArea textArea : textAreas) {
			textArea.clear();
		}
	}

	public static boolean isTextFieldEmpty(TextField[] textFields) {
		for(TextField textField : textFields) {
			if(textField.getText().isEmpty()) {
				return true;
			}
		}
		return false;
	}

}
