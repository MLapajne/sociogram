package si.zitnik.sociogram.gui.graph;

import java.util.ArrayList;

import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.enums.PersonDrawType;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.util.I18n;

public class GraphUtil {
	
	private ArrayList<Boolean> posSelections = new ArrayList<Boolean>(3); //from 1-3
	private ArrayList<Boolean> negSelections = new ArrayList<Boolean>(3); //from 1-3
	private boolean drawWoman;
	private boolean drawMan;
	private PersonDrawType personToDrawType;
	private Person personToDraw;

	public GraphUtil(){
		init();
	}

	private void init() {
		for (int i=0; i<3; i++){
			posSelections.add(false);
			negSelections.add(false);
		}
		drawMan = true;
		drawWoman = true;
		personToDrawType = PersonDrawType.ALL;
		personToDraw = null;
	}
	
	public void setSelection(LikingType likingType, int numberOfSelection, boolean selected){
		if (likingType.equals(LikingType.POSITIVE)){
			posSelections.set(numberOfSelection-1, selected);
		} else {
			negSelections.set(numberOfSelection-1, selected);
		}
	}
	
	public boolean getSelection(LikingType likingType, int numberOfSelection){
		if (likingType.equals(LikingType.POSITIVE)){
			return posSelections.get(numberOfSelection-1);
		} else {
			return negSelections.get(numberOfSelection-1);
		}
	}

	public void setDrawWoman(boolean selected) {
		this.drawWoman = selected;		
	}
	
	public boolean isDrawWoman() {
		return drawWoman;
	}

	public void setDrawMan(boolean selected) {
		this.drawMan = selected;
	}
	
	public boolean isDrawMan() {
		return drawMan;
	}
	
	public void setPersonToDrawType(PersonDrawType personToDrawType, Person person) {
		this.personToDrawType = personToDrawType;
		if (this.personToDrawType.equals(PersonDrawType.SPECIFIC_PERSON)){
			this.personToDraw = person;
			if (this.personToDraw == null){
				try {
					throw new Exception("ERROR: Function called with wrong parameters!");
				} catch (Exception e) {
					@SuppressWarnings("unused")
					JErrorDialog errorDialog = new JErrorDialog(I18n.get("genericErrorMsg"), e);
				}
			}
		}
	}
	
	public Person getPersonToDraw() {
		return personToDraw;
	}
	
	public PersonDrawType getPersonToDrawType() {
		return personToDrawType;
	}
}
