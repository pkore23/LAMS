package com.beproject.lams;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        Intent pI = getIntent();
        String classn = pI.getStringExtra("parent_act");
        Intent i = null;
        try {
            i =new Intent(this,Class.forName(classn));
        } catch (ClassNotFoundException e) {
            Log.e("ParentSett","Unable to set parent activity");
            e.printStackTrace();
            return super.getParentActivityIntent();
        }
        return i;
    }
}
