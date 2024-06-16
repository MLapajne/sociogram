package si.zitnik.sociogram.gui.poll;

import java.awt.Color;
import java.awt.Component;
import java.awt.FontMetrics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.table.TableCellRenderer;

import org.pushingpixels.radiance.theming.api.renderer.RadianceDefaultTableCellRenderer;
import si.zitnik.sociogram.SociogramRunner;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.enums.LikingType;
import si.zitnik.sociogram.util.RunningUtil;

public class PollTable extends JTable implements MouseListener {
	private static final long serialVersionUID = 7539575265821577280L;
	private final PrintableColorRenderer printableColorRenderer;
	private final ColorRenderer colorRenderer;
	private RunningUtil runningUtil;
	private final int pixelsForEachDigitMargin = 10;
	private final int nameColumnMargin = 10;
	private boolean isPrinting;
	
	public PollTable(RunningUtil runningUtil) {
		this.runningUtil = runningUtil;
		PollTableModel tableModel = new PollTableModel(runningUtil);
		this.isPrinting = false;
		this.setModel(tableModel);
		this.setAutoResizeMode(AUTO_RESIZE_OFF);
		setColumnWidths();
		
		this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		this.setRowSelectionAllowed(false);
		this.setColumnSelectionAllowed(false);
		this.setCellSelectionEnabled(true);
		this.setDefaultRenderer(String.class, new ColorRenderer());
		this.addMouseListener(this);

		this.printableColorRenderer = new PrintableColorRenderer();
		this.colorRenderer = new ColorRenderer();

	}

	private void setColumnWidths() {
		if (this.runningUtil.getPersons().size() > 0){
			FontMetrics fm = this.getFontMetrics(this.getFont());


			int numberColumnWidth = fm.stringWidth("3+") + pixelsForEachDigitMargin;
			for (int i = 0; i<this.runningUtil.getPersons().size()+2; i++){
				this.getColumnModel().getColumn(i).setPreferredWidth(numberColumnWidth);

			}

			int nameColumnWidth = numberColumnWidth;
			for (Person person : this.runningUtil.getPersons()) {
				int newWidth = fm.stringWidth(this.runningUtil.getPersonFullName(person.getId()));
				if (newWidth > nameColumnWidth){
					nameColumnWidth = newWidth;
				}
			}
			this.getColumnModel().getColumn(1).setPreferredWidth(nameColumnWidth+nameColumnMargin);
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getButton() == MouseEvent.BUTTON1){
			updateClickedCell(this.getSelectedRow(), this.getSelectedColumn(), LikingType.POSITIVE);
		} else if (event.getButton() == MouseEvent.BUTTON2 || event.getButton() == MouseEvent.BUTTON3){
	        updateClickedCell(rowAtPoint(event.getPoint()), columnAtPoint(event.getPoint()), LikingType.NEGATIVE);
		}
		
	}

	private void updateClickedCell(int selectedRow, int selectedColumn, LikingType likingType) {
		if (selectedColumn > 1 && selectedRow > 0 && (selectedColumn-1) != selectedRow){
			PollTableModel tableModel = ((PollTableModel)this.getModel());
			if (tableModel.isFreeCell(selectedRow, selectedColumn)){
				this.runningUtil.addPersonLikingSelection(selectedRow, selectedColumn-1, likingType);
				if (likingType.equals(LikingType.POSITIVE)){
					if (this.runningUtil.hasAllSelected(selectedRow, LikingType.POSITIVE)){
						tableModel.addFullPositiveSelected(selectedRow);
					}
				} else {
					if (this.runningUtil.hasAllSelected(selectedRow, LikingType.NEGATIVE)){
						tableModel.addFullNegativeSelected(selectedRow);
					}
				}
			} else {
				LikingType oldLikingType = tableModel.getLikingType(selectedRow, selectedColumn);
				this.runningUtil.removePersonLikingSelection(
						selectedRow, 
						selectedColumn-1, 
						oldLikingType);
				tableModel.removeAllSelected(selectedRow, oldLikingType);
			}
			tableModel.fireTableDataChanged();
		}
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		ToolTipManager.sharedInstance().setInitialDelay(0);
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		ToolTipManager.sharedInstance().setInitialDelay(RunningUtil.tooltipDelay);
	}

	@Override
	public void mousePressed(MouseEvent arg0) {}

	@Override
	public void mouseReleased(MouseEvent arg0) {}
	
	public String getToolTipText(MouseEvent e) {
        String tip = null;
        java.awt.Point p = e.getPoint();
        int rowIndex = rowAtPoint(p);
        int colIndex = columnAtPoint(p);

        if (colIndex > 1 && rowIndex > 0 && (colIndex-1) != rowIndex){
        	tip = this.runningUtil.getPersonFullName(rowIndex)+" -> "+this.runningUtil.getPersonFullName(colIndex-1);
        }
        return tip;
    }
	
	@Override
	public TableCellRenderer getCellRenderer(int row, int column) {
		if (this.isPrinting()){
			return this.printableColorRenderer;
		}
		return this.colorRenderer;
	}
	
	public void setIsPrinting(boolean isPrinting){
		this.isPrinting = true;
	}
	
	public boolean isPrinting() {
		return isPrinting;
	}
}

class ColorRenderer extends RadianceDefaultTableCellRenderer {
	private static final long serialVersionUID = 5650575614513642004L;

	public ColorRenderer() {
		//this.setOpaque(true);
	}

	public Component getTableCellRendererComponent(JTable table, Object text, boolean isSelected, boolean hasFocus, int row, int column) {
		this.setText(text.toString());
		if (column == 1){
			setHorizontalAlignment(SwingConstants.LEFT);
		} else {
			setHorizontalAlignment(SwingConstants.CENTER);
		}

		PollTableModel tableModel = (PollTableModel) table.getModel();
		boolean fullPosSelected = tableModel.isFullPositiveSelected(row);
		boolean fullNegSelected = tableModel.isFullNegativeSelected(row);
		if (row == column-1 && column > 1){
			//diagonalci
			setBackground(Colors.NOT_ALLOWED);
		} else if (fullNegSelected && fullPosSelected){
			setBackground(Colors.ALL);
		} else if (fullPosSelected){
			setBackground(Colors.ALL_POS);
		} else if (fullNegSelected){
			setBackground(Colors.ALL_NEG);
		} else {
			setBackground(UIManager.getColor ( "Panel.background" ));
		}
		return this;
	}
}

//TODO: fit paper for printing!!!
class PrintableColorRenderer extends RadianceDefaultTableCellRenderer {
	private static final long serialVersionUID = 6005885231774946910L;

	public PrintableColorRenderer() {
		//this.setOpaque(false);
	}

	public Component getTableCellRendererComponent(JTable table, Object text, boolean isSelected, boolean hasFocus, int row, int column) {
		this.setText(text.toString());
		if (column == 1){
			setHorizontalAlignment(SwingConstants.LEFT);
		} else {
			setHorizontalAlignment(SwingConstants.CENTER);
		}
		
		if (row == column-1 && column > 1){
			//diagonalci
			setBackground(Color.BLACK);
		} else {
			setBackground(Color.WHITE);
		} 
		return this;
	}
}
