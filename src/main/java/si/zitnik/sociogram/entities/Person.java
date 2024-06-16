package si.zitnik.sociogram.entities;

import si.zitnik.sociogram.enums.SocioClassification;
import si.zitnik.sociogram.util.I18n;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "person")
public class Person {
	private int id;
	private String firstName;
	private String lastName;
	private Gender gender;
	private ArrayList<Integer> posSelections;
	private ArrayList<Integer> negSelections;
	private Double socioIndex;
    private double socialPreferentiality;
    private double socialImpact;
    private SocioClassification status;

    public Person(){}

    public Person(int id, String firstName, String lastName, Gender gender, ArrayList<Integer> posSelections, ArrayList<Integer> negSelections, Double socioIndex, double socialPreferentiality, double socialImpact, SocioClassification status) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.posSelections = posSelections;
        this.negSelections = negSelections;
        this.socioIndex = socioIndex;
        this.socialPreferentiality = socialPreferentiality;
        this.socialImpact = socialImpact;
        this.status = status;
    }

    public Person(int id, String firstName, String lastName, Gender gender, ArrayList<Integer> posSelections, ArrayList<Integer> negSelections, Double socioIndex) {
        this(id, firstName, lastName, gender, posSelections, negSelections, socioIndex, 0., 0., SocioClassification.AVERAGES);
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
		this.gender = gender;
		this.posSelections = posSelections;
		this.negSelections = negSelections;
		this.socioIndex = socioIndex;
	}
	
	public Person(int id, String firstName, String lastName, Gender gender) {
		this(id, firstName, lastName, gender, null, null, 0.0);
		
		ArrayList<Integer> list1 = new ArrayList<Integer>();
		list1.add(0); list1.add(0); list1.add(0);
		ArrayList<Integer> list2 = new ArrayList<Integer>();
		list2.add(0); list2.add(0); list2.add(0);
		
		this.posSelections = list1;
		this.negSelections = list2;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	
	public Gender getGender() {
		return gender;
	}
	public void setGender(Gender gender) {
		this.gender = gender;
	}
	
	@XmlElementWrapper(name = "positiveSelections")
	@XmlElement(name = "selection")
	public ArrayList<Integer> getPosSelections() {
		return posSelections;
	}
	public void setPosSelections(ArrayList<Integer> posSelections) {
		this.posSelections = posSelections;
	}
	
	@XmlElementWrapper(name = "negativeSelections")
	@XmlElement(name = "selection")
	public ArrayList<Integer> getNegSelections() {
		return negSelections;
	}
	public void setNegSelections(ArrayList<Integer> negSelections) {
		this.negSelections = negSelections;
	}
	
	@Override
	public String toString() {
		return firstName +";"+ lastName +";"+ (gender == Gender.MALE ? I18n.get("man") : I18n.get("woman"));
	}

	public void setSocioIndex(Double socioIndex) {
		this.socioIndex = socioIndex;
	}

	public Double getSocioIndex() {
		return socioIndex;
	}

    public void setSocialPreferentiality(double socialPreferentiality) {
        this.socialPreferentiality = socialPreferentiality;
    }

    public double getSocialPreferentiality() {
        return socialPreferentiality;
    }

    public void setSocialImpact(double socialImpact) {
        this.socialImpact = socialImpact;
    }

    public double getSocialImpact() {
        return socialImpact;
    }


    public void setStatus(SocioClassification status) {
        this.status = status;
    }

    public SocioClassification getStatus() {
        return status;
    }
}
