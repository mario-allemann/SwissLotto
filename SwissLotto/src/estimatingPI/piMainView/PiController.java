package estimatingPI.piMainView;

import java.util.ArrayList;
import java.util.Random;

import estimatingPI.ServiceLocator;
import estimatingPI.abstractClasses.Controller;
import estimatingPI.commonClasses.Configuration;
import estimatingPI.commonClasses.Translator;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;


public class PiController extends Controller<PiModel, PiView> {
	
	Random random = new Random();
	
	
	public PiController(PiModel model, PiView view) {
		super(model, view);
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Configuration c = sl.getConfiguration();
		Translator t = sl.getTranslator();
		
		view.btnStart.setOnAction((event) -> {
			this.setPoints(1000000);
			System.out.println(model.estimatePi());
		});
		

	}

	public void setPoint(double sideLength) {
		double posX = random.nextDouble()*sideLength;
		double posY = random.nextDouble()*sideLength;
		
		Circle circle = new Circle(posX, posY, 3);
		circle.setFill(Color.RED);
		view.center.getChildren().add(circle);
		
		if(isOutsideCircle(posX, posY, sideLength)) {
			model.outOfCircle++;
		}
		model.totalPoints++;
		
		
		
		
		
		
	}
	
	public void setPoints(int number) {
		for(int i = 0; i<number; i++) {
			this.setPoint(view.sideLength);
		}
		
	}
	
	public boolean isOutsideCircle(double x, double y, double r) {
		System.out.println("x: " + x + "--->" + Math.pow(x, 2));
		//Is inside
		if((Math.pow(x, 2) + Math.pow(y, 2)) < Math.pow(r, 2)) {
			return false;
		}
		
		//Is outside
		return true;
	}
	

	



}
