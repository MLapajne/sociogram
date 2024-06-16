package si.zitnik.sociogram.test;

import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.entities.Sociogram;
import si.zitnik.sociogram.io.xml.XMLManager;

import java.util.ArrayList;

/**
 * Created by slavkoz on 26/08/14.
 */
public class XMLManagerTest {
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
        Sociogram sociogram = new Sociogram("G1.a", "2009/2010", persons, 0.0);

        new XMLManager("/Users/slavkoz/temp/sociogram.socx").encodeXML(sociogram);

        @SuppressWarnings("unused")
        Sociogram retrievedSoc = (Sociogram)new XMLManager("/Users/slavkoz/temp/sociogram.socx").decodeXML(Sociogram.class);
    }
}
