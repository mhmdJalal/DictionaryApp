package com.codepolitan.s3madekamus.helper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class PrefManager {

    private SharedPreferences sharedPref;
    private SharedPreferences.Editor editor;
    private Context context;
    private static final String PREF_NAME = "PrefManager";

    private static final String TIPE_KAMUS = "tipeKamus";
    private static final String FIRST_RUN = "firstRun";
    private static final String IS_ENGLISH = "isEnglish";
    private static final String LANG_SELECTED = "langSelected";

    @SuppressLint("CommitPrefEdits")
    public PrefManager(Context context) {
        sharedPref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPref.edit();
        this.context = context;
    }

    public boolean getIsEnglish() {
        return sharedPref.getBoolean(IS_ENGLISH, true);
    }

    public void setIsEnglish(boolean input) {
        editor.putBoolean(IS_ENGLISH, input);
        editor.apply();
    }

    public String getLangSelected() {
        return sharedPref.getString(LANG_SELECTED, "");
    }

    public void setLangSelected(String input) {
        editor.putString(LANG_SELECTED, input);
        editor.apply();
    }

    public String getTipeKamus() {
        return sharedPref.getString(TIPE_KAMUS, "En-In");
    }

    public void setTipeKamus(String tipeKamus) {
        editor.putString(TIPE_KAMUS, tipeKamus);
        editor.apply();
    }

    public boolean getFirstRun() {
        return sharedPref.getBoolean(FIRST_RUN, true);
    }

    public void setFirstRun(boolean firstRun) {
        editor.putBoolean(FIRST_RUN, firstRun);
        editor.apply();
    }
}
