package red.jinge.habittracker;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String LOG_TAG = MainActivity.class.getName();
    HabitDbHelper habitDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        habitDbHelper = new HabitDbHelper(this);

        /**
         * 数据库测试
         */

        // 插入数据
        insertHabit(getString(R.string.habit1_name),
                getString(R.string.habit1_desc),
                HabitContract.HabitEntry.COLUMN_TYPE_SLEEP);
        insertHabit(getString(R.string.habit2_name),
                getString(R.string.habit2_desc),
                HabitContract.HabitEntry.COLUMN_TYPE_EATING);
        insertHabit(getString(R.string.habit3_name),
                getString(R.string.habit3_desc),
                HabitContract.HabitEntry.COLUMN_TYPE_SPORT);

        // 读取数据
        readHabits();

        // 更新数据
        updateHabit(1, getString(R.string.habit1_new_desc));
        updateHabit(2, getString(R.string.habit2_new_desc));

        // 在此读取数据
        readHabits();
    }

    /**
     * 往habits表中插入一条记录
     * @param name          习惯名称
     * @param description   习惯描述
     * @param type          习惯类型
     * @return  是否插入成功
     */
    public boolean insertHabit(String name, String description, int type) {
        SQLiteDatabase db = habitDbHelper.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(HabitContract.HabitEntry.COLUMN_NAME, name);
        contentValues.put(HabitContract.HabitEntry.COLUMN_DESC, description);
        contentValues.put(HabitContract.HabitEntry.COLUMN_TYPE, type);

        long newRowId = db.insert(HabitContract.HabitEntry.TABLE_NAME, null, contentValues);

        if (newRowId != -1) {
            Log.i(LOG_TAG, "插入记录成功: " + newRowId);
            return false;
        } else {
            Log.e(LOG_TAG, "插入记录失败");
            return true;
        }
    }

    /**
     * 读取所有习惯记录
     */
    public void readHabits() {
        SQLiteDatabase db = habitDbHelper.getReadableDatabase();
        String [] columns = { HabitContract.HabitEntry._ID,
                              HabitContract.HabitEntry.COLUMN_NAME,
                              HabitContract.HabitEntry.COLUMN_DESC };
        Cursor cursor = db.query(
                HabitContract.HabitEntry.TABLE_NAME,
                columns,
                null,
                null,
                null,
                null,
                null,
                null
        );

        while (cursor.moveToNext()) {
            String info = HabitContract.HabitEntry._ID + ": "
                    + cursor.getInt(cursor.getColumnIndex(HabitContract.HabitEntry._ID))
                    + HabitContract.HabitEntry.COLUMN_NAME + ": "
                    + cursor.getString(cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_NAME)) + ", "
                    + HabitContract.HabitEntry.COLUMN_DESC + ": "
                    + cursor.getString(cursor.getColumnIndex(HabitContract.HabitEntry.COLUMN_DESC));
            Log.i(LOG_TAG, info);
        }
    }

    /**
     * 更新一条习惯记录的表述字段
     * @param id    要更新的记录的id
     * @param desc  新的描述
     */
    public void updateHabit(int id, String desc) {
        SQLiteDatabase db = habitDbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(HabitContract.HabitEntry.COLUMN_DESC, desc);

        String [] whereArgs = { String.valueOf(id) };

        db.update(HabitContract.HabitEntry.TABLE_NAME,
                  contentValues,
                HabitContract.HabitEntry._ID + " = ?",
                  whereArgs);
    }
}
