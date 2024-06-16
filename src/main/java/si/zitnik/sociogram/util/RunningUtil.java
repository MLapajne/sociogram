package si.zitnik.sociogram.util;

import java.awt.*;
import java.util.*;

import si.zitnik.sociogram.config.ConfigManager;
import si.zitnik.sociogram.config.PropertiesQuestions;
import si.zitnik.sociogram.config.SociogramConstants;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.entities.Sociogram;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.enums.ProgramType;
import si.zitnik.sociogram.enums.SocioClassification;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.icons.IconCache;
import si.zitnik.sociogram.util.api.SociogramAPIManager;
import si.zitnik.sociogram.util.api.reqres.TrialActiveRequest;
import si.zitnik.sociogram.util.api.reqres.TrialActiveResponse;

import javax.swing.*;

/**
 * This is the most important class. It holds all the information regarding the status of the program.
 * @author szitnik
 *
 */
public class RunningUtil {
    //CAUTION: must end with slash!
	public static String normalizedFilepath = "";
	final public static int tooltipDelay = ToolTipManager.sharedInstance().getInitialDelay();

	
	private Sociogram sociogram;
	private ConfigManager configManager;
	private String saveFile;
	private SocioClassification selectedClassification;
	public IconCache icons;
    private ProgramType programType;
	private String workingDirectory;

    public RunningUtil() throws Exception{
		createRunningUtil();
	}
	
	private void createRunningUtil() throws Exception {
		configManager = new ConfigManager();
        I18n.initOrUpdate(configManager);
		sociogram = new Sociogram("", getDefaultSchoolYear(), new ArrayList<Person>(), 0.0);
		this.saveFile = null;
		this.setSelectedClassification(SocioClassification.ALL);
		this.icons = new IconCache();
        this.programType = configManager.getProgramType();
	}

    public void changeLanguage(String languageLabel) throws Exception {
        configManager.setLanguage(languageLabel);
        I18n.initOrUpdate(configManager);
    }

    public void changeProgramType(ProgramType programType) throws Exception {
        configManager.setProgramType(programType);
        this.programType = programType;

        //Update default questions
        configManager.setPosQuestions(new PropertiesQuestions(new String[]{
                I18n.get("pos_Q1_" + this.programType),
                I18n.get("pos_Q2_" + this.programType),
                I18n.get("pos_Q3_" + this.programType),
        }));
        configManager.setNegQuestions(new PropertiesQuestions(new String[]{
                I18n.get("neg_Q1_" + this.programType),
                I18n.get("neg_Q2_" + this.programType),
                I18n.get("neg_Q3_" + this.programType),
        }));
    }

    public ProgramType getProgramType() {
        return programType;
    }

    private String getDefaultSchoolYear() {
		if (new GregorianCalendar().get(Calendar.MONTH)  > Calendar.JULY){
			return new GregorianCalendar().get(Calendar.YEAR)+"/"+(new GregorianCalendar().get(Calendar.YEAR)+1);
		} else {
			return (new GregorianCalendar().get(Calendar.YEAR)-1)+"/"+new GregorianCalendar().get(Calendar.YEAR);
		}
	}

	public String getInstitution() throws Exception {
		return configManager.getOrganizationTitle();
	}

	public String getDepartment() {
		return this.sociogram.getDepartment();
	}

	public ArrayList<Person> getPersons() {
		return this.sociogram.getPersons();
	}

	public String getSchoolYear() {
		return this.sociogram.getSchoolYear();
	}

	public void addPerson(Person person) {
		int newId = getNewPersonID();
		person.setId(newId);
		this.sociogram.getPersons().add(person);
	}

	private int getNewPersonID() {
		return this.sociogram.getPersons().size()+1;
	}

