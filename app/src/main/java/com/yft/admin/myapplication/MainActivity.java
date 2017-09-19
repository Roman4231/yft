package com.yft.admin.myapplication;
        import android.content.Intent;
        import android.content.SharedPreferences;
        import android.graphics.Color;
        import android.graphics.PorterDuff;
        import android.os.Bundle;
        import android.support.design.widget.TabLayout;
        import android.support.v4.content.ContextCompat;
        import android.support.v4.view.ViewPager;
        import android.support.v7.app.AppCompatActivity;
        import android.support.v7.widget.Toolbar;
        import android.view.Menu;
        import android.view.MenuItem;
        import android.widget.Toast;

        import com.anjlab.android.iab.v3.BillingProcessor;
        import com.anjlab.android.iab.v3.TransactionDetails;
        import com.yft.admin.myapplication.adapters.PagerAdapter;
        import com.yft.admin.myapplication.dialog.Activity_info_dialog;


public class MainActivity extends AppCompatActivity {

    Toolbar toolbar;
    String[] tabsTitles;
    SharedPreferences sPref;
    int dontShowAgain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        setSupportActionBar(toolbar);

        sPref = getSharedPreferences("MyDateBase", MODE_PRIVATE);
        dontShowAgain = sPref.getInt("dontShowDialogAgain", 0);

        tabsTitles=new String[3];
        tabsTitles[0]="Choose Training";
        tabsTitles[1]="Home";
        tabsTitles[2]="Schedule";
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_choose_training));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_home_black_24dp));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.ic_schedule));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        final PagerAdapter adapter = new PagerAdapter
                (getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.getTabAt(0).getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(1).getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        tabLayout.getTabAt(2).getIcon().setColorFilter(Color.parseColor("#ffffff"), PorterDuff.Mode.SRC_IN);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.tabsScrollColor);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
                toolbar.setTitle(tabsTitles[tab.getPosition()]);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                int tabIconColor = ContextCompat.getColor(MainActivity.this, R.color.myWhite);
                tab.getIcon().setColorFilter(tabIconColor, PorterDuff.Mode.SRC_IN);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
        viewPager.setCurrentItem(1);


        if(dontShowAgain == 0){
            Activity_info_dialog dialog = new Activity_info_dialog();
            dialog.show(getSupportFragmentManager(), "info");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this,About.class));
            return true;
        }
        if (id == R.id.settings) {
            startActivity(new Intent(MainActivity.this,Settings.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}