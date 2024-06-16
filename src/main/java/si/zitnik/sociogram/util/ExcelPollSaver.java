package si.zitnik.sociogram.util;

import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.xssf.usermodel.*;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.gui.SociogramRibbon;
import si.zitnik.sociogram.gui.poll.PollTable;
import si.zitnik.sociogram.gui.poll.StatusTable;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.FileOutputStream;

/**
 * Created by slavkoz on 13/09/14.
 */
public class ExcelPollSaver {

    private final PollTable pollTable;
    private SociogramRibbon mainFrame;
    private RunningUtil runningUtil;

    public ExcelPollSaver(SociogramRibbon mainFrame, RunningUtil runningUtil, PollTable pollTable) {
        this.mainFrame = mainFrame;
        this.runningUtil = runningUtil;
        this.pollTable = pollTable;
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
        int nRows = pollTable.getModel().getRowCount();
        int nCols = pollTable.getModel().getColumnCount();

        //PollTable
        for (int row = 0; row < nRows; row++) {
            XSSFRow xRow = sheet.createRow(row);
            for (int cell = 0; cell < nCols; cell++) {
                XSSFCell xCell = xRow.createCell(cell);
                xCell.setCellValue(pollTable.getModel().getValueAt(row, cell).toString());
            }
        }
    }

}
