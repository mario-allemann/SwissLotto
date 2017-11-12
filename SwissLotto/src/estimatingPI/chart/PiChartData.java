package estimatingPI.chart;

import javafx.scene.chart.XYChart;

public class PiChartData {

	XYChart.Series<Integer, Double> series;
	
	public PiChartData() {
		series = new XYChart.Series<>();
	}
	
	public void addPoint(int pointPos, double piEstimate) {
		series.getData().add(new XYChart.Data<>(pointPos, piEstimate));
	}
	
}
