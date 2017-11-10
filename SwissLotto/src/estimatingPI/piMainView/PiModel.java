package estimatingPI.piMainView;

import java.util.ArrayList;

import estimatingPI.abstractClasses.Model;

public class PiModel extends Model {
	protected int outOfCircle;
	protected int totalPoints;
	
	public double estimatePi() {
		System.out.println("Inside: " + (totalPoints - outOfCircle));
		System.out.println("Outside: " + outOfCircle);
		System.out.println("Total: " + totalPoints);
		// 1.0* for an automatic double cast
		return (4 * ((1.0 * totalPoints - outOfCircle) / totalPoints));
	}

	public double comparePi(double estimation) {
		System.out.println(Math.PI);
		return estimation/Math.PI;
		
		
	}

}
