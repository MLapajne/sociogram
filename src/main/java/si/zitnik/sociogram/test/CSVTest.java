package si.zitnik.sociogram.test;

import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.io.csv.CSVManager;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by slavkoz on 26/08/14.
 */
public class CSVTest {
    public static void main(String[] args) throws Exception{
        ArrayList<Integer> list = new ArrayList<Integer>();
        list.add(1);
        list.add(3);
        list.add(555);
        Person person1 = new Person(0, "Janez", "Brezovar", Gender.MALE);
        Person person2 = new Person(2, "Marjan", "Kozlevčar", Gender.MALE);
        ArrayList<Person> persons = new ArrayList<Person>();
        persons.add(person1);
        persons.add(person2);

        new CSVManager("C:/Users/szitnik/Desktop/person.csv").writeData(persons);
        @SuppressWarnings("unused")
        Collection<Person> persons1 = new CSVManager("C:/Users/szitnik/Desktop/person.csv").readData();
    }
}
