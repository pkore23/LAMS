package com.beproject.lams;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class OptionsActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    public int userType;
    SharedPreferences prefs;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        prefs=getPreferences(Context.MODE_PRIVATE);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FragmentManager fmanager = getSupportFragmentManager();
        FragmentTransaction ftransac = fmanager.beginTransaction();
        Fragment frag= new OptionsActivityFragment();
        ftransac.add(R.id.fragment,frag);
        ftransac.commit();
    }


    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        outState.putInt("UserType",userType);
    }



    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit?")
                .setMessage("Are you sure to exit?")
                .setCancelable(false)
                .setNegativeButton("No",this)
                .setPositiveButton("Yes",this)
                .show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("UserType",userType);
    }

    @Override
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){
            SharedPreferences.Editor e1 = prefs.edit();
            e1.remove("UserType");
            e1.clear();
            e1.apply();
            e1.commit();
            this.finish();
            System.gc();
            System.exit(0);
        }
    }


}
