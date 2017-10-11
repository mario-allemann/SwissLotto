package lotto.lotteryTicket;

import java.math.BigInteger;
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
	
	TreeSet<Integer> chosenNumbers = new TreeSet<Integer>();
	public ArrayList<Button> numberButtons = new ArrayList<Button>();
	public ArrayList<Button> luckyNumberButtons = new ArrayList<Button>();
	
	public void removeNumber(int number) {
		chosenNumbers.remove(Integer.valueOf(number));
	}



	public void addNumber(int number) {
		chosenNumbers.add(number);
		
	}
	
	public double getChanceAsPercentage(int pickedCorrecttNormal, int pickedCorrectLucky) {
		int rightNumbers = this.chooseNumber;
		int wrongNumbers = this.maxNumber - this.chooseNumber;
		int pickedWrongNormal = this.chooseNumber-pickedCorrecttNormal;
		
		
<<<<<<< HEAD
		long chancesNormal = binomi(rightNumbers, pickedRightNormal) * binomi(wrongNumbers, pickedWrongNormal);
=======
		BigInteger chancesNormal = chancesNormal.add((binomi(rightNumbers, pickedCorrecttNormal)) * binomi(wrongNumbers, pickedWrongNormal));
		System.out.println("Chanc norm:" +chancesNormal);
>>>>>>> branch 'master' of https://github.com/mario-allemann/SwissLotto.git
		long totalChanceNormal = binomi(this.maxNumber, this.chooseNumber);
		
		
		int rightNumbersLucky = this.chooseLucky;
		int wrongNumbersLucky = this.maxLucky - this.chooseLucky;
		int pickedWrongLucky = this.chooseLucky - pickedCorrectLucky;
		
		long chanceLucky = binomi(rightNumbersLucky, pickedCorrectLucky) * binomi(wrongNumbersLucky, pickedWrongLucky);
		long totalChanceLucky = binomi(this.maxLucky, this.chooseLucky);
		System.out.println(((chancesNormal/totalChanceNormal) * (chanceLucky/totalChanceLucky))*100);
		return ((chancesNormal/totalChanceNormal) * (chanceLucky/totalChanceLucky))*100;
		
		
		
		
		
	}
	
	

		

	//SRC: http://www.programming-idioms.org/idiom/67/binomial-coefficient-n-choose-k/281/java
	static BigInteger binomi(int N, int K) {
	    BigInteger ret = BigInteger.ONE;
	    for (int k = 0; k < K; k++) {
	        ret = ret.multiply(BigInteger.valueOf(N-k))
	                 .divide(BigInteger.valueOf(k+1));
	    }
	    return ret;
	}
	




	
	
	
	
}
