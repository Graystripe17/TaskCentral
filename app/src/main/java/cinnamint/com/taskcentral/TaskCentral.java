package cinnamint.com.taskcentral;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
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


public class TaskCentral extends FragmentActivity {

    static KittyAdapter mAdapter; // Could be listview adapter
    static ArrayList<Tasks> tasks = new ArrayList<Tasks>();
    static ListView taskCentral;
    ViewPager mViewPager;
    PagerAdapter mPagerAdapter;
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

        DatabaseHandler db = new DatabaseHandler(this);



        taskCentral = (ListView)findViewById(R.id.Central);
        //if (mAdapter == null) {
        // MUST BE CONSTANTLY RESET
        mAdapter = new KittyAdapter(this, tasks);
        //}
        taskCentral.setAdapter(mAdapter);

        mAdapter.notifyDataSetChanged();


        taskCentral.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(context, "REMOVE", Toast.LENGTH_LONG).show();
                removeItem(position);
            }
        });


        // Setting the viewpager to TriFragPagerAdapter
        mViewPager = (ViewPager) findViewById(R.id.ViewPager);
        mPagerAdapter = new TriFragPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
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
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void delete (View v) {
        v.setAlpha(1f);
        v.setVisibility(View.VISIBLE);
        v.animate()
                .alpha(0f)
                .setDuration(500)
                .setListener(null);


        DatabaseHandler db = new DatabaseHandler(context);
        db.clear();
        tasks.clear();
        mAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
