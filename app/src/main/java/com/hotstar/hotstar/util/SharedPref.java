package com.hotstar.hotstar.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eshan on 11/16/17.
 */

public class SharedPref
{

    private static SharedPref sharedPreferences;
    private static SharedPreferences mPref;
    private static SharedPreferences.Editor editor;
    private static final String MyPREFERENCES = "catch_preference";
    private static final String USER_ID = "USER_ID";


    public static SharedPref getInsatance(Context context){
        if(sharedPreferences == null){
            sharedPreferences = new SharedPref();
            mPref = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
            editor = mPref.edit();
        }
        return sharedPreferences;
    }

    private SharedPref(){

    }

    public void saveUserId(String userId)
    {
        editor.putString(USER_ID, userId);
        editor.commit();
    }

    public String getUserId()
    {
        return mPref.getString(USER_ID, "");
    }

}
