package lotto.lotteryTicket;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.TreeSet;

import javafx.scene.control.ToggleButton;
import lotto.ServiceLocator;
import lotto.abstractClasses.Model;

public class LotteryTicketModel extends Model{
	ServiceLocator sl = ServiceLocator.getServiceLocator();
	
	
	//Loads all the information relevant for the lottery ticket from the config file
	protected int chooseNumber = Integer.parseInt(sl.getConfiguration().getOption("ChooseNumber"));
	protected int maxNumber = Integer.parseInt(sl.getConfiguration().getOption("MaxNumber"));
	protected int chooseLucky = Integer.parseInt(sl.getConfiguration().getOption("ChooseLucky"));
	protected int maxLucky = Integer.parseInt(sl.getConfiguration().getOption("MaxLucky"));
	
	protected TreeSet<Integer> chosenNumbers = new TreeSet<Integer>();
	protected TreeSet<Integer> chosenLuckys = new TreeSet<Integer>();

	
	
	
	public void addNumber(int number, TreeSet<Integer> numbers) {
		numbers.add(number);
		
	}
	
	public void removeNumber(int number, TreeSet<Integer> numbers) {
		numbers.remove(Integer.valueOf(number));
	}
	

	/** Calculates the chance for a typical lottery scenario
	 * 
	 * 
	 * @param numOfCorrectNumbers How many lottery numbers got guessed correctly
	 * @param numOfDrawnNumbers How many lottery numbers get drawn in a game
	 * @param totalNumbers Total pool of which lottery numbers get drawn from
	 * @return The chance for the given lottery scenario
	 */
	public BigDecimal getChance(int numOfCorrectNumbers, int numOfDrawnNumbers, int totalNumbers) {
		int totalWrongNumbers = totalNumbers-numOfDrawnNumbers;
		int numOfWrongGuesses = numOfDrawnNumbers - numOfCorrectNumbers;
		
		
		BigInteger possibilities = binomi(numOfDrawnNumbers, numOfCorrectNumbers).multiply(binomi(totalWrongNumbers, numOfWrongGuesses));
		BigInteger numOfTotalPossibilities = binomi(totalNumbers, numOfDrawnNumbers);
		
		//Cast to Big Decimal in order to calculate chance. BigInt would be rounded down to zero
		BigDecimal decimalPossiblities = new BigDecimal(possibilities);
		BigDecimal decimalNumOfTotalPossibilities = new BigDecimal(numOfTotalPossibilities);
		
		BigDecimal chance =  decimalPossiblities.divide(decimalNumOfTotalPossibilities, 20, RoundingMode.HALF_EVEN);
		return chance;
		
	}

	
	
	
	/** Calculates the combined chance (normal and lucky numbers) for a lottery ticket
	 * 
	 * @param pickedCorrectNormal How many lottery numbers got guessed correctly
	 * @param pickedCorrectLuckys How many lottery lucky numbers got guessed correctly
	 * @return The chance for a lottery ticket
	 */
	public String getChanceAsPercentage(int pickedCorrectNormal, int pickedCorrectLuckys) {
		
		BigDecimal normalChance = getChance(pickedCorrectNormal, this.chooseNumber, this.maxNumber);
		BigDecimal luckyChance = getChance(pickedCorrectLuckys, this.chooseLucky, this.maxLucky);
		
		return (normalChance.multiply(luckyChance).multiply(BigDecimal.valueOf(100)).toPlainString()).substring(0, 15);
			
		
	}
	
	

	/**Returns the binominal coefficient
	 * 
	 * @param N
	 * @param K
	 * @author http://www.programming-idioms.org/idiom/67/binomial-coefficient-n-choose-k/281/java
	 */
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
