package cinnamint.com.taskcentral;


import android.annotation.TargetApi;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.view.View;
import android.widget.EditText;

import java.util.Objects;
import java.util.Random;

public class AddTaskActivity extends Activity {

    int fragment_position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);

        Intent intent = getIntent();
        fragment_position = intent.getIntExtra("Fragment_Position", 200);

    }

    public void submit_task_to_list(View v) {

        SharedPreferences prefs = getSharedPreferences(Activity.class.getSimpleName(), Context.MODE_PRIVATE);
        int unique_notification_id = prefs.getInt("notificationNumber", 0);

        EditText title = (EditText) findViewById(R.id.Title);
        EditText desc = (EditText) findViewById(R.id.DescriptionText);
        Tasks newTask = new Tasks(title.getText().toString(), desc.getText().toString());


        // Warning: Check if mListAdapter static
        switch(fragment_position) {
            case 0:
                TaskCentral.urgent.add(newTask);
                break;
            case 1:
                TaskCentral.important.add(newTask);
                break;
            case 2:
                TaskCentral.tasks.add(newTask);
                break;
        }

        // Note: after going back to Task Central it will instantiate the ListView adapter
        // again which will reset the ListView. Kind of hacky.
        // notifyDataSetChanged();



        Intent backToMain = new Intent(this, TaskCentral.class);
        backToMain.putExtra("fragment_position", fragment_position);

        DatabaseHandler db = new DatabaseHandler(this);
        db.addTask(newTask, fragment_position);
        db.close();



        // Add Notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.stack_of_papers)
                .setContentTitle(title.getText().toString())
                .setContentText(desc.getText().toString());

        Intent openApp = new Intent(this, TaskCentral.class);
        mBuilder.setContentIntent(build_notification_parent_stack(openApp));
        mBuilder.setColor(Color.GRAY);

        NotificationManager mNotificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.notify(unique_notification_id, mBuilder.build());

        SharedPreferences.Editor editor = prefs.edit();
        unique_notification_id++;
        editor.putInt("notificationNumber", unique_notification_id);
        editor.commit();


        startActivity(backToMain);
    }

    @TargetApi (16)
    private PendingIntent build_notification_parent_stack(Intent openApp) {
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(TaskCentral.class);
        stackBuilder.addNextIntent(openApp);
        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
                0,
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        return resultPendingIntent;
    }
}