	public void removePerson(int realId) {
		this.sociogram.getPersons().remove(realId-1);
		for (int i = 0 ; i<this.sociogram.getPersons().size(); i++){
			Person person = this.sociogram.getPersons().get(i);
			person.setId(i+1);
			for (int j = 0; j<person.getPosSelections().size(); j++){
				if (person.getPosSelections().get(j) == realId){
					person.getPosSelections().set(j, 0);
				}
			}
			
		}
	}
	
	public PropertiesQuestions getPosQuestions() throws Exception{
		return this.configManager.getPosQuestions();
	}

	public PropertiesQuestions getNegQuestions() throws Exception {
		return this.configManager.getNegQuestions();
	}

	public void addPersonLikingSelection(int fromPersonRealId, int toPersonRealId, LikingType likingType) {
		ArrayList<Integer> selectionList = null;
		if (likingType.equals(LikingType.POSITIVE)){
			selectionList = this.getPersons().get(fromPersonRealId-1).getPosSelections();
		} else {
			selectionList = this.getPersons().get(fromPersonRealId-1).getNegSelections();
		}
		
		for (int i = 0; i<3; i++){
			if (selectionList.get(i) == 0){
				selectionList.set(i, toPersonRealId);
				return;
			}
		}
		return;		
	}

	public void removePersonLikingSelection(int fromPersonRealId, int toPersonRealId, LikingType likingType) {
		ArrayList<Integer> selectionList = null;
		if (likingType.equals(LikingType.POSITIVE)){
			selectionList = this.getPersons().get(fromPersonRealId-1).getPosSelections();
		} else {
			selectionList = this.getPersons().get(fromPersonRealId-1).getNegSelections();
		}
		
		for (int i = 0; i<3; i++){
			if (selectionList.get(i) == toPersonRealId){
				selectionList.set(i, 0);
				return;
			}
		}
		return;
	}


	public String getPersonFullName(int realId) {
		return this.sociogram.getPersons().get(realId-1).getFirstName()+" "+this.sociogram.getPersons().get(realId-1).getLastName();
	}

	public boolean hasAllSelected(int realId, LikingType likingType) {
		ArrayList<Integer> selectionList = null;
		if (likingType.equals(LikingType.POSITIVE)){
			selectionList = this.getPersons().get(realId-1).getPosSelections();
		} else {
			selectionList = this.getPersons().get(realId-1).getNegSelections();
		}
		
		for (Integer integer : selectionList) {
			if (integer == 0){
				return false;
			}
		}
		return true;
	}

	public void addAllPersons(Collection<Person> collection) {
		for (Person person : collection) {
			Person newPerson = new Person(this.sociogram.getPersons().size()+1, 
					person.getFirstName(), person.getLastName(), 
					person.getGender());
			
			this.sociogram.getPersons().add(newPerson);
		}
		
	}

	public int getNumOfSelected(int realId, LikingType likingType) {
		Integer counter = 0;
		for (Person person : this.getPersons()) {
			if (LikingType.POSITIVE.equals(likingType)){
				for (Integer posSelection : person.getPosSelections()) {
					if (posSelection == realId){
						counter++;
					}
				}
			} else {
				for (Integer negSelection : person.getNegSelections()) {
					if (negSelection == realId){
						counter++;
					}
				}
			}
		}
		
		return counter;
	}

	public void init() throws Exception {
		this.sociogram.setDepartment("");
		this.sociogram.setNegQuestion(this.configManager.getNegQuestions().get(0));
		this.sociogram.setPosQuestion(this.configManager.getPosQuestions().get(0));
		this.sociogram.setSchoolYear(getDefaultSchoolYear());
		this.sociogram.getPersons().clear();
		this.saveFile = null;
	}

	public boolean hasBeenSaved() {
		if (this.saveFile == null){
			return false;
		}
		return true;
	}

	public String getSaveFile() {
		return this.saveFile;
	}

	public Sociogram getSociogram() {
		return this.sociogram;
	}

	public void setSaveFile(String absolutePath) {
		this.saveFile = absolutePath;		
	}

