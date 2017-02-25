package a.choquantifier.app;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TabHost;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by C POLANIA on 24/10/2016.
 */

public class VotersDialog extends DialogFragment {


    private FragmentTabHost mTabHost;
    private ViewPager viewPager;
    private VotersPagerAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.voters_dialog, container);

        getDialog().setTitle(getArguments().getString("title"));

        mTabHost = (FragmentTabHost) view.findViewById(R.id.tabs);

        mTabHost.setup(getActivity(), getChildFragmentManager());
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator("Плюсов"), Fragment.class, null);
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator("Минусов"), Fragment.class, null);


        adapter = new VotersPagerAdapter(getChildFragmentManager(), getArguments());
        adapter.setTitles(new String[]{"Плюсов", "Минусов"});

        viewPager = (ViewPager)view.findViewById(R.id.pager);
        viewPager.setAdapter(adapter);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i2) {
            }

            @Override
            public void onPageSelected(int i) {
                mTabHost.setCurrentTab(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        mTabHost.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String s) {
                int i = mTabHost.getCurrentTab();
                viewPager.setCurrentItem(i);
            }
        });

        return view;
    }


    public class VotersPagerAdapter extends FragmentPagerAdapter {

        Bundle bundle;
        String [] titles;

        public VotersPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public VotersPagerAdapter(FragmentManager fm, Bundle bundle) {
            super(fm);
            this.bundle = bundle;
        }

        @Override
        public Fragment getItem(int num) {
            Fragment fragment = new VotersListFragment();
            Bundle args = new Bundle();
            args.putSerializable("voters",bundle.getSerializable( num == 0 ? "pros" : "cons"));
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titles[position];
        }

        public void setTitles(String[] titles) {
            this.titles = titles;
        }
    }

    public static class VotersListFragment extends ListFragment {

        List<String> voters;

        /*@Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.list_fragment, container, false);
            return view;
        }*/

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {

            super.onActivityCreated(savedInstanceState);
            voters = (ArrayList) getArguments().getSerializable("voters");

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, voters);
            setListAdapter(adapter);

            /*getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(getActivity(), ProfileActivity_.class);
                    String login = voters.get(i);
                    //intent.putExtra("login", Utils.encodeString(login.substring(0, login.indexOf("(")).trim()));
                    startActivity(intent);
                }
            });*/

        }

    }

}