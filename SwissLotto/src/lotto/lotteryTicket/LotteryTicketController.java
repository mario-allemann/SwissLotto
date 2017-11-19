package lotto.lotteryTicket;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
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

	private AtomicInteger numberCount = new AtomicInteger(0);
	private AtomicInteger luckyCount = new AtomicInteger(0);
	private AtomicBoolean buttonsLocked = new AtomicBoolean(false);
	private AtomicBoolean luckyButtonsLocked = new AtomicBoolean(false);

	private Stage oldStage;
	private Stage chancesStage;
	

	public LotteryTicketController(LotteryTicketModel model, LotteryTicketView view) {
				
		super(model, view);
		
		

		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();
		
		
		
		this.addActionEvent(model.numberButtons, numberCount, model.chooseNumber, model.chosenNumbers,
				this.buttonsLocked);
		this.addActionEvent(model.luckyNumberButtons, luckyCount, model.chooseLucky, model.chosenLuckys,
				this.luckyButtonsLocked);

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
		
		//Shows the chances
		view.btnChance.setOnAction((event) ->{
			if(this.chancesStage != null) {
				this.chancesStage.close();
			}
			VBox main = this.calculateChances();
			main.setStyle("-fx-background-color: #FEE9E9");
			Scene scene = new Scene(main);
			this.chancesStage = new Stage();
			this.chancesStage.setScene(scene);
			this.chancesStage.setTitle(t.getString("lt.chances"));
			this.chancesStage.show();

			
		});
		//Changes language
		view.menuOptionsLanguage.setOnAction((event) -> {
			view.updateTexts();
		});

	}

	/**
	 * Adds ActionEvents on buttons to change color and lock them if too many get
	 * selected
	 * 
	 * @param bArr
	 *            An ArrayList with buttons
	 * @param count
	 *            How many buttons have been clicked on
	 * @param maxClickableButtons
	 *            The amount of buttons which can be clicked before they get locked
	 * @param numbers
	 *            A container for the user selection
	 * @param areButtonsLocked
	 *            true if the buttons are disabled, false if they are activated
	 */
	public void addActionEvent(ArrayList<ToggleButton> bArr, AtomicInteger count, int maxClickableButtons,
			TreeSet<Integer> numbers, AtomicBoolean areButtonsLocked) {
		for (ToggleButton b : bArr) {

			b.setOnAction((event) -> {
				System.out.println("in event");
				System.out.println(b.isSelected());
				// If the button got selected change its color
				if (b.isSelected()) {
					System.out.println("set style blue");
					b.setStyle(cssClicked);
					b.getStyleClass().add("button-clicked");
					count.incrementAndGet();

					// If the button already got selected  and ist clicked once again, change
					// color to default
				} else {
					b.getStyleClass().remove("button-clicked");
					b.getStyleClass().add("button-normal");
					count.decrementAndGet();

				}
				// Lock the buttons if too many have been clicked
				if (count.get() >= maxClickableButtons) {
					System.out.println("max reached");
					lockButtons(bArr, numbers);
					areButtonsLocked.set(true);
					playButtonActivation();

				}
				// If a user has selected the max number of buttons and deselects a button,
				// unlock the other buttons to allow the user to select a new one
				if (count.get() < maxClickableButtons) {
					unlockButtons(bArr);
					areButtonsLocked.set(false);
					playButtonActivation();
				}
			});

		}

	}

	/**
	 * Locks all buttons which are not selected, saves the selected numbers to a
	 * TreeSet
	 * 
	 * @param bArray
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
		System.out.println(this.buttonsLocked.toString() + this.luckyButtonsLocked);
		if (this.buttonsLocked.get() && this.luckyButtonsLocked.get()) {
			view.btnPlay.setDisable(false);
		} else {
			view.btnPlay.setDisable(true);
		}
	}
	
	
	//Iterates through all win-possibilites, calculates their chances and returns a VBox with labels
	public VBox calculateChances() {
		VBox v = new VBox();

		for(int normalNumber = model.chooseNumber; normalNumber >=0; normalNumber--) {
			for(int luckyNumber = model.chooseLucky; luckyNumber>=0; luckyNumber--) {
				String labelString = normalNumber + " + " + luckyNumber + "\t" + model.getChanceAsPercentage(normalNumber, luckyNumber) + "%";
				v.getChildren().add(new Label(labelString));
			}
		}
		return v;
	}

}
