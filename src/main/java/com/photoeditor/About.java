package com.photoeditor;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class About {
	
	public void display() {
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.initStyle(StageStyle.UTILITY);
		window.setTitle("About");
		
		ImageView logo = new ImageView(new Image(getClass().getResource("windowIcon.png").toString()));
		Label titleLabel = new Label("Picture Editor for Java exam");
		titleLabel.setPadding(new Insets(5,0,10,0));
		titleLabel.getStyleClass().add("title-label");
		Label versionLabel = new Label("Software Version 1.0.0");
		Label licenseLabel = new Label("Licence key: XXXXX-XXXXX-XXXXX");
		licenseLabel.setPadding(new Insets(0,0,5,0));
		Label developersLabel = new Label("Developer(s):");
		Hyperlink developersHyperlink = new Hyperlink("Sargis Hakobyan");
		HBox developersBox = new HBox();
		developersBox.setAlignment(Pos.CENTER);
		developersBox.getChildren().addAll(developersLabel,developersHyperlink);
		HBox creditsBox = new HBox();
		creditsBox.setAlignment(Pos.CENTER);
		Label copyrightLabel = new Label("Copyright � 2023 Sargis Hakobyan. All rights reserved.");
		copyrightLabel.setPadding(new Insets(50,0,0,0));
		copyrightLabel.setOpacity(0.6);
		
		Insets insets = new Insets(20);
		VBox mainBox = new VBox();
		mainBox.setPadding(insets);
		mainBox.setAlignment(Pos.TOP_CENTER);
		mainBox.getChildren().add(logo);
		mainBox.getChildren().addAll(titleLabel,versionLabel,licenseLabel,developersBox,creditsBox,copyrightLabel);
		
		Scene scene = new Scene(mainBox,400,300);
		scene.getStylesheets().add(getClass().getResource("secondaryStyle.css").toExternalForm());
		window.setResizable(false);
		window.setScene(scene);
		
		Platform.runLater(new Runnable(){
			@Override
			public void run() {
				MainWindow.centerInsideMain(window);
			}
		});
		
		window.showAndWait();
	}

}
