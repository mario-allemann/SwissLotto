package lotto.lotteryTicket;

import java.util.ArrayList;
import java.util.TreeSet;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import lotto.ServiceLocator;
import lotto.abstractClasses.Controller;
import lotto.changeLotteryTicket.ChangeLotteryTicketController;
import lotto.changeLotteryTicket.ChangeLotteryTicketModel;
import lotto.changeLotteryTicket.ChangeLotteryTicketView;
import lotto.commonClasses.Translator;
import lotto.winScreen.WinScreen;

public class LotteryTicketController extends Controller<LotteryTicketModel, LotteryTicketView> {
	private String cssClicked;

	private int normalCount = 0;
	private int luckyCount = 0;
	private boolean normalsLocked = false;
	private boolean luckysLocked = false;

	private Stage oldStage;
	private Stage chancesStage;

	public ArrayList<ToggleButton> normalButtons;
	public ArrayList<ToggleButton> luckyButtons;

	public LotteryTicketController(LotteryTicketModel model, LotteryTicketView view) {

		super(model, view);

		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();

		normalButtons = new ArrayList<ToggleButton>();
		luckyButtons = new ArrayList<ToggleButton>();

		// Sets up the normal numbers
		for (int i = 1; i <= model.maxNumber; i++) {
			ToggleButton number = new ToggleButton(Integer.toString(i));
			number.getStyleClass().add("button-normal");
			normalButtons.add(number);
			view.numbers.getChildren().add(number);
		}
		// Sets up the lucky numbers
		for (int i = 1; i <= model.maxLucky; i++) {
			ToggleButton luckyNumber = new ToggleButton(Integer.toString(i));

			luckyNumber.setShape(new Circle(2));
			luckyNumber.getStyleClass().add("button-normal");
			luckyNumber.setMinWidth(50);
			luckyNumber.setMinHeight(50);
			luckyButtons.add(luckyNumber);
			view.luckyNumbers.getChildren().add(luckyNumber);
		}

		this.addActionEventNormal();
		this.addActionEventLuckys();

		// Opens up a menu which allows the user to modify the lottery ticket
		view.menuOptionsChangeLT.setOnAction((event) -> {

			Stage changeLTStage = new Stage();
			ChangeLotteryTicketModel changeLTModel = new ChangeLotteryTicketModel();
			ChangeLotteryTicketView changeLTView = new ChangeLotteryTicketView(changeLTStage, changeLTModel);
			new ChangeLotteryTicketController(changeLTModel, changeLTView, view);
			changeLTView.start();
		});

		// Opens up a play menu
		view.btnPlay.setOnAction((event) -> {
			// Allows only one WinScreen to be active. Thus the play Button can be spammed
			if (this.oldStage != null) {
				this.oldStage.close();
			}

			WinScreen winScreen = new WinScreen(model);
			this.oldStage = winScreen.getStage();
		});

		// Shows the chances
		view.btnChance.setOnAction((event) -> {
			if (this.chancesStage != null) {
				this.chancesStage.close();
			}
			ScrollPane root = new ScrollPane();
			root.setStyle("-fx-background: #FEE9E9;");
			root.getStyleClass().add("chances");
			// root.getStyleClass().add("chances");
			VBox main = this.calculateChances();
			main.setStyle("-fx-font-size: 20");
			root.setContent(main);
			Scene scene = new Scene(root);
			this.chancesStage = new Stage();
			this.chancesStage.setScene(scene);
			this.chancesStage.setTitle(t.getString("lt.chances"));
			this.chancesStage.show();

		});
		// Changes language
		view.menuOptionsLanguage.setOnAction((event) -> {
			view.updateTexts();
		});

	}

	/**
	 * Adds ActionEvents on the normal buttons to change color and lock them if too
	 * many get selected
	 * 
	 */
	public void addActionEventNormal() {
		for (ToggleButton b : normalButtons) {

			b.setOnAction((event) -> {
				// If the button got selected change its color
				if (b.isSelected()) {
					b.setStyle(cssClicked);
					b.getStyleClass().add("button-clicked");
					normalCount++;

					// If the button already got selected and ist clicked once again, change
					// color to default
				} else {
					b.getStyleClass().remove("button-clicked");
					b.getStyleClass().add("button-normal");
					normalCount--;

				}
				// Lock the buttons if too many have been clicked
				if (normalCount >= model.maxChooseNormal) {
					lockButtons(normalButtons, model.chosenNumbers);
					normalsLocked = true;
					playButtonActivation();

				}
				// If a user has selected the max number of buttons and deselects a button,
				// unlock the other buttons to allow the user to select a new one
				if (normalCount < model.maxChooseNormal) {
					unlockButtons(normalButtons);
					normalsLocked = false;
					playButtonActivation();
				}
			});

		}

	}

	/**
	 * Adds ActionEvents on the lucky buttons to change color and lock them if too
	 * many get selected
	 * 
	 */
	public void addActionEventLuckys() {
		for (ToggleButton b : luckyButtons) {

			b.setOnAction((event) -> {
				// If the button got selected change its color
				if (b.isSelected()) {
					b.setStyle(cssClicked);
					b.getStyleClass().add("button-clicked");
					luckyCount++;

					// If the button already got selected and ist clicked once again, change
					// color to default
				} else {
					b.getStyleClass().remove("button-clicked");
					b.getStyleClass().add("button-luckys");
					luckyCount--;

				}
				// Lock the buttons if too many have been clicked
				if (luckyCount >= model.maxChooseLucky) {
					lockButtons(luckyButtons, model.chosenLuckys);
					luckysLocked = true;
					playButtonActivation();

				}
				// If a user has selected the max number of buttons and deselects a button,
				// unlock the other buttons to allow the user to select a new one
				if (luckyCount < model.maxChooseLucky) {
					unlockButtons(luckyButtons);
					luckysLocked = false;
					playButtonActivation();
				}
			});

		}

	}

	/**
	 * Locks all buttons which are not selected, saves the selected numbers to a
	 * TreeSet
	 * 
	 * @param numberCountay
	 *            An ArrayList with buttons
	 * @param numbers
	 *            A container for the user selection
	 */

	private void lockButtons(ArrayList<ToggleButton> bArray, TreeSet<Integer> numbers) {
		numbers.clear();
		for (ToggleButton b : bArray) {
			if (!b.isSelected()) {
				b.setDisable(true);
			} else {
				model.addNumber(Integer.parseInt(b.getText()), numbers);
			}
		}

	}

	/**
	 * Unlocks all buttons
	 * 
	 * @param bArray
	 *            An ArrayList with buttons
	 */
	private void unlockButtons(ArrayList<ToggleButton> bArray) {

		for (ToggleButton b : bArray) {
			b.setDisable(false);
		}
	}

	// Checks if the required amount of buttons got selected in order to start the
	// draw (unlock the play Button)
	private void playButtonActivation() {
		if (normalsLocked && luckysLocked) {
			view.btnPlay.setDisable(false);
		} else {
			view.btnPlay.setDisable(true);
		}
	}

	// Iterates through all win-possibilites, calculates their chances and returns a
	// VBox with labels
	public VBox calculateChances() {
		VBox v = new VBox();

		for (int normalNumber = model.maxChooseNormal; normalNumber >= 0; normalNumber--) {
			for (int luckyNumber = model.maxChooseLucky; luckyNumber >= 0; luckyNumber--) {
				String labelString = normalNumber + " + " + luckyNumber + "\t"
						+ model.getChanceAsPercentage(normalNumber, luckyNumber) + "%";
				v.getChildren().add(new Label(labelString));
			}
		}
		return v;
	}

}
