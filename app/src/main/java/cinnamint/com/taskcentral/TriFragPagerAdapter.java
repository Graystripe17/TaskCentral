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
        Fragment mTriFragment;
        switch(position) {
//            case 0:
//                mTriFragment = new TriFragment();
//                break;
//            case 1:
//                mTriFragment = new TriFragment();
//                break;
//            case 2:
//                mTriFragment = new TriFragment();
//                break;
            default:
                mTriFragment = new TriFragment();
                break;
        }
        return mTriFragment;
    }


}
