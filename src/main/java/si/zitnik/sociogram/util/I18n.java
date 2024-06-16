package si.zitnik.sociogram.util;

import si.zitnik.sociogram.config.ConfigManager;
import si.zitnik.sociogram.enums.Language;

import java.util.HashMap;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Created by slavkoz on 02/08/14.
 */
public class I18n {
    private static ResourceBundle strings;
    private static String language;

    public static void initOrUpdate(ConfigManager configManager) {

        try {
            language = configManager.getLanguage();
        } catch (Exception e) {
            language = "sl";
        } finally {
            if (language == null || language.isEmpty()) {
                language = "sl";
            }
        }

        strings = ResourceBundle.getBundle("strings", new Locale(language));
    }

    public static String get(String name) {
        return new String(strings.getString(name));
    }

    public static String getLocale() {
        return language;
    }
}
