package si.zitnik.sociogram.gui.socioclass;

import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import si.zitnik.sociogram.util.RunningUtil;

public class SocioCTable extends JTable {
	private static final long serialVersionUID = -5718551917212872739L;
	@SuppressWarnings("unused")
	private RunningUtil runningUtil;

	public SocioCTable(RunningUtil runningUtil) {
		this.runningUtil = runningUtil;

		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setModel(new SocioCTableModel(runningUtil));
		this.setFillsViewportHeight(true);
	}

}
