package si.zitnik.sociogram.gui.poll;

import java.awt.event.MouseEvent;

import javax.swing.JTable;

import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class StatusTable extends JTable {
	private static final long serialVersionUID = -5962581476313097646L;
	private RunningUtil runningUtil;
	private PollTable pollTable;

	public StatusTable(RunningUtil runningUtil, PollTable polltable) {
		this.runningUtil = runningUtil;
		this.pollTable = polltable;
		this.setModel(new StatusTableModel(runningUtil));
		this.setAutoResizeMode(AUTO_RESIZE_OFF);
		setColumnWidths();
	}

	
	private void setColumnWidths() {
		if (this.runningUtil.getPersons().size() > 0){
			int numberColumnWidth = this.pollTable.getColumnModel().getColumn(2).getPreferredWidth();
			for (int i = 1; i<this.getColumnCount(); i++) {
				this.getColumnModel().getColumn(i).setPreferredWidth(numberColumnWidth);
			}
	
			this.getColumnModel().getColumn(0).setPreferredWidth(this.pollTable.getColumnModel().getColumn(0).getPreferredWidth()+this.pollTable.getColumnModel().getColumn(1).getPreferredWidth());
		}
	}
	
	public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);

        if (colIndex > 0){
        	tip = this.runningUtil.getPersonFullName(colIndex)+": ";
        	if (rowIndex == 0){
            	tip += this.runningUtil.getNumOfSelected(colIndex, LikingType.POSITIVE);
            } else {
            	tip += this.runningUtil.getNumOfSelected(colIndex, LikingType.NEGATIVE);
            }
        } else {
        	if (rowIndex == 0){
				tip = I18n.get("numberOfPositiveChoices") + ":";
			} else {
				tip = I18n.get("numberOfNegativeChoices") + ":";
			}
        }
        
        
        return tip;
    }
}

