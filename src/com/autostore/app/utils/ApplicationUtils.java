package com.autostore.app.utils;

import javafx.scene.control.TextField;

public class ApplicationUtils {
	
	public static void setTextFieldsEmpty(TextField[] textFields) {
		for(TextField textField : textFields) {
			textField.clear();
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

	public static boolean isEmailValid(String email) {

		String pattern = "^[\\w!#$%&’*+/=?`{|}~^-]+(?:\\.[\\w!#$%&’*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";

        return email.matches(pattern);

    }

}
