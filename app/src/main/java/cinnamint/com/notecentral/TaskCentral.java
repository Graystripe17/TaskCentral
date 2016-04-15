package cinnamint.com.notecentral;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class TaskCentral extends FragmentActivity {

    static List<Tasks> tasks = new ArrayList<Tasks>();
    static List<Tasks> urgent = new ArrayList<Tasks>();
    static List<Tasks> important = new ArrayList<Tasks>();
    static ViewPager mViewPager;
    static int absolute_screen_position = 0;
    PagerAdapter mPagerAdapter;
    final String PREFS_NAME = "com.TaskCentral";
    SharedPreferences sharedPref;
    SharedPreferences.Editor editor;
    int reload_size;
    int REQUEST_CODE_ADD_TASK = 100;


    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_central);
        context = this;

        int resume_position = 0;
        Intent backToMain = getIntent();
        if(backToMain != null) {
            resume_position = backToMain.getIntExtra("fragment_position", 0);
        }

        // Setting the viewpager to TriFragPagerAdapter
        mViewPager = (ViewPager) findViewById(R.id.godie);
        mPagerAdapter = new TriFragPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                // This method will be invoked when a new page becomes selected. Animation is not necessarily complete.
                absolute_screen_position = position;
                // Toast.makeText(context, Integer.toString(position), Toast.LENGTH_LONG).show();
            }
        });

        DatabaseHandler dbh = new DatabaseHandler(context);
        urgent = dbh.getAllTasks(0);
        important = dbh.getAllTasks(1);
        tasks = dbh.getAllTasks(2);


        mViewPager.setCurrentItem(resume_position, false);

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

    public void addNew(View v) {
        Intent addTaskIntent = new Intent(this, AddTaskActivity.class);
        startActivityForResult(addTaskIntent, REQUEST_CODE_ADD_TASK);
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }
}
