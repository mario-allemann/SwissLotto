package estimatingPI.piMainView;

import java.util.Locale;

import estimatingPI.ServiceLocator;
import estimatingPI.abstractClasses.View;
import estimatingPI.commonClasses.Translator;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PiView extends View<PiModel> {

	// Top
	MenuBar menuBar;
	Menu menuOptions;
	Menu menuOptionsLanguage;

	// Bottom1:
	Label lblEstimate;
	Label lblPiEstimate;
	Label lblPi;

	// Bottom2:
	Button btnStart;
	Button btnStop;
	Slider slider;
	Button btnChart;
	Button btnClear;

	// Center
	Arc arc;
	Rectangle rectangle;
	Pane center;

	double sideLength;

	public PiView(Stage stage, PiModel model) {
		super(stage, model);

	}

	@Override
	protected Scene create_GUI() {

		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();

		BorderPane main = new BorderPane();
		// Top: Menu Bar
		menuBar = new MenuBar();
		menuOptions = new Menu(t.getString("mw.menu.options"));
		menuOptionsLanguage = new Menu(t.getString("mw.menu.options.language"));
		menuOptions.getItems().addAll(menuOptionsLanguage);

		/**
		 * @author Brad Richards
		 */
		menuBar.getMenus().add(menuOptions);
		for (Locale locale : sl.getLocales()) {
			MenuItem language = new MenuItem(locale.getLanguage());
			menuOptionsLanguage.getItems().add(language);
			language.setOnAction(event -> {
				sl.getConfiguration().setLocalOption("Language", locale.getLanguage());
				sl.setTranslator(new Translator(locale.getLanguage()));
				updateTexts();
			});
		}

		main.setTop(menuBar);

		// Center: Circle and Square
		sideLength = 750;
		center = new Pane();

		// TODO Set these in constructor
		arc = new Arc();
		arc.setCenterX(0);
		arc.setCenterY(0);
		arc.setRadiusX(sideLength);
		arc.setRadiusY(sideLength);
		arc.setStartAngle(270);
		arc.setLength(90);
		arc.setType(ArcType.ROUND);
		arc.setFill(Color.ORANGE);

		rectangle = new Rectangle(sideLength, sideLength);
		rectangle.setFill(Color.LIGHTBLUE);

		center.getChildren().addAll(rectangle, arc);
		main.setCenter(center);

		// Bottom 1: Pi estimation
		VBox bottom1 = new VBox();
		lblEstimate = new Label(t.getString("mw.lblEstimate"));
		lblPiEstimate = new Label();
		lblPi = new Label(Double.toString(Math.PI));

		bottom1.getChildren().addAll(lblEstimate, lblPiEstimate, lblPi);

		// Bottom 2: controls
		HBox bottom2 = new HBox();
		btnStart = new Button(t.getString("mw.btnStart"));
		btnStop = new Button(t.getString("mw.btnStop"));

		slider = new Slider(0, 1, 0.5);
		btnChart = new Button(t.getString("mw.btnChart"));

		// TODO Add text to slider positions
		slider.setShowTickLabels(true);

		btnClear = new Button(t.getString("mw.btnClear"));

		// These buttons are locked by deafault
		btnStop.setDisable(true);
		btnChart.setDisable(true);
		btnStop.setDisable(true);
		btnClear.setDisable(true);

		bottom2.getChildren().addAll(btnStart, btnStop, slider, btnChart, btnClear);

		// Bottom combined
		VBox bottom = new VBox();
		bottom.getChildren().addAll(bottom1, bottom2);

		main.setBottom(bottom);

		Scene scene = new Scene(main);

		return scene;

	}

	public void updateTexts() {

		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Translator t = sl.getTranslator();
		
		menuOptions.setText(t.getString("mw.menu.options"));
		menuOptionsLanguage.setText(t.getString("mw.menu.options.language"));
		System.out.println(t.getString("mw.lblEstimate"));
		lblEstimate.setText(t.getString("mw.lblEstimate"));
		btnStart.setText(t.getString("mw.btnStart"));
		btnStop.setText(t.getString("mw.btnStop"));
		btnChart.setText(t.getString("mw.btnChart"));
		btnClear.setText(t.getString("mw.btnClear"));
	}

}
