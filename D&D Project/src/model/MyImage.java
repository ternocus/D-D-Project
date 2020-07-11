package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import controller.SecondaryController;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import persistence.LogFile;
import persistence.Reader;
import javafx.stage.FileChooser.ExtensionFilter;

public class MyImage {
	
	private HBox imageBox;
	private ScrollPane imageList;
	private ScrollPane playerList;
	private String fileName;
	private ImageView imageTopContainer;
	private String imageName;
	private TextArea text;
	private Reader reader;
	private SecondaryController controller;
	private HBox playerBox;
	
	public MyImage(ScrollPane imageList, String fileName, ImageView imageTopContainer, TextArea text, Reader reader, SecondaryController controller, ScrollPane playerList) throws Exception{
		this.imageList = imageList;
		this.fileName = fileName;
		this.imageTopContainer = imageTopContainer;
		this.text = text;
		this.reader = reader;
		this.controller = controller;
		this.playerList = playerList;
	}
	
	public void uploadImage() throws Exception{
		imageBox = new HBox();
		imageBox.setAlignment(Pos.CENTER_LEFT);
		imageBox.setSpacing(5);
		imageBox.setMaxHeight(imageList.getHeight() - 10);
		imageBox.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		File dir = new File("resource/" + fileName + "/Mappe");
		for(File file : dir.listFiles()) {
			File temp = new File("resource/" + fileName + "/Mappe/" + file.getName());
			if(temp.exists()) {
				Image image = new Image(file.toURI().toString());
				ImageView imageView = new ImageView();
				imageView.setImage(image);
				imageView.setFitHeight(125);
				imageView.setFitWidth(125);
				imageView.setPreserveRatio(true);
				imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						imageTopContainer.setImage(image);
						imageTopContainer.setRotate(0);
						imageName = file.getName();
						try {
							text.setText(reader.readText(fileName, imageName));
						} catch (Exception e2) {
							LogFile.writeLog(e2.toString());
						}
						if(event.getButton() == MouseButton.PRIMARY)
							try {
								controller.setPrimaryImage(image);
							} catch (Exception e1) {
								LogFile.writeLog(e1.toString());
							}
						else
							try {
								controller.setSecondaryImage(image);
							} catch (Exception e) {
								LogFile.writeLog(e.toString());
							}
						event.consume();
					}
					
				});
				imageBox.getChildren().add(imageView);
			}
		}
		imageList.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		imageList.setContent(imageBox);
	}
	
	public void uploadPlayer() throws Exception{
		playerBox = new HBox();
		playerBox.setAlignment(Pos.CENTER_LEFT);
		playerBox.setSpacing(5);
		playerBox.setMaxHeight(playerList.getHeight() - 10);
		playerBox.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		File dir = new File("resource/" + fileName + "/Mappe");
		dir = new File("resource/" + fileName +  "/Personaggi");
		for(File file : dir.listFiles()) {
			File temp = new File("resource/" + fileName + "/Personaggi/" + file.getName());
			if(temp.exists()) {
				Image image = new Image(file.toURI().toString());
				ImageView imageView = new ImageView();
				imageView.setImage(image);
				imageView.setFitHeight(125);
				imageView.setFitWidth(125);
				imageView.setPreserveRatio(true);
				imageView.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						imageTopContainer.setImage(image);
						imageTopContainer.setRotate(0);
						imageName = image.toString();
						try {
							text.setText(reader.readText(fileName, imageName));
						} catch (Exception e1) {
							LogFile.writeLog(e1.toString());
						}
						if(event.getButton() == MouseButton.PRIMARY)
							try {
								controller.setPrimaryImage(image);
							} catch (Exception e) {
								LogFile.writeLog(e.toString());
							}
						else
							try {
								controller.setSecondaryImage(image);
							} catch (Exception e) {
								LogFile.writeLog(e.toString());
							}
						event.consume();
					}
					
				});
				playerBox.getChildren().add(imageView);
			}
		}
		playerList.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		playerList.setContent(playerBox);
	}
	
	public void addImageHandler() throws Exception{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selezionare la mappa da caricare");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		String selectedName;
        if(selectedFile != null) {
        	selectedName = selectedFile.getName();
        	FileOutputStream out = null;
			try {
				out = new FileOutputStream("resource/" + fileName + "/Mappe/" + selectedName);
			} catch (FileNotFoundException e) {
				LogFile.writeLog(e.toString());
			}
            try {
				Files.copy(selectedFile.toPath(), out);
				out.close();
				Image image = new Image(selectedFile.toURI().toString());
				ImageView imageView = new ImageView();
				imageView.setImage(image);
				imageView.setFitHeight(125);
				imageView.setFitWidth(125);
				imageView.setPreserveRatio(true);
				imageBox.getChildren().add(imageView);
				uploadImage();
			} catch (IOException e) {
				LogFile.writeLog(e.toString());
			}
        }
	}
	
	public void removeImageHandler() throws Exception{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selezionare la mappa da eliminare");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File file = new File("resource/" + fileName + "/Mappe/");
		fileChooser.setInitialDirectory(file);
		File selectedFile = fileChooser.showOpenDialog(new Stage());
        if(selectedFile != null) {
        	if(selectedFile.delete())
        		uploadImage();
        	else {
        		Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Errore");
    			alert.setHeaderText("Immagine non eliminata");
    			alert.showAndWait();
        	}
        }
	}
	
	public void addPlayerHandler() throws Exception{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selezionare il personaggio da caricare");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		String selectedName;
        if(selectedFile != null) {
        	selectedName = selectedFile.getName();
        	FileOutputStream out = null;
			try {
				out = new FileOutputStream("resource/" + fileName + "/Personaggi/" + selectedName);
			} catch (FileNotFoundException e) {
				LogFile.writeLog(e.toString());
			}
            try {
				Files.copy(selectedFile.toPath(), out);
				out.close();
				Image image = new Image(selectedFile.toURI().toString());
				ImageView imageView = new ImageView();
				imageView.setImage(image);
				imageView.setFitHeight(125);
				imageView.setFitWidth(125);
				imageView.setPreserveRatio(true);
				playerBox.getChildren().add(imageView);
				uploadPlayer();
			} catch (IOException e) {
				LogFile.writeLog(e.toString());
			}
        }
	}
	
	public void removePlayerHandler() throws Exception{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selezionare il personaggio da eliminare");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
		File file = new File("resource/" + fileName + "/Personaggi/");
		fileChooser.setInitialDirectory(file);
		File selectedFile = fileChooser.showOpenDialog(new Stage());
        if(selectedFile != null) {
        	if(selectedFile.delete())
        		uploadPlayer();
        	else {
        		Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Errore");
    			alert.setHeaderText("Immagine non eliminata");
    			alert.showAndWait();
        	}
        }
	}

	public String getImageName() throws Exception{
		return imageName;
	}
}
