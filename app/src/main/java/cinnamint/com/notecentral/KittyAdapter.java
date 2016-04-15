package cinnamint.com.notecentral;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import java.util.List;

public class KittyAdapter extends ArrayAdapter<Tasks> {

    int fp;

    private int mShortAnimationDuration = 200;

    public KittyAdapter(Context context, List<Tasks> items, int Fragment_Position) {
        super(context, R.layout.custom_task_layout, items);
        fp = Fragment_Position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final KittyAdapter final_copy_of_this = this;

        LayoutInflater myinf = LayoutInflater.from(getContext());
        View customView = myinf.inflate(R.layout.custom_task_layout, parent, false);

        Tasks singleTask = getItem(position);
        final CheckBox done = (CheckBox) customView.findViewById(R.id.Done);
        TextView myItemText = (TextView) customView.findViewById(R.id.ItemText);
        TextView myDescText = (TextView) customView.findViewById(R.id.DescriptionText);

        myItemText.setText(singleTask.getTitle());
        myDescText.setText(singleTask.getDescription());
        int rnd = (int) (3 * Math.random());
        // customView.setBackgroundColor(singleTask.getbColor());


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
                        List<Tasks> currentList;
                        switch (fp) {
                            case 0:
                                currentList = TaskCentral.urgent;
                                break;
                            case 1:
                                currentList = TaskCentral.important;
                                break;
                            case 2:
                                currentList = TaskCentral.tasks;
                                break;
                            default:
                                currentList = TaskCentral.tasks;
                                break;
                        }

                        // YOU MUST UPDATE THE DATABASE, removed by Title
                        DatabaseHandler db = new DatabaseHandler(getContext());
                        db.remove(currentList.get(pos).getTitle(), fp);
                        db.close();
                        currentList.remove(pos);
                        final_copy_of_this.notifyDataSetChanged();
                    }
                }, mShortAnimationDuration);


            }
        });

        this.notifyDataSetChanged();



        return customView;
    }



}