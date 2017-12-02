package red.jinge.habittracker;

import android.provider.BaseColumns;

/**
 * Created by Seeyon on 2017-12-2.
 */

public final class HabitContract {
    private HabitContract() {}

    public static abstract class HabitEntry implements BaseColumns {
        public static final String TABLE_NAME = "habits";

        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_DESC = "description";
        public static final String COLUMN_TYPE = "type";


        public static final int COLUMN_TYPE_STUDY = 0;
        public static final int COLUMN_TYPE_SPORT = 1;
        public static final int COLUMN_TYPE_EATING = 2;
        public static final int COLUMN_TYPE_SLEEP = 3;
    }
}
