package israelbgf.gastei.mobile.actvities;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.fragments.ExpensesByDay;
import israelbgf.gastei.mobile.fragments.ExpensesByPlace;
import israelbgf.gastei.mobile.widgets.MonthPicker;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;
    private MonthPicker currentDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
//        setupVisualEffectsForActivityTransitions();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        currentDate = (MonthPicker) findViewById(R.id.month_picker);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nvView);
        navigationView.setNavigationItemSelectedListener(new MenuListener());
    }

//    private void setupVisualEffectsForActivityTransitions() {
//        getActivity().getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
//        getActivity().getWindow().setEnterTransition(new Fade());
//        getActivity().getWindow().setExitTransition(new Fade());
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        switch (item.getItemId()) {
            case android.R.id.home:
                mDrawer.openDrawer(GravityCompat.START);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public class MenuListener implements NavigationView.OnNavigationItemSelectedListener {
        @Override
        public boolean onNavigationItemSelected(MenuItem menuItem) {
            // Create a new fragment and specify the fragment to show based on nav item clicked
            Fragment fragment = null;
            switch (menuItem.getItemId()) {
                case R.id.nav_first_fragment:
                    fragment = new ExpensesByDay();
                    break;
                case R.id.nav_second_fragment:
                    fragment = new ExpensesByPlace();
                    break;
            }

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            // Highlight the selected item has been done by NavigationView
            menuItem.setChecked(true);
            // Set action bar title
            setTitle(menuItem.getTitle());
            // Close the navigation drawer
            mDrawer.closeDrawers();
            return true;
        }
    }

}
