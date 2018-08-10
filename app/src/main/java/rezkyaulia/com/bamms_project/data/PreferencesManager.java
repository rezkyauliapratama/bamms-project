package rezkyaulia.com.bamms_project.data;

import com.securepreferences.SecurePreferences;

import javax.inject.Inject;
import javax.inject.Singleton;

/**
 * Created by Rezky Aulia Pratama on 10/6/18.
 */
@Singleton
public class PreferencesManager {

    private SecurePreferences mSecurePreferences;

    private final String CURRENT_VERSION = "CURRENT_VERSION";
    private final String FONT_SIZE_KEY = "FONT_SIZE_KEY";
    private final String USER_KEY = "USER_KEY";
    private final String LANGUAGE_LOCALE = "LANGUAGE_LOCALE";
    private final String ROLE = "ROLE";


    @Inject
    public PreferencesManager(SecurePreferences securePreferences) {
        mSecurePreferences = securePreferences;
    }



    public void setCurrentVersion(int version) {
        SecurePreferences.Editor editor = mSecurePreferences.edit();
        editor.putInt(CURRENT_VERSION, version);
        editor.apply();
    }
    public int getCurrentVersion() {
        return mSecurePreferences.getInt(CURRENT_VERSION, 1);
    }


    public void setFontSize(float scale) {
        SecurePreferences.Editor editor = mSecurePreferences.edit();
        editor.putFloat(FONT_SIZE_KEY, scale);
        editor.apply();
    }
    public float getFontSize() {
        return mSecurePreferences.getFloat(FONT_SIZE_KEY, 1.0f);
    }

    public void setUserKey(String userKey) {
        SecurePreferences.Editor editor = mSecurePreferences.edit();
        editor.putString(USER_KEY, userKey);
        editor.apply();
    }
    public String getUserKey() {
        return mSecurePreferences.getString(USER_KEY,"");
    }

    public void setLocale(String locale) {
        SecurePreferences.Editor editor = mSecurePreferences.edit();
        editor.putString(LANGUAGE_LOCALE, locale);
        editor.apply();
    }
    public String getLocale() {
        return mSecurePreferences.getString(LANGUAGE_LOCALE,"en");
    }

    public void setRole(String role) {
        SecurePreferences.Editor editor = mSecurePreferences.edit();
        editor.putString(ROLE, role);
        editor.apply();
    }
    public String getRole() {
        return mSecurePreferences.getString(ROLE,"");
    }

}
