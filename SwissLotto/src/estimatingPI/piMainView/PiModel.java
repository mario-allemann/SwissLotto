package estimatingPI.piMainView;

import estimatingPI.abstractClasses.Model;
import estimatingPI.chart.PiChartData;

public class PiModel extends Model {
	protected int outOfCircle;
	protected int totalPoints;
	protected double speed;
	protected PiChartData piData = new PiChartData();
	
	
	public double estimatePi() {
		// 1.0* for an automatic double cast
		double piEstimate =(4 * ((1.0 * totalPoints - outOfCircle) / totalPoints));
		piData.addPoint(totalPoints, piEstimate);
		
		return piEstimate;
	}


}
