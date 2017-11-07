package estimatingPI.piMainView;

import java.util.ArrayList;

import estimatingPI.ServiceLocator;
import estimatingPI.abstractClasses.Controller;
import estimatingPI.commonClasses.Configuration;
import estimatingPI.commonClasses.Translator;
import javafx.stage.Stage;


public class PiController extends Controller<PiModel, PiView> {

	public PiController(PiModel model, PiView view) {
		super(model, view);
		ServiceLocator sl = ServiceLocator.getServiceLocator();
		Configuration c = sl.getConfiguration();
		Translator t = sl.getTranslator();

	}




}
