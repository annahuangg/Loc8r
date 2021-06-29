package application;

import java.util.ArrayList;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.geometry.Pos;

public class loc8rUI {
	int viewWidth = 700;
	int viewHeight = 600;
	int indent = 10;

	int imageWidth = 80;
	int imageHeight = 40;

	int topBoxHight = imageHeight;
	int midBoxHight = 160;

	Stage mainView;
	BorderPane border;
	HBox topBox;
	VBox leftBox;
	VBox rightBox;
	VBox bottomBox;

	GridPane leftGridPane;
	GridPane rightGridPane;
	BuildLocationData locationData;

	ComboBox<String> listBusinessType;
	TextArea txtLocation;
	TextField txtAddress;
	CheckBox businessNearby;
	TextField txtBusinessName;
	Label lblTimeVal;
	Label lblTime;
	ToggleGroup tg;
	Button reset;
	Label lblRating;
	Button btnSubmit;
	int valRating;

	RadioButton r1;
	RadioButton r2;
	RadioButton r3;
	RadioButton r4;
	RadioButton r5;
	
	String loc8rImage = "Loc8r_Logo.png";

	public loc8rUI(Stage stage, BuildLocationData locData) {
		mainView = stage;
		
		Login login = new Login("Please login to loc8r");
		login.show();
		if (!login.isPasswordmatched())
			System.exit(0);
		
		locationData = locData;
		border = new BorderPane();
		border.setMinSize(viewWidth, viewHeight);
		border.setMaxHeight(viewHeight);

		topBox = new HBox();
		border.setTop(topBox);
		leftBox = new VBox();
		leftBox.setMinSize(viewWidth - 100, viewHeight);
		leftBox.setMaxWidth(viewWidth - 100);
		leftBox.setMinWidth(viewWidth - 100);
		leftBox.setMaxHeight(viewHeight);

		rightBox = new VBox();
		leftBox.setMinHeight(midBoxHight);
		rightBox.setMinHeight(midBoxHight);
		rightBox.setMaxWidth(100);
		bottomBox = new VBox();

		border.setLeft(leftBox);
		border.setRight(rightBox);

		leftGridPane = new GridPane();
		leftGridPane.setVgap(10);
		leftGridPane.setMinWidth(viewWidth - 150);
		leftBox.getChildren().add(leftGridPane);

		rightGridPane = new GridPane();
		rightGridPane.setVgap(10);
		rightBox.getChildren().add(rightGridPane);

		border.setBottom(bottomBox);
		bottomBox.setMinHeight(viewHeight - imageHeight - midBoxHight - 2 * indent);
	}

	public void BuildUI() {
		// Set title for the stage
		mainView.setTitle("LOC8R");

		buildTopBoxOfImage();
		buildLeftPane();
		buildRightPane();
		buildBottomPane();

		Scene scene = new Scene(border);
		// Add the scene to the Stage
		mainView.setScene(scene);
		mainView.setResizable(false);

		enableRating(false);

		mainView.show();

	}

	private void buildTopBoxOfImage() {
		// Creating an image
		Image image = new Image(this.getClass().getResourceAsStream(loc8rImage));

		// Setting the image view
		ImageView imageView = new ImageView(image);

		// Setting the position of the image
		imageView.setTranslateX(10);
		imageView.setTranslateY(10);

		// setting the fit height and width of the image view
		imageView.setFitHeight(imageHeight);
		imageView.setFitWidth(imageWidth);
		// Set the Size of the image

		// Setting the preserve ratio of the image view
		imageView.setPreserveRatio(true);

		Label lblTitle = new Label("Loc8r");
		lblTitle.setTranslateX(210);
		lblTitle.setTranslateY(10);
		lblTitle.setFont(Font.font("Verdana", FontWeight.BOLD, 36));
		topBox.getChildren().add(imageView);
		topBox.getChildren().add(lblTitle);
	}

