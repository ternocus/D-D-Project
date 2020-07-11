package controller;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.StringTokenizer;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import persistence.LogFile;
import persistence.Reader;
import persistence.Writer;

public class StartController {
	
	@FXML
	ImageView imageLogo;
	@FXML
	Button buttonContinue;
	
	private Stage primaryStage;
	private Reader reader;
	private Writer writer;
	private MainController controller;
	
	public Stage getPrimaryStage() throws Exception{
		return primaryStage;
	}

	public void inizialize(Stage primaryStage) throws Exception{
		this.primaryStage = primaryStage;
		reader = new Reader();
		writer = new Writer();
		File file = new File("resource/application/dice20.png");
		Image image = new Image(file.toURI().toString());
		imageLogo.setImage(image);
		File dir = new File("resource");
		Boolean found = false;
		buttonContinue.setDisable(true);
		for(File tmp : dir.listFiles()) {
			if(!found && tmp.getName().compareTo("application") != 0) {
				found = true;
				buttonContinue.setDisable(false);
			}
		}
		primaryStage.setOnCloseRequest(event -> {
			try {
				if(controller != null && controller.getSecondaryStage() != null)
					controller.getSecondaryStage().close();
			} catch (Exception e) {
				LogFile.writeLog(e.toString());
			}
			primaryStage.close();
		});
	}
	