	public void copyDataFrom(Sociogram newSociogram) {
		this.saveFile = null;

        this.sociogram.setComment(newSociogram.getComment());
        this.sociogram.setDate(newSociogram.getDate());
        this.sociogram.setDepartment(newSociogram.getDepartment());
        this.sociogram.setGrader(newSociogram.getGrader());
        this.sociogram.setIndexGroupIntegration(newSociogram.getIndexGroupIntegration());
        this.sociogram.setIndexKohez(newSociogram.getIndexKohez());
        this.sociogram.setNegQuestion(newSociogram.getNegQuestion());
        this.sociogram.setPosQuestion(newSociogram.getPosQuestion());
        this.sociogram.setSchoolYear(newSociogram.getSchoolYear());
        this.sociogram.setTeacher(newSociogram.getTeacher());

		this.sociogram.getPersons().clear();
		this.sociogram.getPersons().addAll(newSociogram.getPersons());
	}

    public void showNotActivatedDialog() {
        JOptionPane.showOptionDialog(null,
                I18n.get("demoMsg") + "\n" + I18n.get("activationMessage"),
                I18n.get("demoTitle"),
                0,
                JOptionPane.WARNING_MESSAGE,
                null,
                new String[]{I18n.get("ok")}, I18n.get("ok"));
    }

	public Person getPerson(Integer realId) {
		if (realId == 0){
			return new Person();
		}
		return this.sociogram.getPersons().get(realId-1);
	}

	public void setPosQuestions(PropertiesQuestions posQuestions) throws Exception {
			this.configManager.setPosQuestions(posQuestions);		
	}

	public void setNegQuestions(PropertiesQuestions questions) throws Exception {
		this.configManager.setNegQuestions(questions);
	}

	public String getSelectedPosQuestion() {
		return this.sociogram.getPosQuestion();
	}

	public String getSelectedNegQuestion() {
		return this.sociogram.getNegQuestion();
	}

	public void setSelectedPosQuestion(String question) {
		this.sociogram.setPosQuestion(question);
	}

	public void setSelectedNegQuestion(String question) {
		this.sociogram.setNegQuestion(question);
	}

	public void setDepartment(String department) {
		this.sociogram.setDepartment(department);		
	}

	public void setSchoolYear(String schoolYear) {
		this.sociogram.setSchoolYear(schoolYear);
	}

	public void setActivated(String ownerOfficialName) throws Exception {
		this.configManager.setOrganizationTitle(ownerOfficialName);
		this.configManager.setActivated(true);
	}

	public boolean checkTrial(String serial) throws Exception {
    	try {
			var requestData = new TrialActiveRequest(serial);

			var responseData = SociogramAPIManager.request(
					SociogramConstants.SOCIOGRAM_API_TRIAL,
					requestData,
					TrialActiveResponse.class
			);

			return responseData.getResult();
		} catch (Exception e) {
			throw new Exception("POST request not worked. Please check your internet connection! \n\n" + e.getMessage() + "\n\n" + e.getStackTrace());
		}
	}

	public boolean isActivated() {
        try {
			boolean isTrial =  this.configManager.getTrial();
            boolean activated =  this.configManager.getActivated();
            if (!isTrial) {
				return activated;
			} else {
            	//Check trial
				boolean trialActive = checkTrial(this.configManager.getSerial());
				return trialActive;
			}

        } catch (Exception e) {
            e.printStackTrace();
            JErrorDialog errorDialog = new JErrorDialog(I18n.get("genericErrorMsg"), e);
            return false;
        }
    }

    public boolean isLanguageSelected() throws Exception {
        return this.configManager.getLanguageSelected();
    }

    public void setLanguageSelected() throws Exception {
        this.configManager.setLanguageSelected(true);
    }

    public boolean isProgramTypeSelected() throws Exception {
        return this.configManager.getProgramTypeSelected();
    }

    public void setProgramTypeSelected() throws Exception {
        this.configManager.setProgramTypeSelected(true);
    }

