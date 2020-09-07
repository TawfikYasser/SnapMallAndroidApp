package com.snapmall.snapmall;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class AppData {
    private static final String FILE_NAME = "SNAPMALLFile";
    private static final String ANIM_ID = "anim_id";
    private static final String COLOR_ID = "color_id";
    private static final String GENDER = "gender";
    private static final String LANG = "language";
    private static final String COUNTRY = "country";

    SharedPreferences mSharedPreferences;
    SharedPreferences.Editor mEditor;
    Context mContext;

    @SuppressLint("CommitPrefEdits")
    public AppData(Context mContext) {
        this.mContext = mContext;
        mSharedPreferences = mContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();
    }


    public void saveAnimationId(String AnimId) {
        mEditor.putString(ANIM_ID, AnimId);
        mEditor.apply();
    }

    public String getAnimId() {
        return mSharedPreferences.getString(ANIM_ID, "0");
    }

    public void saveColorId(String ColorId ) {
        mEditor.putString(COLOR_ID, ColorId);
        mEditor.apply();
    }

    public String getColorId() {
        return mSharedPreferences.getString(COLOR_ID, "");
    }

    public void saveGender(String gen) {
        mEditor.putString(GENDER, gen);
        mEditor.apply();
    }

    public String getGender() {
        return mSharedPreferences.getString(GENDER, "male");
    }

    public void saveLang(String lan) {
        mEditor.putString(LANG, lan);
        mEditor.apply();
    }

    public String getLang() {
        return mSharedPreferences.getString(LANG, "En");
    }

    public void saveCountry(String country){
        mEditor.putString(COUNTRY,country);
        mEditor.apply();
    }
    public String getCountry(){
        return mSharedPreferences.getString(COUNTRY,"");
    }




}
