package com.rasco.lightstick.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    public static int DB_VERSION = 1;
    public static final String DATABASE_NAME = "colorsDB.db";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ColorCombinationDB.CREATE_TABLE_COLOR_COMBINATION);
        db.execSQL(ColorCombinationDB.CREATE_TABLE_COLOR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ColorCombinationDB.TABLE_COLOR_COMBINATION);
        db.execSQL("DROP TABLE IF EXISTS " + ColorCombinationDB.TABLE_COLOR);

        onCreate(db);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            String query = String.format ("PRAGMA foreign_keys = %s","ON");
            db.execSQL(query);
        }
    }
}
