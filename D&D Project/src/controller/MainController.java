package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Screen;
import javafx.stage.Stage;
import model.DrawCanvas;
import model.MyAudio;
import persistence.LogFile;
import persistence.Reader;
import persistence.Writer;

public class MainController {
			
	@FXML
	VBox listBot;
	@FXML
	HBox hboxAudio;
	@FXML
	AnchorPane anchorTop;
	@FXML
	AnchorPane anchorBot;
	@FXML
	VBox textTop;
	@FXML
	VBox imageTop;
	@FXML
	ScrollPane imageList;
	@FXML
	ScrollPane playerList;
	@FXML
	Button imageAdd;
	@FXML
	Button imageRemove;
	@FXML
	Button playerAdd;
	@FXML
	Button playerRemove;
	@FXML
	Button audioAdd;
	@FXML
	Button audioRemove;
	@FXML
	Label labelSong1;
	@FXML
	Label labelSong2;
	@FXML
	Slider sliderSong1;
	@FXML
	Slider sliderSong2;
	@FXML
	Slider audioTot;
	@FXML
	HBox buttonList;
	@FXML
	TextArea text;
	@FXML
	Button modifyText;
	@FXML
	Button deleteText;
	@FXML
	Button saveText;
	@FXML
	MenuItem newMenu;
	@FXML
	MenuItem openMenu;
	@FXML
	MenuItem saveMenu;
	@FXML
	MenuItem closeMenu;
	@FXML
	MenuItem openRecentMenu;
	@FXML
	MenuItem textImageMenuImport;
	@FXML
	MenuItem textSpellMenuImport;
	@FXML
	MenuItem textTalentMenuImport;
	@FXML
	MenuItem textBestiaryMenuImport;
	@FXML
	MenuItem textImageMenuExport;
	@FXML
	MenuItem textSpellMenuExport;
	@FXML
	MenuItem textTalentMenuExport;
	@FXML
	MenuItem textBestiaryMenuExport;
	@FXML
	Button spellGlossary;
	@FXML
	Button talentGlossary;
	@FXML
	Button bestiaryGlossary;
	@FXML
	Button playerGlossary;
	@FXML
	VBox VBoxCenter;
	@FXML
	VBox audioBox;
	@FXML
	VBox audioBoxSong1;
	@FXML
	VBox audioBoxSong2;
	@FXML
	VBox leftBoxButton;
	@FXML
	VBox rightBoxButton;
	@FXML
	HBox botBottonText;
	@FXML
	ImageView logo;
	@FXML
	HBox rotateButton;
	@FXML
	VBox vBoxTop;
	
	private String fileName;
	private Writer writer;
	private Reader reader;
	private Rectangle2D bounds;
	private StartController startController;
	private SecondaryController controller;
	private Stage secondaryStage;
	private ImageView imageTopContainer;
	private Canvas canvas;
	private DrawCanvas drawCanvas;

	public void setInit(String fileName, String type, Writer writer, Reader reader, StartController startController) throws Exception{
		this.fileName = fileName;
		this.writer = writer;
		this.reader = reader;
		this.startController = startController;
		inizialize(type);
	}

