package controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Optional;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import persistence.LogFile;
import persistence.Reader;

public class ThirdController {

	@SuppressWarnings("rawtypes")
	@FXML
	ListView listItem;
	@FXML
	VBox leftVBox;
	@FXML
	HBox leftHBox;
	@FXML
	VBox centerVBox;
	@FXML
	HBox centerHBox;
	@FXML
	ImageView backImage;
	@FXML
	ImageView image;
	@FXML
	ImageView upImage;
	@FXML
	Button background;
	@FXML
	Button addButton;
	@FXML
	Button removeButton;
	
	private String name;
	private String fileName;
	private String imageOn;
	private Rectangle2D bounds;
	
	@SuppressWarnings("unchecked")
	public void setInit(String fileName, Reader reader) throws Exception{
		this.fileName = fileName;
		
		ObservableList<Screen> screens = Screen.getScreens();
		bounds = screens.get(0).getVisualBounds();
		listItem.setMaxSize(getRealWidth(200), getRealHeight(700));
		listItem.setMinSize(getRealWidth(200), getRealHeight(700));
		leftVBox.setMaxHeight(getRealHeight(800));
		leftVBox.setMinHeight(getRealHeight(800));
		leftVBox.setMaxWidth(getRealWidth(200));
		leftVBox.setMinWidth(getRealWidth(200));
		leftHBox.setMaxSize(getRealWidth(200), getRealHeight(100));
		centerVBox.setMaxSize(getRealWidth(800), getRealHeight(800));
		centerVBox.setMinSize(getRealWidth(800), getRealHeight(800));
		centerHBox.setMaxSize(getRealWidth(800), getRealHeight(750));
		centerHBox.setMinSize(getRealWidth(800), getRealHeight(750));
		backImage.setFitHeight(getRealHeight(150));
		backImage.setFitWidth(getRealWidth(150));
		image.setFitHeight(getRealHeight(750));
		image.setFitWidth(getRealWidth(700));
		upImage.setFitHeight(getRealHeight(150));
		upImage.setFitWidth(getRealWidth(150));
		
		File fileDx = new File("resource/application/frecciaDx.png");
		Image imageDx = new Image(fileDx.toURI().toString());
		upImage.setImage(imageDx);

		upImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			File file = new File("resource/" + fileName + "/Player/" + name);
			boolean check = false;
			for(File tmp : file.listFiles()) {
				if(check) {
					check = false;
					Image ima = new Image(tmp.toURI().toString());
					image.setImage(ima);
					imageOn = tmp.getName();
					break;
				}
				if(tmp.getName().compareTo(imageOn) == 0) {
					check = true;
				}
			}
		});
		File fileSx = new File("resource/application/frecciaSx.png");
		Image imageSx = new Image(fileSx.toURI().toString());
		backImage.setImage(imageSx);
		backImage.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
			String oldFile = "";
			String currentFile = "";
			File file = new File("resource/" + fileName + "/Player/" + name);
			for(File tmp : file.listFiles()) {
				oldFile = currentFile;
				currentFile = tmp.getName();
				if(imageOn.compareTo(currentFile) == 0 && !oldFile.isEmpty() && !currentFile.isEmpty()) {
					File back = new File("resource/" + fileName + "/Player/" + name + "/" + oldFile);
					Image ima = new Image(back.toURI().toString());
					image.setImage(ima);
					imageOn = back.getName();
				}
			}
		});
		
		update();
		listItem.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<String>() {

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				File file = new File("resource/" + fileName + "/Player/" + newValue);
				boolean check = false;
				String url = null;
				for(File tmp : file.listFiles()) {
					if(!check) {
						url = tmp.toURI().toString();
						imageOn = tmp.getName();
						name = newValue;
						check = true;
					}
				}
				Image ima = new Image(url);
				image.setImage(ima);
			}
			
		});
		
		background.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		background.setTextFill(Color.WHITE);
		background.setOnAction(event -> {
			if(name != null) {
				File pdfFile = new File("resource/" + fileName + "/Player/" + name);
				System.out.println(pdfFile.getAbsolutePath());
				try {
					Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFile.getAbsolutePath());
				} catch (IOException e) {
					LogFile.writeLog(e.toString());
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Errore");
				alert.setHeaderText("Selezionare un player");
				alert.showAndWait();
			}
		});
		
		addButton.setOnAction(event -> {
			try {
				addPlayerHandler(event);
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
		});
		addButton.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		addButton.setTextFill(Color.WHITE);
		
		removeButton.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
		removeButton.setTextFill(Color.WHITE);
		removeButton.setOnAction(event -> {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Selezionare l'immagine da eliminare");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
			File file = new File("resource/" + fileName + "/Player/");
			fileChooser.setInitialDirectory(file);
			File selectedFile = fileChooser.showOpenDialog(new Stage());
	        if(selectedFile != null) {
	        	if(selectedFile.delete())
					try {
						update();
					} catch (Exception e) {
						LogFile.writeLog(e.toString());
					}
				else {
	        		Alert alert = new Alert(AlertType.ERROR);
	    			alert.setTitle("Errore");
	    			alert.setHeaderText("Immagine non eliminata");
	    			alert.showAndWait();
	        	}
	        }
		});
	}
	
	@SuppressWarnings("unchecked")
	private void update() throws Exception{
		listItem.getItems().clear();
		ObservableList<String> list = FXCollections.observableArrayList();
		File dir = new File("resource/" + fileName + "/Player");
		for(File file : dir.listFiles()) {
			boolean found = false;
			for(int i = 0; i < list.size(); i++) {
				if(list.get(i).contains(file.getName()))
					found = true;
			}
			if(!found)
				list.add(file.getName());
		}
		listItem.setItems(list);
	}
	
	private void addPlayerHandler(ActionEvent event) throws Exception{
		Stage stage = new Stage();
		VBox box = new VBox(2);
		{
			box.setPadding(new Insets(10));
			box.setSpacing(10);
			box.setAlignment(Pos.CENTER);
			box.setMinSize(200, 150);
			box.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
			Button newPlayer = new Button("Nuovo giocatore");
			newPlayer.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
			newPlayer.setTextFill(Color.WHITE);
			newPlayer.setOnAction(e -> {
				VBox finalBox = new VBox(2);
				{
					finalBox.setBackground(new Background(new BackgroundFill(Color.DIMGRAY, CornerRadii.EMPTY, Insets.EMPTY)));
					finalBox.setSpacing(10);
					finalBox.setAlignment(Pos.CENTER);
					finalBox.setPadding(new Insets(10));
					finalBox.setMinSize(150, 100);
					TextField text;
					HBox hBox = new HBox(2);
					{
						Label label = new Label("Inserire il nome del giocatore:  ");
						label.setTextFill(Color.WHITE);
						text = new TextField();
						hBox.getChildren().addAll(label, text);
					}
					HBox hBox2 = new HBox(2);
					{
						Button but = new Button("Acceta");
						but.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
						but.setTextFill(Color.WHITE);
						but.setOnAction(e2 -> {
							if(text.getText() == null ) {
								Alert alert = new Alert(AlertType.ERROR);
								alert.setTitle("Errore");
								alert.setHeaderText("Inserire il nome del giocatore");
								alert.showAndWait();
							} else {
								File dir = new File("resource/" + fileName + "/Player/");
								boolean check = false;
								for(File tmp : dir.listFiles()) {
									if((tmp.getName()).compareTo(text.getText()) == 0)
										check = true;
								}
								if(check) {
									Alert alert = new Alert(AlertType.ERROR);
									alert.setTitle("Errore");
									alert.setHeaderText("Nome gia' in uso");
									alert.showAndWait();
								} else {
									File file = new File(dir.getAbsoluteFile() + "/" + text.getText());
									file.mkdir();
									FileChooser fileChooser = new FileChooser();
									fileChooser.setTitle("Selezionare l'immagine");
									fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
									File selectedFile = fileChooser.showOpenDialog(new Stage());
									String selectedName;
							        if(selectedFile != null) {
							        	selectedName = selectedFile.getName();
							        	FileOutputStream out = null;
										try {
											out = new FileOutputStream("resource/" + fileName + "/Player/" + text.getText() + "/" + selectedName);
										} catch (FileNotFoundException err) {
											LogFile.writeLog(e.toString());
										}
							            try {
											Files.copy(selectedFile.toPath(), out);
											out.close();
											update();
											stage.close();
										} catch (IOException er) {
											LogFile.writeLog(e.toString());
										} catch (Exception e1) {
											LogFile.writeLog(e1.toString());
										}
							        }
								}
							}
						});
						Button but2 = new Button("Cancella");
						but2.setBackground(new Background(new BackgroundFill(Color.GREY, CornerRadii.EMPTY, Insets.EMPTY)));
						but2.setTextFill(Color.WHITE);
						but2.setOnAction(e3 -> {
							stage.close();
						});
						hBox2.getChildren().addAll(but, but2);
					}
					finalBox.getChildren().addAll(hBox, hBox2);
					stage.setScene(new Scene(finalBox));
				}
			});
			Button modifyPlayer = new Button("Giocatore esistente");
			modifyPlayer.setBackground(new Background(new BackgroundFill(Color.GRAY, CornerRadii.EMPTY, Insets.EMPTY)));
			modifyPlayer.setTextFill(Color.WHITE);
			modifyPlayer.setOnAction(eve -> {
				String result = null;
				try {
					DialogLoad dialog = new DialogLoad();
					result = dialog.showDialog();
				} catch (Exception e2) {
					LogFile.writeLog(e2.toString());
				}
				if(result != null) {
					FileChooser fileChooser = new FileChooser();
					fileChooser.setTitle("Selezionare l'immagine");
					fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Image Files", "*.png", "*.jpg"));
					File selectedFile = fileChooser.showOpenDialog(new Stage());
					String selectedName;
			        if(selectedFile != null) {
			        	selectedName = selectedFile.getName();
			        	FileOutputStream out = null;
						try {
							out = new FileOutputStream("resource/" + fileName + "/Player/" + result + "/" + selectedName);
						} catch (FileNotFoundException err) {
							LogFile.writeLog(err.toString());
						}
			            try {
							Files.copy(selectedFile.toPath(), out);
							out.close();
							update();
						} catch (IOException er) {
							LogFile.writeLog(er.toString());
						} catch (Exception e1) {
							LogFile.writeLog(e1.toString());
						}
			        }
		        }
			});
			box.getChildren().addAll(newPlayer, modifyPlayer);
		}
		Scene scene = new Scene(box);
		stage.setScene(scene);
		stage.show();
	}
	
	private class DialogLoad {
		
		private Dialog<String> date;
		
		@SuppressWarnings({ "unchecked", "rawtypes"})
		public DialogLoad() throws Exception{
			date = new Dialog<>();
			date.setTitle("Selezionare un giocatore");
			date.setHeaderText(null);
			date.setResizable(false);
			
			TableView<FileName> table;
			VBox box = new VBox(2);
			{
				table = new TableView<>();
				TableColumn column = new TableColumn("Player");
				column.setCellValueFactory(new PropertyValueFactory<>("name"));
				column.setMinWidth(150);
				
				ObservableList<FileName> list = FXCollections.observableArrayList();
				File file = new File("resource/" + fileName + "/Player/");
				for(String name : file.list()) {
						list.add(new FileName(name));						
				}
				table.setItems(list);
				table.getColumns().addAll(column);
				box.getChildren().add(table);
			}

			date.getDialogPane().setContent(box);
			date.getDialogPane().getButtonTypes().add(ButtonType.OK);
			date.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);	
			
			date.setResultConverter(bt -> {
				try {
					return bt == ButtonType.OK ? table.getSelectionModel().getSelectedItem().getName() : null;
				} catch (Exception e) {
					LogFile.writeLog(e.toString());
					return null;
				}
			});
		}
		
		public String showDialog() throws Exception{
			Optional<String> result = date.showAndWait();
			return result.isPresent() ? result.get() : null;
		}
		
		public class FileName {
			
			private String name;
			
			public FileName(String name) throws Exception{
				this.name = name;
			}
			
			public String getName() throws Exception{
				return name;
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
