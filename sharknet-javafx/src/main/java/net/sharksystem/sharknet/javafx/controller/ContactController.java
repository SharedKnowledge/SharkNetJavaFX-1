package net.sharksystem.sharknet.javafx.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import net.sharksystem.sharknet.javafx.App;
import net.sharksystem.sharknet.javafx.actions.annotations.Controller;
import net.sharksystem.sharknet.javafx.utils.AbstractController;
import javafx.fxml.FXML;
import net.sharksystem.sharknet.api.ImplContact;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


@Controller( title = "%sidebar.contacts")
public class ContactController extends AbstractController {

	private AppController appController;


	private ImplContact testKontakt;
	private ImplContact testKontakt2;
	private ImplContact testKontakt3;

	public ContactController(AppController appController) {
		super(App.class.getResource("views/contactsView.fxml"));
		this.appController = appController;

		this.testKontakt = new ImplContact("Max", "", "");
		this.testKontakt2 = new ImplContact("Chris", "", "");
		this.testKontakt3 = new ImplContact("Marie", "", "");
	}


	@FXML
	private ListView<String> contactList;
	@FXML
	private ListView<String> groupList;
	@FXML
	private ListView<String> blackList;


	@FXML
	private void onNewContactButtonClick() {
		System.out.println("Neuen Kontakt erstellen");
		//appController.getChildren().clear();
		//appController.getChildren().add(FXMLLoader.load(getClass().getResource("Content2.fxml"));
	}

	@FXML
	private void onContactDeleteButtonClick() {
		System.out.println("Kontakt löschen:");
		System.out.println(contactList.getSelectionModel().getSelectedItem());
		testKontakt.deleteContact();
	}

	@FXML
	private void onContactGroupButtonClick() {
		System.out.println("Kontakt zu Gruppe:");
		System.out.println(contactList.getSelectionModel().getSelectedItem());
	}

	@FXML
	private void onContactBlockButtonClick() {
		System.out.println("Kontakt blockieren:");
		System.out.println(contactList.getSelectionModel().getSelectedItem());
	}


	//
	Image getImageForContact(String contactName){

		return null;
	}
	//

	@Override
	protected void onFxmlLoaded() {

		ObservableList<String> contactsData = FXCollections.observableArrayList();
		ObservableList<String> blackListData = FXCollections.observableArrayList();
		ObservableList<String> groupsData = FXCollections.observableArrayList("Gruppe1","Gruppe2", "Gruppe3");


		//Mit Testwerten füllen
		List<ImplContact> allContacts = new ArrayList<ImplContact>();
		allContacts.add(testKontakt);
		allContacts.add(testKontakt2);

		List<ImplContact> allBlockedContacts = new ArrayList<ImplContact>();
		allBlockedContacts.add(testKontakt3);

		//List<ImplGroup> allGroups = new ArrayList<ImplGroup>();


		// Kontaktlisten füllen
		for (ImplContact ic : allContacts) {
			System.out.println(ic.getNickname());
			contactsData.add(ic.getNickname());
		}

		for (ImplContact ic : allBlockedContacts) {
			System.out.println(ic.getNickname());
			blackListData.add(ic.getNickname());
		}


		contactList.setItems(contactsData);
		blackList.setItems(blackListData);
		groupList.setItems(groupsData);

		contactList.setCellFactory(listView -> new ListCell<String>() {
			private ImageView imageView = new ImageView();
			@Override
			public void updateItem(String contactName, boolean empty) {
				super.updateItem(contactName, empty);
				if (empty) {
					setText(null);
					setGraphic(null);
				} else {
					//Image image = getImageForContact(contactName);
					Image profilePicture = new Image(App.class.getResource("images/plus.png").toExternalForm());
					imageView.setImage(profilePicture);
					setText(contactName);
					setGraphic(imageView);
				}
			}
		});



	}
}
