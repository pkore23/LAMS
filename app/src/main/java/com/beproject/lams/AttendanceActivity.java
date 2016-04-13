package com.beproject.lams;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.beproject.lams.dummy.AttendanceContent;
import com.beproject.lams.service.uploadAttendance;

import java.util.concurrent.ExecutionException;

public class AttendanceActivity extends AppCompatActivity implements AttendanceFragment.OnListFragmentInteractionListener {

    @SuppressWarnings("All")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Mark attendance");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_attendance,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id==R.id.action_submit){
            uploadAttendance ua = new uploadAttendance(this);
            ua.execute();
            try {
                ua.get();
                this.onBackPressed();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
        return true;
    }

    @Override
    public void onListFragmentInteraction(AttendanceContent.AttendanceItem item) {
        if(Constants.attd.contains(item.id)){
            Constants.attd.remove(item.id);
        }
        else{
            Constants.attd.add(item.id);
        }
    }
}
