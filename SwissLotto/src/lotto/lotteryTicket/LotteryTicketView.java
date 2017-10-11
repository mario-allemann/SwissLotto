package lotto.lotteryTicket;

import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lotto.ServiceLocator;
import lotto.abstractClasses.View;
import lotto.commonClasses.Translator;

public class LotteryTicketView extends View<LotteryTicketModel> {
	ServiceLocator sl = ServiceLocator.getServiceLocator();

	Menu menuOptions;
	Menu menuOptionsChangeLT;
	Menu menuOptionsLanguage;

	TilePane numbers;
	TilePane luckyNumbers;
	Button play;
	
	


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
		
		
		VBox chances = new VBox();
		
		chances = this.calculateChances();
		bp.setRight(chances);
		bp.setCenter(allNumbers);

		this.play = new Button(t.getString("lt.button"));
		HBox hb = new HBox();
		hb.getChildren().addAll(play);

		bp.setBottom(hb);

		Scene scene = new Scene(bp);

		return scene;
	}

	//Iterates through all win-possibilites, calculates their chances and return a VBox with labels
	public VBox calculateChances() {
		VBox v = new VBox();
		v.getChildren().add(new Label("Chances:"));
		for(int normalNumber = model.chooseNumber; normalNumber >=3; normalNumber--) {
			for(int luckyNumber = model.chooseLucky; luckyNumber>=0; luckyNumber--) {
				HBox h = new HBox();
				String labelString = normalNumber + " + " + luckyNumber + "\t" + model.getChanceAsPercentage(normalNumber, luckyNumber) + "%";
				v.getChildren().add(new Label(labelString));
			}
//			v.getChildren().add(new Label(normalNumber + "\t\t " + model.getChanceAsPercentage(normalNumber, 0) + "%"));
		}
		return v;
	}

}
