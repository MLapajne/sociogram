package si.zitnik.sociogram.test;

import si.zitnik.sociogram.io.printing.PrintingManager;
import si.zitnik.sociogram.io.printing.PrintingType;

/**
 * Created by slavkoz on 26/08/14.
 */
public class PollPrintTest {
    public static void main(String[] args) throws Exception {
        PrintingManager pm = new PrintingManager("OŠ Jožeta Mazovca", "Ljubljana 30.9.2009",
                "G3.a", "2009/2010", "S kom bi najrajši sedel?", "S kom ne bi hotel sedeti?",
                PrintingType.TWO_PER_PAGE, 11);
        pm.print();
    }
}
