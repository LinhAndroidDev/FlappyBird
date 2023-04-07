package com.example.flappybird;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;

public class SaveScoreBest {
    String MY_SHARED_PREFERENCES = "MY_SHARED_PREFERENCES";
    private Context context;

    public SaveScoreBest(Context context) {
        this.context = context;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void putIntValue(String key, int value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public int getIntValue(String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(MY_SHARED_PREFERENCES, 0);
        return sharedPreferences.getInt(key, 0);
    }
}
