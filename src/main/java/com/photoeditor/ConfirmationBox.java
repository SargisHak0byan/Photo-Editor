package com.photoeditor;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmationBox {
	
	private int answer;
	public static final int NO = 0;
	public static final int YES = 1;
	
	public int display(String message) {
		answer = NO;
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setTitle("File not saved");
		window.getIcons().add(new Image(getClass().getResource("windowIcon.png").toString()));
		
		ImageView confirmationImage = new ImageView(new Image(getClass().getResource("confirmationImage.png").toString()));
		Label messageLabel = new Label();
		messageLabel.setText(message);
		messageLabel.setWrapText(true);
		Button yesButton = new Button("Yes");
		yesButton.setMinWidth(100);
		yesButton.setMinHeight(25);
		yesButton.setMaxWidth(100);
		yesButton.setMaxHeight(25);
		Button noButton = new Button("No");
		noButton.setMinWidth(100);
		noButton.setMinHeight(25);
		noButton.setMaxWidth(100);
		noButton.setMaxHeight(25);
		
		yesButton.setOnAction(e ->{
			answer = YES;
			window.close();
		});
		
		noButton.setOnAction(e ->{
			answer = NO;
			window.close();
		});
		
		Insets boxInsets = new Insets(10);
		VBox mainBox = new VBox();
		mainBox.setPadding(boxInsets);
		mainBox.setAlignment(Pos.CENTER);
		HBox messageBox = new HBox();
		messageBox.setPadding(boxInsets);
		messageBox.setSpacing(10);
		messageBox.setAlignment(Pos.CENTER_LEFT);
		messageBox.getChildren().add(confirmationImage);
		messageBox.getChildren().add(messageLabel);
		mainBox.getChildren().add(messageBox);
		HBox buttonBox = new HBox();
		buttonBox.setPadding(boxInsets);
		buttonBox.setSpacing(10);
		buttonBox.setAlignment(Pos.CENTER_RIGHT);
		buttonBox.getChildren().add(yesButton);
		buttonBox.getChildren().add(noButton);
		mainBox.getChildren().add(buttonBox);
		Scene scene = new Scene(mainBox,450,120);
		scene.getStylesheets().add(getClass().getResource("secondaryStyle.css").toExternalForm());
		window.setResizable(false);
		window.setScene(scene);
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				MainWindow.centerInsideMain(window);
			}
			
		});
		
		window.showAndWait();
		
		return answer;
	}

}
