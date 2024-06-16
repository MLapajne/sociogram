package si.zitnik.sociogram.gui.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;

import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.gui.inputdata.InputTableModel;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class AddPersonListener implements ActionListener {
	private RunningUtil runningUtil;
	private JTextField name;
	private JTextField surname;
	private JComboBox gender;
	private JTable table;
	
	
	public AddPersonListener(RunningUtil runningUtil, JTextField name, JTextField surname, JComboBox gender, JTable table) {
		this.runningUtil = runningUtil;
		this.name = name;
		this.surname = surname;
		this.gender = gender;
		this.table = table;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (name.getText().length() > 0 && surname.getText().length() > 0){
			Person person = new Person(3, name.getText(), surname.getText(), (gender.getSelectedItem().toString().toLowerCase().startsWith("m")) ? Gender.MALE : Gender.FEMALE);
			this.runningUtil.addPerson(person);
			((InputTableModel)table.getModel()).fireTableDataChanged();
			name.setText(""); surname.setText(""); gender.setSelectedIndex(0);
		} else {
			JOptionPane.showMessageDialog(null,
                    I18n.get("basicDataWarn"),
				    I18n.get("addPersonWarnTxt"),
				    JOptionPane.WARNING_MESSAGE); 
		}
		
	}

}
