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
	protected MenuBar menuBar;
	protected Menu menuOptions;
	protected Menu menuOptionsLanguage;

	// Center: A visualisation
	protected Arc arc;
	protected Rectangle rectangle;
	protected Pane center;

	// Bottom1: PI estimation
	protected Label lblEstimate;
	protected Label lblPiEstimate;
	protected Label lblPi;

	// Bottom2: Control buttons
	protected Button btnStart;
	protected Button btnStop;
	protected Slider slider;
	protected Button btnChart;
	protected Button btnClear;

	double sideLength;

	public PiView(Stage stage, PiModel model) {
		super(stage, model);
		stage.setTitle("Estimating Pi");

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

		arc = new Arc(0, 0, sideLength, sideLength, 270, 90);
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
		bottom2.setSpacing(4);
		btnStart = new Button(t.getString("mw.btnStart"));
		btnStop = new Button(t.getString("mw.btnStop"));

		slider = new Slider(0, 1, 0.5);
		btnChart = new Button(t.getString("mw.btnChart"));

		btnClear = new Button(t.getString("mw.btnClear"));

		// These buttons are locked by default
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

	// Immediately updates the elements after the language gets changed
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
