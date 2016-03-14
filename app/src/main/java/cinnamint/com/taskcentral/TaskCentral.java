package cinnamint.com.taskcentral;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;


public class TaskCentral extends Activity {

    static KittyAdapter mAdapter; // Could be listview adapter
    static ArrayList<Tasks> tasks = new ArrayList<Tasks>();
    static ListView taskCentral;
    final int REQUEST_CODE_ADD_TASK = 100;
    final String PREFS_NAME = "com.TaskCentral";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    int reload_size;

    Context context;

    float historicX = Float.NaN, historicY = Float.NaN;
    static final int DELTA = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_central);
        context = this;
        sharedPref = getSharedPreferences(PREFS_NAME, MODE_PRIVATE);
        editor = sharedPref.edit();
        int k = sharedPref.getInt("Read", 7);
        Toast.makeText(context, "K: " + k, Toast.LENGTH_LONG).show();

        // Are we coming back from AddTaskActivity
        if(getIntent() != null) {
            reload_size = getIntent().getIntExtra("SizeFromAddTask", -1);
            if(reload_size != -1) {
                // We just came back from AddTaskActivity
            }
        }
        // Are we coming back from closing the app
        else if(sharedPref != null) {
            // If there is no previous preference, then the current task size will work
            reload_size = sharedPref.getInt("SIZE", tasks.size());

        }
        // Start constructing tasks
        tasks.clear();
        for(int i = 0; i < reload_size; i++) {
            Tasks builder = new Tasks();
            builder.setTitle(sharedPref.getString("Title_" + i, "Cant find " + i));
            builder.setDescription(sharedPref.getString("Description_" + i, "Cant find " + i));
            builder.setbColor(sharedPref.getInt("Color_" + i, 10));
            tasks.add(builder);
        }
        // End constructing tasks


        taskCentral = (ListView)findViewById(R.id.Central);
        if (mAdapter == null) {
            mAdapter = new KittyAdapter(this, tasks);
        }
        taskCentral.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();

//        taskCentral.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_DOWN:
//                        historicX = event.getX();
//                        historicY = event.getY();
//                        break;
//                    case MotionEvent.ACTION_UP:
//                        if (event.getX() - historicX < -DELTA) {
//                            Toast.makeText(context, "LEFT", Toast.LENGTH_LONG).show();
//                            return true;
//                        } else if (event.getY() - historicX > DELTA) {
//                            Toast.makeText(context, "RIGHT", Toast.LENGTH_SHORT).show();
//                            return true;
//                        }
//                        break;
//                    default:
//                        return false;
//                }
//                return false;
//            }
//        });

        taskCentral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "REMOVE", Toast.LENGTH_LONG).show();
                removeItem(position);
            }
        });

//        for(int i = 0; i < taskCentral.getCount(); i++) {
//            final int place = i;
//            ((CheckBox) (taskCentral.getChildAt(i).findViewById(R.id.Done))).setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//                @Override
//                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                    TaskCentral.tasks.remove(place);
//                    Tasks[] TaskArr = new Tasks[TaskCentral.tasks.size()];
//                    TaskArr = TaskCentral.tasks.toArray(TaskArr);
//                    TaskCentral.tasks.add(new Tasks("DFSION", "ASKLD"));
//                    TaskCentral.mAdapter.notifyDataSetChanged();
//                }
//            });
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_task_central, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addNew(View v) {
        Intent addTaskIntent = new Intent(this, AddTaskActivity.class);
        startActivityForResult(addTaskIntent, REQUEST_CODE_ADD_TASK);
    }

    public void removeItem(int pos) {
        Toast.makeText(context, "YOYOYOYOYO", Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onPause() {
        editor.putInt("Read", 3);
        editor.commit();
        if(sharedPref != null) {
            // Get rid of Bloating
            // editor.clear();

            editor.putInt("SIZE", tasks.size());
            for(int i = 0; i < tasks.size(); i++) {
                Tasks target = tasks.get(i);
                editor.putString("Title_"+i, target.getTitle());
                editor.putString("Description_" + i, target.getDescription());
                editor.putInt("Color_" + i, target.getbColor());
                Toast.makeText(context, "Title: "+target.getTitle() + "   Size: " + tasks.size(), Toast.LENGTH_SHORT).show();
            }
        }
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        editor.putInt("Read", 5);
        editor.commit();
        if(sharedPref != null) {
            // Get rid of Bloating
            //editor.clear();

            editor.putInt("SIZE", tasks.size());
            for(int i = 0; i < tasks.size(); i++) {
                Tasks target = tasks.get(i);
                editor.putString("Title_"+i, target.getTitle());
                editor.putString("Description_" + i, target.getDescription());
                editor.putInt("Color_" + i, target.getbColor());
                Toast.makeText(context, "destroy; Title: " + target.getTitle() + "   Size: " + tasks.size(), Toast.LENGTH_SHORT).show();
            }
            editor.commit();
        }
        super.onDestroy();
    }

    public void delete (View v) {
        tasks.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        editor.putInt("SIZE", tasks.size());
        editor.commit();
    }
}
