package net.sharksystem.sharknet.javafx.controller.contactlist;


import net.sharksystem.sharknet.api.Contact;
import net.sharksystem.sharknet.javafx.controls.medialist.MediaListCell;
import net.sharksystem.sharknet.javafx.controls.medialist.MediaListView;

public class ContactList extends MediaListView<Contact> {

	public ContactList() {
		super();
		setCellFactory(param -> new MediaListCell<Contact>(ContactListEntryController.class));
	}
}