	private void buildLeftPane() {
		Label lblName = new Label("Business Name ");
		lblName.setMinWidth(110);
		lblName.setAlignment(Pos.CENTER_RIGHT);
		Label lblAddress = new Label("Address ");
		lblAddress.setMinWidth(110);
		lblAddress.setAlignment(Pos.CENTER_RIGHT);

		Label lblType = new Label("Business Type ");
		lblType.setMinWidth(110);
		lblType.setAlignment(Pos.CENTER_RIGHT);

		Label lblNearby = new Label("Include Business Nearby");
		lblNearby.setMinWidth(180);
		lblNearby.setTranslateX(-170);

		lblRating = new Label("Latest rating ");
		lblRating.setMinWidth(110);
		lblRating.setAlignment(Pos.CENTER_RIGHT);

		txtBusinessName = new TextField();
		txtBusinessName.setMaxWidth(450);
		txtBusinessName.setMinWidth(450);
		txtBusinessName.setOnAction(event -> {
			if (tg.getSelectedToggle() != null)
				tg.getSelectedToggle().setSelected(false);
			displaySearchResult();
		});

		txtAddress = new TextField();
		txtAddress.setMaxWidth(450);
		txtAddress.setMinWidth(450);
		txtAddress.setOnAction(event -> {
			if (tg.getSelectedToggle() != null)
				tg.getSelectedToggle().setSelected(false);
			displaySearchResult();
		});

		listBusinessType = new ComboBox<>();
		listBusinessType.setMaxWidth(150);
		listBusinessType.setMinWidth(150);
		for (int i = 0; i < locationData.getLocationTypeList().size(); i++)
			listBusinessType.getItems().add(locationData.getLocationTypeList().get(i));
		listBusinessType.setOnAction(event -> {
			if (tg.getSelectedToggle() != null)
				tg.getSelectedToggle().setSelected(false);
			txtBusinessName.setText("");
			txtAddress.setText("");
			displaySearchResult();
		});

		businessNearby = new CheckBox();
		businessNearby.setTranslateX(-200);
		businessNearby.setOnAction(event -> {
			displaySearchResult();
		});

		tg = new ToggleGroup();

		r1 = new RadioButton("1");
		r1.setOnAction(event -> {
			valRating = getRating(r1);
			btnSubmit.setDisable(false);
		});
		r2 = new RadioButton("2");
		r2.setOnAction(event -> {
			valRating = getRating(r2);
			btnSubmit.setDisable(false);
		});
		r3 = new RadioButton("3");
		r3.setOnAction(event -> {
			valRating = getRating(r3);
			btnSubmit.setDisable(false);
		});
		r4 = new RadioButton("4");
		r4.setOnAction(event -> {
			valRating = getRating(r4);
			btnSubmit.setDisable(false);
		});
		r5 = new RadioButton("5");
		r5.setOnAction(event -> {
			valRating = getRating(r5);
			btnSubmit.setDisable(false);
		});
		r1.setToggleGroup(tg);
		r2.setToggleGroup(tg);
		r3.setToggleGroup(tg);
		r4.setToggleGroup(tg);
		r5.setToggleGroup(tg);

		TilePane r = new TilePane();
		r.setHgap(12);
		r.setTranslateY(5);

		r.getChildren().add(r1);
		r.getChildren().add(r2);
		r.getChildren().add(r3);
		r.getChildren().add(r4);
		r.getChildren().add(r5);

		reset = new Button("Reset");
		reset.setTranslateX(-240);
		reset.setOnAction(event -> {
			if (tg.getSelectedToggle() != null)
				tg.getSelectedToggle().setSelected(false);
			btnSubmit.setDisable(true);
		});

		btnSubmit = new Button("Submit");
		btnSubmit.setOnAction(event -> {
			int tm = Integer.parseInt(lblTimeVal.getText()) + 1;
			locationData.addToRatingMap(txtAddress.getText(), valRating, tm);
			lblTimeVal.setText(Integer.toString(tm));
			setRating(valRating);
			locationData.saveRatingToFile();
		});
		btnSubmit.setTranslateX(-360);
		btnSubmit.setMaxWidth(70);
		btnSubmit.setMinWidth(70);


		lblTime = new Label("Rating times:");
		lblTime.setTranslateX(-340);
		lblTime.setMinWidth(90);
		lblTime.setMaxWidth(90);
		lblTimeVal = new Label();
		lblTimeVal.setTranslateX(-350);
		lblTimeVal.setMinWidth(30);
		lblTimeVal.setMaxWidth(30);
		leftGridPane.addRow(0, new Label(""));
		leftGridPane.addRow(1, lblType, listBusinessType, lblNearby, businessNearby);
		leftGridPane.addRow(2, lblName, txtBusinessName);
		leftGridPane.addRow(3, lblAddress, txtAddress);
		leftGridPane.addRow(4, lblRating, r, reset, btnSubmit, lblTime, lblTimeVal);
	}

	private void enableRating(boolean status) {
		btnSubmit.setDisable(!status);
		lblRating.setDisable(!status);
		reset.setDisable(!status);
		tg.getToggles().forEach(toggle -> ((RadioButton) toggle).setDisable(!status));
		if (tg.getSelectedToggle() == null)
			btnSubmit.setDisable(true);
	}

	private int getRating(RadioButton rb) {
		return Integer.valueOf(rb.getText());
	}

