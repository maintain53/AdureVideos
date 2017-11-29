package com.example.nwokoye.adurevideos;

import android.app.Activity;
import android.app.Application;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import android.content.pm.Signature;

/**
 * Created by NWOKOYE on 9/17/2017.
 */
public class prefutil{
        private Activity activity;

        // Constructor
        public prefutil( Activity activity) {
            this.activity = activity;
        }

        public void saveAccessToken(String token) {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("fb_access_token", token);
            editor.apply(); // This line is IMPORTANT !!!
        }


        public String getToken() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
            return prefs.getString("fb_access_token", null);
        }

        public void clearToken() {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear();
            editor.apply(); // This line is IMPORTANT !!!
        }

        public void saveFacebookUserInfo(String first_name,String last_name, String email, String gender, String profileURL){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString("fb_first_name", first_name);
            editor.putString("fb_last_name", last_name);
            editor.putString("fb_email", email);
            editor.putString("fb_gender", gender);
            editor.putString("fb_profileURL", profileURL);
            editor.apply(); // This line is IMPORTANT !!!
            Log.d("MyApp", "Shared Name : "+first_name+"\nLast Name : "+last_name+"\nEmail : "+email+"\nGender : "+gender+"\nProfile Pic : "+profileURL);
        }

        public void getFacebookUserInfo(){
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(activity);
            Log.d("MyApp", "Name : "+prefs.getString("fb_name",null)+"\nEmail : "+prefs.getString("fb_email",null));
        }


}
