package application;

import javafx.application.Application;
import javafx.stage.Stage;


public class loc8r extends Application {
	
	public static void main(String args[]) {
		// Launch the application
		launch(args);
	}

	@Override
	// Launch the application
	public void start(Stage stage) {
		String locationFile = "locations.txt";

		BuildLocationData bld = new BuildLocationData();
		try {
			bld.BuildLocationDatabaset(locationFile);
		} catch (Exception e) {
			System.out.println("Read location file '" + locationFile + "' failed");
			System.exit(-1);
		}
		
		bld.createLocationTypeList();

		//bld.DisplayLocationData();
		loc8rUI lui = new loc8rUI(stage, bld);
		lui.BuildUI();

	}
}