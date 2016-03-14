package cinnamint.com.taskcentral;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "Tasks";

    private static final String TABLE_TASKS = "tasks";

    private static final String KEY_TITLE = "Title";
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_COLOR = "Color";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
                + KEY_TITLE + " STRING, "
                + KEY_DESCRIPTION + " STRING, "
                + KEY_COLOR + " COLOR" + ")";
        db.execSQL(CREATE_TASKS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        onCreate(db);
    }

    public void addTask(Tasks task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_COLOR, task.getbColor());

        if (db.insert(TABLE_TASKS, null, values) == -1) {
            Log.d("WRONG", "BAD");
        };
        db.close();
    }

    public ArrayList<Tasks> getAllTasks() {
        ArrayList<Tasks> TList = new ArrayList<Tasks>();
        String selectQuery = "SELECT * FROM " + TABLE_TASKS;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if(cursor.moveToFirst()) {
            do {
                Tasks target = new Tasks();
                target.setTitle(cursor.getString(0));
                target.setDescription(cursor.getString(1));
                target.setbColor(Integer.parseInt(cursor.getString(2)));
                TList.add(target);
            } while(cursor.moveToNext());
        }
        return TList;
    }

    public void clear() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASKS);
        db.close();
    }

    public void remove(String t) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_TASKS + " WHERE Title = " + "'" + t + "'");
        db.close();
    }

}
