package lotto.lotteryTicket;

import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;

import javafx.scene.control.Button;
import lotto.ServiceLocator;
import lotto.abstractClasses.Model;

public class LotteryTicketModel extends Model{
	ServiceLocator sl = ServiceLocator.getServiceLocator();
	
	protected int chooseNumber = Integer.parseInt(sl.getConfiguration().getOption("ChooseNumber"));
	protected int maxNumber = Integer.parseInt(sl.getConfiguration().getOption("MaxNumber"));
	protected int chooseLucky = Integer.parseInt(sl.getConfiguration().getOption("ChooseLucky"));
	protected int maxLucky = Integer.parseInt(sl.getConfiguration().getOption("MaxLucky"));
	
	ArrayList<Integer> chosenNumbers = new ArrayList<Integer>();
	public ArrayList<Button> numberButtons = new ArrayList<Button>();
	public ArrayList<Button> luckyNumberButtons = new ArrayList<Button>();
	
	public void removeNumber(int number) {
		chosenNumbers.remove(Integer.valueOf(number));
	}



	public void addNumber(int number) {
		chosenNumbers.add(number);
		
	}
	
	public double getChanceAsPercentage(int pickedRightNormal, int pickedRightLucky) {
		int rightNumbers = this.chooseNumber;
		int wrongNumbers = this.maxNumber - this.chooseNumber;
		int pickedWrongNormal = this.chooseNumber-pickedRightNormal;
		
		
		long chancesNormal = binomi(rightNumbers, pickedRightNormal) * binomi(wrongNumbers, pickedWrongNormal);
		System.out.println("Chanc norm:" +chancesNormal);
		long totalChanceNormal = binomi(this.maxNumber, this.chooseNumber);
		
		
		int rightNumbersLucky = this.chooseLucky;
		int wrongNumbersLucky = this.maxLucky - this.chooseLucky;
		int pickedWrongLucky = this.chooseLucky - pickedRightLucky;
		
		long chanceLucky = binomi(rightNumbersLucky, pickedRightLucky) * binomi(wrongNumbersLucky, pickedWrongLucky);
		long totalChanceLucky = binomi(this.maxLucky, this.chooseLucky);
		System.out.println(((chancesNormal/totalChanceNormal) * (chanceLucky/totalChanceLucky))*100);
		return ((chancesNormal/totalChanceNormal) * (chanceLucky/totalChanceLucky))*100;
		
		
		
		
		
	}
	
	
	
	//SRC: https://stackoverflow.com/questions/36925730/java-calculating-binomial-coefficient by Mad Matts
	public long binomi(long n, long k) {
		if(k==0) {
			return 1;
		}
		
		if(k>n/2) {
			return binomi(n,n-k);
		}
		return n * binomi(n-1,k-1) / k;
		
	}
	
	




	
	
	
	
}