	public void onContinue() throws Exception{
		String read = reader.readConf("application", "Last");
		File folder = new File("resource");
		boolean check = false;
		for(File file : folder.listFiles()) {
			if(file.getName().compareTo(read) == 0) {
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPane.fxml"));
					BorderPane root = loader.load();
					Scene scene = new Scene(root);
					controller = loader.getController();
					String type = reader.readConf(file.getName(), "Type");
					controller.setInit(read, type, writer, reader, this);
					primaryStage.setX(50);
					primaryStage.setY(50);
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException e) {
					LogFile.writeLog(e.toString());
				}
				check = true;
				writer.writeConf("application", "Last", file.getName());
			}
		}
		if(!check)
			onNew();
	}
	
	public void onNew() throws Exception{
		DialogNew dialog = new DialogNew();
		String resume = dialog.showDialog();
		if(resume != null) {
			StringTokenizer stk = new StringTokenizer(resume);
			String folderName = stk.nextToken("-").trim();
			String type = stk.nextToken("-").trim();
			String title = stk.nextToken("-");
			if(folderName == null || folderName.isEmpty() || type == null || type.isEmpty() || title == null || title.isEmpty())
				return;
			
			File folder = new File("resource");
			Boolean check = false;
			for(File file : folder.listFiles()) {
				if(!check && file.getName().compareTo(folderName) == 0)
					check = true;
			}
			if(!check) {
				File file = new File(folder.getAbsolutePath() + "/" + folderName);
				file.mkdir();
				String path = file.getAbsoluteFile().toString();
				file = new File(path + "/Mappe");
				file.mkdir();
				file = new File(path + "/Personaggi");
				file.mkdir();
				file = new File(path + "/Player");
				file.mkdir();
				file = new File(path + "/text.txt");
				try {
					file.createNewFile();
				} catch (IOException e1) {
					LogFile.writeLog(e1.toString());
				}
				file = new File(path + "/config.txt");
				try {
					file.createNewFile();
				} catch (IOException e1) {
					LogFile.writeLog(e1.toString());
				}
				writer.writeConf(folderName, "Type", type);
				writer.writeConf(folderName, "Title", title);
				try {
					FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPane.fxml"));
					BorderPane root = loader.load();
					Scene scene = new Scene(root);
					controller = loader.getController();
					controller.setInit(folderName, type, writer, reader, this);
					primaryStage.setScene(scene);
					primaryStage.show();
				} catch (IOException e) {
					LogFile.writeLog(e.toString());
				}
			} else {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Errore");
				alert.setHeaderText("Nome gia' in uso");
				alert.showAndWait();
			}
			writer.writeConf("application", "Last", folderName);
		}
	}
	
	public void onLoad() throws Exception{
		DialogLoad dialog = new DialogLoad();
		controller.StartController.DialogLoad.FileName result = dialog.showDialog();
		if(result == null)
			return;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/MainPane.fxml"));
			BorderPane root = loader.load();
			Scene scene = new Scene(root);
			controller = loader.getController();
			controller.setInit(result.getName(), result.getType(), writer, reader, this);
			primaryStage.setScene(scene);
			primaryStage.show();
		} catch (IOException e) {
			LogFile.writeLog(e.toString());
		}
		writer.writeConf("application", "Last", result.getName());
	}
	
	public void onDelete() throws Exception{
		DialogLoad dialog = new DialogLoad();
		controller.StartController.DialogLoad.FileName result = dialog.showDialog();
		if(result == null)
			return;
		File file = new File("resource/" + result.getName());
		deleteDirectory(file);
	}
	
	private void deleteDirectory(File path) throws Exception{
		if(path.exists()) {
			File[] files = path.listFiles();
			for(int i = 0; i < files.length; i++) {
				if(files[i].isDirectory())
					deleteDirectory(files[i]);
				else
					files[i].delete();
			}
		}
		path.delete();
	}
	
	private class DialogNew {
		
		private Dialog<String> date;
		
		public DialogNew() throws Exception{
			date = new Dialog<>();
			date.setTitle("Nuovo Progetto");
			date.setHeaderText(null);
			date.setResizable(false);
			
			TextField text;
			TextField text2;
			TextField text3;
			HBox box = new HBox(2);
			{
				Label label = new Label("Inserisci il nome:   			     ");
				text = new TextField();
				text.setPrefColumnCount(10);
				box.getChildren().addAll(label, text);
			}
			HBox box2 = new HBox(2);
			{
				Label label = new Label("Inserisci la tipologia:	             ");
				text2 = new TextField();
				text2.setPrefColumnCount(10);
				box2.getChildren().addAll(label, text2);
			}
			HBox box3 = new HBox(2);
			{
				Label label = new Label("Inserisci il titolo della campagna: ");
				text3 = new TextField();
				text3.setPrefColumnCount(10);
				box3.getChildren().addAll(label, text3);
			}
			VBox finalBox = new VBox(2);
			{
				finalBox.getChildren().addAll(box, box2, box3);
			}
			date.getDialogPane().setContent(finalBox);
			date.getDialogPane().getButtonTypes().add(ButtonType.OK);
			date.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);	
			
			date.setResultConverter(bt -> {
				return bt == ButtonType.OK ? (text.getText() + "-" + text2.getText() + "-" + text3.getText()) : null;
			});
		}
		
		public String showDialog() throws Exception{
			Optional<String> result = date.showAndWait();
			return result.isPresent() ? result.get() : null;
		}
	}

	private class DialogLoad {
		
		private Dialog<FileName> date;
		
		@SuppressWarnings({ "unchecked", "rawtypes"})
		public DialogLoad() throws Exception{
			date = new Dialog<>();
			date.setTitle("Selezionare un progetto");
			date.setHeaderText(null);
			date.setResizable(false);
			
			TableView<FileName> table;
			VBox box = new VBox(2);
			{
				table = new TableView<>();
				TableColumn column = new TableColumn("Nome");
				column.setCellValueFactory(new PropertyValueFactory<>("name"));
				column.setMinWidth(150);
				TableColumn column2 = new TableColumn("Tipologia");
				column2.setCellValueFactory(new PropertyValueFactory<>("type"));
				column2.setMinWidth(150);
				
				ObservableList<FileName> list = FXCollections.observableArrayList();
				File file = new File("resource");
				for(String name : file.list()) {
					if(name.compareTo("application") != 0 && name.compareTo("music") != 0) {
						list.add(new FileName(name, reader.readConf(name, "Type")));
					}
						
				}
				table.setItems(list);
				table.getColumns().addAll(column, column2);
				box.getChildren().add(table);
			}

			date.getDialogPane().setContent(box);
			date.getDialogPane().getButtonTypes().add(ButtonType.OK);
			date.getDialogPane().getButtonTypes().add(ButtonType.CANCEL);	
			
			date.setResultConverter(bt -> {
				try {
					return bt == ButtonType.OK ? new FileName(table.getSelectionModel().getSelectedItem().getName(), table.getSelectionModel().getSelectedItem().getType()) : null;
				} catch (Exception e) {
					LogFile.writeLog(e.toString());
					return null;
				}
			});
		}
		
		public FileName showDialog() throws Exception{
			Optional<FileName> result = date.showAndWait();
			return result.isPresent() ? result.get() : null;
		}
		
		public class FileName {
			
			private String name;
			private String type;
			
			public FileName(String name, String type) throws Exception{
				this.name = name;
				this.type = type;
			}
			
			public String getName() throws Exception{
				return name;
			}
			
			public String getType() throws Exception{
				return type;
			}
		}
	}
}