	public void setSelectedClassification(SocioClassification selectedClassification) {
		this.selectedClassification = selectedClassification;
	}

	public SocioClassification getSelectedClassification() {
		return selectedClassification;
	}

	public void calculateSociometricClassification() {
		double numOfSelections = 3.0;
		double maxIzbir = this.getPersons().size()*numOfSelections*1.0/2;
		
		double numOfVzajemnihIzbir = (getAllVzajemnoSelected().size()/2)/2.0;
		this.sociogram.setIndexKohez(numOfVzajemnihIzbir/maxIzbir);

        double groupIntegration = 0.0;
            int isolatedNum = 0;
            for (Person person : getPersons()) {
                int numOfSelected = getNumOfSelected(person.getId(), LikingType.POSITIVE) + getNumOfSelected(person.getId(), LikingType.NEGATIVE);
                if (numOfSelected == 0){
                    isolatedNum ++;
                }
            }
            if (isolatedNum != 0) {
                groupIntegration = 1.0 / isolatedNum;
            }
        this.sociogram.setIndexGroupIntegration(groupIntegration);
		
		for (Person person : this.getPersons()) {
			int numOfSelected = this.getNumOfSelected(person.getId(), LikingType.POSITIVE) + 
								this.getNumOfSelected(person.getId(), LikingType.NEGATIVE);
			
			double ssn = 1+1.0*(numOfSelected-numOfSelections)/((this.getPersons().size()-1));
			person.setSocioIndex(ssn);
		}

        calculte2DimSocioClassification();
	}

	public Double getIndexKohez() {
        if (this.sociogram.getIndexKohez() == null || this.sociogram.getIndexKohez().isNaN()) {
            return 0.0;
        }
		return this.sociogram.getIndexKohez();
	}

	public ArrayList<Person> getAllVzajemnoSelected() {
		ArrayList<Person> retVal = new ArrayList<Person>();
		
		for (Person personA : this.getPersons()) {
			for (Person personB : this.getPersons()) {
				if ( (personA.getNegSelections().contains(personB.getId()) && personB.getNegSelections().contains(personA.getId()))
					|| (personA.getPosSelections().contains(personB.getId()) && personB.getPosSelections().contains(personA.getId())) ){
					retVal.add(personA);
					retVal.add(personB);
				}
			}
		}
		
		return retVal;
	}

