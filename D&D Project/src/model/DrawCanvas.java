package model;

import controller.SecondaryController;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import persistence.LogFile;
import thread.ClearCircleCanvas;
import thread.DrawCircleCanvas;

public class DrawCanvas {
	
	private Canvas primaryCanvas;
	private Canvas secondaryCanvas;
	private GraphicsContext primaryGraphic;
	private GraphicsContext secondaryGraphic;
	private SecondaryController controller;
	private ImageView imageTopContainer;

	public DrawCanvas(Canvas primaryCanvas, SecondaryController controller, ImageView imageTopContainer) throws Exception{
		this.primaryCanvas = primaryCanvas;
		this.controller = controller;
		this.imageTopContainer = imageTopContainer;
		primaryGraphic = primaryCanvas.getGraphicsContext2D();
		secondaryCanvas = controller.getCanvas();
		secondaryGraphic = secondaryCanvas.getGraphicsContext2D();
		secondaryGraphic.setLineWidth(6);
	}
	
	public void setImageClick() throws Exception{
		primaryCanvas.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent event) {
				if(event.getButton().equals(MouseButton.PRIMARY)) {
					if(event.getClickCount() == 2) {
						try {
							if(controller.getPrimaryImageName() == null)
								System.out.println("PRIMO");
						} catch (Exception e) {
							LogFile.writeLog(e.toString());
						}
						if(imageTopContainer.getImage() == null)
							System.out.println("SECONDO");
						try {
							if(controller.getPrimaryImageName().equals(imageTopContainer.getImage())) {
								double y  = (event.getY() - 15) + (15 - ((event.getY() - 15) * 1/28));
								new DrawCircleCanvas(DrawCanvas.this, imageTopContainer.getFitWidth(), imageTopContainer.getFitHeight(), event.getX() - 6, y, 20, event).start();
								new ClearCircleCanvas(DrawCanvas.this).start();
							}
						} catch (Exception e) {
							LogFile.writeLog(e.toString());
						}
					}
				}
			}
			
		});
	}

	public void clearPrimaryCanvas() throws Exception{
		primaryGraphic.clearRect(0, 0, primaryCanvas.getWidth(), primaryCanvas.getHeight());
	}

	public void drawPrimaryPoint(MouseEvent event, int h) throws Exception{
		primaryGraphic.strokeOval(event.getX() - (10 + ((h - 20) / 2)), event.getY() - (10  + ((h - 20) / 2)), h, h);
	}
	
	public void drawSecondaryPoint(double width, double height, double pointX, double pointY, int h) throws Exception{
		double x = (pointX * controller.getPrimaryImage().getFitWidth()) / width;
		double y = (pointY * controller.getPrimaryImage().getFitHeight()) / height;
		secondaryGraphic.strokeOval(x - ((h - 20) / 2), y - ((h - 20) / 2), h + 40, h + 40);
	}
	
	public void clearSecondaryCanvas() throws Exception{
		secondaryGraphic.clearRect(0, 0, secondaryCanvas.getWidth(), secondaryCanvas.getHeight());
	}
}
