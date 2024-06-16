package si.zitnik.sociogram.gui.inputdata;

import java.util.ArrayList;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.util.I18n;

public class InputTableModel extends AbstractTableModel implements TableModelListener{
	private static final long serialVersionUID = 6670182195215273289L;
	ArrayList<Person> persons;
	
	public InputTableModel(ArrayList<Person> persons) {
		this.persons = persons;
	}

	@Override
	public int getColumnCount() {
		return 4;
	}

	@Override
	public int getRowCount() {
		return this.persons.size();
	}

	@Override
	public Object getValueAt(int row, int col) {
		switch (col){
		case 0:
			return this.persons.get(row).getId();
		case 1:
			return this.persons.get(row).getFirstName();
		case 2:
			return this.persons.get(row).getLastName();
		case 3:
			return this.persons.get(row).getGender().equals(Gender.MALE) ? I18n.get("man") : I18n.get("woman") ;
		}
		return "ERROR!";
	}
	

    public boolean isCellEditable(int row, int col) {
        if (col < 1) {
            return false;
        } else {
            return true;
        }
    }


    public void setValueAt(Object value, int row, int col) {
        //fireTableCellUpdated(row, col);
    	switch (col){
		case 1:
			this.persons.get(row).setFirstName((String) value);
			break;
		case 2:
			this.persons.get(row).setLastName((String) value);
			break;
		case 3:
			Gender gender = (value.toString().toLowerCase().startsWith("m")) ? Gender.MALE : Gender.FEMALE;
			this.persons.get(row).setGender(gender);
			break;
		}
    }

    @Override
    public String getColumnName(int column) {
    	switch (column){
    	case 0: return I18n.get("seqNo");
    	case 1: return I18n.get("name");
    	case 2: return I18n.get("surname");
    	case 3: return I18n.get("gender");
    	}
    	return "ERROR!";
    }
    
	@Override
	public void tableChanged(TableModelEvent e) {
		
		
	}

}
