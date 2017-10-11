package lotto.winScreen;

import java.util.Random;
import java.util.TreeSet;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lotto.lotteryTicket.LotteryTicketModel;


public class WinScreen {
	private Random rand = new Random();
	private Stage stage;
	
	
	public TreeSet<Integer> generateRandNumbers(int number, int upperLimit) {
		//
		if(number>upperLimit) {
			return null;
		}
		TreeSet<Integer> ts = new TreeSet<Integer>();
		
		while(ts.size()<number) {
			ts.add(rand.nextInt(upperLimit)+1);
			System.out.println(ts.size());
		}
		
		
		return ts;
	}
	
	public WinScreen(LotteryTicketModel model) {
		VBox root = new VBox();
		HBox chosenNumbers = new HBox();

		TreeSet<Integer> randNumbers = this.generateRandNumbers(model.getChooseNumber(), model.getMaxNumber());
		TreeSet<Integer> randLucky = this.generateRandNumbers(model.getChooseLucky(), model.getMaxLucky());
		
		//Displays the chosen numbers on screen, correct numbers are colored
		for(Integer i : model.getChosenNumbers()) {
			Label l = new Label(" " + Integer.toString(i) + " ");
			l.setStyle("-fx-font-size: 40;");
			if(randNumbers.contains(i)) {
				l.setStyle(l.getStyle() + "-fx-background-color: lime;");
			}
			
			chosenNumbers.getChildren().add(l);
			
		}
		
		Label plus = new Label("+");
		plus.setStyle("-fx-font-size: 40;");
		
		chosenNumbers.getChildren().add(plus);
			

		for(Integer i : model.getChosenLuckys()) {
	
			Label l = new Label(" " + Integer.toString(i) + " ");
		
			l.setStyle("-fx-font-size: 40;");
			if(randLucky.contains(i)) {
				l.setStyle(l.getStyle() + "-fx-background-color: lime;");
			}
			
			chosenNumbers.getChildren().add(l);
		}
		
		HBox draw = new HBox();		
		for(Integer i : randNumbers) {
			Label l = new Label(" " + Integer.toString(i) + " ");
			l.setStyle("-fx-font-size: 40;");
			if(model.getChosenNumbers().contains(i)) {
				l.setStyle(l.getStyle() + "-fx-background-color: lime;");
			}
			draw.getChildren().add(l);
			
		}
		Label pluss = new Label("+");
		pluss.setStyle("-fx-font-size: 40;");
		draw.getChildren().add(pluss);
		
		for(Integer i : randLucky) {
			Label l = new Label(" " + Integer.toString(i) + " ");
			l.setStyle("-fx-font-size: 40;");
			
			if(model.getChosenLuckys().contains(i)) {
				l.setStyle(l.getStyle() + "-fx-background-color: lime;");
			}
			draw.getChildren().add(l);
		}
		
		
		
		root.getChildren().addAll(chosenNumbers, draw);
		
		
		

		
		
		
		
		
		
		Scene scene = new Scene(root);
		this.stage = new Stage();
		stage.setScene(scene);
		
		
		stage.show();
	}
	
	public Stage getStage() {
		return this.stage;
	}
	

}
