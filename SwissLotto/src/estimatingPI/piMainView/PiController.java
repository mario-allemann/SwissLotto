package estimatingPI.piMainView;

import java.util.ArrayList;
import java.util.Random;

import estimatingPI.ServiceLocator;
import estimatingPI.abstractClasses.Controller;
import estimatingPI.chart.PiChart;
import estimatingPI.commonClasses.Configuration;
import estimatingPI.commonClasses.Translator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class PiController extends Controller<PiModel, PiView> implements Runnable {

	Random random = new Random();
	double sideLengthSquared;
	PiChart chart;

	public PiController(PiModel model, PiView view) {
		super(model, view);
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Configuration c = sl.getConfiguration();
		Translator t = sl.getTranslator();
		
		sideLengthSquared = Math.pow(view.sideLength, 2);

		view.btnStart.setOnAction((event) -> {
			this.setPoints(1000000);
			System.out.println(model.estimatePi());
			
		});
		
		
		
		view.slider.valueProperty().addListener((observable,oldvalue,newValue) -> {
			model.speed = newValue.doubleValue();
		});
		
		//Opens a chart window
		view.btnChart.setOnAction((event) ->{
			//Allows only one windows of chart to be open
			if(chart != null) {
				chart.stop();
			}
			chart = new PiChart(model.totalPoints, model.piData);
			
		});
		
	}
	//Sets a single point on the pane
	public void setPoint(double sideLength) {
		double posX = random.nextDouble() * sideLength;
		double posY = random.nextDouble() * sideLength;
//		System.out.println("x: " + posX + "--->" + Math.pow(posX, 2));
//		System.out.println("y: " + posY + "--->" + Math.pow(posY, 2));
//		System.out.println("x2 + y2: " + Math.pow(posX, 2)+ Math.pow(posY, 2));
		
		

		
		Circle circle = new Circle(posX, posY, 3);
		circle.setFill(Color.RED);
		

		boolean isOutside = isOutsideCircle(posX, posY);
		
		//Makes another color for points outside
		if(isOutside) {
			circle.setFill(Color.DARKGREEN);
		}
		
		view.center.getChildren().add(circle);
		

		if (isOutside) {
			model.outOfCircle++;
		}
		model.totalPoints++;
		

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
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}


}
