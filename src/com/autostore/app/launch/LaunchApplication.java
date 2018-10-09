package com.autostore.app.launch;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class LaunchApplication extends Application{

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) {

		try {
			
			FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("com/autostore/app/fxml/main_window.fxml"));
			Pane root = loader.load();
			Scene scene = new Scene(root);			
			primaryStage.setScene(scene);
			primaryStage.setTitle("Auto Store Management System");
			primaryStage.getIcons().add(new Image("com/autostore/app/images/car_icon.png"));
			primaryStage.setResizable(true);
			primaryStage.setMaximized(true);
            primaryStage.show();

		} catch(Exception e) {
				e.printStackTrace();
		}
	}
}
