package si.zitnik.sociogram.util;

import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import si.zitnik.sociogram.SociogramRunner;
import si.zitnik.sociogram.config.SociogramConstants;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.gui.socioclass.SocioCTable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by slavkoz on 13/09/14.
 */
public class WordSaver {

    private final SocioCTable socioTable;
    private SociogramRibbon mainFrame;
    private RunningUtil runningUtil;

    public WordSaver(SociogramRibbon mainFrame, RunningUtil runningUtil, SocioCTable socioTable) {
        this.mainFrame = mainFrame;
        this.runningUtil = runningUtil;
        this.socioTable = socioTable;
    }

    public void saveDialog() {
        try {
            JFileChooser chooser = new JFileChooser(runningUtil.getWorkingDirectory());
            FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.get("documentWord"), "docx");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(mainFrame);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String saveFile = chooser.getSelectedFile().getAbsolutePath();
                runningUtil.setWorkingDirectory(chooser.getCurrentDirectory().getAbsolutePath());
                if (!saveFile.endsWith(".docx")){
                    saveFile += ".docx";
                }
                if (saveFile.endsWith(".doc")){
                    saveFile += "x";
                }
                //save
                export(saveFile);
            }
        } catch (Exception e1) {
            @SuppressWarnings("unused")
            JErrorDialog errorDialog = new JErrorDialog(I18n.get("helpShowErrorMsg"), e1);
        }
    }

    private void export(String saveFile) throws Exception {
        XWPFDocument doc = new XWPFDocument();

        createHeader(doc);
        createTable(doc);

        FileOutputStream out = new FileOutputStream(saveFile);
        doc.write(out);
        out.close();
    }


    public void createHeader(XWPFDocument doc) throws Exception {
        String title = SociogramConstants.PROGRAM_NAME+" - "+runningUtil.getInstitution();
        String text = String.format(
                I18n.get("wordText_" + runningUtil.getProgramType().toString()),
                runningUtil.getDate(),
                runningUtil.getDepartment(),
                runningUtil.getTeacher(),
                runningUtil.getGrader(),
                runningUtil.getComment());

        XWPFParagraph p1 = doc.createParagraph();
        p1.setAlignment(ParagraphAlignment.CENTER);

        XWPFRun r1 = p1.createRun();
        r1.setBold(true);
        r1.setText(title);
        r1.setFontSize(20);
        r1.addCarriageReturn();
        r1.addCarriageReturn();


        XWPFParagraph p2 = doc.createParagraph();
        p2.setAlignment(ParagraphAlignment.LEFT);
        p2.setWordWrap(true);
        p2.setAlignment(ParagraphAlignment.BOTH);


        XWPFRun r4 = p2.createRun();
        r4.setText(text);
        r4.addCarriageReturn();
        r4.addCarriageReturn();
    }

    public void createTable(XWPFDocument doc) throws Exception {

        // Create a new table with 6 rows and 3 columns
        int nRows = socioTable.getModel().getRowCount();
        int nCols = socioTable.getModel().getColumnCount();
        XWPFTable table = doc.createTable(nRows + 1, nCols);

        // Set the table style. If the style is not defined, the table style
        // will become "Normal".
        CTTblPr tblPr = table.getCTTbl().getTblPr();
        CTString styleStr = tblPr.addNewTblStyle();
        styleStr.setVal("StyledTable");

        // Get a list of the rows in the table
        List<XWPFTableRow> rows = table.getRows();
        int rowCt = 0;
        int colCt = 0;
        for (XWPFTableRow row : rows) {
            // get table row properties (trPr)
            CTTrPr trPr = row.getCtRow().addNewTrPr();
            // set row height; units = twentieth of a point, 360 = 0.25"
            CTHeight ht = trPr.addNewTrHeight();
            ht.setVal(BigInteger.valueOf(360));

            // get the cells in this row
            List<XWPFTableCell> cells = row.getTableCells();
            // add content to each cell
            for (XWPFTableCell cell : cells) {
                // get a table cell properties element (tcPr)
                CTTcPr tcpr = cell.getCTTc().addNewTcPr();
                // set vertical alignment to "center"
                CTVerticalJc va = tcpr.addNewVAlign();
                va.setVal(STVerticalJc.CENTER);

                // create cell color element
                CTShd ctshd = tcpr.addNewShd();
                ctshd.setColor("auto");
                ctshd.setVal(STShd.CLEAR);
                if (rowCt == 0) {
                    // header row
                    ctshd.setFill("A7BFDE");
                }
                else if (rowCt % 2 == 0) {
                    // even row
                    ctshd.setFill("D3DFEE");
                }
                else {
                    // odd row
                    ctshd.setFill("EDF2F8");
                }

                // get 1st paragraph in cell's paragraph list
                XWPFParagraph para = cell.getParagraphs().get(0);
                // create a run to contain the content
                XWPFRun rh = para.createRun();
                if (rowCt == 0) {
                    // header row
                    rh.setText(socioTable.getModel().getColumnName(colCt));
                    rh.setBold(true);
                    para.setAlignment(ParagraphAlignment.CENTER);
                }
                else {
                    // other row
                    rh.setText(socioTable.getModel().getValueAt(rowCt - 1, colCt).toString());
                    para.setAlignment(ParagraphAlignment.LEFT);
                }
                colCt++;
            } // for cell
            colCt = 0;
            rowCt++;
        } // for row

    }

}
