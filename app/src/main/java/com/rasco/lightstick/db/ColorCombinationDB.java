package com.rasco.lightstick.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.rasco.lightstick.base.Color;
import com.rasco.lightstick.base.ColorCombination;

import java.util.ArrayList;

/**
 * Created by Admin on 2/15/2018.
 */

public class ColorCombinationDB extends DBHelper {

    public static final String TABLE_COLOR_COMBINATION = "COLORCOMB";
    public static final String COLUMN_COLOR_COMB_ID = "color_comb_id";
    public static final String COLUMN_COLOR_COMB_NAME ="name";

    public static final String TABLE_COLOR = "COLOR";
    public static final String COLUMN_COLOR_ID = "color_id";
    public static final String COLUMN_CC_ID = "cc_id";
    public static final String COLUMN_COLOR_RGB = "color_rgb";

    final static String CREATE_TABLE_COLOR_COMBINATION = "CREATE TABLE " + TABLE_COLOR_COMBINATION + "(" +
            COLUMN_COLOR_COMB_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_COLOR_COMB_NAME + " TEXT) ";

    final static String CREATE_TABLE_COLOR = "CREATE TABLE " + TABLE_COLOR + "(" +
            COLUMN_COLOR_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_COLOR_RGB + " TEXT, " +
            COLUMN_CC_ID + " INTEGER," +
            "CONSTRAINT FK_COLORS FOREIGN KEY(" + COLUMN_CC_ID + ") REFERENCES " + TABLE_COLOR_COMBINATION +
            "(" + COLUMN_COLOR_COMB_ID + ") ON DELETE CASCADE)";

    public ColorCombinationDB(Context context) {
        super(context);
    }

    /* CRUD Color */
    // CREATE Color
    public void addColor(Color color){
        ContentValues values = new ContentValues();
        values.put(COLUMN_CC_ID, color.getCc_id());
        values.put(COLUMN_COLOR_RGB, color.getColor_rbg());

        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_COLOR, null, values);
        db.close();
    }

    // READ COLOR COMBINATION
    public Color getColor(int id) {
        String query = "SELECT * FROM " + TABLE_COLOR + " WHERE " +
                COLUMN_COLOR_ID + " = " + id;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();
        Color color = new Color(c.getInt(c.getColumnIndex(COLUMN_COLOR_ID)),
                c.getInt(c.getInt(c.getColumnIndex(COLUMN_CC_ID))),
                c.getString(c.getColumnIndex(COLUMN_COLOR_RGB)));
        db.close();
        return color;
    }

    // READ ALL
    public ArrayList<Color> getAllCcColors(int cc_id) {
        ArrayList<Color> colors = new ArrayList<Color>();
        SQLiteDatabase db = getReadableDatabase();

        String query = "SELECT * FROM " + TABLE_COLOR + " WHERE " +
                COLUMN_CC_ID + "= " + cc_id;

        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        if (c.moveToFirst()) {
            do {
                Color color = new Color(c.getInt(c.getColumnIndex(COLUMN_COLOR_ID)),
                        cc_id, c.getString(c.getColumnIndex(COLUMN_COLOR_RGB)));
                colors.add(color);

            } while (c.moveToNext());
        }
        db.close();
        return colors;
    }

    // UPDATE COLOR
    public boolean updateColor(Color color) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COLOR_ID, color.getId());
        contentValues.put(COLUMN_CC_ID, color.getCc_id());
        contentValues.put(COLUMN_COLOR_RGB, color.getColor_rbg());

        db.update(TABLE_COLOR, contentValues, COLUMN_COLOR_ID + " = ?",
                new String[]{Integer.toString(color.getId())});
        db.close();
        return true;
    }

    // DELETE COLOR COMBINATION
    public void deleteColor(Color color) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_COLOR + " WHERE " +
                COLUMN_COLOR_ID + " = ?" ;

        db.execSQL(query, new Integer[] {color.getId()});
        db.close();
    }

    /* CRUD Color Combination */

    // CREATE Color Combination
    public long addColorCombination(ColorCombination colorCombination){

        ContentValues values = new ContentValues();
        values.put(COLUMN_COLOR_COMB_NAME, colorCombination.getName());

        SQLiteDatabase db = getWritableDatabase();
        long id = db.insert(TABLE_COLOR_COMBINATION, null, values);
        db.close();

        return id;

    }

    // READ COLOR COMBINATION
    public ColorCombination getColorCombination(int cc_id) {
        String query = "SELECT * FROM " + TABLE_COLOR_COMBINATION + " WHERE " +
                COLUMN_COLOR_COMB_ID + " = " + cc_id;

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();
        ColorCombination cc = new ColorCombination();
        cc.set_id(c.getInt(c.getColumnIndex(COLUMN_COLOR_COMB_ID)));
        cc.setName(c.getString(c.getColumnIndex(COLUMN_COLOR_COMB_NAME)));
        db.close();
        return cc;
    }

    public ColorCombination getColorCombinationByName(String name) {
        String query = "SELECT * FROM " + TABLE_COLOR_COMBINATION + " WHERE " +
                COLUMN_COLOR_COMB_NAME + " LIKE '" + name + "'";

        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery(query, null);

        c.moveToFirst();
        ColorCombination cc = new ColorCombination();
        cc.set_id(c.getInt(c.getColumnIndex(COLUMN_COLOR_COMB_ID)));
        cc.setName(c.getString(c.getColumnIndex(COLUMN_COLOR_COMB_NAME)));
        db.close();
        return cc;
    }

    // UPDATE COLOR COMBINATION
    public boolean updateColorCombination(ColorCombination colorCombination) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_COLOR_COMB_NAME, colorCombination.getName());

        db.update(TABLE_COLOR_COMBINATION, contentValues, COLUMN_COLOR_COMB_ID + " = ? ",
                new String[]{Integer.toString(colorCombination.get_id())});
        db.close();
        return true;
    }

    // DELETE COLOR COMBINATION
    public void deleteColorCombination(ColorCombination colorCombination) {
        SQLiteDatabase db = getWritableDatabase();
        String query = "DELETE FROM " + TABLE_COLOR_COMBINATION + " WHERE " +
                COLUMN_COLOR_COMB_ID + " = ?" ;

        db.execSQL(query, new Integer[] {colorCombination.get_id()});
        db.close();
    }

    // READ ALL
    public ArrayList<ColorCombination> getAllColorCombinationLite(){
        ArrayList<ColorCombination> colorCombinationList = new ArrayList<>();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_COLOR_COMBINATION + " WHERE 1";

        //Cursor points to a location in your results
        Cursor c = db.rawQuery(query, null);
        //Move to the first row in your results
        c.moveToFirst();

        //Position after the last row means the end of the results
        while (!c.isAfterLast()) {
            if (c.getString(c.getColumnIndex(COLUMN_COLOR_COMB_NAME)) != null) {
                colorCombinationList.add(new ColorCombination(c.getInt(c.getColumnIndex(COLUMN_COLOR_COMB_ID)),
                        c.getString(c.getColumnIndex(COLUMN_COLOR_COMB_NAME))));
            }
            c.moveToNext();
        }
        db.close();

        return colorCombinationList;
    }
}