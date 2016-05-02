package israelbgf.gastei.mobile.actvities;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import israelbgf.gastei.core.usecases.RegisterExpenseFromSMS;
import israelbgf.gastei.mobile.R;
import israelbgf.gastei.mobile.actvities.fragments.ExpensesByDay;
import israelbgf.gastei.mobile.actvities.fragments.ExpensesByPlace;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set a Toolbar to replace the ActionBar.
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        MenuListener menuListener = new MenuListener();
        navigationView.setNavigationItemSelectedListener(menuListener);
        menuListener.onNavigationItemSelected(navigationView.getMenu().findItem(R.id.expenses_by_day));
    }

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
                case R.id.expenses_by_day:
                    fragment = new ExpensesByDay();
                    break;
                case R.id.expenses_by_place:
                    fragment = new ExpensesByPlace();
                    break;
                default:
                    return true;
            }

            // Insert the fragment by replacing any existing fragment
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment).commit();

            menuItem.setChecked(true);
            mDrawer.closeDrawers();
            return true;
        }
    }

    private List<String> messagesFromPhone() {
        String[] selection = new String[] { RegisterExpenseFromSMS.BRADESCO_SMS_NUMBER };
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://sms/inbox"), new String[] {"body"}, "address=?", selection, null);

        List<String> messages = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                messages.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        cursor.close();

        return messages;
    }

}
