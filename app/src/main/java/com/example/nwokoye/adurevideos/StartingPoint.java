package com.example.nwokoye.adurevideos;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.MenuInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.nwokoye.adurevideos.fragments.Four;
import com.example.nwokoye.adurevideos.fragments.One;
import com.example.nwokoye.adurevideos.fragments.Testing;
import com.example.nwokoye.adurevideos.fragments.Three;
import com.example.nwokoye.adurevideos.fragments.Two;
import com.example.nwokoye.adurevideos.fragments.comedy;
import com.example.nwokoye.adurevideos.fragments.live;
import com.example.nwokoye.adurevideos.fragments.music;
import com.example.nwokoye.adurevideos.fragments.reality;

import java.util.HashMap;

public class StartingPoint extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
        Intent intent;
        Fragment fragment;
        private ViewPager mViewPager;
        NavigationView navigationView;
        private SearchView searchView;
        private MenuItem searchMenuItem;
   session_management session;

        @Override
        protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_starting_point);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
            AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar);
            // hiding & showing the title when toolbar expanded & collapsed
            session = new session_management(getApplicationContext());
            session.checkLogin();
            appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
                boolean isShow = false;
                int scrollRange = -1;

                @Override
                public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                    if (scrollRange == -1) {
                        scrollRange = appBarLayout.getTotalScrollRange();
                    }
                    if (scrollRange + verticalOffset == 0) {
                        isShow = true;
                    } else if (isShow) {
                        isShow = false;
                    }
                }
            });
            SectionsPagerAdapter adapter = new SectionsPagerAdapter(getSupportFragmentManager(), StartingPoint.this);
        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(adapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

        @Override
        public void onBackPressed () {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

        @Override
        public boolean onCreateOptionsMenu (Menu menu){
            MenuInflater inflater = getMenuInflater();
            // Inflate menu to add items to action bar if it is present.
            inflater.inflate(R.menu.starting_point, menu);
            // Associate searchable configuration with the SearchView
            SearchManager searchManager =
                    (SearchManager) getSystemService(Context.SEARCH_SERVICE);
            SearchView searchView =
                    (SearchView) menu.findItem(R.id.action_search).getActionView();
            searchView.setSearchableInfo(
                    searchManager.getSearchableInfo(getComponentName()));
            searchView.setSubmitButtonEnabled(true);;

            return true;
    }

        @Override
        public boolean onOptionsItemSelected (MenuItem item){
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

        @SuppressWarnings("StatementWithEmptyBody")
        @Override
        public boolean onNavigationItemSelected (MenuItem item){
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_channels) {
            intent = new Intent(this, StartingPoint.class);
            startActivity(intent);
        } else if (id == R.id.nav_subscription) {
          intent = new Intent(this, payment.class);
            startActivity(intent);
        } else if (id == R.id.nav_download) {
          //  intent = new Intent(this, Download.class);
           // startActivity(intent);
        } else if (id == R.id.nav_contact) {
           intent = new Intent(Intent.ACTION_SENDTO);
            intent.setType("text/plain");
            intent.setData(Uri.parse("mailto:hello@aduretv.com"));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } else if (id == R.id.nav_abt) {
           intent = new Intent(this, AboutUs.class);
           startActivity(intent);
        } else if (id == R.id.nav_log) {
            session.logoutUser();
            finish();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.frame, fragment).commit();


        } else {
            Log.e("MainActivity", "Error in creating fragment");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
        public class SectionsPagerAdapter extends FragmentPagerAdapter {
            private String tabTitles[] = new String[]{"MOVIES", "COMEDY", "MUSIC", "REALITY", "LIVE"};
            private Context context;


            public SectionsPagerAdapter(FragmentManager fm, Context context) {
                super(fm);
                this.context = context;
            }

            @Override
            public Fragment getItem(int position) {
                if (position == 0) {
                    return new One();
                } else if (position == 1) {
                    return new Two();
                } else if (position == 2) {
                    return new Three();
                } else if (position == 3) {
                    return new reality();
                } else {
                    return new Four();
                }
            }

            @Override
            public int getCount() {

                return 5;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                // Generate title based on item position
                return tabTitles[position];
            }
        }


    }


