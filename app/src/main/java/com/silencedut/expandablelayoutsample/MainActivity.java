package com.silencedut.expandablelayoutsample;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.silencedut.expandablelayout.ExpandableLayout;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tableLayout);
        ViewPager viewPager = (ViewPager)findViewById(R.id.viewPager);
        setupViewPager(viewPager);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        SamplePageAdapter adapter = new SamplePageAdapter(getSupportFragmentManager());

        Fragment fragment1 = new RecyclerViewFragment();
        Bundle data1 = new Bundle();
        data1.putInt("id", 0);
        fragment1.setArguments(data1);
        adapter.addFrag(fragment1, getString(R.string.UsageOne));

        Fragment fragment2 = new ListViewFragment();
        Bundle data2 = new Bundle();
        data2.putInt("id", 0);
        fragment2.setArguments(data2);
        adapter.addFrag(fragment2, getString(R.string.UsageTwo));

        Fragment fragment3 = new SimpleUseFragment();
        Bundle data3 = new Bundle();
        data3.putInt("id", 0);
        fragment3.setArguments(data3);
        adapter.addFrag(fragment3, getString(R.string.UsageThree));

        viewPager.setAdapter(adapter);

    }
}
