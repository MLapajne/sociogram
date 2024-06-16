package si.zitnik.sociogram.config;

import java.util.prefs.Preferences;
import si.zitnik.sociogram.enums.ProgramType;
import si.zitnik.sociogram.util.I18n;

public class ConfigManager {
	private final String comments = "CAUTION: Do not change this file - Authorised personnel only!";
	private final String splitString = ";";
	
	private final Preferences userPreferences;
	private StringEncrypter encrypter;
	
 	public ConfigManager() throws Exception{
		userPreferences = Preferences.userNodeForPackage(getClass());
		this.encrypter = new StringEncrypter();     
	}

    public void setLanguage(String language) throws Exception{
        this.userPreferences.put(encrypter.encrypt("language"), encrypter.encrypt(language));
    }
    public String getLanguage() throws Exception{
        return this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("language"),
				encrypter.encrypt("sl")));
    }

    public void setProgramType(ProgramType programType) throws Exception{
        this.userPreferences.put(encrypter.encrypt("programType"), encrypter.encrypt(programType.toString()));
    }
    public ProgramType getProgramType() throws Exception{
        String programType = this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("programType"),
				encrypter.encrypt(ProgramType.SOLSTVO.toString())));
        if (programType == null || programType.isEmpty()) {
            return ProgramType.SOLSTVO;
        } else {
            return ProgramType.valueOf(programType);
        }
    }

    public void setProgramTypeSelected(Boolean activated) throws Exception{
        this.userPreferences.put(encrypter.encrypt("programTypeSelected"), encrypter.encrypt(activated.toString()));
    }
	
    public Boolean getProgramTypeSelected() throws Exception{
        if (this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("programTypeSelected"),
				encrypter.encrypt("false"))).equals("true")) {
            return true;
        }
        return false;
    }

	public void setTrial(Boolean trialActive) throws Exception{
		this.userPreferences.put(encrypter.encrypt("trial"), encrypter.encrypt(trialActive.toString()));
	}
	
	public Boolean getTrial() throws Exception{
		if (this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("trial"),
				encrypter.encrypt("false"))).equals("true")) {
			return true;
		}
		return false;
	}

    public void setLanguageSelected(Boolean activated) throws Exception{
        this.userPreferences.put(encrypter.encrypt("languageSelected"), encrypter.encrypt(activated.toString()));
    }
	
    public Boolean getLanguageSelected() throws Exception{
        if (this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("languageSelected"),
				encrypter.encrypt("false"))).equals("true")) {
            return true;
        }
        return false;
    }

 	public void setOrganizationTitle(String orgTitle) throws Exception{
 		this.userPreferences.put(encrypter.encrypt("organizationTitle"), encrypter.encrypt(orgTitle));
 	}
	 
 	public String getOrganizationTitle() throws Exception{
 		return this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("organizationTitle"),
				encrypter.encrypt("MikroGrafArt & Krimar.si - DEMO")));
 	}

	public void setSerial(String serial) throws Exception{
		this.userPreferences.put(encrypter.encrypt("serial"), encrypter.encrypt(serial));
	}
	
	public String getSerial() throws Exception{
		return this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("serial"),
				null));
	}

 	public void setActivated(Boolean activated) throws Exception{
 		this.userPreferences.put(encrypter.encrypt("activated"), encrypter.encrypt(activated.toString()));
 	}
	 
 	public Boolean getActivated() throws Exception{
 		if (this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("activated"),
				encrypter.encrypt("false"))).equals("true")) {
 			return true;
 		}
 		return false;
 	}
	 
 	public void setPosQuestions(PropertiesQuestions questions) throws Exception{
 		this.userPreferences.put(encrypter.encrypt("posQuestions"), encrypter.encrypt(questions.toString()));
 	}
	 
 	public PropertiesQuestions getPosQuestions() throws Exception{
		String defaultPosQuestions = new PropertiesQuestions(new String[]{
				I18n.get("pos_Q1_" + ProgramType.SOLSTVO),
				I18n.get("pos_Q2_" + ProgramType.SOLSTVO),
				I18n.get("pos_Q3_" + ProgramType.SOLSTVO),
		}).toString();

 		return new PropertiesQuestions(this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("posQuestions"),
				encrypter.encrypt(defaultPosQuestions))).split(this.splitString));
 	}
	 
 	public void setNegQuestions(PropertiesQuestions questions) throws Exception{
 		this.userPreferences.put(encrypter.encrypt("negQuestions"), encrypter.encrypt(questions.toString()));
 	}
	 
 	public PropertiesQuestions getNegQuestions() throws Exception{
		String defaultQuestions = new PropertiesQuestions(new String[]{
				I18n.get("neg_Q1_" + ProgramType.SOLSTVO),
				I18n.get("neg_Q2_" + ProgramType.SOLSTVO),
				I18n.get("neg_Q3_" + ProgramType.SOLSTVO),
		}).toString();

 		return new PropertiesQuestions(this.encrypter.decrypt(this.userPreferences.get(encrypter.encrypt("negQuestions"),
				encrypter.encrypt(defaultQuestions))).split(this.splitString));
 	}

	public static void main(String[] args) throws Exception{
		//Storage:
		// Linux: user-home/.java/.userPrefs (to check)
		// Windows: Windows registry (to check)
		// Mac: ~/Library/Preferences/si.zitnik.sociogram.plist

		ConfigManager cm = new ConfigManager();

		//REMOVE EVERYTHING!!!
		cm.userPreferences.removeNode();

		//DEFAULT DATA
		I18n.initOrUpdate(null);

		System.out.println(cm.getOrganizationTitle());
		System.out.println(cm.getActivated());
		System.out.println(cm.getNegQuestions());
		System.out.println(cm.getPosQuestions());
        System.out.println(cm.getLanguage());
        System.out.println(cm.getLanguageSelected());
        System.out.println(cm.getProgramType());
        System.out.println(cm.getProgramTypeSelected());
		System.out.println(cm.getTrial());
		System.out.println(cm.getSerial());

		//Test
		//cm.setOrganizationTitle("TEST ORGANIZATION CC");
	}
}
