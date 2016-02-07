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
public class OptionsAdminFragment extends Fragment {

    public OptionsAdminFragment() {
    }
    View rootView;
    TextView tv;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_options_admin, container, false);
        tv = (TextView) rootView.findViewById(R.id.acc_sett);
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getContext(),ProfileActivity.class);
                i.putExtra("parent_act","OptionsAdmin");
                startActivity(i);
            }
        });
        return rootView;
    }
}
