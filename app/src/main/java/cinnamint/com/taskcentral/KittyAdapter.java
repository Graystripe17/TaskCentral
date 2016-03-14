package cinnamint.com.taskcentral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class KittyAdapter extends ArrayAdapter<Tasks> {

    public KittyAdapter(Context context, ArrayList<Tasks> items) {
        super(context, R.layout.custom_task_layout, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        LayoutInflater myinf = LayoutInflater.from(getContext());
        View customView = myinf.inflate(R.layout.custom_task_layout, parent, false);

        Tasks singleTask = getItem(position);
        CheckBox done = (CheckBox) customView.findViewById(R.id.Done);
        TextView myItemText = (TextView) customView.findViewById(R.id.ItemText);
        TextView myDescText = (TextView) customView.findViewById(R.id.DescriptionText);

        myItemText.setText(singleTask.getTitle());
        myDescText.setText(singleTask.getDescription());
        customView.setBackgroundColor(singleTask.getbColor());

        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            TaskCentral.tasks.remove(pos);

            TaskCentral.mAdapter.notifyDataSetChanged();
            }
        });

        this.notifyDataSetChanged();

        return customView;
    }


}
