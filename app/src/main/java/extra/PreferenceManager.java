package extra;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by dell pc on 01-09-2017.
 */

public class PreferenceManager {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    private static PreferenceManager yourPreference;
    Context _context;

    // shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "afm_auto_customer";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    public PreferenceManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return pref.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }


    public static PreferenceManager getInstance(Context context) {
        if (yourPreference == null) {
            yourPreference = new PreferenceManager(context);
        }
        return yourPreference;
    }



    public void saveStringData(String key,String value) {
        SharedPreferences.Editor prefsEditor = pref.edit();
        prefsEditor .putString(key, value);
        prefsEditor.commit();
    }

    public String getStringData(String key) {
        if (pref!= null) {
            return pref.getString(key, "");
        }
        return "";
    }
    public void saveBoolData(String key,Boolean value) {
        SharedPreferences.Editor prefsEditor = pref.edit();
        prefsEditor .putBoolean(key, value);
        prefsEditor.commit();
    }

    public Boolean getBoolData(String key) {
        if (pref!= null) {
            return pref.getBoolean(key, false);
        }
        return false;
    }
}
