package si.zitnik.sociogram.gui.poll;

import java.util.HashSet;

import javax.swing.table.AbstractTableModel;

import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.util.RunningUtil;

public class PollTableModel extends AbstractTableModel {
	private static final long serialVersionUID = 7246999819885015265L;
	
	private RunningUtil runningUtil;
	private HashSet<Integer> fullPositiveSelectedRows;
	private HashSet<Integer> fullNegativeSelectedRows;
	
	public PollTableModel(RunningUtil runningUtil) {
		this.runningUtil = runningUtil;
		this.fullNegativeSelectedRows = new HashSet<Integer>();
		this.fullPositiveSelectedRows = new HashSet<Integer>();
		initFullSelectedRows();
	}


	private void initFullSelectedRows() {
		for (int i = 0; i < this.runningUtil.getPersons().size(); i++) {
			int realId = i+1;
			if (this.runningUtil.hasAllSelected(realId, LikingType.POSITIVE)){
				addFullPositiveSelected(realId);
			}
			if (this.runningUtil.hasAllSelected(realId, LikingType.NEGATIVE)){
				addFullNegativeSelected(realId);
			}
		}
		
		
	}


	@Override
	public int getColumnCount() {
		if (this.runningUtil.getPersons().size() == 0){
			return 0;
		}
		return this.runningUtil.getPersons().size()+2;
	}

	@Override
	public String getColumnName(int columnIndex) {
		return "";
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Class getColumnClass(int c) {
        return String.class;
    }

	@Override
	public int getRowCount() {
		if (this.runningUtil.getPersons().size() == 0){
			return 0;
		}
		return this.runningUtil.getPersons().size()+1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if (columnIndex == 0 && rowIndex > 0){
			//id-ji vertical
			return rowIndex;
		} else if (columnIndex == 1 && rowIndex > 0){
			//imena
			return this.runningUtil.getPersonFullName(rowIndex);
		} else if (columnIndex > 1 && rowIndex == 0){
			//id-ji horizontal
			return columnIndex-1;
		} else if (columnIndex > 1 && rowIndex > 0){
			if (columnIndex-1 == rowIndex){
				//diagonalci
				return "";
			} else {
				//vrednost med osebami
				return getIzbiro(Integer.parseInt(getValueAt(rowIndex, 0).toString()), Integer.parseInt(getValueAt(0, columnIndex).toString()));
			}
		} else {
			//zgornji levi prazen del
			return "";
		}
	}

	private String getIzbiro(int realId1, int realId2) {
		int counter = 1;
		for (int chosenId : this.runningUtil.getPersons().get(realId1-1).getNegSelections()) {
			if (chosenId == realId2){
				return counter+"-";
			}
			counter++;
		}
		
		counter = 1;
		for (int chosenId : this.runningUtil.getPersons().get(realId1-1).getPosSelections()) {
			if (chosenId == realId2){
				return counter+"+";
			}
			counter++;
		}
		
		return "";
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return false;
	}


	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
	}


	public boolean isFreeCell(int selectedRow, int selectedColumn) {
		if (this.getValueAt(selectedRow, selectedColumn).toString().equals("")){
			return true;
		} else {
			return false;
		}
	}


	public LikingType getLikingType(int selectedRow, int selectedColumn) {
		if (this.getValueAt(selectedRow, selectedColumn).toString().contains("+")){
			return LikingType.POSITIVE;
		} else {
			return LikingType.NEGATIVE;
		}
	}


	public boolean isFullPositiveSelected(int row) {
		return this.fullPositiveSelectedRows.contains(row);
	}


	public boolean isFullNegativeSelected(int row) {
		return this.fullNegativeSelectedRows.contains(row);
	}


	public void addFullPositiveSelected(int row) {
		this.fullPositiveSelectedRows.add(row);		
	}


	public void addFullNegativeSelected(int row) {
		this.fullNegativeSelectedRows.add(row);
	}


	public void removeAllSelected(int row, LikingType oldLikingType) {
		if (oldLikingType.equals(LikingType.POSITIVE)){
			this.fullPositiveSelectedRows.remove(row);
		} else {
			this.fullNegativeSelectedRows.remove(row);
		}
	}



	

}
