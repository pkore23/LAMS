package com.beproject.lams;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.beproject.lams.dummy.DummyContent;

public class StudentReport extends AppCompatActivity implements ItemFragment.OnListFragmentInteractionListener {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_report);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onListFragmentInteraction(String item) {
        Toast.makeText(this,"Clicked on list",Toast.LENGTH_SHORT).show();
    }
}
