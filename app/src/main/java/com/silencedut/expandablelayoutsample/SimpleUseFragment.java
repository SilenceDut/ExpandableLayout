package com.silencedut.expandablelayoutsample;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.silencedut.expandablelayout.ExpandableLayout;

/**
 * Created by SilenceDut on 16/6/7.
 */

public class SimpleUseFragment extends Fragment {
    public static RecyclerViewFragment newInstance() {
        RecyclerViewFragment recyclerViewFragment = new RecyclerViewFragment();
        return recyclerViewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_simple_use,container,false);
        ExpandableLayout expandableLayout = (ExpandableLayout)rootView.findViewById(R.id.expandable_layout);
        expandableLayout.setOnExpandListener(new ExpandableLayout.OnExpandListener() {
            @Override
            public void onExpand(boolean expanded) {
                Toast.makeText(getActivity(),"expand?"+expanded,Toast.LENGTH_SHORT).show();
            }
        });
        return rootView;
    }
}
