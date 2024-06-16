package si.zitnik.sociogram.test;

import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by slavkoz on 02/08/14.
 */
public class Test {
    public static void main(String[] args) throws UnsupportedEncodingException {
        ResourceBundle strings = ResourceBundle.getBundle("strings", new Locale("sl", "SI"));

        String str = new String(strings.getString("man").getBytes("iso-8859-2"), "utf-8");
        System.out.println(str);
    }
}
