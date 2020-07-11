package controller;

import java.io.File;

import javafx.fxml.FXML;
import javafx.geometry.Rectangle2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import persistence.Reader;

public class SecondaryController {

	@FXML
	ImageView primaryImage;
	@FXML
	ImageView secondaryImage;
	@FXML
	ImageView logoImage;
	@FXML
	HBox box;
	@FXML
	Label nameCampain;
	@FXML
	Label typeCampain;
	@FXML
	VBox vBoxLeft;
	@FXML
	VBox vBoxRight;
	@FXML
	HBox hBoxRight;
	@FXML
	Canvas canvas;
	
	private Rectangle2D bounds;

	public void setInit(String fileName, Reader reader, Rectangle2D bounds) throws Exception{
		this.bounds = bounds;
		box.setMaxSize(getRealWidth(1920), getRealHeight(1040));
		box.setMinSize(getRealWidth(1920), getRealHeight(1040));
		vBoxLeft.setMaxSize(getRealWidth(1090), getRealHeight(1040));
		vBoxLeft.setMinSize(getRealWidth(1090), getRealHeight(1040));
		vBoxRight.setMaxSize(getRealWidth(880), getRealHeight(1040));
		vBoxRight.setMinSize(getRealWidth(880), getRealHeight(1040));
		primaryImage.setFitHeight(getRealHeight(1000));
		primaryImage.setFitWidth(getRealWidth(1000));
		canvas.setWidth(getRealWidth(1000));
		canvas.setHeight(getRealHeight(1000));
		canvas.setLayoutX(primaryImage.getLayoutX());
		canvas.setLayoutY(primaryImage.getLayoutY());
		hBoxRight.setMaxSize(getRealWidth(880), getRealHeight(300));
		hBoxRight.setMinSize(getRealWidth(800), getRealHeight(300));
		secondaryImage.setFitHeight(600);
		secondaryImage.setFitWidth(600);
		
		File background = new File("resource/application/sfondoSecondaryPane.png");
		Image image = new Image(background.toURI().toString());
		box.setBackground(new Background(new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
		File fileLogo = new File("resource/application/SfondoTitolo.png");
		Image logo = new Image(fileLogo.toURI().toString());
		logoImage.setImage(logo);
		nameCampain.setText(reader.readConf(fileName, "Title"));
		typeCampain.setText(reader.readConf(fileName, "Type"));
		
		
	}
	
	public Canvas getCanvas() throws Exception{
		return canvas;
	}
	
	public void setPrimaryImage(Image image) throws Exception{
		primaryImage.setImage(image);
	}

	public void setSecondaryImage(Image image) throws Exception{
		secondaryImage.setImage(image);
	}
	
	public void changeRotationImage(Image image, double rotate) throws Exception{
		if(primaryImage.getImage().equals(image)) {
			primaryImage.setRotate(rotate);
		} else
			secondaryImage.setRotate(rotate);
	}
	
	private double getRealWidth(double x) throws Exception{
		return (x * bounds.getWidth()) / 1920;
	}
	
	private double getRealHeight(double y) throws Exception{
		return (y * bounds.getHeight()) / 1040;
	}
	
	public ImageView getPrimaryImage() throws Exception{
		return primaryImage;
	}
	
	public Image getPrimaryImageName() throws Exception{
		return primaryImage.getImage();
	}
}
