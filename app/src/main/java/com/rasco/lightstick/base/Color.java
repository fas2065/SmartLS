package com.rasco.lightstick.base;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Admin on 3/7/2018.
 */

public class Color  {

    private int id;
    private int cc_id;
    private String color_rbg;


    public Color() {
    }

    public Color(int id, int cc_id, String color_rbg) {
        this.id = id;
        this.cc_id = cc_id;
        this.color_rbg = color_rbg;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCc_id() {
        return cc_id;
    }

    public void setCc_id(int cc_id) {
        this.cc_id = cc_id;
    }

    public String getColor_rbg() {
        return color_rbg;
    }

    public void setColor_rbg(String color_rbg) {
        this.color_rbg = color_rbg;
    }
}
