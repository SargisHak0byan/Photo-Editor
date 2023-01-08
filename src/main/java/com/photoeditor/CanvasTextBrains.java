package com.photoeditor;

import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;

public class CanvasTextBrains {
	
	private double lastX;
	private double lastY;
	private int caretPos;
	private boolean mouseWasDragged;
	private WritableImage firstSnapshotImage;
	
	private Controller controll;
	
	public EventHandler<MouseEvent> mousePressed; 
	public EventHandler<MouseEvent> mouseReleased;
	public EventHandler<MouseEvent> mouseMoved;
	public EventHandler<MouseEvent> mouseDragged;
	
	
	public CanvasTextBrains(Controller controller){
		controll = controller;
		
		mouseMoved = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				if(controll.canvasText.getCursor()!=Cursor.MOVE) {
					controll.canvasText.setCursor(Cursor.MOVE);
				}
			}
			
		};
		mousePressed = new EventHandler<MouseEvent>() {
			
			@Override
			public void handle(MouseEvent e) {
				lastX = e.getX();
				lastY = e.getY();
				mouseWasDragged = false;
				caretPos = controll.canvasText.getCaretPosition();
				if(!controll.firstSnapshot) {
					controll.gc.drawImage(controll.rubberBandSnapshot, 0, 0);
					controll.canvasText.setStyle("-fx-highlight-fill: transparent; -fx-highlight-text-fill: " +
							controll.getColorHex(controll.colorPicker.getValue()) + "; " + "-fx-text-fill: " + 
							controll.getColorHex(controll.colorPicker.getValue()) + "; " + "-fx-display-caret: false;");
					firstSnapshotImage = controll.canvasPane.snapshot(controll.sParameters, null);
					controll.gc.setLineDashes(4);
					controll.rubberBand(controll.rubberBandBounds.getMinX(), controll.rubberBandBounds.getMinY(),
							controll.rubberBandBounds.getMaxX(), controll.rubberBandBounds.getMaxY());
					controll.gc.setLineDashes(null);
				}
				controll.canvasText.setStyle("-fx-cursor: closed-hand; -fx-text-fill: " + controll.getColorHex(controll.colorPicker.getValue()) + ";" );
			}
		};
		mouseReleased = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				controll.canvasText.deselect();
				if(mouseWasDragged) {
					if(!controll.firstSnapshot) {
						controll.addNewSnapshot(firstSnapshotImage);
						controll.firstSnapshot = true;
					}
					controll.addNewSnapshot();
				}
				controll.gc.setLineDashes(4);
				controll.rubberBand(controll.rubberBandBounds.getMinX(), controll.rubberBandBounds.getMinY(),
						controll.rubberBandBounds.getMaxX(), controll.rubberBandBounds.getMaxY());
				controll.gc.setLineDashes(null);
			}
			
		};
		mouseDragged = new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent e) {
				mouseWasDragged = true;
				controll.canvasText.positionCaret(caretPos);
				controll.gc.drawImage(controll.rubberBandSnapshot, 0, 0);
				double minX = e.getX()-lastX+controll.rubberBandBounds.getMinX();
				double minY = e.getY()-lastY+controll.rubberBandBounds.getMinY();
				controll.rubberBandBounds = new Rectangle2D(minX,minY,controll.rubberBandBounds.getWidth(),controll.rubberBandBounds.getHeight());
				controll.canvasText.setLayoutX(minX);
				controll.canvasText.setLayoutY(minY);
			}
			
		};
	}
}
