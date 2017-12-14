package lotto.lotteryTicket;

import java.util.Locale;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
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

	// TOP
	protected Menu menuOptions;
	protected Menu menuOptionsChangeLT;
	protected Menu menuOptionsLanguage;

	// CENTER
	protected TilePane numbers;
	protected TilePane luckyNumbers;

	// BOTTOM
	protected Button btnPlay;
	protected Button btnChance;

	protected VBox chances;

	public LotteryTicketView(Stage stage, LotteryTicketModel model) {

		super(stage, model);
		stage.setTitle("LotteryTicket");

		ServiceLocator.getServiceLocator().getLogger().info("Application view initialized");

	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();

		BorderPane bp = new BorderPane();
		bp.getStylesheets().add(sl.getStylesheet());
		bp.getStyleClass().add("background");

		// Sets up the menu
		MenuBar menuBar = new MenuBar();
		menuOptions = new Menu(t.getString("program.menu.options"));
		menuOptionsChangeLT = new Menu(t.getString("lt.menu.options.changeLT"));
		menuOptionsLanguage = new Menu(t.getString("program.menu.options.language"));
		menuOptions.getItems().addAll(menuOptionsChangeLT, menuOptionsLanguage);

		/**
		 * @author Brad Richards
		 */
		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			menuOptionsLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateTexts();
			});
		}

		menuBar.getMenus().addAll(menuOptions);

		bp.setTop(menuBar);

		// CENTER - the numbers
		numbers = new TilePane();
		numbers.setVgap(3.5);
		numbers.setHgap(3.5);
		numbers.setPrefColumns(6);

		luckyNumbers = new TilePane();
		luckyNumbers.setHgap(3.5);
		luckyNumbers.setVgap(3.5);

		VBox allNumbers = new VBox();

		allNumbers.setSpacing(20);
		allNumbers.getChildren().addAll(numbers, luckyNumbers);

		bp.setCenter(allNumbers);

		// Bottom - controls
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

	// Updates the GUI elements after the language gets changed
	public void updateTexts() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();
		menuOptions.setText(t.getString("program.menu.options"));
		menuOptionsChangeLT.setText(t.getString("lt.menu.options.changeLT"));
		menuOptionsLanguage.setText(t.getString("program.menu.options.language"));
		btnPlay.setText(t.getString("lt.button"));

	}

}