	public HashMap<SocioClassification, ArrayList<Person>> calculte2DimSocioClassification() {
		HashMap<Person, Integer> numOfPosHitsPerPerson = new HashMap<Person, Integer>();
		HashMap<Person, Integer> numOfNegHitsPerPerson = new HashMap<Person, Integer>();
		
		double posHitsAvg = 0;
		double negHitsAvg = 0;
		for (Person person : this.getPersons()){
			int posHits = this.getNumOfSelected(person.getId(), LikingType.POSITIVE);
			int negHits = this.getNumOfSelected(person.getId(), LikingType.NEGATIVE);
			posHitsAvg += posHits;
			negHitsAvg += negHits;
			numOfNegHitsPerPerson.put(person, negHits);
			numOfPosHitsPerPerson.put(person, posHits);
		}
		posHitsAvg /= this.getPersons().size();
		negHitsAvg /= this.getPersons().size();
		
		double posStdDev = 0;
		for (Iterator<Person> it = numOfPosHitsPerPerson.keySet().iterator(); it.hasNext() ; ) {
			int hits = numOfPosHitsPerPerson.get(it.next());
			posStdDev += (hits-posHitsAvg)*(hits-posHitsAvg);
		}
		posStdDev /= this.getPersons().size();
		posStdDev = Math.sqrt(posStdDev);
		
		double negStdDev = 0;
		for (Iterator<Person> it = numOfNegHitsPerPerson.keySet().iterator(); it.hasNext() ; ) {
			int hits = numOfNegHitsPerPerson.get(it.next());
			negStdDev += (hits-negHitsAvg)*(hits-negHitsAvg);
		}
		negStdDev /= this.getPersons().size();
		negStdDev = Math.sqrt(negStdDev);
		
		HashMap<SocioClassification, ArrayList<Person>> retVal = new HashMap<SocioClassification, ArrayList<Person>>();
		for (SocioClassification sc : SocioClassification.values()) {
			retVal.put(sc, new ArrayList<Person>());
		}
		
		for (Person person : this.getPersons()) {
			double zp = (numOfPosHitsPerPerson.get(person)-posHitsAvg)/posStdDev; 
			double zn = (numOfNegHitsPerPerson.get(person)-negHitsAvg)/negStdDev; 
			double socialPref = zp - zn;
			double socialVpliv = zp + zn;
			
			if (socialPref > 1.0 && zp > 0 && zn < 0){
				retVal.get(SocioClassification.LIKING).add(person);
                person.setStatus(SocioClassification.LIKING);
			} else if (socialPref < -1.0 && zp < 0 && zn > 0){
				retVal.get(SocioClassification.UNLIKING).add(person);
                person.setStatus(SocioClassification.UNLIKING);
			} else if (zp < 0 && zn < 0 && socialVpliv < -1.0){
				retVal.get(SocioClassification.UNSEEN).add(person);
                person.setStatus(SocioClassification.UNSEEN);
			} else if (zp > 0 && zn > 0 && socialVpliv > 1.0){
				retVal.get(SocioClassification.CONTROVERSE).add(person);
                person.setStatus(SocioClassification.CONTROVERSE);
			} else {
				retVal.get(SocioClassification.AVERAGES).add(person);
                person.setStatus(SocioClassification.AVERAGES);
			}

            person.setSocialPreferentiality(socialPref);
            person.setSocialImpact(socialVpliv);
			
		}
		
		return retVal;
	}

    public double getIndexGroupIntegration() {
        return this.sociogram.getIndexGroupIntegration();
    }

    public String getGrader() {
        return this.sociogram.getGrader();
    }

    public void setGrader(String text) {
        this.sociogram.setGrader(text);
    }

    public String getDate() {
        return this.sociogram.getDate();
    }

    public void setDate(String text) {
        this.sociogram.setDate(text);
    }

    public String getTeacher() {
        return this.sociogram.getTeacher();
    }

    public void setTeacher(String text) {
        this.sociogram.setTeacher(text);
    }

    public String getComment() {
        return this.sociogram.getComment();
    }

    public void setComment(String text) {
        this.sociogram.setComment(text);
    }

    public HashSet<Integer> getAllSelectedPersonIds(LikingType likingType, Person person) {
        HashSet<Integer> retVal = new HashSet<Integer>();

        for (int i = 0; i<3; i++) {
            if (likingType.equals(LikingType.POSITIVE)) {
                retVal.add(person.getPosSelections().get(i));
            } else {
                retVal.add(person.getNegSelections().get(i));
            }
        }

        return retVal;
    }

	public String getWorkingDirectory() {
		return workingDirectory;
	}

	public void setWorkingDirectory(String workingDirectory) {
		this.workingDirectory = workingDirectory;
	}

	public void setTrial(boolean isTrial) throws Exception {
    	this.configManager.setTrial(isTrial);
	}

	public void setSerial(String enteredSerial) throws Exception {
		this.configManager.setSerial(enteredSerial);
	}

	public void exitSociogram(Component parentComponent) {
		int result = JOptionPane.showOptionDialog(parentComponent,
				I18n.get("exitWarn"),
				I18n.get("programExit"),
				JOptionPane.YES_NO_OPTION,
				JOptionPane.WARNING_MESSAGE,
				null,
				new String[]{I18n.get("yes"),I18n.get("no")}, I18n.get("no"));
		if (result == JOptionPane.YES_OPTION){
			System.exit(0);
		}
	}
}
