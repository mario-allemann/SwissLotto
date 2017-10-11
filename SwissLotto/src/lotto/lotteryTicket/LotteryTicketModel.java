package lotto.lotteryTicket;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javafx.scene.control.Button;
import lotto.ServiceLocator;
import lotto.abstractClasses.Model;

public class LotteryTicketModel extends Model{
	ServiceLocator sl = ServiceLocator.getServiceLocator();
	
	protected int chooseNumber = Integer.parseInt(sl.getConfiguration().getOption("ChooseNumber"));
	protected int maxNumber = Integer.parseInt(sl.getConfiguration().getOption("MaxNumber"));
	protected int chooseLucky = Integer.parseInt(sl.getConfiguration().getOption("ChooseLucky"));
	protected int maxLucky = Integer.parseInt(sl.getConfiguration().getOption("MaxLucky"));
	
	protected TreeSet<Integer> chosenNumbers = new TreeSet<Integer>();
	protected TreeSet<Integer> chosenLuckys = new TreeSet<Integer>();
	public ArrayList<Button> numberButtons = new ArrayList<Button>();
	public ArrayList<Button> luckyNumberButtons = new ArrayList<Button>();
	
	
	
	public void addNumber(int number) {
		chosenNumbers.add(number);
		
	}
	
	public void removeNumber(int number) {
		chosenNumbers.remove(Integer.valueOf(number));
	}
	
	public void addLucky(int number) {
		this.chosenLuckys.add(number);
	}
	
	public void removeLucky(int number) {
		this.chosenLuckys.remove(Integer.valueOf(number));
	}




	//Calculates the a chance for a lotteryticket.
	public String getChanceAsPercentage(int pickedCorrectNormal, int pickedCorrectLucky) {
		int rightNumbers = this.chooseNumber;
		int wrongNumbers = this.maxNumber - this.chooseNumber;
		int pickedWrongNormal = this.chooseNumber-pickedCorrectNormal;
		
		
		//Possibilites of
		BigInteger possNormal = binomi(rightNumbers, pickedCorrectNormal).multiply(binomi(wrongNumbers, pickedWrongNormal));
		System.out.println("Poss normal: " + possNormal);
		BigInteger totalPossNormal = binomi(this.maxNumber, this.chooseNumber);
		System.out.println("totalPossNormal" + totalPossNormal);
		
		BigDecimal decPossNormal = new BigDecimal(possNormal);
		System.out.println("decPossNormal" + decPossNormal );
		BigDecimal decTotalPossNormal = new BigDecimal(totalPossNormal);
		System.out.println("decTotalPossNormal" + decTotalPossNormal);
		
		
		
		BigDecimal chanceNormal = decPossNormal.divide(decTotalPossNormal, 200, RoundingMode.HALF_EVEN);
	
		
		
		int rightNumbersLucky = this.chooseLucky;
		int wrongNumbersLucky = this.maxLucky - this.chooseLucky;
		int pickedWrongLucky = this.chooseLucky - pickedCorrectLucky;
		
		BigInteger possLucky = binomi(rightNumbersLucky, pickedCorrectLucky).multiply(binomi(wrongNumbersLucky, pickedWrongLucky));
		BigInteger totalPossLucky = binomi(this.maxLucky, this.chooseLucky);
		
		BigDecimal decPossLucky = new BigDecimal(possLucky);
		BigDecimal decTotalPossLucky = new BigDecimal(totalPossLucky);
		
		BigDecimal chanceLucky = decPossLucky.divide(decTotalPossLucky, 200, RoundingMode.HALF_EVEN);
		
		String result = (chanceNormal.multiply(chanceLucky)).multiply(new BigDecimal(100)).toPlainString().substring(0, 15);
		
		return result;
		
		
		
		
	}
	
	


	//SRC: http://www.programming-idioms.org/idiom/67/binomial-coefficient-n-choose-k/281/java
	public BigInteger binomi(int N, int K) {
	    BigInteger ret = BigInteger.ONE;
	    for (int k = 0; k < K; k++) {
	        ret = ret.multiply(BigInteger.valueOf(N-k))
	                 .divide(BigInteger.valueOf(k+1));
	    }
	    return ret;
	}


	//GETTERS AND SETTERS
	
	public int getChooseNumber() {
		return chooseNumber;
	}



	public void setChooseNumber(int chooseNumber) {
		this.chooseNumber = chooseNumber;
	}



	public int getMaxNumber() {
		return maxNumber;
	}



	public void setMaxNumber(int maxNumber) {
		this.maxNumber = maxNumber;
	}



	public int getChooseLucky() {
		return chooseLucky;
	}



	public void setChooseLucky(int chooseLucky) {
		this.chooseLucky = chooseLucky;
	}



	public int getMaxLucky() {
		return maxLucky;
	}



	public void setMaxLucky(int maxLucky) {
		this.maxLucky = maxLucky;
	}

	public TreeSet<Integer> getChosenNumbers() {
		return chosenNumbers;
	}

	public void setChosenNumbers(TreeSet<Integer> chosenNumbers) {
		this.chosenNumbers = chosenNumbers;
	}

	public TreeSet<Integer> getChosenLuckys() {
		return chosenLuckys;
	}

	public void setChosenLuckys(TreeSet<Integer> chosenLuckys) {
		this.chosenLuckys = chosenLuckys;
	}
	




	
	
	
	
}
