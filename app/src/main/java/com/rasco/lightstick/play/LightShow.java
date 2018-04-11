package com.rasco.lightstick.play;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

import com.rasco.lightstick.LogUtil;

import java.util.ArrayList;

/**
 * Created by Admin on 2/5/2018.
 */

public class LightShow extends View {

    private float roll = 0;
    private ArrayList<String> colorsList;

    private int ROLL_ANGEL = 60;

    int numberOfColors = 0;

    public LightShow(Context context, ArrayList<String> colors){
        super(context);
        colorsList = colors;

    }

    public void redraw(float roll) {
        this.roll = roll;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas){
        Paint paint = new Paint();
        paint.setStyle(Paint.Style.FILL);

        numberOfColors = colorsList.size();
        int region = (int) (roll + (ROLL_ANGEL/2))/(ROLL_ANGEL/numberOfColors);

        if (region < 0) {
            region = 0;
        } else if (region >= numberOfColors){
            region = numberOfColors -1;
        }

        paint.setColor(android.graphics.Color.parseColor(colorsList.get(region)));
        canvas.drawPaint(paint);
    }

}
