package si.zitnik.sociogram.gui.poll;

import javax.swing.table.AbstractTableModel;

import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class StatusTableModel extends AbstractTableModel {
	private static final long serialVersionUID = -2840366908265903445L;
		private RunningUtil runningUtil;

		public StatusTableModel(RunningUtil runningUtil){
			this.runningUtil = runningUtil;
		}
		
		@Override
		public void setValueAt(Object arg0, int arg1, int arg2) {
		}

		
		@Override
		public boolean isCellEditable(int arg0, int arg1) {
			return false;
		}
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0){
				if (rowIndex == 0){
					return I18n.get("numberOfPositiveChoices") + ":";
				} else {
					return I18n.get("numberOfNegativeChoices") + ":";
				}
			} else {
				if (rowIndex == 0){
					return this.runningUtil.getNumOfSelected(columnIndex, LikingType.POSITIVE);
				} else {
					return this.runningUtil.getNumOfSelected(columnIndex, LikingType.NEGATIVE);
				}
			}
		}
		
		@Override
		public int getRowCount() {
			return 2;
		}
		
		@Override
		public String getColumnName(int arg0) {
			return "";
		}
		
		@Override
		public int getColumnCount() {
			return this.runningUtil.getPersons().size()+1;
		}
		
		@Override
		public Class<?> getColumnClass(int arg0) {
			return String.class;
		}

}

