package thread;

import model.DrawCanvas;
import persistence.LogFile;

public class ClearCircleCanvas extends Thread{
	
	private DrawCanvas drawCanvas;
	
	public ClearCircleCanvas(DrawCanvas drawCanvas) throws Exception{
		this.drawCanvas = drawCanvas;
	}
	
	public void run(){
		int count = 0;
		while(count < 4) {
			try {
				if(count == 3) {
					Thread.sleep(2000);
				} else {
					Thread.sleep(500);
				}
			} catch(InterruptedException e) {
				LogFile.writeLog(e.toString());
			}
			try {
				drawCanvas.clearPrimaryCanvas();
				drawCanvas.clearSecondaryCanvas();
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
			count++;
		}
	}
}
