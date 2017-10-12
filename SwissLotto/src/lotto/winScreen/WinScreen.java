package lotto.winScreen;

import java.util.Random;
import java.util.TreeSet;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import lotto.ServiceLocator;
import lotto.commonClasses.Translator;
import lotto.lotteryTicket.LotteryTicketModel;


public class WinScreen {
	
	
	
	private Random rand = new Random();
	private Stage stage;
	


	public WinScreen(LotteryTicketModel model) {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();
		VBox root = new VBox();

		TreeSet<Integer> randNumbers = this.generateRandNumbers(model.getChooseNumber(), model.getMaxNumber());
		TreeSet<Integer> randLucky = this.generateRandNumbers(model.getChooseLucky(), model.getMaxLucky());
		
		
		
		Label yourNumbers = new Label(t.getString("ws.label.yourNumbers"));
		yourNumbers.setStyle("-fx-font-size: 40;");
		
		HBox numbers = this.createLabels(model.getChosenNumbers(), model.getChosenLuckys(), randNumbers, randLucky);
		
		Label theLottery = new Label(t.getString("ws.label.lotteryNumbers"));
		theLottery.setStyle("-fx-font-size: 40;");
		HBox lotteryDraw  = this.createLabels(randNumbers, randLucky, model.getChosenNumbers(), model.getChosenLuckys());

		root.getChildren().addAll(yourNumbers, numbers, theLottery, lotteryDraw);
		Scene scene = new Scene(root);
		this.stage = new Stage();
		stage.setScene(scene);

		stage.show();
	}

	//Sets styling for all Labels in this screen
	public Label designLabel(Label l) {
		l.setMinWidth(75);
		l.setAlignment(Pos.CENTER);
		l.setStyle("-fx-font-size: 40;");
		return l;
	}

	public TreeSet<Integer> generateRandNumbers(int number, int upperLimit) {
		//
		if (number > upperLimit) {
			return null;
		}
		TreeSet<Integer> ts = new TreeSet<Integer>();

		while (ts.size() < number) {
			ts.add(rand.nextInt(upperLimit) + 1);
		}

		return ts;
	}

	public HBox createLabels(TreeSet<Integer> numbers, TreeSet<Integer> luckyNumbers, TreeSet<Integer> otherNumbers,
			TreeSet<Integer> otherLuckys) {
		HBox allNumbers = new HBox();
	

		
		for(Integer i : numbers) {
			Label l = new Label();
			l = this.designLabel(l);
			l.setText(Integer.toString(i));
			
			if (otherNumbers.contains(i)) {
				l.setStyle(l.getStyle() + "-fx-background-color: lime;");
			}

			allNumbers.getChildren().add(l);
			
		}
		
		Label plus = new Label("+");
		plus = this.designLabel(plus);
		allNumbers.getChildren().add(plus);
		
		for(Integer i: luckyNumbers) {
			Label l = new Label();
			l = this.designLabel(l);
			l.setText(Integer.toString(i));
			
			if(otherLuckys.contains(i)) {
				l.setStyle(l.getStyle() + "-fx-background-color: lime;");
			}
			
			allNumbers.getChildren().add(l);
		}
		
				
		return allNumbers;
	}

	
	public Stage getStage() {
		return this.stage;
	}

}
