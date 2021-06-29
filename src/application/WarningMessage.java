package application;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class WarningMessage {
	String warningMsg;

	static public enum OPTION {
		OK, YES_NO
	}

	static public enum RET_OPTION {
		OK, YES, No
	}

	RET_OPTION retVal;
	
	public WarningMessage(String message) {
		warningMsg = message;
	}

	public RET_OPTION show(OPTION option) {
		Stage popupwindow = new Stage();
		popupwindow.initModality(Modality.APPLICATION_MODAL);
		popupwindow.setTitle("Warning");
		Label lblMessage = new Label(warningMsg);
		lblMessage.setFont(Font.font("Verdana", FontWeight.BOLD, 12));
		lblMessage.setTranslateX(50);
		//lblMessage.setAlignment(Pos.CENTER);
		
		VBox layout = new VBox(30);
		GridPane pane = new GridPane();
		layout.getChildren().add(pane);


		if (option == OPTION.OK) {
			Button btnOk = new Button("OK");
			btnOk.setTranslateX(160);
			btnOk.setOnAction(e -> {
				retVal = RET_OPTION.OK;
				popupwindow.close();
			});
			pane.addRow(0, lblMessage);
			pane.addRow(0, btnOk);			
			//layout.getChildren().addAll(lblMessage, btnOk);
		} else {
			Button btnOk = new Button("Yes");
			btnOk.setTranslateX(140);
			btnOk.setOnAction(e -> {
				retVal = RET_OPTION.YES;
				popupwindow.close();
			});
			Button btnNo = new Button("No");
			btnNo.setTranslateX(-30);
			btnNo.setOnAction(e -> {
				retVal = RET_OPTION.No;
				popupwindow.close();
			});
			pane.addRow(0, lblMessage);
			pane.addRow(1, new Label(""));
			pane.addRow(2, btnOk, btnNo);			
			//layout.getChildren().addAll(lblMessage, btnOk, btnNo);
		}

		layout.setAlignment(Pos.CENTER);

		Scene scene1 = new Scene(layout, 400, 100);
		popupwindow.setScene(scene1);
		popupwindow.showAndWait();
		return retVal;
	}

}
