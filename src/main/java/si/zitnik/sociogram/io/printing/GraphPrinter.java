package si.zitnik.sociogram.io.printing;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

import si.zitnik.sociogram.enums.PersonDrawType;
import si.zitnik.sociogram.gui.graph.CircleGraphPanel;
import si.zitnik.sociogram.gui.graph.GraphUtil;
import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;

public class GraphPrinter implements Printable {
	private CircleGraphPanel panel;
	private RunningUtil runningUtil;
	private GraphUtil graphUtil;

	public GraphPrinter(CircleGraphPanel panel, RunningUtil runningUtil, GraphUtil graphUtil){
		this.runningUtil = runningUtil;
		this.panel = panel;
		this.graphUtil = graphUtil;
	}

	@Override
	public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
		if (pageIndex > 0) { /* We have only one page, and 'page' is zero-based */
	         return NO_SUCH_PAGE;
	    } else {
		    Graphics2D g2d = (Graphics2D)graphics;
		    
	    	FontMetrics fm = g2d.getFontMetrics(); 

	    	
		    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
		    String title = null;
			try {
				title = I18n.get("sociometricGraph") + " - ";
				title += runningUtil.getInstitution();
			} catch (Exception e) {	}
			TextPrinter.printText(g2d, (int)pageFormat.getImageableWidth(), 20, ((int)pageFormat.getImageableWidth()-fm.stringWidth(title))/2, 30, title);
		    //g2d.drawString(title, ((int)pageFormat.getImageableWidth()-fm.stringWidth(title))/2, 30);
		    int x = 0; int y = 50;
		    String pos = I18n.get("positiveQuestion") + ": "+runningUtil.getSelectedPosQuestion();
		    String neg = I18n.get("negativeQuestion") + ": "+runningUtil.getSelectedNegQuestion();
		    String gender = I18n.get("selectedGender") + ": ";
		    if (this.graphUtil.isDrawWoman()){
		    	gender += I18n.get("woman").toUpperCase() + " ";
		    }
		    if (this.graphUtil.isDrawWoman() && this.graphUtil.isDrawMan()){
		    	gender += I18n.get("and") + " ";
		    }
		    if (this.graphUtil.isDrawMan()){
		    	gender += I18n.get("man").toUpperCase();
		    }
		    String tipGrafa = I18n.get("graphType") + ": ";
		    if (PersonDrawType.ALL.equals(this.graphUtil.getPersonToDrawType())) {
		    	tipGrafa += I18n.get("all").toUpperCase();
		    } else if (PersonDrawType.SPECIFIC_PERSON.equals(this.graphUtil.getPersonToDrawType())) {
		    	tipGrafa += I18n.get("selectedPerson") + " - "+this.runningUtil.getPersonFullName(this.graphUtil.getPersonToDraw().getId());
		    } else if (PersonDrawType.UNSELECTED.equals(this.graphUtil.getPersonToDrawType())) {
		    	tipGrafa += I18n.get("nonselected").toUpperCase();
		    } else if (PersonDrawType.VZAJEMNO_SELECTED.equals(this.graphUtil.getPersonToDrawType())) {
		    	tipGrafa += I18n.get("mutuallySelected").toUpperCase();
		    }
		    
		    
		    y = TextPrinter.printText(g2d, (int)pageFormat.getImageableWidth()+x, 20, x, y, pos);
		    y = TextPrinter.printText(g2d, (int)pageFormat.getImageableWidth()+x, 20, x, y, neg);
//		    g2d.drawString(pos, 0, y);
//		    y += 20;
//		    g2d.drawString(neg, 0, y);
//		    y += 20;
		    g2d.drawString(tipGrafa, 0, y);
		    y += 20;
		    g2d.drawString(gender, 0, y);
		    y += 20;
		    g2d.translate(pageFormat.getImageableX(), pageFormat.getImageableY()+y); 
		    Color oldColor = panel.getBackground();
		    
		    panel.setBackground(Color.WHITE);
		    
		    double factorX = pageFormat.getImageableWidth() / panel.getWidth();
		    double factorY = pageFormat.getImageableHeight() / panel.getHeight();
		    double factor = Math.min( factorX, factorY );
		    g2d.scale(factor,factor);

		    
		    panel.print(g2d);
		    panel.setBackground(oldColor);
		    
		    /* tell the caller that this page is part of the printed document */
		    return PAGE_EXISTS;
	    }
	}
	
	public void print() throws Exception{
		PrinterJob job = PrinterJob.getPrinterJob();
		job.setPrintable(this);	
		boolean doPrint = job.printDialog();
		if (doPrint){
			job.print();
		}
	}

}
