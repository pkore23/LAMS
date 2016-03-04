package com.beproject.lams;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.internal.NavigationSubMenu;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.beproject.lams.dummy.DummyContent;

public class UserActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, EventFragment.OnListFragmentInteractionListener, ErrorFragment.OnFragmentInteractionListener {

    Menu navMenu;
    FragmentManager fragmentManager;
    Fragment content;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        content = new EventFragment();
        ft.replace(R.id.user_container,content);
        ft.disallowAddToBackStack();
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        NavigationView nv = (NavigationView) drawer.findViewById(R.id.nav_view);
        navMenu = nv.getMenu();
        setNavMenu();
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    public void setNavMenu()
    {
        MenuItem m1 = navMenu.getItem(1);
        MenuItem m2 = navMenu.getItem(2);
        MenuItem m3 = navMenu.getItem(3);
        MenuItem m4 = navMenu.getItem(4);
        SharedPreferences prefs= getSharedPreferences("com.beproject.lams", Context.MODE_PRIVATE);
        int UserType = prefs.getInt("UserType",-1);
        switch(UserType){
            default:
            case -1: m1.setTitle("Error").setIcon(R.drawable.ic_warning);
                m2.setTitle("Error").setIcon(R.drawable.ic_warning);
                m3.setTitle("Error").setIcon(R.drawable.ic_warning);
                m4.setTitle("Error").setIcon(R.drawable.ic_warning);
                navMenu.removeGroup(R.id.adminRights);
                break;
            case 1: navMenu.removeGroup(R.id.adminRights);
            case 0: m1.setTitle(R.string.new_lec).setIcon(R.drawable.ic_new_lec);
                m2.setTitle(R.string.attd_rep_std).setIcon(R.drawable.ic_action_name2);
                m3.setTitle(R.string.attd_rep_sub).setIcon(R.drawable.ic_action_name2);
                m4.setTitle(R.string.mentor_stud).setIcon(R.drawable.ic_action_name3);
                break;
        }

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.exit)
                    .setMessage(R.string.exit_msg)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setNegativeButton("No",null)
                    .show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentTransaction ft = fragmentManager.beginTransaction();
        if(item.getTitle().equals("Error")){
            content = new ErrorFragment();
        }
        else {
            if (id == R.id.nav_home) {
                content = new EventFragment();
            } else if (id == R.id.nav_first) {
                content = new ErrorFragment();
            } else if (id == R.id.nav_second) {
                content = new ErrorFragment();
            } else if (id == R.id.nav_third) {
                content = new ErrorFragment();
            } else if (id == R.id.nav_fourth) {
                content = new ErrorFragment();
            } else if (id == R.id.nav_share) {
                content = new ErrorFragment();
            } else if (id == R.id.nav_send) {
                content = new ErrorFragment();
            }
        }
        ft.replace(R.id.user_container,content);
        ft.disallowAddToBackStack();
        ft.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(DummyContent.DummyItem item) {
        return;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        return;
    }
}
