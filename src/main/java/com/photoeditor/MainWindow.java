package com.photoeditor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.URL;

import javafx.application.Application;
import javafx.application.HostServices;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class MainWindow extends Application {
	
	public static HostServices hostServices;
	public static Rectangle2D stageBounds;
	
	private static Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
	
	private ConfirmationBox cb = new ConfirmationBox();
	private File storage;
	
	public static void main(String args[]) {
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		try {
			hostServices = getHostServices();
			URL fxml = getClass().getResource("interface.fxml");
			Parent root = FXMLLoader.load(fxml);
			stage.setTitle("Picture Editor");
			Scene scene = new Scene(root,screenBounds.getWidth()/1.25,screenBounds.getHeight()/1.25);
			scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());
			stage.setScene(scene);
			stage.setMinWidth(675);
			stage.setMinHeight(475);
			stage.getIcons().add(new Image(getClass().getResource("windowIcon.png").toString()));
			stage.show();
			stage.setOnCloseRequest(e ->{
				e.consume();
				if(!Controller.saved) {
					int answer = cb.display("You have unsaved changes. Do you wish to close out anyway?");
					switch(answer) {
						case ConfirmationBox.YES:
							System.exit(0);
							break;
						case ConfirmationBox.NO:
							//
							break;
					}
				}else {
					System.exit(0);
				}
			});
			stageBounds = new Rectangle2D(stage.getX(), stage.getY(), stage.getWidth(),stage.getHeight());
			stage.widthProperty().addListener((property, oldVal, newVal) ->{
				stageBounds = new Rectangle2D(stage.getX(),stage.getY(),(double)newVal,stage.getHeight());
				serialize("widthValue.ser", (double)newVal);
			});
			stage.heightProperty().addListener((property, oldVal, newVal) ->{
				stageBounds = new Rectangle2D(stage.getX(),stage.getY(),stage.getWidth(),(double)newVal);
				serialize("heightValue.ser", (double)newVal);
			});
			stage.xProperty().addListener((property, oldVal, newVal) ->{
				stageBounds = new Rectangle2D((double)newVal, stage.getY(), stage.getWidth(), stage.getHeight());
				serialize("xValue.ser", (double)newVal);
			});
			stage.yProperty().addListener((property, oldVal, newVal) ->{
				stageBounds = new Rectangle2D(stage.getX(), (double)newVal, stage.getWidth(), stage.getHeight());
				serialize("yValue.ser", (double)newVal);
			});
			stage.maximizedProperty().addListener((property, oldVal, newVal) ->{
				try {
					FileOutputStream serializedFile = new FileOutputStream(storage.getAbsolutePath()+"/isMaximized.ser");
					ObjectOutputStream serializedOutput = new ObjectOutputStream(serializedFile);
					serializedOutput.writeObject(stage.isMaximized());
					serializedOutput.close();
					serializedFile.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			});
			
			String home = System.getenv("APPDATA");
			if(home==null) {
				home = (System.getProperty("user.home"));
			}
			storage = new File(home,".pictureEditor");
			storage.mkdir();
			
			Boolean isMaximized = null;
			try {
				FileInputStream deserializedFile = new FileInputStream(storage.getAbsolutePath()+"/isMaximized.ser");
				ObjectInputStream deserializedInput = new ObjectInputStream(deserializedFile);
				isMaximized = (boolean)deserializedInput.readObject();
				deserializedInput.close();
				deserializedFile.close();
			} catch (FileNotFoundException e) {
				//
			} catch (ClassNotFoundException e) {
				//
			} catch (IOException e) {
				//
			}
			
			if(isMaximized!=null&&isMaximized!=false) {
				stage.setMaximized(true);
			}else {
				if(deserialize("widthValue.ser")!=null) {
					stage.setWidth(deserialize("widthValue.ser"));
				}
				if(deserialize("heightValue.ser")!=null) {
					stage.setHeight(deserialize("heightValue.ser"));
				}
				if(deserialize("xValue.ser")!=null) {
					if(deserialize("xValue.ser")<0) {
						stage.setX(0);
						
					}else if(deserialize("xValue.ser")+stage.getWidth()>screenBounds.getMaxX()){
						stage.setX(screenBounds.getMaxX()-stage.getWidth());
					}else stage.setX(deserialize("xValue.ser"));
				}
				if(deserialize("yValue.ser")!=null) {
					if(deserialize("yValue.ser")<0) {
						stage.setY(0);
						
					}else if(deserialize("yValue.ser")+stage.getHeight()>screenBounds.getMaxY()){
						stage.setY(screenBounds.getMaxY()-stage.getHeight());
					}else stage.setY(deserialize("yValue.ser"));
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void centerInsideMain(Stage window) {
		double x = stageBounds.getMinX()+(stageBounds.getWidth()-window.getWidth())/2;
		double y = stageBounds.getMinY()+(stageBounds.getHeight()-window.getHeight())/2;
				
		if(x+window.getWidth()>screenBounds.getMaxX()) {
			x = x - (x+window.getWidth()-screenBounds.getMaxX());
		}else if(x<screenBounds.getMinX()) {
			x = screenBounds.getMinX();
		}
				
		if(y+window.getHeight()>screenBounds.getMaxY()) {
			y = y - (y+window.getHeight()-screenBounds.getMaxY());
		}else if(y < screenBounds.getMinY()) {
			y = screenBounds.getMinY();
		}
				
		window.setX(x);
		window.setY(y);
	}
	
	private void serialize(String fileName, double serializedValue){
		try {
			FileOutputStream serializedFile = new FileOutputStream(storage.getAbsolutePath()+"/"+fileName);
			ObjectOutputStream serializedOutput = new ObjectOutputStream(serializedFile);
			serializedOutput.writeObject(serializedValue);
			serializedOutput.close();
			serializedFile.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	private Double deserialize(String fileName) {
		Double deserializedValue = null;
		try {
			FileInputStream deserializedFile = new FileInputStream(storage.getAbsolutePath()+"/"+fileName);
			ObjectInputStream deserializedInput = new ObjectInputStream(deserializedFile);
			deserializedValue = (double)deserializedInput.readObject();
			deserializedInput.close();
			deserializedFile.close();
		} catch (FileNotFoundException e) {
			//
		} catch (ClassNotFoundException e) {
			//
		} catch (IOException e) {
			//
		}
		return deserializedValue;
	}

}
