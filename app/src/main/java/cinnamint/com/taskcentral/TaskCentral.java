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
import java.util.List;


public class TaskCentral extends FragmentActivity {

    static List<Tasks> tasks = new ArrayList<Tasks>();
    static List<Tasks> urgent = new ArrayList<Tasks>();
    static List<Tasks> important = new ArrayList<Tasks>();
    static List<ArrayList<Tasks>> all_tasks;
    static ViewPager mViewPager;
    PagerAdapter mPagerAdapter;
    final int REQUEST_CODE_ADD_TASK = 100;
    final String PREFS_NAME = "com.TaskCentral";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    int reload_size;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_tri);
        context = this;

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

//    public void delete (View v) {
//        v.setAlpha(1f);
//        v.setVisibility(View.VISIBLE);
//        v.animate()
//                .alpha(0f)
//                .setDuration(500)
//                .setListener(null);
//
//
//        DatabaseHandler db = new DatabaseHandler(context);
//        db.clear();
//        tasks.clear();
//        mListAdapter.notifyDataSetChanged();
//    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
