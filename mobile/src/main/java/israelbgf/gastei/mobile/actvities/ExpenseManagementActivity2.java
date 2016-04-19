package israelbgf.gastei.mobile.actvities;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.fragments.ExpensesByDay;
import israelbgf.gastei.mobile.fragments.ExpensesByPlace;

import java.util.ArrayList;
import java.util.List;

public class ExpenseManagementActivity2 extends AppCompatActivity {
 
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
 
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);
 
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_expense_management, menu);
        return true;
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ExpensesByDay(), "By Day");
        adapter.addFragment(new ExpensesByPlace(), "By Place");
        viewPager.setAdapter(adapter);
    }
 
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();
 
        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }
 
        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }
 
        @Override
        public int getCount() {
            return fragmentList.size();
        }
 
        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }
 
        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}
