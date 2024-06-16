package si.zitnik.sociogram.entities;

import java.util.ArrayList;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement
@XmlType(propOrder = { "department", "schoolYear", "posQuestion", "negQuestion", "persons", "indexKohez", "indexGroupIntegration", "grader", "date", "teacher", "comment" })
public class Sociogram {
	private String department = "";
	private String schoolYear = "";
	private String posQuestion = "";
	private String negQuestion = "";
	private ArrayList<Person> persons;
	private Double indexKohez = 0.;
    private Double indexGroupIntegration = 0.;
    private String grader = "";
    private String date = "";
    private String teacher = "";
    private String comment = "";

    public Sociogram(){}

	public Sociogram(String department, String schoolYear, ArrayList<Person> persons, Double indexKohez) {
		this.department = department;
		this.schoolYear = schoolYear;
		this.persons = persons;
		this.indexKohez = indexKohez;
	}
	
	public String getDepartment() {
		return department;
	}
	public void setDepartment(String department) {
		this.department = department;
	}
	public String getSchoolYear() {
		return this.schoolYear;
	}
	public void setSchoolYear(String schoolYear) {
		this.schoolYear = schoolYear;
	}
	public String getPosQuestion() {
		return posQuestion;
	}
	public void setPosQuestion(String posQuestion) {
		this.posQuestion = posQuestion;
	}
	public String getNegQuestion() {
		return negQuestion;
	}
	public void setNegQuestion(String negQuestion) {
		this.negQuestion = negQuestion;
	}
	@XmlElementWrapper(name = "persons")
	@XmlElement(name = "person")
	public ArrayList<Person> getPersons() {
		return this.persons;
	}

	public void setPersons(ArrayList<Person> persons) {
		this.persons = persons;
	}

	public void setIndexKohez(Double indexKohez) {
		this.indexKohez = indexKohez;
	}

	public Double getIndexKohez() {
		return indexKohez;
	}

    public Double getIndexGroupIntegration() {
        return indexGroupIntegration;
    }

    public void setIndexGroupIntegration(Double indexGroupIntegration) {
        this.indexGroupIntegration = indexGroupIntegration;
    }

    public String getGrader() {
        return grader;
    }

    public void setGrader(String grader) {
        this.grader = grader;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }


}
