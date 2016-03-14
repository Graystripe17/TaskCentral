package cinnamint.com.taskcentral;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.Objects;

public class AddTaskActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_task);
    }

    public void submit_task_to_list(View v) {
        EditText title = (EditText) findViewById(R.id.Title);
        EditText desc = (EditText) findViewById(R.id.DescriptionText);
        Tasks newTask = new Tasks(title.getText().toString(), desc.getText().toString());

        TaskCentral.tasks.add(newTask);
        TaskCentral.mAdapter.notifyDataSetChanged();

        Intent backToMain = new Intent(this, TaskCentral.class);
        // backToMain.putExtra("SizeFromAddTask", TaskCentral.tasks.size());

        DatabaseHandler db = new DatabaseHandler(this);
        db.addTask(newTask);
        db.close();



        startActivity(backToMain);
    }


}
