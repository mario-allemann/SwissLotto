package lotto.changeLotteryTicket;

import javafx.stage.Stage;
import lotto.ServiceLocator;
import lotto.abstractClasses.Controller;
import lotto.commonClasses.Configuration;
import lotto.commonClasses.Translator;
import lotto.lotteryTicket.*;

public class ChangeLotteryTicketController extends Controller<ChangeLotteryTicketModel, ChangeLotteryTicketView> {

	public ChangeLotteryTicketController(ChangeLotteryTicketModel model, ChangeLotteryTicketView view) {
		super(model, view);
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Configuration c = sl.getConfiguration();
		Translator t = sl.getTranslator();
		final int MAX_NUM_OF_BUTTONS = 100;
		
		
		view.save.setOnAction((event) -> {
			boolean isValidInput = true;
			for (TextFieldWProperty tf : model.tfp) {
				

				// Check if input is valid
				try {
					int number = Integer.parseInt(tf.getText());
					if (number <= 0) {
						view.notification.setText(t.getString("cLT.label.notification.onlyPositiveNumbers"));
						isValidInput = false;
						break;
					}
					if (number > MAX_NUM_OF_BUTTONS) {
						view.notification
								.setText(t.getString("cLT.label.notification.maxNumOfButton") + MAX_NUM_OF_BUTTONS);
						isValidInput=false;
						break;
					}
					
					 
					
					model.closeValue = "save";
				} catch (NumberFormatException e) {
					view.notification.setText(t.getString("cLT.label.notification.onlyEnterNumbers"));
					isValidInput = false;
					break;
					
				}

				// set configs and save to file
				c.setLocalOption(tf.property, tf.getText());

			}
			
			//If all inputs are corret save config and close windows
			if (isValidInput) {
				c.save();
				this.createNewLotteryTicket();
				view.stop();
			}
		});

		view.cancel.setOnAction((event) -> {
			model.closeValue = "cancel";
			view.stop();
		});
	}
	

	public void createNewLotteryTicket() {
		Stage changeLTStage = new Stage();
		LotteryTicketModel changeLTModel = new LotteryTicketModel();
		LotteryTicketView changeLTView = new LotteryTicketView(changeLTStage, changeLTModel);
		new LotteryTicketController(changeLTModel, changeLTView);
		changeLTView.start();
	}

}
