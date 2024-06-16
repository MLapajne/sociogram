package si.zitnik.sociogram.io;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import si.zitnik.sociogram.entities.Gender;
import si.zitnik.sociogram.entities.Person;
import si.zitnik.sociogram.error.JErrorDialog;
import si.zitnik.sociogram.util.I18n;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by slavkoz on 20/06/16.
 */
public class ExcelImporter {
    private String filename;
    private final static int MAX_LINE_SEARCH = 100;

    public ExcelImporter(String filename){
        this.filename = filename;
    }


    public Collection<Person> readData() throws Exception {
        InputStream ExcelFileToRead = new FileInputStream(this.filename);
        XSSFWorkbook  wb = new XSSFWorkbook(ExcelFileToRead);

        XSSFSheet sheet = wb.getSheetAt(0);
        XSSFRow row;
        XSSFCell cell;

        Collection<Person> retVal = new ArrayList<Person>();
        int id = 1;
        Integer nameIdx = null;
        Integer surnameIdx = null;
        Integer genderIdx = null;

        //find the first row
        Iterator rows = sheet.rowIterator();
        while (rows.hasNext())
        {
            row=(XSSFRow) rows.next();
            Iterator cells = row.cellIterator();
            while (cells.hasNext())
            {
                cell=(XSSFCell) cells.next();

                if (cell.getCellType() == XSSFCell.CELL_TYPE_STRING)
                {
                    if (cell.getStringCellValue().toLowerCase().equals(I18n.get("csvNameHeader"))){
                        nameIdx = cell.getColumnIndex();
                    } else if (cell.getStringCellValue().toLowerCase().equals(I18n.get("csvSurnameHeader"))){
                        surnameIdx = cell.getColumnIndex();
                    } else if (cell.getStringCellValue().toLowerCase().equals(I18n.get("csvGenderHeader"))){
                        genderIdx = cell.getColumnIndex();
                    }
                }

            }
            if (nameIdx != null || surnameIdx != null || genderIdx != null){
                break;
            }

        }
        if (nameIdx == null || surnameIdx == null || genderIdx == null){
            throw new Exception(I18n.get("csvReadingErrorMsg"));
        }


        while (rows.hasNext())
        {
            row=(XSSFRow) rows.next();

            retVal.add(new Person(id++, row.getCell(nameIdx).getStringCellValue(), row.getCell(surnameIdx).getStringCellValue(),
                    (row.getCell(genderIdx).getStringCellValue().substring(0, 1).equalsIgnoreCase("m")) ? Gender.MALE : Gender.FEMALE));

        }
        return retVal;
    }
}
