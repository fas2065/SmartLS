package com.rasco.lightstick.base;

import java.util.ArrayList;

/**
 * Created by Admin on 2/15/2018.
 */

public class ColorCombination {

    private int _id;
    private String name;
    private ArrayList<Color> colors;

    public ColorCombination() {
    }

    public ColorCombination(String name) {
        this.name = name;
    }

    public ColorCombination(String name, ArrayList<Color> colors) {
        this.name = name;
        this.colors = colors;
    }

    public ColorCombination(int _id, String name) {
        this._id = _id;
        this.name = name;
    }


    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Color> getColors() {
        return colors;
    }

    public void setColors(ArrayList<Color> colors) {
        this.colors = colors;
    }
}
