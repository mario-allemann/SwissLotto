package estimatingPI.piMainView;

import java.util.Random;

import estimatingPI.abstractClasses.Controller;
import estimatingPI.chart.PiChart;
import javafx.application.Platform;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PiController extends Controller<PiModel, PiView> implements Runnable {

	Random random = new Random();
	double sideLengthSquared;
	PiChart chart;
	Thread thread;
	boolean isRunning = false;
	
	public PiController(PiModel model, PiView view) {
		super(model, view);

		sideLengthSquared = Math.pow(view.sideLength, 2);

		// Starts a new Thread
		view.btnStart.setOnAction((event) -> {
			this.isRunning = true;
			this.lockButtons(true);

			thread = new Thread(this);
			thread.start();

		});

		// Stops the Thread
		view.btnStop.setOnAction((event) -> {
			isRunning = false;
			this.lockButtons(false);
		});

		// Adjusts the speed based on the slider position
		view.slider.valueProperty().addListener((observable, oldvalue, newValue) -> {
			model.speed = newValue.doubleValue();
		});

		// Opens a chart window
		view.btnChart.setOnAction((event) -> {
			// Stops points generation
			view.btnStop.fire();
			// Allows only one window of chart to be open
			if (chart != null) {
				chart.stop();
			}
			
			if(model.maxPointsReached) {
				chart = new PiChart(model.maxPlottablePoints, model.piData);
			} else {
				chart = new PiChart(model.totalPoints, model.piData);
			}


		});

		// Clears all the points from the GUI and model
		view.btnClear.setOnAction((event) -> {
			this.clear();
		});

	}

	// Sets a single point on the pane
	public void setPoint(double sideLength) {
		double posX = random.nextDouble() * sideLength;
		double posY = random.nextDouble() * sideLength;
		// System.out.println("x: " + posX + "--->" + Math.pow(posX, 2));
		// System.out.println("y: " + posY + "--->" + Math.pow(posY, 2));
		// System.out.println("x2 + y2: " + Math.pow(posX, 2)+ Math.pow(posY, 2));

		Circle circle = new Circle(posX, posY, 3);
		circle.setFill(Color.RED);

		boolean isOutside = isOutsideCircle(posX, posY);

		// Makes another color for points outside
		if (isOutside) {
			circle.setFill(Color.DARKGREEN);
		}

		view.center.getChildren().add(circle);

		if (isOutside) {
			model.outOfCircle++;
		}
		model.totalPoints++;

		view.lblPiEstimate.setText(Double.toString(model.estimatePi()));

	}

	// Locks or unlocks the buttons, depending on whether or not the thread is
	// running
	public void lockButtons(boolean isRunning) {
		view.btnStart.setDisable(isRunning);
		view.btnStop.setDisable(!isRunning);
		view.btnChart.setDisable(isRunning);
		view.btnClear.setDisable(isRunning);
	}

	public void setPoints(int number) {
		for (int i = 0; i < number; i++) {
			this.setPoint(view.sideLength);
		}

	}

	public boolean isOutsideCircle(double x, double y) {
		// If point is inside
		if ((Math.pow(x, 2) + Math.pow(y, 2)) < sideLengthSquared) {
			return false;
		}

		// If outside
		return true;
	}

	// Creates a complete new window. Only deleting the points would result in lag
	public void clear() {
	
		Stage stage = new Stage();
		PiModel newModel = new PiModel();
		PiView newView = new PiView(stage, new PiModel());
		PiController newController = new PiController(newModel, newView);
		newView.start();
		view.stop();

	}
	

	
	@Override
	public void run() {
		while (this.isRunning) {
			try {
				// Determines the speed at which points are placed. Ranges from one point every
				// second to one point every millisecond (based on slider position)
				Thread.sleep(1001 - (1000 * (int) model.speed));
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Platform.runLater(() -> {
				this.setPoint(view.sideLength);
						
			});

		}

	}

}
