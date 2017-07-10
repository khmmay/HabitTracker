package com.example.henrik.habittracker.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Henrik on 08.07.2017.
 */
import com.example.henrik.habittracker.data.HabitContract.HabitEntry;


public class HabitDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "hab.db";


    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE "
            + HabitEntry.TABLE_NAME + " ("
            + HabitEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + HabitEntry.COLUMN_HABIT_NAME + " TEXT NOT NULL,"
            + HabitEntry.COLUMN_HABIT_TOTALTIME + " TEXT,"
            + HabitEntry.COLUMN_HABIT_GROUP + " INTEGER NOT NULL);";

    private static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + HabitEntry.TABLE_NAME;

    public HabitDbHelper(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_ENTRIES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(SQL_DELETE_ENTRIES);
        onCreate(db);
    }
}
