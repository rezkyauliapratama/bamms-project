package rezkyaulia.com.bamms_project.util;

import java.util.Locale;

import timber.log.Timber;

public class Language {
    public String text;
    public String name;
    public String locale;
    ;

    public Language(String text, String name, String locale) {
        this.text = text;
        this.name = name;
        this.locale = locale;
    }

    public Language(String text, Locale locale) {
        this.text = text;
        this.name = locale.getDisplayLanguage();
        this.locale = locale.getLanguage();

        Timber.e("name : " + this.name + " | locale : " + this.locale);
    }

}