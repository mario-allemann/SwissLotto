package lotto.winScreen;

import java.util.Iterator;
import java.util.Random;
import java.util.TreeSet;

import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Border;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
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
		Label designedLabel = new Label();
		designedLabel.setStyle("-fx-font-size: 40;"
				+ "");
		TreeSet<Integer> randNumbers = this.generateRandNumbers(model.getChooseNumber(), model.getMaxNumber());
		TreeSet<Integer> randLucky = this.generateRandNumbers(model.getChooseLucky(), model.getMaxLucky());
		
		//Displays the chosen numbers on screen, correct numbers are colored
		for(Integer i : model.getChosenNumbers()) {
			designedLabel.setText(" " + Integer.toString(i) + " ");
			
			if(randNumbers.contains(i)) {
				designedLabel.setStyle(designedLabel.getStyle() + ";" + "-fx-background-color: lime");
			}
			
			chosenNumbers.getChildren().add(designedLabel);
			
		}
		
		HBox draw = new HBox();
		for(Integer i : randLucky) {
			Label l = new Label();
			l.setText(Integer.toString(i));
			l.setStyle("-fx-border-color: black");
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
