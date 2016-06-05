package net.sharksystem.sharknet.api;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by timol on 16.05.2016.
 */
public class ImplSharkNet implements SharkNet {

	//ToDo: Implment - Blacklistfunctionality

	//ToDo: Implement - Initialisierung bauen (inkl übergabe KB etc)

	//ToDo: Implement - getter for feeds with interest as param

	//ToDo: Implement - getter for feeds with searchstring

	//ToDo: Implement - sort list with time per default

	//ToDo: Implement - Anzahl an Messages and time from - to loadmessage(int index, int anzahl) - C





	List<Feed> feed_list = new LinkedList<>();
	List<Profile> profile_list = new LinkedList<>();
	List<Contact> contact_list = new LinkedList<>();
	List<Chat> chat_list = new LinkedList<>();
	Profile myProfile;
	ArrayList<Dummy> chatListenerList = new ArrayList<Dummy>();

	@Override
	public List<Profile> getProfiles() {

		//ToDo: Shark - search in KB for Profiles an return a List of them
		//Implementation of DummyDB
		profile_list = DummyDB.getInstance().getProfile_list();
		return profile_list;
	}

	@Override
	public List<Feed> getFeeds(int Anzahl) {

		//ToDo: Shark - Search in KB for Feeds and return a list of them
		//Implementation of DummyDB
		if(myProfile == null) return null;
		feed_list = DummyDB.getInstance().getFeed_list(myProfile);
		return feed_list;
	}

	@Override
	public List<Contact> getContacts() {

		if(myProfile == null) return null;
		//ToDo: Shark - Search in KB for Contacts and return a list of them
		//Implementation of DummyDB
		contact_list = DummyDB.getInstance().getContact_list(myProfile);
		return contact_list;
	}

	@Override
	public List<Chat> getChats() {

		if(myProfile == null) return null;
		//ToDo: Shark - Search in KB vor Chats and return a list of them
		//Implementation of DummyDB
		chat_list = DummyDB.getInstance().getChat_list(myProfile);
		return chat_list;
	}

	@Override
	public Feed newFeed(String content, Interest interest, Contact sender) {
		if(myProfile == null) return null;
		Feed f = new ImplFeed(content, interest, sender, myProfile);
		feed_list.add(f);
		return f;
	}

	@Override
	public Profile newProfile(String nickname, String uid, String publickey) {
		Profile p = new ImplProfile(new ImplContact(nickname, uid, publickey, null));
		ImplContact c = (ImplContact)p.getContact();
		c.setOwner(p);
		c.save();
		profile_list.add(p);
		return p;
	}

	@Override
	public Chat newChat(List<Contact> recipients, Contact sender) {
		if(myProfile == null) return null;
		Chat chat = new ImplChat(recipients, sender, myProfile);
		chat_list.add(chat);
		return chat;
	}

	@Override
	public Contact newContact(String nickname, String uid, String publickey) {
		if(myProfile == null) return null;
		Contact c = new ImplContact(nickname, uid, publickey, myProfile);
		contact_list.add(c);
		return c;

		//ToDo: Clearify - how to share contacts
	}

	@Override
	public boolean setProfile(Profile myProfile, String password) {
		if(myProfile.login(password)){
			this.myProfile = myProfile;
			return true;
		}
		else return false;
	}

	@Override
	public Profile getMyProfile() {
		return myProfile;
	}

	@Override
	public void addChatListener(Profile p, Dummy listener) {
		if (!chatListenerList.contains(listener)) {
			chatListenerList.add(listener);
		}
	}

	@Override
	public void addFeedListener(Profile p, Dummy listener) {

	}

	@Override
	public void informNewMessage(Profile p, Message m) {

	}

	@Override
	public void informNewFeed(Profile p, Feed f) {

	}

	public void fillWithDummyData(){
		Dummy d = new Dummy();
		d.fillWithDummyData(this);
	}
	public void updateListwithDummyData(List<Feed> feed_list, List<Profile> profile_list, List<Contact> contact_list, List<Chat> chat_list){
		this.feed_list = feed_list;
		this.profile_list = profile_list;
		this.contact_list = contact_list;
		this.chat_list = chat_list;
	}


}
