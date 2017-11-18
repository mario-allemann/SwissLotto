package lotto.lotteryTicket;

import java.util.Locale;
import java.util.logging.Logger;

import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.ListView;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import lotto.ServiceLocator;
import lotto.abstractClasses.View;
import lotto.commonClasses.Translator;

public class LotteryTicketView extends View<LotteryTicketModel> {
	
	//TOP
	Menu menuOptions;
	Menu menuOptionsChangeLT;
	Menu menuOptionsLanguage;

	
	
	
	TilePane numbers;
	TilePane luckyNumbers;
	Button btnPlay;
	Button btnChance;

	VBox chances;



	public LotteryTicketView(Stage stage, LotteryTicketModel model) {
		
		super(stage, model);
		stage.setTitle("LotteryTicket");

		ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");
		// TODO Auto-generated constructor stub
		

	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();

		BorderPane bp = new BorderPane();
		bp.getStylesheets().add(sl.getStylesheet());
		bp.getStyleClass().add("background");
		
		//Sets up the menu
		MenuBar menuBar = new MenuBar();
		menuOptions = new Menu(t.getString("program.menu.options"));
		menuOptionsChangeLT = new Menu(t.getString("lt.menu.options.changeLT"));
		menuOptionsLanguage = new Menu(t.getString("program.menu.options.language"));
		menuOptions.getItems().addAll(menuOptionsChangeLT, menuOptionsLanguage);
		
	       for (Locale locale : sl.getLocales()) {
	           MenuItem language = new MenuItem(locale.getLanguage());
	           menuOptionsLanguage.getItems().add(language);
	           language.setOnAction( event -> {
					sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
	                sl.setTranslator(new Translator(locale.getLanguage()));
	                updateTexts();
	            });
	        }

		menuBar.getMenus().addAll(menuOptions);

		bp.setTop(menuBar);

		// Sets up the normal numbers
		numbers = new TilePane();
		for (int i = 1; i <= model.maxNumber; i++) {
			ToggleButton number = new ToggleButton(Integer.toString(i));
			number.getStyleClass().add("button-normal");
			//TODO for some reason this arraylist only works in the model
			model.numberButtons.add(number);
			numbers.getChildren().add(number);
		}
		numbers.setVgap(3.5);
		numbers.setHgap(3.5);
		numbers.setPrefColumns(6);
		
		
		// Sets up the lucky numbers
		luckyNumbers = new TilePane();
		for (int i = 1; i <= model.maxLucky; i++) {
			ToggleButton luckyNumber = new ToggleButton(Integer.toString(i));
			
			luckyNumber.setShape(new Circle(2));
			luckyNumber.getStyleClass().add("button-normal");
			luckyNumber.setMinWidth(50);
			luckyNumber.setMinHeight(50);
			model.luckyNumberButtons.add(luckyNumber);
			luckyNumbers.getChildren().add(luckyNumber);
		}
		luckyNumbers.setHgap(3.5);
		luckyNumbers.setVgap(3.5);

		VBox allNumbers = new VBox();
		
		allNumbers.setSpacing(20);
		allNumbers.getChildren().addAll(numbers, luckyNumbers);
		
		bp.setCenter(allNumbers);

		
		//Bottom - controls
		this.btnPlay = new Button(t.getString("lt.button"));
		this.btnPlay.getStyleClass().add("control-button");
		this.btnPlay.setDisable(true);
		
		this.btnChance = new Button(t.getString("lt.btnChance"));
		this.btnChance.getStyleClass().add("control-button");
		HBox hb = new HBox();
		hb.getChildren().addAll(btnPlay, btnChance);
		bp.setBottom(hb);
		
		Scene scene = new Scene(bp);
		

		return scene;
	}


	
	//Updates the GUI elements after the language gets changed
	public void updateTexts() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();
		menuOptions.setText(t.getString("program.menu.options"));
		menuOptionsChangeLT.setText(t.getString("lt.menu.options.changeLT"));
		menuOptionsLanguage.setText(t.getString("program.menu.options.language"));
		btnPlay.setText(t.getString("lt.button"));
		
	}

}
