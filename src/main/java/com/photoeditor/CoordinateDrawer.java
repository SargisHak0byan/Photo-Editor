package com.photoeditor;

import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class CoordinateDrawer {
	
	private boolean noExceptions;
	private boolean emptyFields;
	private boolean allTheSame;
	private Label errorLabel;
	
	public boolean finishedSuccessfully;
	
	public int[] display(){
		finishedSuccessfully = false;
		int[] coordinates = new int[4];
		Stage window = new Stage();
		window.initModality(Modality.APPLICATION_MODAL);
		window.setResizable(false);
		window.setTitle("Coordinate drawer");
		window.getIcons().add(new Image(getClass().getResource("windowIcon.png").toString()));
		
		//layout management
		GridPane mainPane = new GridPane();
		mainPane.setId("main-pane");
		setRowConstraints(mainPane, 10.0, VPos.CENTER);
		setRowConstraints(mainPane, 65.0, VPos.CENTER);
		setRowConstraints(mainPane, 25.0, VPos.TOP);
		setColumnConstraints(mainPane, 100.0, HPos.CENTER);
		Label messageLabel = new Label("Enter the coordinates of your shape");
		messageLabel.getStyleClass().add("highlight-label");
		mainPane.add(messageLabel, 0, 0);
		GridPane innerMainPane1 = new GridPane();
		mainPane.add(innerMainPane1, 0, 1);
		GridPane innerMainPane2 = new GridPane();
		mainPane.add(innerMainPane2, 0, 2);
		
		setRowConstraints(innerMainPane1, 100.0, VPos.CENTER);
		for(int i=0; i<2; i++) {
			setColumnConstraints(innerMainPane1, 100.0/2, HPos.CENTER);
		}
		GridPane startPane = new GridPane();
		innerMainPane1.add(startPane, 0, 0);
		GridPane endPane = new GridPane();
		innerMainPane1.add(endPane, 1, 0);
		
		for(int i = 0; i<3; i++) {
			setRowConstraints(startPane, 100.0/3, VPos.CENTER);
		}
		setColumnConstraints(startPane, 100.0, HPos.CENTER);
		Label startLabel = new Label("Start");
		startLabel.getStyleClass().add("highlight-label");
		startPane.add(startLabel, 0, 0);
		HBox startXBox = new HBox();
		startXBox.setAlignment(Pos.TOP_CENTER);
		startXBox.setSpacing(10);
		Label startXLabel = new Label("x:");
		startXLabel.setStyle("-fx-text-fill: #bfc1bd");
		TextField startXField = new TextField();
		startXField.setId("start-x-field");
		startXField.setMaxWidth(50);
		startXBox.getChildren().addAll(startXLabel,startXField);
		startPane.add(startXBox, 0, 1);
		HBox startYBox = new HBox();
		startYBox.setAlignment(Pos.TOP_CENTER);
		startYBox.setSpacing(10);
		Label startYLabel = new Label("y:");
		startYLabel.setStyle("-fx-text-fill: #bfc1bd");
		TextField startYField = new TextField();
		startYField.setId("start-y-field");
		startYField.setMaxWidth(50);
		startYBox.getChildren().addAll(startYLabel,startYField);
		startPane.add(startYBox, 0, 2);
		
		for(int i = 0; i<3; i++) {
			setRowConstraints(endPane, 100.0/3, VPos.CENTER);
		}
		setColumnConstraints(endPane, 100.0, HPos.CENTER);
		Label endLabel = new Label("End");
		endLabel.getStyleClass().add("highlight-label");
		endPane.add(endLabel, 0, 0);
		HBox endXBox = new HBox();
		endXBox.setAlignment(Pos.TOP_CENTER);
		endXBox.setSpacing(10);
		Label endXLabel = new Label("x:");
		endXLabel.setStyle("-fx-text-fill: #bfc1bd");
		TextField endXField = new TextField();
		endXField.setId("end-x-field");
		endXField.setMaxWidth(50);
		endXBox.getChildren().addAll(endXLabel,endXField);
		endPane.add(endXBox, 0, 1);
		HBox endYBox = new HBox();
		endYBox.setAlignment(Pos.TOP_CENTER);
		endYBox.setSpacing(10);
		Label endYLabel = new Label("y:");
		endYLabel.setStyle("-fx-text-fill: #bfc1bd");
		TextField endYField = new TextField();
		endYField.setId("end-y-field");
		endYField.setMaxWidth(50);
		endYBox.getChildren().addAll(endYLabel,endYField);
		endPane.add(endYBox, 0, 2);
		
		for(int i = 0; i<2; i++) {
			setRowConstraints(innerMainPane2, 100.0/2, VPos.TOP);
		}
		setColumnConstraints(innerMainPane2, 100.0, HPos.CENTER);
		errorLabel = new Label();
		errorLabel.setId("error-label");
		innerMainPane2.add(errorLabel, 0, 0);
		Button drawButton = new Button("Draw");
		innerMainPane2.add(drawButton, 0, 1);
		//end of layout management
		
		drawButton.setOnAction(e->{
			noExceptions = true;
			emptyFields = false;
			allTheSame = false;
			coordinates[0] = parseFromTextField(startXField);
			coordinates[1] = parseFromTextField(startYField);
			coordinates[2] = parseFromTextField(endXField);
			coordinates[3] = parseFromTextField(endYField);
			if(coordinates[0]==coordinates[2]&&coordinates[1]==coordinates[3]) {
				allTheSame = true;
			}
			if(noExceptions&&!allTheSame) {
				window.close();
				finishedSuccessfully=true;
			}else if(allTheSame&&noExceptions) {
				errorLabel.setText("Your coordinates can not be the same!");
			}
		});
		
		Scene scene = new Scene(mainPane,350,300);
		scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
		window.setScene(scene);
		
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				MainWindow.centerInsideMain(window);
				
			}
			
		});
		
		window.showAndWait();
		
		return coordinates;
	}
	
	private void setRowConstraints(GridPane gp, double percentHeight, VPos vAlignment) {
		RowConstraints rc = new RowConstraints();
		rc.setPercentHeight(percentHeight);
		rc.setValignment(vAlignment);
		gp.getRowConstraints().add(rc);
	}
	
	private void setColumnConstraints(GridPane gp, double percentWidth, HPos hAlignment) {
		ColumnConstraints cc = new ColumnConstraints();
		cc.setPercentWidth(percentWidth);
		cc.setHalignment(hAlignment);
		gp.getColumnConstraints().add(cc);
	}
	
	private int parseFromTextField(TextField tf) {
		int returnInt = 0;
		try {
			String value = tf.getText();
			char firstChar = value.charAt(0);
			NumberFormatException nb = new NumberFormatException();
			if(value.length()==1) {
				if(!(firstChar=='0'||firstChar=='1'||firstChar=='2'||firstChar=='3'||firstChar=='4'||firstChar=='5'||firstChar=='6'||
						firstChar=='7'||firstChar=='8'||firstChar=='9')) {
					throw nb;
				}
			}else {
				if(!(firstChar=='1'||firstChar=='2'||firstChar=='3'||firstChar=='4'||firstChar=='5'||firstChar=='6'||
						firstChar=='7'||firstChar=='8'||firstChar=='9')) {
					throw nb;
				}
			}
			for(int i =1; i< value.length();i++) {
				char currentChar = value.charAt(i);
				if(!(currentChar=='0'||currentChar=='1'||currentChar=='2'||currentChar=='3'||currentChar=='4'||
						currentChar=='5'||currentChar=='6'||currentChar=='7'||currentChar=='8'||currentChar=='9')) {
					throw nb;
				}
			}
			returnInt = Integer.parseInt(value);
		}catch(NumberFormatException e) {
			noExceptions=false;
			if(!emptyFields) {
				errorLabel.setText("Please enter valid coordinates!");
			}
		}catch(StringIndexOutOfBoundsException e2) {
			noExceptions=false;
			emptyFields=true;
			errorLabel.setText("You have empty coordinate field/s!");
		}
		return returnInt;
	}

}
