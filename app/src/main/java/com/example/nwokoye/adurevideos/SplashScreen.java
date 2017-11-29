package com.example.nwokoye.adurevideos;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    session_management session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        ImageView animationTarget = (ImageView) this.findViewById(R.id.logo);
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.rotate);
        animationTarget.startAnimation(animation);
        Typewriter writer = (Typewriter)findViewById(R.id.typewriter);
        Typeface type = Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf");
        writer.setTypeface(type);
        //Add a character every 150ms
        writer.setCharacterDelay(150);
        writer.animateText("Welcome to Adure TV_");
        Thread timer = new Thread(){
            public void run(){
                try{
                    sleep(6000);
                }
                catch(InterruptedException e){
                    e.printStackTrace();}
                finally{
                    Intent peace = new Intent (SplashScreen.this,StartingPoint.class);
                    startActivity(peace);
                }
            }

        };
        timer.start();}

    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }

}

