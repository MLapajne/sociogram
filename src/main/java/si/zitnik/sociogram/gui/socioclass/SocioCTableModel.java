package si.zitnik.sociogram.gui.socioclass;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.enums.SocioClassification;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class SocioCTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -2484190579248201885L;
	private RunningUtil runningUtil;
	private ArrayList<Person> persons;

	public SocioCTableModel(RunningUtil runningUtil) {
		this.runningUtil = runningUtil;
		init();
	}

	public void init() {
		if (!this.runningUtil.getSelectedClassification().equals(SocioClassification.ALL)) {
			this.persons = this.runningUtil.calculte2DimSocioClassification().get(
					this.runningUtil.getSelectedClassification());
		} else {
			this.persons = this.runningUtil.getPersons();
		}
	}

	@Override
	public int getColumnCount() {
		return 7;
	}

	@Override
	public int getRowCount() {
		return this.persons.size();
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Person person = this.persons.get(rowIndex);
			if (columnIndex == 0){
				return this.runningUtil.getPersonFullName(person.getId());
			} else if (columnIndex == 1) {
				java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
				return " "+df.format(person.getSocioIndex());
			} else if (columnIndex == 2) {
                if (Double.isNaN(person.getSocialPreferentiality())) {
                    return "";
                }
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
                return " "+df.format(person.getSocialPreferentiality());
            } else if (columnIndex == 3) {
                if (Double.isNaN(person.getSocialImpact())) {
                    return "";
                }
                java.text.DecimalFormat df = new java.text.DecimalFormat("#.##");
                return " "+df.format(person.getSocialImpact());
            } else if (columnIndex == 4) {
                switch (person.getStatus()) {
                    case AVERAGES:
                        return I18n.get("averagePerson");
                    case CONTROVERSE:
                        return I18n.get("controversialPerson");
                    case LIKING:
                        return I18n.get("likedPerson");
                    case UNLIKING:
                        return I18n.get("rejectedPerson");
                    case UNSEEN:
                        return I18n.get("unseenPerson");
                }
            } else if (columnIndex == 5) {
                return this.runningUtil.getNumOfSelected(person.getId(), LikingType.POSITIVE);
            } else if (columnIndex == 6) {
                return this.runningUtil.getNumOfSelected(person.getId(), LikingType.NEGATIVE);
            }
        return "";
	}
	
	@Override
	public void setValueAt(Object arg0, int arg1, int arg2) {}
	
	@Override
	public boolean isCellEditable(int arg0, int arg1) {
		return false;
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0){
			return I18n.get("name");
		} else if (column == 1) {
			return I18n.get("socioIndex");
		} else if (column == 2) {
            return I18n.get("socioPref");
        } else if (column == 3) {
            return I18n.get("socioImpact");
        } else if (column == 4) {
            return I18n.get("socioStatus");
        } else if (column == 5) {
            return I18n.get("posSelections");
        } else if (column == 6) {
            return I18n.get("negSelections");
        }
        return "";
    }
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

	
}
