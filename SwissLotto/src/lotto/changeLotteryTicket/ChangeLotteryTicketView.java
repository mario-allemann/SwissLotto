package lotto.changeLotteryTicket;

import java.util.logging.Logger;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lotto.ServiceLocator;
import lotto.abstractClasses.Model;
import lotto.abstractClasses.View;
import lotto.commonClasses.Configuration;
import lotto.commonClasses.Translator;
import lotto.lotteryTicket.LotteryTicketController;

public class ChangeLotteryTicketView extends View<ChangeLotteryTicketModel> {

	public ChangeLotteryTicketView(Stage stage, ChangeLotteryTicketModel model) {
		super(stage, model);
		// TODO Auto-generated constructor stub
	}
	
	



	Menu menuOptions;
	Menu menuOptionsLanguage;

	protected Button save;
	protected Button cancel;
	protected Label notification;

	protected VBox vb;
	//Sets up a row with Label and Textfield to change the configs
	public void setConfigTF(String translatorKey, String configKey) {
		
		//TODO change architecture to only create one servicelocator,config,translator per class
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		
		Configuration c = sl.getConfiguration();
		Translator t = sl.getTranslator();
		
		Label l = new Label(t.getString(translatorKey));
		//Basically a TextField that holds the configKey as additional information
		TextFieldWProperty tf = new TextFieldWProperty(c.getOption(configKey), configKey);
		model.tfp.add(tf);
		HBox hb = new HBox();
		
		hb.getChildren().addAll(l, tf);
		this.vb.getChildren().addAll(hb);
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Configuration c = sl.getConfiguration();
		Logger logger = sl.getLogger();
		Translator t = sl.getTranslator();


		BorderPane bp = new BorderPane();
		
		//Setting up menu bar
		MenuBar menu = new MenuBar();
		menuOptions = new Menu(t.getString("program.menu.options"));
		menuOptionsLanguage = new Menu("program.menu.language");
		menuOptions.getItems().addAll(menuOptionsLanguage);

		menu.getMenus().addAll(menuOptions);
		bp.setTop(menu);
		
		
		//setting up TextFields and Labels for every config
		this.vb = new VBox();
		this.setConfigTF("cLT.label.chooseNumber", "ChooseNumber");
		this.setConfigTF("cLT.label.maxNumber", "MaxNumber");
		this.setConfigTF("cLT.label.chooseLNumber", "ChooseLucky");
		this.setConfigTF("cLT.label.maxLNumber", "MaxLucky");

		bp.setCenter(this.vb);
		
		
		//Setting up some buttons
		HBox buttons = new HBox();
		save = new Button(t.getString("cLT.button.save"));
		cancel = new Button(t.getString("cLT.button.cancel"));
		notification = new Label();
		buttons.getChildren().addAll(save, cancel,notification);

		bp.setBottom(buttons);

		Scene scene = new Scene(bp);

		return scene;

		// Obsolete code: replaced by setConfigTF
		//
		// HBox chooseNumbers = new HBox();
		// Label chooseNumbersLabel = new Label("How many numbers to choose on the
		// lottery ticket");
		// TextField chooseNumbersTF = new TextField();
		// chooseNumbersTF.setText(sl.getConfiguration().getOption("ChooseNumber"));
		// chooseNumbers.getChildren().addAll(chooseNumbersLabel, chooseNumbersTF);
		//
		// HBox maxNumbers = new HBox();
		// Label maxNumbersLabel = new Label("Numbers of digits on lottery ticket");
		// TextField maxNumbersTF = new TextField();
		// maxNumbersTF.setText(sl.getConfiguration().getOption("MaxNumber"));
		//

	}

}
