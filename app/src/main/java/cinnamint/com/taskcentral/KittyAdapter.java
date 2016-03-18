package cinnamint.com.taskcentral;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class KittyAdapter extends ArrayAdapter<Tasks> {

    private int mShortAnimationDuration = 500;

    public KittyAdapter(Context context, ArrayList<Tasks> items) {
        super(context, R.layout.custom_task_layout, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;

        LayoutInflater myinf = LayoutInflater.from(getContext());
        View customView = myinf.inflate(R.layout.custom_task_layout, parent, false);

        Tasks singleTask = getItem(position);
        final CheckBox done = (CheckBox) customView.findViewById(R.id.Done);
        TextView myItemText = (TextView) customView.findViewById(R.id.ItemText);
        TextView myDescText = (TextView) customView.findViewById(R.id.DescriptionText);

        myItemText.setText(singleTask.getTitle());
        myDescText.setText(singleTask.getDescription());
        customView.setBackgroundColor(singleTask.getbColor());


        done.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                Animation mFadeAnim = AnimationUtils.loadAnimation(getContext(), R.anim.abc_shrink_fade_out_from_bottom);
                mFadeAnim.setDuration(mShortAnimationDuration);

                View parent = (View) buttonView.getParent();
//                parent.setAlpha(1f);
//                parent.setVisibility(View.VISIBLE);
//                parent.animate()
//                        .alpha(0f)
//                        .setDuration(mShortAnimationDuration)
//                        .setListener(null);

                parent.startAnimation(mFadeAnim);


                done.setEnabled(false);
                // Updates database after a the animation completes
                parent.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        // YOU MUST UPDATE THE DATABASE, removed by Title
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        db.remove(TaskCentral.tasks.get(pos).getTitle());
                        db.close();
                        TaskCentral.tasks.remove(pos);
                        TaskCentral.mAdapter.notifyDataSetChanged();
                    }
                }, mShortAnimationDuration);



            }
        });

        this.notifyDataSetChanged();



        return customView;
    }



}
