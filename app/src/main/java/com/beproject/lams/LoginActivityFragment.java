package com.beproject.lams;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment implements View.OnClickListener {
    View rootView;
    Button b;
    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        b = (Button) rootView.findViewById(R.id.btnlogin);
        b.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        Intent i = new Intent(getContext(),OptionsActivity.class);
        startActivity(i);
    }
}
