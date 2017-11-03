package lotto.lotteryTicket;

import java.util.ArrayList;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import javafx.scene.control.Button;
import javafx.stage.Stage;
import lotto.abstractClasses.Controller;
import lotto.changeLotteryTicket.ChangeLotteryTicketController;
import lotto.changeLotteryTicket.ChangeLotteryTicketModel;
import lotto.changeLotteryTicket.ChangeLotteryTicketView;
import lotto.winScreen.WinScreen;

public class LotteryTicketController extends Controller<LotteryTicketModel, LotteryTicketView> {
	private String cssGreen = "-fx-background-color: lime";

	private AtomicInteger numberCount = new AtomicInteger(0);
	private AtomicInteger luckyCount = new AtomicInteger(0);
	private AtomicBoolean buttonsLocked = new AtomicBoolean(false);
	private AtomicBoolean luckyButtonsLocked = new AtomicBoolean(false);

	private Stage oldStage;

	public LotteryTicketController(LotteryTicketModel model, LotteryTicketView view) {
		super(model, view);

		this.addActionEvent(model.numberButtons, numberCount, model.chooseNumber, model.chosenNumbers,
				this.buttonsLocked);
		this.addActionEvent(model.luckyNumberButtons, luckyCount, model.chooseLucky, model.chosenLuckys,
				this.luckyButtonsLocked);

		// Opens up a menu which allows the user to modify the lottery ticket
		view.menuOptionsChangeLT.setOnAction((event) -> {

			Stage changeLTStage = new Stage();
			ChangeLotteryTicketModel changeLTModel = new ChangeLotteryTicketModel();
			ChangeLotteryTicketView changeLTView = new ChangeLotteryTicketView(changeLTStage, changeLTModel);
			new ChangeLotteryTicketController(changeLTModel, changeLTView);
			changeLTView.start();
			changeLTView.getStage().setOnHidden((event2) -> {
				if (changeLTView.getCloseValue().equals("save")) {
					view.stop();
				}
			});
		});

		// Opens up a play menu
		view.play.setOnAction((event) -> {
			// Allows only one WinScreen to be active. Thus the play Button can be spammed
			if (this.oldStage != null) {
				this.oldStage.close();
			}

			WinScreen winScreen = new WinScreen(model);
			this.oldStage = winScreen.getStage();
		});
		
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
	public void addActionEvent(ArrayList<Button> bArr, AtomicInteger count, int maxClickableButtons,
			TreeSet<Integer> numbers, AtomicBoolean areButtonsLocked) {
		for (Button b : bArr) {

			b.setOnAction((event) -> {

				// If the button is not selected (default color), change its color to green
				if (!b.getStyle().equals(cssGreen)) {
					b.setStyle(cssGreen);

					count.incrementAndGet();

					// If the button is already selected (green) and clicked once again, change
					// color to default
				} else {
					b.setStyle(null);
					count.decrementAndGet();

				}
				// Lock the buttons if too many have been clicked
				if (count.get() >= maxClickableButtons) {
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

	private void lockButtons(ArrayList<Button> bArray, TreeSet<Integer> numbers) {
		numbers.clear();
		for (Button b : bArray) {
			if (!b.getStyle().equals(cssGreen)) {
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
	private void unlockButtons(ArrayList<Button> bArray) {

		for (Button b : bArray) {
			b.setDisable(false);
		}
	}

	// Checks if the required amount of buttons got selected in order to start the
	// draw (unlock the play Button)
	private void playButtonActivation() {
		System.out.println(this.buttonsLocked.toString() + this.luckyButtonsLocked);
		if (this.buttonsLocked.get() && this.luckyButtonsLocked.get()) {
			view.play.setDisable(false);
		} else {
			view.play.setDisable(true);
		}
	}

}
