package lotto.changeLotteryTicket;

import java.util.ArrayList;

import javafx.stage.Stage;
import lotto.ServiceLocator;
import lotto.abstractClasses.Controller;
import lotto.commonClasses.Configuration;
import lotto.commonClasses.Translator;
import lotto.lotteryTicket.*;

public class ChangeLotteryTicketController extends Controller<ChangeLotteryTicketModel, ChangeLotteryTicketView> {

	public ChangeLotteryTicketController(ChangeLotteryTicketModel model, ChangeLotteryTicketView view,
			LotteryTicketView ticketView) {
		super(model, view);
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Configuration c = sl.getConfiguration();
		Translator t = sl.getTranslator();
		final int MAX_NUM_OF_BUTTONS = 100;

		view.save.setOnAction((event) -> {

			ArrayList<Integer> userInputs = new ArrayList<Integer>();

			// Adds user inputs to an ArrayList for easier use
			for (TextFieldWProperty tf : model.tfp) {
				try {
					userInputs.add(Integer.parseInt(tf.getText()));
				}
				// Checks whether user has entered non-numbers
				catch (NumberFormatException e) {
					view.notification.setText(t.getString("cLT.label.notification.onlyEnterNumbers"));
					return;
				}
			}

			// The user cannot select more numbers than there are on the lottery ticket
			if (userInputs.get(0) > userInputs.get(1) || userInputs.get(2) > userInputs.get(3)) {
				view.notification.setText(t.getString("cLT.label.notification.totalNumbesTooSmall"));
				return;
			}

			for (Integer i : userInputs) {

				try {
					// Checks whether the user has entered negative numbers
					if (i <= 0) {
						view.notification.setText(t.getString("cLT.label.notification.onlyPositiveNumbers"));
						return;
					}
					// Checks whether the user wants too many buttons (e.g. Generating 1'000'000
					// Buttons can't be handled by the system)
					if (i > MAX_NUM_OF_BUTTONS) {
						view.notification
								.setText(t.getString("cLT.label.notification.maxNumOfButton") + MAX_NUM_OF_BUTTONS);
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();

				}

			}
			// If the inputs are correct, the config can be set
			for (TextFieldWProperty tf : model.tfp) {
				c.setLocalOption(tf.property, tf.getText());
			}

			// Save config, stop the old ticket, create a new one, and stop config view
			c.save();
			ticketView.stop();
			this.createNewLotteryTicket();
			view.stop();

		});

		// Stop the view do nothing else
		view.cancel.setOnAction((event) -> {
			view.stop();
		});
	}

	// Creates a new lottery ticket
	public void createNewLotteryTicket() {
		Stage changeLTStage = new Stage();
		LotteryTicketModel changeLTModel = new LotteryTicketModel();
		LotteryTicketView changeLTView = new LotteryTicketView(changeLTStage, changeLTModel);
		new LotteryTicketController(changeLTModel, changeLTView);
		changeLTView.start();
	}

}
