package com.beproject.lams;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class OptionsActivity extends AppCompatActivity implements DialogInterface.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
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
    public void onClick(DialogInterface dialog, int which) {
        if(which==DialogInterface.BUTTON_POSITIVE){
            this.finish();
            System.exit(0);
        }
    }
}
