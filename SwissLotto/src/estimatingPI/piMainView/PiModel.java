package estimatingPI.piMainView;

import estimatingPI.abstractClasses.Model;
import estimatingPI.chart.PiChartData;

public class PiModel extends Model {
	protected int outOfCircle;
	protected int totalPoints;
	protected double speed;
	protected PiChartData piData = new PiChartData();

	protected final int MAXPLOTTABLEPOINTS = 7500;
	protected boolean maxPointsReached = false;

	/**
	 * Given the points on the pane, estimate PI
	 * @return A estimation of PI
	 */
	public double estimatePi() {
		// 1.0* for an automatic double cast
		double piEstimate = (4 * ((1.0 * totalPoints - outOfCircle) / totalPoints));

		// Plotting on the line chart stops after a certain amount as this would
		// drastically slow down performance
		if (!maxPointsReached) {
			piData.addPoint(totalPoints, piEstimate);

			if (totalPoints > MAXPLOTTABLEPOINTS) {
				maxPointsReached = true;
			}
		}

		return piEstimate;
	}

}
