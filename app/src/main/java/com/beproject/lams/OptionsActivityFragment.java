package com.beproject.lams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.prefs.Preferences;

/**
 * A placeholder fragment containing a simple view.
 */
public class OptionsActivityFragment extends Fragment {

    public OptionsActivityFragment() {
    }
    View rootView;
    TextView tv,rep;
    int userType;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences prefs = getActivity().getSharedPreferences("com.beproject.lams",Context.MODE_PRIVATE);
        userType = prefs.getInt("UserType",-1);
        switch(userType){
            case -1:rootView = inflater.inflate(R.layout.fragment_error, container, false);
                break;
            case 0: rootView = inflater.inflate(R.layout.fragment_options_admin, container, false);
                break;
            case 1: rootView = inflater.inflate(R.layout.fragment_options, container, false);
                break;
        }
        tv = (TextView) rootView.findViewById(R.id.acc_sett);
//        rep = (TextView) rootView.findViewById(R.id.studRep);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ProfileActivity.class);
                i.putExtra("parent_act","OptionsActivity");
                startActivity(i);
            }
        });
        /*rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), StudentListActivity.class);
                startActivity(i);
            }
        });*/
        return rootView;
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        Log.v("OpionsActvityFragment","In onSavedInstanceState()");
        outState.putInt("UserType", userType);
        super.onSaveInstanceState(outState);
    }
}
