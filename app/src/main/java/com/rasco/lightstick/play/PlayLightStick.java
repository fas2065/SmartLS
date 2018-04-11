package com.rasco.lightstick.play;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;

public class PlayLightStick extends AppCompatActivity implements Orientation.Listener{

    private Orientation mOrientation;
    private LightShow mLightShow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mOrientation = new Orientation(this);
        ArrayList<String> colors = getIntent().getStringArrayListExtra("colors");
        mOrientation = new Orientation(this);
        mLightShow = new LightShow(this, colors);
        setContentView(mLightShow);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mOrientation.startListening(this);
    }

    @Override
    public void onOrientationChanged(float pitch, float roll) {
        mLightShow.redraw(roll);
    }



}
