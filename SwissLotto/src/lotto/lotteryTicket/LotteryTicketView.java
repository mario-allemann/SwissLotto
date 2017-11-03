package lotto.lotteryTicket;

import java.util.Locale;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lotto.ServiceLocator;
import lotto.abstractClasses.View;
import lotto.commonClasses.Translator;

public class LotteryTicketView extends View<LotteryTicketModel> {
	

	Menu menuOptions;
	Menu menuOptionsChangeLT;
	Menu menuOptionsLanguage;

	TilePane numbers;
	TilePane luckyNumbers;
	Button play;

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
			Button number = new Button(Integer.toString(i));
			number.setMinWidth(50);
			number.setMinHeight(50);
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
			Button luckyNumber = new Button(Integer.toString(i));
			
			luckyNumber.setShape(new Circle(2));
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
		
		
		chances = this.calculateChances();
		ScrollPane sp = new ScrollPane();
		sp.setContent(chances);
		bp.setRight(sp);
		bp.setCenter(allNumbers);

		this.play = new Button(t.getString("lt.button"));
		this.play.setDisable(true);
		HBox hb = new HBox();
		hb.getChildren().addAll(play);

		bp.setBottom(hb);

		Scene scene = new Scene(bp);

		return scene;
	}

	//Iterates through all win-possibilites, calculates their chances and returns a VBox with labels
	public VBox calculateChances() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();
		
		VBox v = new VBox();
		v.getChildren().add(new Label(t.getString("lt.chances")));
		for(int normalNumber = model.chooseNumber; normalNumber >=0; normalNumber--) {
			for(int luckyNumber = model.chooseLucky; luckyNumber>=0; luckyNumber--) {
				String labelString = normalNumber + " + " + luckyNumber + "\t" + model.getChanceAsPercentage(normalNumber, luckyNumber) + "%";
				v.getChildren().add(new Label(labelString));
			}
		}
		return v;
	}
	
	public void updateTexts() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();
		menuOptions.setText(t.getString("program.menu.options"));
		menuOptionsChangeLT.setText(t.getString("lt.menu.options.changeLT"));
		menuOptionsLanguage.setText(t.getString("program.menu.options.language"));
		play.setText(t.getString("lt.button"));
		((Labeled) chances.getChildren().get(0)).setText(t.getString("lt.chances"));
		
	}

}
