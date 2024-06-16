package si.zitnik.sociogram.io.printing;

import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.util.Calendar;

import si.zitnik.sociogram.util.I18n;
import si.zitnik.sociogram.util.RunningUtil;


public class PollPrinter implements Printable {
	final private int padding = 30;
	final private int lineLength = 8;
	final private int lineWhiteSpaceLength = 6;
	
	private String institution;
	private String townAndDate;
	private String department;
	@SuppressWarnings("unused")
	private String schoolYear;
	private String posQuestion;
	private String negQuestion;
	private PrintingType printType;
	private int numOfPolls;

	private RunningUtil runningUtil;
	private boolean printNames;
	private int curNameCounter = 0;


	private PollPrinter(String institution, String townAndDate, String department, String schoolYear,
			String posQuestion, String neqQuestion, PrintingType printType, int numOfPolls){
		this.institution = institution;
		this.townAndDate = townAndDate;
		this.department = department;
		this.schoolYear = schoolYear;
		this.posQuestion = posQuestion;
		this.negQuestion = neqQuestion;
		this.printType = printType;
		this.numOfPolls = numOfPolls;
	}
	
	public PollPrinter(RunningUtil runningUtil, boolean printNames,PrintingType printType) throws Exception {
		this(runningUtil.getInstitution(),
				Calendar.getInstance().get(Calendar.DAY_OF_MONTH)+"."+(Calendar.getInstance().get(Calendar.MONTH)+1)+"."+Calendar.getInstance().get(Calendar.YEAR),
				runningUtil.getDepartment(),
				runningUtil.getSchoolYear(),
				runningUtil.getSelectedPosQuestion(),
				runningUtil.getSelectedNegQuestion(),
				printType,
				runningUtil.getPersons().size());
		this.runningUtil = runningUtil;
		this.printNames = printNames;
	}

	/**
	 * draws the main part 
	 * @param x
	 * @param y
	 */
	public void drawMainPart(Graphics2D g2d, int x, int y, int maxWidth, int maxHeight){
		FontMetrics fm = g2d.getFontMetrics();
		y= y+fm.getHeight();
		
		
		
		g2d.drawString(I18n.get("pollPrint").toUpperCase(), x, y);
		g2d.drawString(this.townAndDate, x+maxWidth-fm.stringWidth(this.townAndDate), y);
		y= y+fm.getHeight()*2;
		y = TextPrinter.printText(g2d, maxWidth+x, 20, x, y, this.institution);
		y += 20;
		//g2d.drawString(this.institution, x, y);
		//y= y+fm.getHeight()*2;
		g2d.drawString(I18n.get("class") + ": "+this.department, x, y);
		y= y+fm.getHeight();
		g2d.drawString(I18n.get("nameAndSurname") + ":", x, y);
		if (this.printNames){
			y = TextPrinter.printText(g2d, maxWidth+x, 20, x+fm.stringWidth(I18n.get("nameAndSurname") + ": "), y, this.runningUtil.getPersonFullName(this.runningUtil.getPersons().get(curNameCounter).getId()));
			//g2d.drawString(this.runningUtil.getPersonFullName(this.runningUtil.getPersons().get(curNameCounter++).getId()),	x+fm.stringWidth("Ime in priimek: "), y);
		}
		y= y+fm.getHeight();
		y = TextPrinter.printText(g2d, maxWidth+x, 20, x, y, this.posQuestion);
		//g2d.drawString(this.posQuestion, x, y);
		//y= y+20;
		g2d.drawLine(x, y, x+maxWidth, y);
		y= y+20;
		g2d.drawLine(x, y, x+maxWidth, y);
		y= y+20;
		g2d.drawLine(x, y, x+maxWidth, y);
		y= y+20*2;
		y = TextPrinter.printText(g2d, maxWidth+x, 20, x, y, this.negQuestion);
		//g2d.drawString(this.negQuestion, x, y);
		//y= y+20;
		g2d.drawLine(x, y, x+maxWidth, y);
		y= y+20;
		g2d.drawLine(x, y, x+maxWidth, y);
		y= y+20;
		g2d.drawLine(x, y, x+maxWidth, y);
	}
	
