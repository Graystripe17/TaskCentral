package cinnamint.com.taskcentral;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;


public class TriFragment extends Fragment {
    // Multiple list adapters, do not make static
    // Could be listview adapter
    KittyAdapter mListAdapter;
    ListView taskCentral;

    public static TriFragment newInstance(String param1, String param2) {
        TriFragment fragment = new TriFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TriFragment() {

    }

    public KittyAdapter getListAdapter() {
        return mListAdapter;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_tri, container, false);

        // Must be grabbed on view and passed to other activities
        final int pos = TaskCentral.absolute_screen_position;
        List<Tasks> targetList;
        switch(pos) {
            case 0:
                targetList = TaskCentral.urgent;
                break;
            case 1:
                targetList = TaskCentral.important;
                break;
            case 2:
                targetList = TaskCentral.tasks;
                break;
            default:
                targetList = TaskCentral.tasks;
                break;
        }

        taskCentral = (ListView)rootView.findViewById(R.id.Central);
        // getActivity returns FragmentActivity (extends) Activity (extends) Context
        // getActivity returns null if called before onAttach
        // onAttach->onCreate->onCreateView
        mListAdapter = new KittyAdapter(getActivity(), targetList);
        taskCentral.setAdapter(mListAdapter);
        mListAdapter.notifyDataSetChanged();

        Button check = (Button)rootView.findViewById(R.id.Add);
        check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent addTaskIntent = new Intent(getActivity(), AddTaskActivity.class);
                startActivity(addTaskIntent);
            }
        });

        // Inflate the layout for this fragment
        return rootView;
    }






    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

}
