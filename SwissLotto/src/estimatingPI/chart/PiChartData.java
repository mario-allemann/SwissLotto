package estimatingPI.chart;

import javafx.scene.chart.XYChart;

public class PiChartData {

	XYChart.Series<Integer, Double> series;
	XYChart.Series<Integer, Double> constantPI;

	public PiChartData() {
		series = new XYChart.Series<>();
		constantPI = new XYChart.Series<>();
		series.setName("Estimate");
		constantPI.setName("Pi");
		
	}
	
	//Adds a point to the chart for the pi-estimate and the real pi (constant)
	public void addPoint(int pointPos, double piEstimate) {
		series.getData().add(new XYChart.Data<>(pointPos, piEstimate));
		constantPI.getData().add(new XYChart.Data<>(pointPos, Math.PI));
	}
	
}
