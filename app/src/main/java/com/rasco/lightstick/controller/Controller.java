package com.rasco.lightstick.controller;

import android.content.Context;

import com.rasco.lightstick.base.Color;
import com.rasco.lightstick.base.ColorCombination;
import com.rasco.lightstick.db.ColorCombinationDB;

import java.util.ArrayList;

public class Controller {

    ColorCombinationDB colorCombinationDB;

    public Controller(Context context) {
        colorCombinationDB = new ColorCombinationDB(context);
    }

    public ArrayList<ColorCombination> getAllColorCombos() {
        return colorCombinationDB.getAllColorCombinationLite();
    }

    public ColorCombination getColorCombo(int id) {
        ColorCombination colorCombination = colorCombinationDB.getColorCombination(id);
        return colorCombination;
    }

    public ColorCombination getColorComboByName(String name) {
        ColorCombination colorCombination = colorCombinationDB.getColorCombinationByName(name);
        return colorCombination;
    }


    public ColorCombination addColorCombo(ColorCombination colorCombination) {
        int id = (int) colorCombinationDB.addColorCombination(colorCombination);
        colorCombination.set_id(id);
        return colorCombination;

    }

    public void deleteColorCombo(ColorCombination colorCombination) {
        colorCombinationDB.deleteColorCombination(colorCombination);
    }

    public void updateColorCombo(ColorCombination colorCombination) {
        colorCombinationDB.updateColorCombination(colorCombination);
    }

    public ArrayList<Color> getAllCcColors(int id) {
        return colorCombinationDB.getAllCcColors(id);
    }

    public void addColor(Color color) {
        colorCombinationDB.addColor(color);
    }

    public void deleteColor(Color color) {
        colorCombinationDB.deleteColor(color);
    }
}
