package fr.justgame.quickcolor.common;

import android.content.Context;
import android.content.SharedPreferences;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by aaitzeouay on 17/07/2017.
 */

public class Authentication {
    public static final Authentication INSTANCE = new Authentication();

    private static final String PREFS_NAME = "QuickColorSharedInstance";
    private static final String PREF_TUTO_SCREEN_SEEN = "TutoScreenSeen";

    public boolean getSharedPreferences(Context context){
        SharedPreferences pref = context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        return pref.getBoolean(PREF_TUTO_SCREEN_SEEN, false);
    }
    public void setSharedPreferences(boolean isScreenSeen, Context context){
        context.getSharedPreferences(PREFS_NAME,MODE_PRIVATE)
                .edit()
                .putBoolean(PREF_TUTO_SCREEN_SEEN, isScreenSeen)
                .apply();
    }
}
