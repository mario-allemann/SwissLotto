package lotto.winScreen;

import java.util.Random;
import java.util.TreeSet;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

		root.getStylesheets().add(sl.getStylesheet());
		root.getStyleClass().add("background");

		// The lottery draw
		TreeSet<Integer> randNumbers = this.generateRandNumbers(model.getChooseNumber(), model.getMaxNumber());
		TreeSet<Integer> randLucky = this.generateRandNumbers(model.getChooseLucky(), model.getMaxLucky());

		Label yourNumbers = new Label(t.getString("ws.label.yourNumbers"));

		HBox numbers = this.createLabels(model.getChosenNumbers(), model.getChosenLuckys(), randNumbers, randLucky);
		yourNumbers.getStyleClass().add("label-normal");
		Label theLottery = new Label(t.getString("ws.label.lotteryNumbers"));
		theLottery.getStyleClass().add("label-normal");
		HBox lotteryDraw = this.createLabels(randNumbers, randLucky, model.getChosenNumbers(), model.getChosenLuckys());

		root.getChildren().addAll(yourNumbers, numbers, theLottery, lotteryDraw);
		Scene scene = new Scene(root);
		this.stage = new Stage();
		stage.setScene(scene);
		stage.setTitle(t.getString("ws.title"));
		stage.show();

		// Jackpot function
		if (model.getChosenNumbers().equals(randNumbers) && model.getChosenLuckys().equals(randLucky)) {
			System.out.println("Jackpot");
			Pane p = sl.getJackpotGIF();

			Scene jackpotScene = new Scene(p);
			Stage stage = new Stage();
			stage.setScene(jackpotScene);
			stage.setTitle("JACKPOT");
			stage.show();

		}
	}

	public TreeSet<Integer> generateRandNumbers(int number, int upperLimit) {

		TreeSet<Integer> ts = new TreeSet<Integer>();

		while (ts.size() < number) {
			ts.add(rand.nextInt(upperLimit) + 1);
		}

		return ts;
	}

	public HBox createLabels(TreeSet<Integer> numbers, TreeSet<Integer> luckyNumbers, TreeSet<Integer> otherNumbers,
			TreeSet<Integer> otherLuckys) {
		HBox allNumbers = new HBox();

		for (Integer i : numbers) {
			Label l = new Label();
			l.getStyleClass().add("label-normal");
			l.setText(Integer.toString(i));

			if (otherNumbers.contains(i)) {
				l.getStyleClass().add("label-correct");
			}

			allNumbers.getChildren().add(l);

		}

		Label plus = new Label("+");
		plus.getStyleClass().add("label-normal");
		allNumbers.getChildren().add(plus);

		for (Integer i : luckyNumbers) {
			Label l = new Label();
			l.getStyleClass().add("label-normal");
			l.setText(Integer.toString(i));

			if (otherLuckys.contains(i)) {
				l.getStyleClass().add("label-correct");
			}

			allNumbers.getChildren().add(l);
		}

		return allNumbers;
	}

	public Stage getStage() {
		return this.stage;
	}

}
