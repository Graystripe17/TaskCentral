package cinnamint.com.taskcentral;


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
        return new TriFragment(position);
    }


}
