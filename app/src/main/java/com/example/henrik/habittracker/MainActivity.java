package com.example.henrik.habittracker;

import android.content.ContentValues;
import android.content.Entity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.henrik.habittracker.data.HabitDbHelper;
import com.example.henrik.habittracker.data.HabitContract.HabitEntry;

public class MainActivity extends AppCompatActivity {

    private HabitDbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDbHelper=new HabitDbHelper(this);

        insertHabit("Swimming", 5004, HabitEntry.GROUP_TOGETHER);
        insertHabit("Jogging", 5468, HabitEntry.GROUP_ALONE);
        insertHabit("Android Programming", 1000, HabitEntry.GROUP_UNKNOWN);

        displayDatabaseInfo();
    }

    private void insertHabit(String nameString, Integer totalInt, Integer groupInt){
        ContentValues values = new ContentValues();
        values.put(HabitEntry.COLUMN_HABIT_NAME, nameString);
        values.put(HabitEntry.COLUMN_HABIT_TOTALTIME, totalInt);
        values.put(HabitEntry.COLUMN_HABIT_GROUP, groupInt);

        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        long newRowID= db.insert(HabitEntry.TABLE_NAME, null, values);
        if (newRowID == -1) {
            // If the row ID is -1, then there was an error with insertion.
            Toast.makeText(this, "Error with saving habit", Toast.LENGTH_SHORT).show();
        } else {
            // Otherwise, the insertion was successful and we can display a toast with the row ID.
            Toast.makeText(this, "Habit saved with row id: " + newRowID, Toast.LENGTH_SHORT).show();
        }
    }

    private void displayDatabaseInfo() {
        // Create and/or open a database to read from it
        SQLiteDatabase db = mDbHelper.getReadableDatabase();


        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                HabitEntry._ID,
                HabitEntry.COLUMN_HABIT_NAME,
                HabitEntry.COLUMN_HABIT_TOTALTIME,
                HabitEntry.COLUMN_HABIT_GROUP};

        // Perform a query on the habits table
        Cursor cursor = db.query(
                HabitEntry.TABLE_NAME,   // The table to query
                projection,            // The columns to return
                null,                  // The columns for the WHERE clause
                null,                  // The values for the WHERE clause
                null,                  // Don't group the rows
                null,                  // Don't filter by row groups
                null);                   // The sort order
        TextView displayView = (TextView) findViewById(R.id.output);

        try {
            displayView.setText("The habits table contains " + cursor.getCount() + " entries.\n\n");
            displayView.append(HabitEntry._ID + " - " +
                    HabitEntry.COLUMN_HABIT_NAME + " - " +
                    HabitEntry.COLUMN_HABIT_TOTALTIME + " - " +
                    HabitEntry.COLUMN_HABIT_GROUP + "\n");

            // Figure out the index of each column
            int idColumnIndex = cursor.getColumnIndex(HabitEntry._ID);
            int nameColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_NAME);
            int totalTimeColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_TOTALTIME);
            int groupColumnIndex = cursor.getColumnIndex(HabitEntry.COLUMN_HABIT_GROUP);

            // Iterate through all the returned rows in the cursor
            while (cursor.moveToNext()) {
                // Use that index to extract the String or Int value of the word
                // at the current row the cursor is on.
                int currentID = cursor.getInt(idColumnIndex);
                String currentName = cursor.getString(nameColumnIndex);
                String currentTotalTime = cursor.getString(totalTimeColumnIndex);
                int currentGroup = cursor.getInt(groupColumnIndex);
                // Display the values from each column of the current row in the cursor in the TextView
                displayView.append(("\n" + currentID + " - " +
                        currentName + " - " +
                        currentTotalTime + " - " +
                        currentGroup));
            }
        } finally {
            cursor.close();
        }

    }

    public void clear(View v){
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        db.delete(HabitEntry.TABLE_NAME, null, null);
        displayDatabaseInfo();
    }




}