	private void inizialize(String type) throws Exception{
		ObservableList<Screen> screens = Screen.getScreens();
		bounds = screens.get(0).getVisualBounds();
		listBot.setPrefWidth((bounds.getWidth()/3)*2);
		listBot.setMaxWidth((bounds.getWidth()/3)*2);
		anchorTop.setPrefSize(bounds.getWidth(), (bounds.getHeight()/3)*2);
		anchorTop.setMaxSize(bounds.getWidth(), (bounds.getHeight()/3)*2);
		anchorBot.setPrefSize(bounds.getWidth(), (bounds.getHeight()/3)-60);
		anchorBot.setMaxSize(bounds.getWidth(), (bounds.getHeight()/3)-60);
		imageTop.setPrefSize((bounds.getWidth()-120)/2, (bounds.getHeight()/3)*2);
		imageTop.setMaxSize((bounds.getWidth()-120)/2, (bounds.getHeight()/3)*2);
		textTop.setPrefSize((bounds.getWidth()-120)/2, (bounds.getHeight()/3)*2);
		textTop.setMaxSize((bounds.getWidth()-120)/2, (bounds.getHeight()/3)*2);
		imageList.setPrefHeight((bounds.getHeight()/3)/2);
		imageList.setMaxHeight((bounds.getHeight()/3)/2);
		playerList.setPrefHeight((bounds.getHeight()/3)/2);
		playerList.setMaxHeight((bounds.getHeight()/3)/2);
	
		VBoxCenter.setMaxWidth(getRealWidth(150));
		VBoxCenter.setMinWidth(getRealWidth(150));
		VBoxCenter.setMinHeight(getRealWidth(155));
		VBoxCenter.setMaxHeight(getRealWidth(155));
		audioBox.setMaxSize(getRealWidth(160), getRealHeight(265));
		audioTot.setMinWidth(150);
		audioTot.setMaxWidth(150);
		audioBoxSong1.setMinSize(getRealWidth(80), getRealHeight(100) + 40);
		audioBoxSong1.setMaxSize(getRealWidth(80), getRealHeight(100) + 40);
		sliderSong1.setMaxHeight(getRealHeight(90) + 40);
		audioBoxSong2.setMinSize(getRealWidth(80), getRealHeight(100) + 40);
		audioBoxSong2.setMaxSize(getRealWidth(80), getRealHeight(100) + 40);
		sliderSong2.setMaxHeight(getRealHeight(90) + 40);
		text.setMinSize(getRealWidth(600)-50, getRealHeight(600)-50);
		text.setMaxSize(getRealWidth(600)-50, getRealHeight(600)-50);
		leftBoxButton.setMaxWidth(getRealWidth(35));
		leftBoxButton.setMinWidth(getRealWidth(35));
		rightBoxButton.setMaxWidth(getRealWidth(35));
		rightBoxButton.setMinWidth(getRealWidth(35));
		File fileLogo = new File("resource/application/dice20.png");
		Image imageLogo = new Image(fileLogo.toURI().toString());
		logo.setImage(imageLogo);
		logo.setFitHeight(getRealHeight(150));
		logo.setFitWidth(getRealWidth(150));
		vBoxTop.setMinHeight(imageTop.getHeight() + getRealHeight(600));
		vBoxTop.setMaxHeight(imageTop.getHeight() + getRealHeight(600));
		
		imageTopContainer = new ImageView();
		imageTopContainer.setFitHeight(getRealHeight(600));
		imageTopContainer.setFitWidth(getRealWidth(600));
		imageTopContainer.setPreserveRatio(true);
		
		canvas = new Canvas();
		canvas.setWidth(getRealWidth(600));
		canvas.setHeight(getRealHeight(600));
		canvas.setLayoutX(imageTopContainer.getLayoutX());
		canvas.setLayoutY(imageTopContainer.getLayoutY());
		
		StackPane stack = new StackPane();
		stack.setAlignment(Pos.CENTER);
		stack.getChildren().addAll(imageTopContainer, canvas);
		vBoxTop.getChildren().add(stack);

		modifyText.setOnAction(event -> {
			if(text.isEditable())
				text.setEditable(false);
			else
				text.setEditable(true);
		});
		
		setSecondaryScreen();
		drawCanvas = new DrawCanvas(canvas, controller, imageTopContainer);
		MyAudio audio = new MyAudio(bounds, buttonList, audioTot, sliderSong1, sliderSong2, hboxAudio, labelSong1, labelSong2);
		model.MyImage image = new model.MyImage(imageList, fileName, imageTopContainer, text, reader, controller, playerList);
		
		
		image.uploadImage();
		image.uploadPlayer();
		audio.uploadAudio();
		drawCanvas.setImageClick();
		
		imageAdd.setOnAction(event -> {try {
			image.addImageHandler();
		} catch (Exception e) {
			LogFile.writeLog(e.toString());
		}});
		imageRemove.setOnAction(event -> {try {
			image.removeImageHandler();
		} catch (Exception e) {
			LogFile.writeLog(e.toString());
		}});
		playerAdd.setOnAction(event -> {try {
			image.addPlayerHandler();
		} catch (Exception e) {
			LogFile.writeLog(e.toString());
		}});
		playerRemove.setOnAction(event -> {try {
			image.removePlayerHandler();
		} catch (Exception e) {
			LogFile.writeLog(e.toString());
		}});
		audioAdd.setOnAction(event -> {try {
			audio.addAudioHandler();
		} catch (Exception e) {
			LogFile.writeLog(e.toString());
		}});
		audioRemove.setOnAction(event -> {try {
			audio.removeAudioHandler();
		} catch (Exception e) {
			LogFile.writeLog(e.toString());
		}});
		
		saveText.setOnAction(event -> {
			try {
				if(image.getImageName() != null && text.getText() != null) {
					if(text.getText().contains("#")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Errore");
						alert.setHeaderText("Non è possibile inserire il carattere '#' all'interno del testo");
						alert.showAndWait();
					} else {
						writer.writeText(fileName, image.getImageName(), text.getText());
						text.setEditable(false);
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Completato");
						alert.setHeaderText("Il testo è stato correttamente salvato");
						alert.showAndWait();
					}
				}
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		deleteText.setOnAction(event -> {
			text.setText("");
			try {
				writer.writeText(fileName, image.getImageName(), "");
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Completato");
			alert.setHeaderText("Il testo è stato correttamente elliminato");
			alert.showAndWait();
		});
		
		newMenu.setOnAction(event -> {
			try {
				startController.onNew();
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		openMenu.setOnAction(event -> {
			try {
				startController.onLoad();
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		saveMenu.setOnAction(event -> {
			try {
				if(image.getImageName() != null && text.getText() != null) {
					if(text.getText().contains("#")) {
						Alert alert = new Alert(AlertType.ERROR);
						alert.setTitle("Errore");
						alert.setHeaderText("Non è possibile inserire il carattere '#' all'interno del testo");
						alert.showAndWait();
					} else {
						writer.writeText(fileName, image.getImageName(), text.getText());
						text.setEditable(false);
						Alert alert = new Alert(AlertType.CONFIRMATION);
						alert.setTitle("Completato");
						alert.setHeaderText("Il testo è stato correttamente salvato");
						alert.showAndWait();
					}
				}
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		closeMenu.setOnAction(event -> {
			Stage primaryStage = null;
			try {
				primaryStage = startController.getPrimaryStage();
			} catch (Exception e1) {
				LogFile.writeLog(e1.toString());
			}
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/StartPane.fxml"));
			VBox root = null;
			try {
				root = loader.load();
			} catch (IOException e) {
				LogFile.writeLog(e.toString());
			}
			StartController controller = loader.getController();
			try {
				controller.inizialize(primaryStage);
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
			Scene scene = new Scene(root);
			Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();
			primaryStage.setX(primaryScreenBounds.getWidth()/2);
			primaryStage.setX(primaryScreenBounds.getHeight()/2);
			primaryStage.setWidth(400);
			primaryStage.setHeight(400);
			primaryStage.setScene(scene);
			primaryStage.show();
		});
		openRecentMenu.setOnAction(event -> {
			try {
				startController.onContinue();
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		textImageMenuImport.setOnAction(event -> {
			try {
				importHandler( type + "-text.txt");
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		textSpellMenuImport.setOnAction(event -> {
			try {
				importHandler(type + "-spell.pdf");
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		textTalentMenuImport.setOnAction(event -> {
			try {
				importHandler(type + "-talent.pdf");
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		textBestiaryMenuImport.setOnAction(event -> {
			try {
				importHandler(type + "-bestiary.pdf");
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		textImageMenuExport.setOnAction(event -> {
			try {
				exportHandler(fileName + "/", "text.txt");
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		textSpellMenuExport.setOnAction(event -> {
			try {
				exportHandler("application/", type + "-spell.pdf");
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		textTalentMenuExport.setOnAction(event -> {
			try {
				exportHandler("application/", type + "-talent.pdf");
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		textBestiaryMenuExport.setOnAction(event -> {
			try {
				exportHandler("application/", type + "-bestiary.pdf");
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		
		spellGlossary.setOnAction(event -> {
			File pdfFile = new File("resource/application/" + type + "-spell.pdf");
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFile.getAbsolutePath());
			} catch (IOException e) {
				LogFile.writeLog(e.toString());
			}
		});
		talentGlossary.setOnAction(event -> {
			File pdfFile = new File("resource/application/" + type + "-talent.pdf");
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFile.getAbsolutePath());
			} catch (IOException e) {
				LogFile.writeLog(e.toString());
			}
		});
		bestiaryGlossary.setOnAction(event -> {
			File pdfFile = new File("resource/application/" + type + "-bestiary.pdf");
			try {
				Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFile.getAbsolutePath());
			} catch (IOException e) {
				LogFile.writeLog(e.toString());
			}
		});
		playerGlossary.setOnAction(event -> {
			try {
				glossaryhandler();
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		
		File f = new File("resource/application/orario.png");
		ImageView im = new ImageView(new Image(f.toURI().toString()));
		im.setFitHeight(getRealHeight(30));
		im.setFitWidth(getRealWidth(30));
		Button orarioButton = new Button("", im);
		orarioButton.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		orarioButton.setMinSize(getRealWidth(40), getRealHeight(40));
		orarioButton.setMaxSize(getRealWidth(40), getRealHeight(40));
		orarioButton.setOnAction(event -> {
			imageTopContainer.setRotate(imageTopContainer.getRotate() + 90);
			try {
				controller.changeRotationImage(imageTopContainer.getImage(), imageTopContainer.getRotate());
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		
		File f1 = new File("resource/application/antiorario.png");
		im = new ImageView(new Image(f1.toURI().toString()));
		im.setFitHeight(getRealHeight(30));
		im.setFitWidth(getRealWidth(30));
		Button antiorarioButton = new Button("", im);
		antiorarioButton.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		antiorarioButton.setMinSize(getRealWidth(40), getRealHeight(40));
		antiorarioButton.setMaxSize(getRealWidth(40), getRealHeight(40));
		antiorarioButton.setOnAction(event -> {
			imageTopContainer.setRotate(imageTopContainer.getRotate() - 90);
			try {
				controller.changeRotationImage(imageTopContainer.getImage(), imageTopContainer.getRotate());
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		rotateButton.setAlignment(Pos.CENTER_RIGHT);
		rotateButton.setPadding(new Insets(5));
		rotateButton.setSpacing(10);
		rotateButton.getChildren().addAll(orarioButton, antiorarioButton);
	}
	
	private void setSecondaryScreen() throws Exception{
		secondaryStage = new Stage();
		secondaryStage.setTitle("MithGenesis");
		ObservableList<Screen> screens = Screen.getScreens();
		if(screens.size() == 1) {
			Alert alert = new Alert(AlertType.ERROR);
			alert.setTitle("Attenzione");
			alert.setHeaderText("Per un corretto utilizzo del programma è necessario avere un secondo schermo collegato");
			alert.showAndWait();
		} else {			
			Rectangle2D bounds = screens.get(1).getVisualBounds();
            secondaryStage.setX(bounds.getMinX());
            secondaryStage.setY(bounds.getMinY());
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/SecondaryPane.fxml"));
			try {
				HBox stackPane = loader.load();
				controller = loader.getController();
				controller.setInit(fileName, reader, bounds);
				Scene scene = new Scene(stackPane);
				secondaryStage.setScene(scene);
			} catch (IOException e) {
				LogFile.writeLog(e.toString());
			}
			secondaryStage.show();
    		secondaryStage.setFullScreen(true);
		}
	}
	
	private void importHandler(String file) throws Exception{
		if(file.contains("text")) {
			Alert alert = new Alert(AlertType.WARNING);
			alert.setTitle("Attenzione");
			alert.setHeaderText("Il documento selezionato deve essere ben formato.\nIn caso di caricamento di un file errato produrràuna malformazione\n del file e un non corretto utilizzo del programma stesso.");
			alert.showAndWait();
		}
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Selezionare il file");
		fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Text Files", "*.pdf"));
		File selectedFile = fileChooser.showOpenDialog(new Stage());
        if(selectedFile != null) {
        	try {
        		File dir = new File("resource/application/");
        		for(File f : dir.listFiles()) {
        			if(f.getName().compareTo(file) == 0) {
        				Alert alert = new Alert(AlertType.WARNING);
            			alert.setTitle("Attenzione");
            			alert.setHeaderText("E' gia' presente un file con questo nome, proseguendo\nesso verrà sovrascritto.\nContinuare?");
            			alert.getButtonTypes().add(ButtonType.CANCEL);
            			Optional<ButtonType> result = alert.showAndWait();
            			if(result.get() == ButtonType.OK) {
            				FileOutputStream outPut = new FileOutputStream("resource/application/" + file);
            				Files.copy(selectedFile.toPath(), outPut);
            				outPut.close();
            				Alert alert2 = new Alert(AlertType.CONFIRMATION);
                			alert2.setTitle("Completato");
                			alert2.setHeaderText("Il trasferimento dei file è andato a buon fine");
                			alert2.showAndWait();
            			}
        			}
        		}
			} catch (FileNotFoundException e) {
				LogFile.writeLog(e.toString());
				File tmp = new File("resource/application/");
				for(File bad : tmp.listFiles()) {
					if(bad.getName().compareTo(selectedFile.getName()) == 0)
						bad.delete();
				}
				Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Errore");
    			alert.setHeaderText("Il trasferimento non è stato completato");
    			alert.showAndWait();
			} catch (IOException e) {
				LogFile.writeLog(e.toString());
				File tmp = new File("resource/application/");
				for(File bad : tmp.listFiles()) {
					if(bad.getName().compareTo(selectedFile.getName()) == 0)
						bad.delete();
				}
				Alert alert = new Alert(AlertType.ERROR);
    			alert.setTitle("Errore");
    			alert.setHeaderText("Il trasferimento non è stato completato");
    			alert.showAndWait();
			}
        }
	}
	
	private void exportHandler(String folder, String file) throws Exception{
		DirectoryChooser dir = new DirectoryChooser();
		File selectedDir = dir.showDialog(new Stage());
		if(selectedDir != null) {
			File directory = new File("resource/" + folder);
			for(File fileTmp : directory.listFiles()) {
				if(fileTmp.getName().compareTo(file) == 0) {
					try {
						FileOutputStream outPut = new FileOutputStream(selectedDir.getAbsolutePath() + "/" + fileTmp.getName());
						Files.copy(fileTmp.toPath(), outPut);
						outPut.close();
						Alert alert = new Alert(AlertType.CONFIRMATION);
		    			alert.setTitle("Completato");
		    			alert.setHeaderText("Il trasferimento dei file è andato a buon fine");
		    			alert.showAndWait();
					} catch (FileNotFoundException e) {
						LogFile.writeLog(e.toString());
						for(File tmp : selectedDir.listFiles()) {
							for(File bad : directory.listFiles()) {
								if(bad.getName().contains(".pdf")) {
									if(bad.getName() == tmp.getName())
										tmp.delete();
								}
							}
						}
						Alert alert = new Alert(AlertType.ERROR);
		    			alert.setTitle("Errore");
		    			alert.setHeaderText("Il trasferimento non è stato completato");
		    			alert.showAndWait();
					} catch (IOException e) {
						LogFile.writeLog(e.toString());
						for(File tmp : selectedDir.listFiles()) {
							for(File bad : directory.listFiles()) {
								if(bad.getName().contains(".pdf")) {
									if(bad.getName() == tmp.getName())
										tmp.delete();
								}
							}
						}
						Alert alert = new Alert(AlertType.ERROR);
		    			alert.setTitle("Errore");
		    			alert.setHeaderText("Il trasferimento non è stato completato");
		    			alert.showAndWait();
					}
				}
			}
		}
	}
	
	private void glossaryhandler() throws Exception{
		Stage thirdStage = new Stage();
		thirdStage.setTitle("Giocatori");
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ThirdPane.fxml"));
		BorderPane thirdPane = null;
		try {
			thirdPane = loader.load();
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
		ThirdController controller = loader.getController();
		controller.setInit(fileName, reader);
		Scene scene = new Scene(thirdPane);
		thirdStage.setScene(scene);
		thirdStage.show();
	}
	
	private double getRealWidth(int x) throws Exception{
		return (x * bounds.getWidth()) / 1920;
	}
	
	private double getRealHeight(int x) throws Exception{
		return (x * bounds.getHeight()) / 1040;
	}
	
	public Stage getSecondaryStage() throws Exception{
		return secondaryStage;
	}
}
