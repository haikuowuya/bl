package com.haikuowuya.bl;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.inputmethod.EditorInfo;

import com.haikuowuya.bl.base.BaseActivity;
import com.haikuowuya.bl.fragment.MainFragment;

public class MainActivity extends BaseActivity   implements NavigationView.OnNavigationItemSelectedListener
{
  private MainFragment mMainFragment;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        mMainFragment= MainFragment.newInstance();
        getSupportFragmentManager().beginTransaction().add(R.id.frame_container,  mMainFragment).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.activity_main,menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_search);
        SearchView searchView = (SearchView)  MenuItemCompat.getActionView(menuItem);
          searchView.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
            {
                @Override
                public boolean onQueryTextSubmit(String query)
                {
                    MenuItemCompat.collapseActionView(menuItem);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText)
                {
                    if(!TextUtils.isEmpty(newText))
                    {
                        mMainFragment.doGetData(newText);
                    }
                    return false;
                }
            });

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed()
    {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START))
        {
            drawer.closeDrawer(GravityCompat.START);
        }
        else
        {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item)
    {
        final int id = item.getItemId();
        if (id == R.id.nav_camera)
        {
        }
        else if (id == R.id.nav_gallery)
        {

        }
        else if (id == R.id.nav_slideshow)
        {

        }
        else if (id == R.id.nav_manage)
        {
        }
        else if (id == R.id.nav_rx)
        {
            new Handler().postDelayed(new Runnable()
            {
                public void run()
                {
                    RxActivity.actionRx(mActivity);
                }
            },400L);
            //use lambda
        // new Handler().postDelayed(()->RxActivity.actionRx(mActivity),400L);
        }
        else if (id == R.id.nav_settings)
        {
           new Handler().postDelayed(()->SettingsActivity.actionSettings(mActivity),400L);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
