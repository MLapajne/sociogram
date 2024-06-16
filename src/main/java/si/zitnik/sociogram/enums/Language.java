package si.zitnik.sociogram.enums;

import si.zitnik.sociogram.util.I18n;

/**
 * Created by slavkoz on 28/09/14.
 */
public enum Language {
    SLOVENE("sl", I18n.get("sloLang")), CROATIAN("hr", I18n.get("hrLang")), ENGLISH("en", I18n.get("enLang"));

    private String label;
    private String text;

    private Language(String label, String text) {
        this.label = label; this.text = text;
    }

    public String getLabel() {
        return label;
    }

    public String getText() {
        return text;
    }
}
