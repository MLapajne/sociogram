package si.zitnik.sociogram.io.csv;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.util.I18n;

public class CSVManager {
	private String filename;
	private final String splitString = ";"; 
	
	public CSVManager(String filename){
		this.filename = filename;
	}
	
	public void writeData(Collection<Person> data) throws Exception {
		BufferedWriter fw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filename, false), Charset.forName("UTF8")));
		fw.append(I18n.get("csvNameHeader")+splitString+I18n.get("csvSurnameHeader")+splitString+I18n.get("csvGenderHeader"));
		fw.newLine();
		for (Person person : data) {
			fw.append(person.toString());
			fw.newLine();
		}
		fw.close();
	}
	
	public Collection<Person> readData() throws Exception {
		Scanner sc = new Scanner(new InputStreamReader(new FileInputStream(filename), Charset.forName("UTF8")));
		Collection<Person> retVal = new ArrayList<Person>();
		int id = 1;
		Integer nameIdx = null;
		Integer surnameIdx = null;
		Integer genderIdx = null;
		
		String[] firstLine = sc.nextLine().split(splitString);
		for (int i = 0; i<firstLine.length; i++){
			if (firstLine[i].toLowerCase().equals(I18n.get("csvNameHeader").toLowerCase().trim())){
				nameIdx = i;
			} else if (firstLine[i].toLowerCase().equals(I18n.get("csvSurnameHeader").toLowerCase().trim())){
				surnameIdx = i;
			} else if (firstLine[i].toLowerCase().equals(I18n.get("csvGenderHeader").toLowerCase().trim())){
				genderIdx = i;
			}
		}
		if (nameIdx == null || surnameIdx == null || genderIdx == null){
			throw new Exception(I18n.get("csvReadingErrorMsg"));
		}

        int currentLine = 1;
		while (sc.hasNext()){
            try {
                String[] line = sc.nextLine().split(this.splitString);
                retVal.add(new Person(id++, line[nameIdx], line[surnameIdx],
                        (line[genderIdx].substring(0, 1).equalsIgnoreCase("m")) ? Gender.MALE : Gender.FEMALE));
            } catch (Exception e1) {
                @SuppressWarnings("unused")
                JErrorDialog errorDialog = new JErrorDialog(String.format(I18n.get("csvTxtMalformedMsg"), currentLine), e1);
            }
            currentLine++;
		}
		sc.close();
		return retVal;
	}
}
