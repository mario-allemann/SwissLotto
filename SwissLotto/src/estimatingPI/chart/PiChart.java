package estimatingPI.chart;

import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.stage.Stage;

public class PiChart {

	Stage stage;

	public PiChart(int totalPoints, PiChartData piData) {

		final NumberAxis xAxis = new NumberAxis(0, totalPoints, totalPoints / 10);
		xAxis.setLabel("Generated Points");
		final NumberAxis yAxis = new NumberAxis(2.5, 3.5, 0.1);
		yAxis.setLabel("Pi estimation");
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		//Adds two points to the chart (estimation and PI constant)
		lineChart.getData().addAll(piData.series, piData.constantPI);
		lineChart.setCreateSymbols(false);

		Scene scene = new Scene(lineChart);

		stage = new Stage();
		stage.setScene(scene);
		stage.setMaximized(true);
		stage.show();

	}

	public void start() {

	}

	public void stop() {
		stage.close();
	}

}
