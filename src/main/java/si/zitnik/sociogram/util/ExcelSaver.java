package si.zitnik.sociogram.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.xssf.usermodel.*;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import si.zitnik.sociogram.SociogramRunner;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.gui.socioclass.SocioCTable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileOutputStream;
import java.math.BigInteger;
import java.util.List;

/**
 * Created by slavkoz on 13/09/14.
 */
public class ExcelSaver {

    private final SocioCTable socioTable;
    private SociogramRibbon mainFrame;
    private RunningUtil runningUtil;

    public ExcelSaver(SociogramRibbon mainFrame, RunningUtil runningUtil, SocioCTable socioTable) {
        this.mainFrame = mainFrame;
        this.runningUtil = runningUtil;
        this.socioTable = socioTable;
    }

    public void saveDialog() {
        try {
            JFileChooser chooser = new JFileChooser(runningUtil.getWorkingDirectory());
            FileNameExtensionFilter filter = new FileNameExtensionFilter(I18n.get("documentExcel"), "xlsx");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(mainFrame);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                String saveFile = chooser.getSelectedFile().getAbsolutePath();
                runningUtil.setWorkingDirectory(chooser.getCurrentDirectory().getAbsolutePath());
                if (!saveFile.endsWith(".xlsx")){
                    saveFile += ".xlsx";
                }
                if (saveFile.endsWith(".xls")){
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
        XSSFWorkbook xlsx = new XSSFWorkbook();
        XSSFSheet sheet = xlsx.createSheet();

        createTable(xlsx, sheet);

        FileOutputStream out = new FileOutputStream(saveFile);
        xlsx.write(out);
        out.close();
    }

    public void createTable(XSSFWorkbook xlsx, XSSFSheet sheet) throws Exception {

        // Create a new table with 6 rows and 3 columns
        int nRows = socioTable.getModel().getRowCount();
        int nCols = socioTable.getModel().getColumnCount();

        XSSFFont font = xlsx.createFont();
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        XSSFCellStyle style = xlsx.createCellStyle();

        for (int row = 0; row < nRows + 1; row++) {
            XSSFRow xRow = sheet.createRow(row);
            for (int cell = 0; cell < nCols; cell++) {
                XSSFCell xCell = xRow.createCell(cell);
                if (row == 0) {
                    // header row
                    xCell.setCellValue(socioTable.getModel().getColumnName(cell));
                    xCell.setCellStyle(style);
                }
                else {
                    // other row
                    xCell.setCellValue(socioTable.getModel().getValueAt(row - 1, cell).toString());
                }
            }
        }
    }

}
