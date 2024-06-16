package si.zitnik.sociogram.io.xml;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Writer;
import java.util.ArrayList;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.entities.Sociogram;

public class XMLManager {
	private String _filename;
	
	public XMLManager(String filename){
		_filename = filename;
	}
	
	public void encodeXML(Object objToEncode) throws Exception {
		JAXBContext context = JAXBContext.newInstance(objToEncode.getClass());
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
		//m.marshal(objToEncode, System.out);
		
		Writer w = null;
		w = new FileWriter(_filename);
		m.marshal(objToEncode, w);
		w.close();
	}
	
	@SuppressWarnings("unchecked")
	public Object decodeXML(Class objType) throws Exception{
		JAXBContext context = JAXBContext.newInstance(objType);
		Unmarshaller um = context.createUnmarshaller();
		FileReader fReader = new FileReader(_filename);
		Object retVal = um.unmarshal(fReader);
		fReader.close();
		return retVal;
	}
}
