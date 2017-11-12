package estimatingPI.piMainView;

import java.util.ArrayList;

import estimatingPI.abstractClasses.Model;
import estimatingPI.chart.PiChartData;

public class PiModel extends Model {
	protected int outOfCircle;
	protected int totalPoints;
	protected double speed;
	protected PiChartData piData = new PiChartData();
	
	
	public double estimatePi() {
		System.out.println("Inside: " + (totalPoints - outOfCircle));
		System.out.println("Outside: " + outOfCircle);
		System.out.println("Total: " + totalPoints);
		// 1.0* for an automatic double cast
		double piEstimate =(4 * ((1.0 * totalPoints - outOfCircle) / totalPoints));
		piData.addPoint(totalPoints, piEstimate);
		
		return piEstimate;
	}

	
	
	public double comparePi(double estimation) {
		System.out.println(Math.PI);
		return estimation/Math.PI;
		
		
	}

}
