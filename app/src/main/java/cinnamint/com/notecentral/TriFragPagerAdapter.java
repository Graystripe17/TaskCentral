package cinnamint.com.notecentral;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class TriFragPagerAdapter extends FragmentPagerAdapter {

    public TriFragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int position) {
        Bundle args = new Bundle();
        args.putInt("position", position);

        TriFragment newTriFragment = new TriFragment();
        newTriFragment.setArguments(args);

        return newTriFragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch(position) {
            case 0:
                return "Urgent";
            case 1:
                return "Important";
            case 2:
                return "Tasks";
            default:
                return "Other";
        }
    }


}
