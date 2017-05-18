package com.lackhite.borrowland_mobile_app.borrowland.LoanList;

/**
 * Created by kannabi on 19.02.17.
 */

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import java.util.ArrayList;
import java.util.List;

import com.lackhite.borrowland_mobile_app.borrowland.LoanList.LoanListInTab.LoanListFragment;
import com.lackhite.borrowland_mobile_app.borrowland.R;


public class LoanListTabsFragment extends Fragment{

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        RelativeLayout view = (RelativeLayout) inflater.inflate(
                R.layout.loan_list_tab_fragment, container, false);

        // Setting ViewPager for each Tabs
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        setupViewPager(viewPager);
        // Set Tabs inside Toolbar
        TabLayout tabs = (TabLayout) view.findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        return view;
    }

    // Add Fragments to Tabs
    private void setupViewPager(ViewPager viewPager) {
        Adapter adapter = new Adapter(getChildFragmentManager());

        Bundle arg = new Bundle();


        LoanListFragment outList = new LoanListFragment();
        arg.putInt("direction", -1);
        outList.setArguments(arg);
        adapter.addFragment(outList, getString(R.string.name_tab_out_loans));

        LoanListFragment allList = new LoanListFragment();
        arg = new Bundle();
        arg.putInt("direction", 0);
        allList.setArguments(arg);
//        allList.setDirection(0);
        adapter.addFragment(allList, getString(R.string.name_tab_all_loans));

        LoanListFragment inList = new LoanListFragment();
        arg = new Bundle();
        arg.putInt("direction", 1);
        inList.setArguments(arg);
//        inList.setDirection(1);
        adapter.addFragment(inList, getString(R.string.name_tab_in_loans));

        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);
    }


    static class Adapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public Adapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
