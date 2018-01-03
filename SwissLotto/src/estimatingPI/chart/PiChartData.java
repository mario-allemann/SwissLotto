package estimatingPI.chart;

import javafx.scene.chart.XYChart;
/**
 * Holds the data for the points seen in the chart
 * @author Mario
 *
 */
public class PiChartData {

	XYChart.Series<Integer, Double> series;
	XYChart.Series<Integer, Double> constantPI;

	public PiChartData() {
		series = new XYChart.Series<>();
		constantPI = new XYChart.Series<>();
		series.setName("Estimate");
		constantPI.setName("Pi");
		
	}
	
	/**
	 * Adds a point to the XY-Chart
	 * @param pointPos The n-th point
	 * @param piEstimate The estimate value of pi
	 */
	public void addPoint(int pointPos, double piEstimate) {
		series.getData().add(new XYChart.Data<>(pointPos, piEstimate));
		constantPI.getData().add(new XYChart.Data<>(pointPos, Math.PI));
	}
	
}
