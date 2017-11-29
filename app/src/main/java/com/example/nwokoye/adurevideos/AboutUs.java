package com.example.nwokoye.adurevideos;

import android.graphics.Typeface;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class AboutUs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("ABOUT US");
        TextView writer = (TextView) findViewById(R.id.textView);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf");
        writer.setTypeface(type);
        writer.setText("The name Adure originates from Nigeria, West Africa. It is a term that signifies LOVE and the special nature of the first daughter amongst the people of Eastern Nigeria. Adure connotes fertility, continuity and the joys of an African family.\n" +
                "\n" +
                "Adure TV will bring to you a showcase of Africa’s finest drama, movies, comedy, music, reality, talk shows and lifestyle features. In Adure TV, you will find the best of local television content meeting and exceeding international standards. Adure TV has come to set the pace for 24 hours mobile entertainment.\n" +
                "\n" +
                "We will be with where ever you are and keep that SMILE.");
    }
}
