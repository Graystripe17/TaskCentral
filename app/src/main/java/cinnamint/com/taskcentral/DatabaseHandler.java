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
    private static final String TABLE_URGENT = "urgent";
    private static final String TABLE_IMPORTANT = "important";

    private static final String KEY_TITLE = "Title";
    private static final String KEY_DESCRIPTION = "Description";
    private static final String KEY_COLOR = "Color";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_URGENT_TABLE = "CREATE TABLE " + TABLE_URGENT + "("
                + KEY_TITLE + " STRING, "
                + KEY_DESCRIPTION + " STRING, "
                + KEY_COLOR + " COLOR" + ")";
        try {
            db.execSQL(CREATE_URGENT_TABLE);
        } catch(Exception e) {
            Log.e("WRONG", e.toString());
        }


        String CREATE_IMPORTANT_TABLE = "CREATE TABLE " + TABLE_IMPORTANT + "("
                + KEY_TITLE + " STRING, "
                + KEY_DESCRIPTION + " STRING, "
                + KEY_COLOR + " COLOR" + ")";
        try {
            db.execSQL(CREATE_IMPORTANT_TABLE);
        } catch(Exception e) {
            Log.e("WRONG", e.toString());
        }

        String CREATE_TASKS_TABLE = "CREATE TABLE " + TABLE_TASKS + "("
        + KEY_TITLE + " STRING, "
        + KEY_DESCRIPTION + " STRING, "
        + KEY_COLOR + " COLOR" + ")";
        try {
            db.execSQL(CREATE_TASKS_TABLE);
        } catch(Exception e) {
            Log.e("WRONG", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASKS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_URGENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_IMPORTANT);
        onCreate(db);
    }

    public void addTask(Tasks task) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_TITLE, task.getTitle());
        values.put(KEY_DESCRIPTION, task.getDescription());
        values.put(KEY_COLOR, task.getbColor());

        String TARGET_TABLE;
        switch(TaskCentral.absolute_screen_position) {
            case 0:
                TARGET_TABLE = TABLE_URGENT;
                break;
            case 1:
                TARGET_TABLE = TABLE_IMPORTANT;
                break;
            case 2:
                TARGET_TABLE = TABLE_TASKS;
                break;
            default:
                TARGET_TABLE = TABLE_TASKS;
                break;
        }

        if (db.insert(TARGET_TABLE, null, values) == -1) {
            Log.d("WRONG", "BAD");
        }

        String insString = "INSERT INTO " +
                            TABLE_URGENT + " (Title, Description, Color)" +
                            " VALUES ('MYTITLE', 'mydesc', '231')";
        db.execSQL(insString);


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

    public void remove(String t, int frag_pos) {
        //TODO: REWRITE THIS FOR ALL TABLES
        SQLiteDatabase db = this.getWritableDatabase();

        String target_table;
        switch(frag_pos) {
            case 0:
                target_table = TABLE_URGENT;
                break;
            case 1:
                target_table = TABLE_IMPORTANT;
                break;
            case 2:
                target_table = TABLE_TASKS;
                break;
            default:
                target_table = TABLE_TASKS;
        }

        String delete_sql = "DELETE FROM " + target_table + " WHERE Title = " + "'" + t + "'";
        db.execSQL(delete_sql);
        db.close();
    }

}
