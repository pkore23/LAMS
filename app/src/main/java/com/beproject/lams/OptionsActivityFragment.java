package com.beproject.lams;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A placeholder fragment containing a simple view.
 */
public class OptionsActivityFragment extends Fragment {

    public OptionsActivityFragment() {
    }
    View rootView;
    TextView tv,rep;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_options, container, false);
        tv = (TextView) rootView.findViewById(R.id.acc_sett);
        rep = (TextView) rootView.findViewById(R.id.studRep);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ProfileActivity.class);
                i.putExtra("parent_act","OptionsActivity");
                startActivity(i);
            }
        });
        rep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(), StudentReport.class);
                startActivity(i);
            }
        });
        return rootView;
    }
}
