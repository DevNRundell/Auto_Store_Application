package com.autostore.app.controllers;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.autostore.app.utils.WindowUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DialogController implements Initializable {

    @FXML
    private AnchorPane anchorPane;
	@FXML
    private ImageView iconImageView;
    @FXML
    private Label errorTitleLabel;
    @FXML
    private TextArea errorMessageTextArea;

    @FXML
    private Button okayButton;
    public final static String ERROR_ICON = "com/autostore/app/images/error_icon.png";
    public final static String SUCCESS_ICON = "com/autostore/app/images/success_icon.png";
    public final static String INFO_ICON = "com/autostore/app/images/error_icon";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		WindowUtils.closeWindow(anchorPane, okayButton);
	}
	
	public static void showDialog(String title, String message, Image image) {
		
		try {
			
			FXMLLoader loader = new FXMLLoader(DialogController.class.getClassLoader().getResource("com/autostore/app/fxml/dialog_window.fxml"));
			Pane root = loader.load();
			Scene scene = new Scene(root);	
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setAlwaysOnTop(true);
			stage.show();
			
			DialogController controller = loader.getController();
			controller.errorTitleLabel.setText(title);
			controller.errorMessageTextArea.setText(message);
			controller.iconImageView.setImage(image);

		} catch(Exception e) {
				e.printStackTrace();
		}
	}
	
	public static void showDialog(String title, ArrayList<String> messages, Image image) {
		
		StringBuilder stringBuilder = new StringBuilder();
		
		try {
			
			FXMLLoader loader = new FXMLLoader(DialogController.class.getClassLoader().getResource("com/autostore/app/fxml/dialog_window.fxml"));
			Pane root = loader.load();
			Scene scene = new Scene(root);	
			Stage stage = new Stage();
			stage.initStyle(StageStyle.TRANSPARENT); 
			stage.setScene(scene);
			stage.setResizable(false);
			stage.setAlwaysOnTop(true);
			stage.show();
			
			DialogController controller = loader.getController();
			controller.errorTitleLabel.setText(title);

			for(String message: messages) {
				stringBuilder.append("\u2022").append(message).append("\n");
			}
			
			controller.errorMessageTextArea.setText(stringBuilder.toString());
			controller.iconImageView.setImage(image);

		} catch(Exception e) {
				e.printStackTrace();
		}
	}
	
	public static void showDatabaseError() {
		String title = "Database Error";
		String message = "Were sorry. There seems to be an issue. Please try again later.";		
		showDialog(title, message, new Image(DialogController.ERROR_ICON));
	}
	
	public static void showConnectionError() {
		String title = "Connection Error";
		String message = "Were sorry. There seems to be an issue with the connection. Please try again later.";
		showDialog(title, message, new Image(DialogController.ERROR_ICON));
	}
}
























































