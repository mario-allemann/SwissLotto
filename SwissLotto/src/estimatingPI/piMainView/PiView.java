package estimatingPI.piMainView;

import estimatingPI.ServiceLocator;
import estimatingPI.abstractClasses.View;
import estimatingPI.commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.StringConverter;

public class PiView extends View<PiModel> {
	
	
	//Bottom1:
	Label lblPiEstimate;
	Label lblEstimateAccuracy;
	
	//Bottom2:
	Button btnStart;
	Button btnStop;
	Slider slider;
	
	

	public PiView(Stage stage, PiModel model) {
		super(stage, model);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Scene create_GUI() {
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();
		
		
		BorderPane main = new BorderPane();
		//Top: Menu Bar
		
		//Center: Circle and Square
		
		//Bottom 1: Pi estimation
		VBox bottom1 = new VBox();
		Label estimate = new Label(t.getString("mw.lblEstimate"));
		lblPiEstimate = new Label();
		Label lblEstimateAccuracy = new Label(t.getString("mw.lblEsitmateAccuracy"));
		
		bottom1.getChildren().addAll(estimate, lblPiEstimate, lblEstimateAccuracy);
		
		//Bottom 2: controls
		HBox bottom2 = new HBox();
		btnStart = new Button(t.getString("mw.btnStart"));
		btnStop = new Button(t.getString("mw.btnStop"));
		slider = new Slider(0, 1, 0.5);
		
		
		//TODO Add text to slider positions
		slider.setShowTickLabels(true);
		
		
		
		bottom2.getChildren().addAll(btnStart, btnStop, slider);
		
		//Bottom
		VBox bottom = new VBox();
		bottom.getChildren().addAll(bottom1, bottom2);
		
		main.setBottom(bottom);
		
		
		Scene scene = new Scene(main);
		
		return scene;
		
		
	}
	







}