	@Override
	public int print(Graphics g, PageFormat pf, int pageIndex) throws PrinterException {
	
		if (pageIndex+1 > numOfPages()) { /* We have only one page, and 'page' is zero-based */
	         return NO_SUCH_PAGE;
	    } else {

		    /* User (0,0) is typically outside the imageable area, so we must
		     * translate by the X and Y values in the PageFormat to avoid clipping
		     */
		    Graphics2D g2d = (Graphics2D)g;
		    g2d.translate(pf.getImageableX(), pf.getImageableY());
	
		    /* Now we perform our rendering */
		    switch (this.printType){
		    case TWO_PER_PAGE:
			    	//zgornji
		    		curNameCounter = 2*pageIndex;
			    	drawMainPart(g2d, 0+this.padding, 0+this.padding, (int)pf.getImageableWidth()-2*this.padding, (int)pf.getImageableHeight()/2-2*this.padding);
			    	//spodnji
			    	curNameCounter++;
			    	if (pageIndex+1 < numOfPages() || pageIndex+1 == numOfPages() && this.numOfPolls%2 == 0)
			    	drawMainPart(g2d, 0+this.padding, (int)pf.getImageableHeight()/2+this.padding, (int)pf.getImageableWidth()-2*this.padding, (int)pf.getImageableHeight()/2-2*this.padding);
			    	
			    	//draw cut lines
			    	drawHorLine(g2d,(int)pf.getImageableWidth()-2*this.padding, this.padding, ((int)pf.getImageableHeight())/2);
			    break;
		    case FOUR_PER_PAGE:
			    	//zgornji levi
		    		curNameCounter = 4*pageIndex;
			    	drawMainPart(g2d, 0+this.padding, 0+this.padding, (int)pf.getImageableWidth()/2-2*this.padding, (int)pf.getImageableHeight()/2-2*this.padding);
			    	//spodnji levi
			    	curNameCounter++;
			    	if (pageIndex+1 < numOfPages() || pageIndex+1 == numOfPages() && this.numOfPolls%4 == 0|| pageIndex+1 == numOfPages() && this.numOfPolls%4 == 3 || pageIndex+1 == numOfPages() && this.numOfPolls%4 == 2)
			    	drawMainPart(g2d, 0+this.padding, (int)pf.getImageableHeight()/2+this.padding, (int)pf.getImageableWidth()/2-2*this.padding, (int)pf.getImageableHeight()/2-2*this.padding);
			    	//zgornji desni
			    	curNameCounter++;
			    	if (pageIndex+1 < numOfPages() || pageIndex+1 == numOfPages() && this.numOfPolls%4 == 0 || pageIndex+1 == numOfPages() && this.numOfPolls%4 == 3)
			    	drawMainPart(g2d, (int)pf.getImageableWidth()/2+this.padding, 0+this.padding, (int)pf.getImageableWidth()/2-2*this.padding, (int)pf.getImageableHeight()/2-2*this.padding);
			    	//spodnji desni
			    	curNameCounter++;
			    	if (pageIndex+1 < numOfPages() || pageIndex+1 == numOfPages() && this.numOfPolls%4 == 0)
			    	drawMainPart(g2d, (int)pf.getImageableWidth()/2+this.padding, (int)pf.getImageableHeight()/2+this.padding, (int)pf.getImageableWidth()/2-2*this.padding, (int)pf.getImageableHeight()/2-2*this.padding);
			    	
			    	//draw cut lines
			    	drawHorLine(g2d,(int)pf.getImageableWidth()-2*this.padding, this.padding, ((int)pf.getImageableHeight())/2);
			    	drawVerLine(g2d,(int)pf.getImageableHeight()-2*this.padding,  ((int)pf.getImageableWidth())/2, this.padding);
		    	break;
		    } 
		    /* tell the caller that this page is part of the printed document */
		    return PAGE_EXISTS;
	    }
	}


	private void drawHorLine(Graphics2D g2d, int width, int x, int y) {
		int newx = x;
		while (newx-x <= width){
			g2d.drawLine(newx, y, newx+this.lineLength, y);
			newx += this.lineLength+this.lineWhiteSpaceLength;
		}
	}
	
	private void drawVerLine(Graphics2D g2d, int height, int x, int y) {
		int newy = y;
		while (newy-y <= height){
			g2d.drawLine(x, newy, x, newy+this.lineLength);
			newy += this.lineLength+this.lineWhiteSpaceLength;
		}
	}

	private int numOfPages() {
		if (this.printType.equals(PrintingType.FOUR_PER_PAGE)) {
			return (int)Math.ceil(this.numOfPolls*1.0/4);
		} else {//if (this.printType.equals(PrintingType.TWO_PER_PAGE)){
			return (int)Math.ceil(this.numOfPolls*1.0/2);
		} 
	}

	public void print() throws Exception{
		curNameCounter = 0;
		PrinterJob job = PrinterJob.getPrinterJob();	
		job.setPrintable(this);		
		boolean doPrint = job.printDialog();
		if (doPrint){
			job.print();
		}
	}

	
	
	
	public static void main(String[] args) throws Exception {
		PollPrinter pm = new PollPrinter("O� Jo�eta Mazovca", "Ljubljana 30.9.2009",
				"G3.a", "2009/2010", "S kom bi najraj�i sedel?", "S kom ne bi hotel sedeti?",
				PrintingType.TWO_PER_PAGE, 11);
		pm.print();
	}

	
		
}


