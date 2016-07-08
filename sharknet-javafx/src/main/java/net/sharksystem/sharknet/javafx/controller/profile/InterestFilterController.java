package net.sharksystem.sharknet.javafx.controller.profile;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.Pair;
import org.controlsfx.dialog.LoginDialog;

/**
 * Filter controller for all possible
 * interests.
 */
public class InterestFilterController {

	/******************************************************************************
	 *
	 * FXML Fields
	 *
	 ******************************************************************************/

	@FXML private Button addInterestButton;
	@FXML private TextField interestFilter;

	/******************************************************************************
	 *
	 * Fields
	 *
	 ******************************************************************************/

	/******************************************************************************
	 *
	 * Constructors
	 *
	 ******************************************************************************/

	public void initialize() {
		addInterestButton.setOnAction((e) -> createInterestDialog());
	}

	/******************************************************************************
	 *
	 * Methods
	 *
	 ******************************************************************************/

	public Dialog<Pair<String, String>> createInterestDialog() {
		InterestCreationDialog dialog = new InterestCreationDialog();
		dialog.showAndWait();

		return dialog;
	}
}