package com.example.henrik.habittracker.data;

import android.provider.BaseColumns;

/**
 * Created by Henrik on 08.07.2017.
 */

public final class HabitContract {

    private HabitContract(){}

    public static class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";

        public final static String _ID=BaseColumns._ID;
        public static final String COLUMN_HABIT_NAME = "name";
        public static final String COLUMN_HABIT_TOTALTIME = "Totaltime";
        public static final String COLUMN_HABIT_GROUP = "Groupactivity";

        public static final int GROUP_UNKNOWN = 0;
        public static final int GROUP_ALONE = 1;
        public static final int GROUP_TOGETHER = 2;
    }
}

