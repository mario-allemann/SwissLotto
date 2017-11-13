package estimatingPI.chart;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

public class PiChart {

	
	
	Stage stage;


	
	public PiChart(int totalPoints, PiChartData piData) {
		
		

		
		
		NumberAxis xAxis = new NumberAxis(0,totalPoints, totalPoints/10);
		xAxis.setLabel("Generated Points");
		NumberAxis yAxis = new NumberAxis(2,4,0.1);
		yAxis.setLabel("Pi estimation");
		LineChart<Integer, Double> lineChart = new LineChart(xAxis, yAxis);
		
		
		lineChart.getData().add(piData.series);
	
		
		Scene scene = new Scene(lineChart);
		
		stage = new Stage();
		stage.setScene(scene);
		stage.show();
		
	}
	
	

	
	
	public void start() {
		
	}
	
	public void stop() {
		stage.close();
	}
	
	
}
