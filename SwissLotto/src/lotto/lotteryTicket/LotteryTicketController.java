package lotto.lotteryTicket;

import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import lotto.abstractClasses.Controller;
import lotto.changeLotteryTicket.ChangeLotteryTicketController;
import lotto.changeLotteryTicket.ChangeLotteryTicketModel;
import lotto.changeLotteryTicket.ChangeLotteryTicketView;
import lotto.winScreen.WinScreen;

public class LotteryTicketController extends Controller<LotteryTicketModel, LotteryTicketView> {
	String cssGreen = "-fx-background-color: lime";

	private int numberCount = 0;
	private int luckyNumberCount = 0;
	private Stage oldStage;

	// public void addActionEvents(ArrayList<Button> bArray, int
	// maxNumOfSelectableButtons, int numOfSelectedButtons) {
	// int i = numOfSelectedButtons;
	// for (Button b : bArray) {
	// b.setOnAction((event) -> {
	// // If the button is not selected (default color), change its color to green
	// if (!b.getStyle().equals(cssGreen)) {
	// b.setStyle(cssGreen);
	// numOfSelectedButtons++;
	//
	// // If the button is already selected (green) and clicked once again, change
	// // color to default
	// } else {
	// b.setStyle(null);
	// numOfSelectedButtons--;
	//
	// }
	// // Lock the buttons if too many have been clicked
	// if (numOfSelectedButtons >= maxNumOfSelectableButtons) {
	// lockButtons(bArray);
	// }
	// // If a user has selected the max number of buttons and deselects a button,
	// // unlock the other buttons to allow the user to select a new one
	// if (numOfSelectedButtons < maxNumOfSelectableButtons) {
	// unlockButtons(bArray);
	// }
	//
	// });
	//
	// }
	// }

	public LotteryTicketController(LotteryTicketModel model, LotteryTicketView view) {
		super(model, view);

		// this.addActionEvents(model.numberButtons, model.chooseNumber,
		// this.numberCount);
		// this.addActionEvents(model.luckyNumberButtons, model.chooseLucky,
		// this.luckyNumberCount);

		// Adds ActionEvent on number buttons, which makes them change color if they get
		// selected. Also prevents user from selecting too many Buttons
		for (Button b : model.numberButtons) {

			b.setOnAction((event) -> {
				// If the button is not selected (default color), change its color to green
				if (!b.getStyle().equals(cssGreen)) {
					b.setStyle(cssGreen);
					numberCount++;

					// If the button is already selected (green) and clicked once again, change
					// color to default
				} else {
					b.setStyle(null);
					numberCount--;

				}
				// Lock the buttons if too many have been clicked
				if (numberCount >= model.chooseNumber) {
					lockButtons(model.numberButtons);
				}
				// If a user has selected the max number of buttons and deselects a button,
				// unlock the other buttons to allow the user to select a new one
				if (numberCount < model.chooseNumber) {
					unlockButtons(model.numberButtons);
				}

			});

		}
		// TODO this code is basically copy paste from above.
		// Adds ActionEvent on lucky-number buttons, which makes them change color if
		// they get
		// selected. Also prevents user from selecting too many Buttons
		for (Button b : model.luckyNumberButtons) {

			b.setOnAction((event) -> {
				// If the button is not selected (default color), change its color to green
				if (!b.getStyle().equals(cssGreen)) {
					b.setStyle(cssGreen);
					luckyNumberCount++;

					// If the button is already selected (green) and clicked once again, change
					// color to default
				} else {
					b.setStyle(null);
					luckyNumberCount--;

				}
				// Lock the buttons if too many have been clicked
				if (luckyNumberCount >= model.chooseLucky) {
					lockLuckyButtons(model.luckyNumberButtons);
				}
				// If a user has selected the max number of buttons and deselects a button,
				// unlock the other buttons to allow the user to select a new one
				if (luckyNumberCount < model.chooseLucky) {
					unlockLuckyButtons(model.luckyNumberButtons);
				}

			});

		}
		
		//Opens a menu to change the lottery ticket
		view.menuOptionsChangeLT.setOnAction((event) -> {
			
			Stage changeLTStage = new Stage();
			ChangeLotteryTicketModel changeLTModel = new ChangeLotteryTicketModel();
			ChangeLotteryTicketView changeLTView = new ChangeLotteryTicketView(changeLTStage, changeLTModel);
			new ChangeLotteryTicketController(changeLTModel, changeLTView);
			changeLTView.start();
			changeLTView.getStage().setOnHidden((event2) -> {
				if(changeLTView.getCloseValue().equals("save")){
					view.stop();
				}
			});
//			view.stop();
		});
		
		view.play.setOnAction((event) -> {
			//This allows only one WinScreen to be active. Thus the play Button can be spammed
			if(this.oldStage != null) {
			this.oldStage.close();
			}
			WinScreen winScreen = new WinScreen(model);
			this.oldStage = winScreen.getStage();
		});
		
	}
	
		

	// Locks all number buttons which are not selected, the selected buttons(int
	// values) get saved to an ArrayList
	private void lockButtons(ArrayList<Button> bArray) {
		model.chosenNumbers.clear();
		for (Button b : bArray) {
			if (!b.getStyle().equals(cssGreen)) {
				b.setDisable(true);
			} else {
				model.addNumber(Integer.parseInt(b.getText()));
			}
		}

	}

	// Unlocks all number Buttons
	private void unlockButtons(ArrayList<Button> bArray) {

		for (Button b : bArray) {
			b.setDisable(false);
		}

	}
	
	private void lockLuckyButtons(ArrayList<Button> bArray) {
		model.chosenLuckys.clear();
		for (Button b : bArray) {
			if (!b.getStyle().equals(cssGreen)) {
				b.setDisable(true);
			} else {
				model.addLucky(Integer.parseInt(b.getText()));
			}
		}

	}

	// Unlocks all number Buttons
	private void unlockLuckyButtons(ArrayList<Button> bArray) {

		for (Button b : bArray) {
			b.setDisable(false);
		}

	}
	
	

}
