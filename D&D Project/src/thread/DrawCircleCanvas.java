package thread;

import javafx.scene.input.MouseEvent;
import model.DrawCanvas;
import persistence.LogFile;

public class DrawCircleCanvas extends Thread{

	private DrawCanvas drawCanvas;
	private double width;
	private double height;
	private double pointX;
	private double pointY;
	private int h;
	private MouseEvent event;
	
	public DrawCircleCanvas(DrawCanvas drawCanvas, double width, double height, double pointX, double pointY, int h, MouseEvent event) throws Exception{
		this.drawCanvas = drawCanvas;
		this.width = width;
		this.height = height;
		this.pointX = pointX;
		this.pointY = pointY;
		this.h = h;
		this.event = event;
	}
	
	public void run() {
		int count = 0;
		while(count < 4) {
			try {
				drawCanvas.drawPrimaryPoint(event, h);
				drawCanvas.drawSecondaryPoint(width, height, pointX, pointY, h);
			} catch (Exception e1) {
				LogFile.writeLog(e1.toString());
			}
			h += 25;
			count++;
			try {
				Thread.sleep(600);
			} catch (InterruptedException e) {
				LogFile.writeLog(e.toString());
			}
		}
	}
}
