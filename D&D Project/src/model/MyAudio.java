package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.control.Alert;
import javafx.scene.control.ListView;
import javafx.scene.control.Slider;
import javafx.scene.control.Alert.AlertType; 
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import persistence.LogFile;

public class MyAudio {
	
	private Rectangle2D bounds;
	private HBox audio;
	private HBox hboxAudio;
	private Label labelSong1;
	private Label labelSong2;
	private MediaPlayer song1;
	private MediaPlayer song2;
	private double volume = 0;
	private double volume1 = 100;
	private double volume2 = 100;
	
	public MyAudio(Rectangle2D bounds, HBox buttonList, Slider audioTot, Slider sliderSong1, Slider sliderSong2, HBox hboxAudio, Label labelSong1, Label labelSong2) throws Exception{
		this.bounds = bounds;
		this.hboxAudio = hboxAudio;
		this.labelSong1 = labelSong1;
		this.labelSong2 = labelSong2;
		
		File filePlay = new File("resource/application/play.png");
		ImageView play1 = new ImageView(new Image(filePlay.toURI().toString()));
		ImageView play2 = new ImageView(new Image(filePlay.toURI().toString()));
		play1.setFitHeight(getRealHeight(25));
		play1.setFitWidth(getRealWidth(25));
		play2.setFitHeight(getRealHeight(25));
		play2.setFitWidth(getRealWidth(25));
		play1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			song1.play();
		});
		play2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			song2.play();
		});
		
		File filePause = new File("resource/application/pause.png");
		ImageView pause1 = new ImageView(new Image(filePause.toURI().toString()));
		ImageView pause2 = new ImageView(new Image(filePause.toURI().toString()));
		pause1.setFitHeight(getRealHeight(25));
		pause1.setFitWidth(getRealWidth(25));
		pause2.setFitHeight(getRealHeight(25));
		pause2.setFitWidth(getRealWidth(25));
		pause1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			song1.pause();
		});
		pause2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			song2.pause();
		});
		
		File fileStop = new File("resource/application/stop.png");
		ImageView stop1 = new ImageView(new Image(fileStop.toURI().toString()));
		ImageView stop2 = new ImageView(new Image(fileStop.toURI().toString()));
		stop1.setFitHeight(getRealHeight(25));
		stop1.setFitWidth(getRealWidth(25));
		stop2.setFitHeight(getRealHeight(25));
		stop2.setFitWidth(getRealWidth(25));
		stop1.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			song1.stop();
		});
		stop2.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			song2.stop();
		});
		
		buttonList.getChildren().addAll(play1, pause1, stop1, play2, pause2, stop2);
		
		
		audioTot.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				volume = newValue.doubleValue();
				if(song1 != null)
					song1.setVolume(((50 - volume) * volume1 / 100)/100);
				if(song2 != null)
					song2.setVolume(((50 + volume) * volume2 / 100)/100);
			}
			
		});
		
		sliderSong1.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				volume1 = newValue.doubleValue();
				song1.setVolume(((50 - volume) * volume1 / 100)/100);
			}
			
		});
		
		sliderSong2.valueProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
				volume2 = newValue.doubleValue();
				song2.setVolume(((50 + volume) * volume2 / 100)/100);
			}
			
		});
	}
	
	public void uploadAudio() throws Exception{
		if(hboxAudio.getChildren().contains(audio))
			hboxAudio.getChildren().remove(audio);
		audio = new HBox();
		audio.setPrefSize(bounds.getWidth()/3, (bounds.getHeight()/3));
		audio.setMaxSize(bounds.getWidth()/3, (bounds.getHeight()/3));
		audio.setAlignment(Pos.CENTER);
		ListView<String> listView = new ListView<>();
		listView.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		ListView<String> listView2 = new ListView<>();
		listView2.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		File dir = new File("resource/music");
		int i = 0;
		for(File file : dir.listFiles()) {
			if(i++ % 2 == 0) {
				listView.getItems().add(file.getName());
				listView.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if(event.getButton() == MouseButton.PRIMARY) {
							Media media = new Media(new File("resource/music/" + listView.getSelectionModel().getSelectedItem()).toURI().toString());
							labelSong1.setText(listView.getSelectionModel().getSelectedItem());
							if(song1 != null)
								song1.stop();
							song1 = new MediaPlayer(media);
							song1.setVolume(((50 - volume) * volume1 / 100)/100);
						} else {
							Media media = new Media(new File("resource/music/" + listView.getSelectionModel().getSelectedItem()).toURI().toString());
							labelSong2.setText(listView.getSelectionModel().getSelectedItem());
							if(song2 != null)
								song2.stop();
							song2 = new MediaPlayer(media);
							song2.setVolume(((50 + volume) * volume2 / 100)/100);
						}
					}
					
				});
			} else {
				listView2.getItems().add(file.getName());
				listView2.setOnMouseClicked(new EventHandler<MouseEvent>() {

					@Override
					public void handle(MouseEvent event) {
						if(event.getButton() == MouseButton.PRIMARY) {
							Media media = new Media(new File("resource/music/" + listView2.getSelectionModel().getSelectedItem()).toURI().toString());
							labelSong1.setText(listView2.getSelectionModel().getSelectedItem());
							if(song1 != null)
								song1.stop();
							song1 = new MediaPlayer(media);
							song1.setVolume(((50 - volume) * volume1 / 100)/100);
						} else {
							Media media = new Media(new File("resource/music/" + listView2.getSelectionModel().getSelectedItem()).toURI().toString());
							labelSong2.setText(listView2.getSelectionModel().getSelectedItem());
							if(song2 != null)
								song2.stop();
							song2 = new MediaPlayer(media);
							song2.setVolume(((50 + volume) * volume2 / 100)/100);
						}
					}
					
				});
			}
		}
		audio.getChildren().addAll(listView, listView2);
		hboxAudio.getChildren().add(audio);	
	}
	
	public void addAudioHandler() throws Exception{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selezionare la traccia audio da caricare");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.wma"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
		String selectedName;
        if(selectedFile != null) {
        	selectedName = selectedFile.getName();
        	FileOutputStream out = null;
			try {
				out = new FileOutputStream("resource/music/" + selectedName);
			} catch (FileNotFoundException e) {
				LogFile.writeLog(e.toString());
			}
            try {
				Files.copy(selectedFile.toPath(), out);
				out.close();
			} catch (IOException e) {
				LogFile.writeLog(e.toString());
			}
            uploadAudio();
        }
	}
	
	public void removeAudioHandler() throws Exception{
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selezionare la traccia audio da eliminare");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Audio Files", "*.wav", "*.mp3", "*.wma"));
		File file = new File("resource/music");
		fileChooser.setInitialDirectory(file);
		File selectedFile = fileChooser.showOpenDialog(new Stage());
        if(selectedFile != null) {
        	if(selectedFile.delete())
        		uploadAudio();
        	else {
        		Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Errore");
    			alert.setHeaderText("Immagine non eliminata");
    			alert.showAndWait();
        	}
        }
	}

	private double getRealWidth(int x) throws Exception{
		return (x * bounds.getWidth()) / 1920;
	}
	
	private double getRealHeight(int x) throws Exception{
		return (x * bounds.getHeight()) / 1040;
	}
}
