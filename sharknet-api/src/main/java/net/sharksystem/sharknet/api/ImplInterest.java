package net.sharksystem.sharknet.api;


import net.sharkfw.knowledgeBase.*;
import net.sharkfw.knowledgeBase.inmemory.InMemoSharkKB;

import java.util.*;

/**
 * Created by timol on 18.05.2016.
 */

public class ImplInterest implements Interest {

	SharkKB kb = null;
	Taxonomy tx = null;

	public ImplInterest(Contact owner){
		kb = new InMemoSharkKB();
		kb.setOwner(InMemoSharkKB.createInMemoPeerSemanticTag(owner.getName(),
			owner.getUID(),
			""));
		try {
			tx = kb.getTopicsAsTaxonomy();
		} catch (SharkKBException e) {
			e.printStackTrace();
		}
	}


	@Override
	public Taxonomy getInterests() {
		return tx;
	}

	@Override
	public TXSemanticTag addInterest(String name, String si) {
		TXSemanticTag newtopic = null;
		try {
			newtopic = tx.createTXSemanticTag(name, si);

		} catch (SharkKBException e) {
			e.printStackTrace();
		}
		return newtopic;
	}

	@Override
	public void addInterest(TXSemanticTag interest) {
		try {
			tx.merge(interest);
		} catch (SharkKBException e) {
			e.printStackTrace();
		}
	}

	@Override
	public TXSemanticTag getTopicAsSemanticTag(String si) {
		TXSemanticTag topic = null;
		try {
			topic = tx.getSemanticTag(si);
		} catch (SharkKBException e) {
			e.printStackTrace();
		}

		return topic;
	}

	@Override
	public void deleteInterest(TXSemanticTag i) {
		try {
			tx.removeSemanticTag(i);
		} catch (SharkKBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void moveInterest(TXSemanticTag parent, TXSemanticTag child) {
		try {
			tx.move(parent, child);
		} catch (SharkKBException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void removeFromParent(TXSemanticTag child){
		HashMap<TXSemanticTag, List <TXSemanticTag>> subtagMap = new HashMap<>();
		getSubTagsAsMap(child, subtagMap);
		try {
			tx.removeSubTree(child);
		} catch (SharkKBException e) {
			e.printStackTrace();
		}


		Iterator it = subtagMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry)it.next();
			TXSemanticTag parent = (TXSemanticTag)pair.getKey();
			addInterest(parent);
			List<TXSemanticTag> childList = (List<TXSemanticTag>)pair.getValue();

			for(TXSemanticTag newChild : childList){
				addInterest(newChild);
				try {
					tx.move(tx.getSemanticTag(parent.getSI()), tx.getSemanticTag(newChild.getSI()));
				} catch (SharkKBException e) {
					e.printStackTrace();
				}

			}
			it.remove(); // avoids a ConcurrentModificationException
		}





	}

	private void getSubTagsAsMap(TXSemanticTag tag, HashMap<TXSemanticTag, List <TXSemanticTag>> subtagMap){

		if(!subtagMap.containsKey(tag)){
			subtagMap.put(tag, new LinkedList<TXSemanticTag>());
			Enumeration<TXSemanticTag> enumChilds = tag.getSubTags();
			while(enumChilds != null && enumChilds.hasMoreElements()){
				TXSemanticTag child = enumChilds.nextElement();
				subtagMap.get(tag).add(child);
				getSubTagsAsMap(child, subtagMap);
			}
		}
	}



	@Override
	public boolean contains(Interest i) {
		List<TXSemanticTag> it = i.getAllTopics();
		Boolean contains = false;
		for(TXSemanticTag tag : it){
			SemanticTag st = null;
			try {
				st = tx.getSemanticTag(tag.getSI());
			} catch (SharkKBException e) {
				e.printStackTrace();
			}
			if(st!= null){
				contains = true;
			}
		}
		return contains;
	}

	/***
	 * Loads all topics of this interest.
	 *
	 * @return a flat list of all topics
	 * @throws RuntimeException if topic loading failed
     */
	@Override
	public List<TXSemanticTag> getAllTopics() {

		if (tx == null) return Collections.emptyList();

		try {
			List<TXSemanticTag> tags = new LinkedList<>();
			Enumeration<TXSemanticTag> enum_Tag = tx.rootTags();
			while (enum_Tag != null && enum_Tag.hasMoreElements()){
				TXSemanticTag tag = enum_Tag.nextElement();
				tags.add(tag);
				getSubTags(tag, tags);
			}

			return tags;
		} catch (SharkKBException e) {
			throw new RuntimeException("topic loading failed", e);
		}

	}

	private void getSubTags(TXSemanticTag tag, List<TXSemanticTag> tags){
		Enumeration<TXSemanticTag> subtagenum = tag.getSubTags();

		if(subtagenum == null){
			return;
		}

		while(subtagenum.hasMoreElements()){
			TXSemanticTag txTag = subtagenum.nextElement();
			getSubTags(txTag, tags);
			tags.add(txTag);
		}
	}

	@Override
	public void save() {
		//ToDo: Shark - safe Interest in KB
	}

	@Override
	public void delete() {
		//ToDo: Shark - delete Interest from KB
	}

}