	private void buildRightPane() {
		Button btnSearch = new Button("Search");
		btnSearch.setOnAction(event -> {
			displaySearchResult();
		});

		Button btnClear = new Button("Clear");
		btnClear.setOnAction(event -> {
			txtBusinessName.setText("");
			txtAddress.setText("");
			txtLocation.setText("");
			listBusinessType.getSelectionModel().clearSelection();
			if (tg.getSelectedToggle() != null)
				tg.getSelectedToggle().setSelected(false);
			enableRating(false);
			lblTimeVal.setText("");
		});

		Button btnClearHis = new Button("Delete history");
		btnClearHis.setOnAction(event -> {
			WarningMessage msg = new WarningMessage("Are you sure to delete rating history?");
			if (msg.show(WarningMessage.OPTION.YES_NO) == WarningMessage.RET_OPTION.YES) {
				if (locationData.deleteRatingHistory() == -1) {
					msg = new WarningMessage("History not exist or delete history failed");
					msg.show(WarningMessage.OPTION.OK);
				}
				else {
					lblTimeVal.setText("0");
					if (tg.getSelectedToggle() != null)
						tg.getSelectedToggle().setSelected(false);
					locationData.resetRating();

				}
			}

			
		});

		Button btnQuit = new Button("Quit");
		btnQuit.setOnAction(event -> {
			System.exit(0);
		});


		btnSearch.setMaxWidth(90);
		btnSearch.setMinWidth(90);
		btnClear.setMaxWidth(90);
		btnClear.setMinWidth(90);
		btnClearHis.setMaxWidth(90);
		btnClearHis.setMinWidth(90);
		btnQuit.setMaxWidth(90);
		btnQuit.setMinWidth(90);
		btnSearch.setTranslateX(-20);
		btnClear.setTranslateX(-20);
		btnClearHis.setTranslateX(-20);
		btnQuit.setTranslateX(-20);

		rightGridPane.addRow(0, new Label(""));
		rightGridPane.addRow(1, btnSearch);
		rightGridPane.addRow(2, btnClear);
		rightGridPane.addRow(3, btnClearHis);
		rightGridPane.addRow(4, btnQuit);
	}

	private void buildBottomPane() {
		txtLocation = new TextArea();
		txtLocation.setEditable(false);
		txtLocation.setWrapText(true);
		txtLocation.setTranslateX(indent);
		txtLocation.setTranslateY(10);
		txtLocation.setMinWidth(viewWidth - 2 * indent);
		txtLocation.setMaxWidth(viewWidth - 2 * indent);
		txtLocation.setMinHeight(viewHeight - imageHeight - midBoxHight - 4 * indent);
		txtLocation.setMaxHeight(viewHeight - imageHeight - midBoxHight - 4 * indent);

		bottomBox.getChildren().add(txtLocation);
	}

	private String getText(TextField fieldName) {
		String addr = fieldName.getText();
		if (addr == null)
			return addr;
		else {
			addr = addr.trim();
			if (addr.length() == 0)
				addr = null;
		}
		return addr;
	}

	private void displaySearchResult() {
		// String busType = null;
		// if (listBusinessType.getSelectionModel().getSelectedIndex() == 0)
		// busType = null;
		// else
		String busType = listBusinessType.getSelectionModel().getSelectedItem();
		String busName = getText(txtBusinessName);
		String busAddr = getText(txtAddress);
		boolean busNearby = businessNearby.isSelected();
		ArrayList<location> locs;

		if (busName == null && busAddr == null && busType == null) {
			//WarningMessage msg = new WarningMessage("Please enter search criteria");
			//msg.show();
			return;
		} else if (busName == null && busAddr == null && busType != null)
			locs = locationData.searchByType(busType, busNearby);
		else if (busName == null && busAddr != null && busType == null)
			locs = locationData.searchByAddr(busAddr, busNearby);
		else if (busName != null && busAddr == null && busType == null)
			locs = locationData.searchByName(busName, busNearby);
		else if (busName == null && busAddr != null && busType != null)
			locs = locationData.searchByAddr_Type(busAddr, busType, busNearby);
		else if (busName != null && busAddr == null && busType != null)
			locs = locationData.searchByName_Type(busName, busType, busNearby);
		else if (busName != null && busAddr != null && busType == null)
			locs = locationData.searchByName_Addr(busName, busAddr, busNearby);
		else
			locs = locationData.searchByName_Addr_Type(busName, busAddr, busType, busNearby);

		displaySearchResult(locs);

	}

	private void displaySearchResult(ArrayList<location> locs) {
		location loc;
		String locInfo = "", distance;
		for (int i = 0; i < locs.size(); i++) {
			loc = locs.get(i);
			if (i == 0)
				distance = ")";
			else
				distance = "), " + String.format("%,.2f", loc.getDistance()) + " miles";

			locInfo += loc.getType() + ": " + loc.getName() + ", " + loc.getAddress() + ", (" + loc.getLongitude()
					+ ", " + loc.getLatitude() + distance + "\n";
		}

		if (locs.size() >= 1) {
			enableRating(true);
			loc = locs.get(0);
			txtBusinessName.setText(loc.getName());
			txtAddress.setText(loc.getAddress());
			lblTimeVal.setText(Integer.toString(loc.getNumberRating()));
			setRating(loc.getRating());
		} else {
			enableRating(false);
		}
		txtLocation.setText(locInfo);
	}

	private void setRating(int r) {
		tg.getToggles().forEach(toggle -> ((RadioButton) toggle).setDisable(false));
		switch (r) {
		case 1: r1.setSelected(true); break;
		case 2: r2.setSelected(true); break;
		case 3: r3.setSelected(true); break;
		case 4: r4.setSelected(true); break;
		case 5: r5.setSelected(true); break;
		default:  break;
		}
	}

}
