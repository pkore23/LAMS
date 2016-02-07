package com.beproject.lams;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment implements View.OnClickListener {
    View rootView;
    Button b;
    EditText p,u;
    Intent i;
    private String DUMMY_CREDENTIALS[] = {"admin:admin:0","staff:staff:1"};
    private int loginAttempt;
    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginAttempt = 0;
        p = (EditText) rootView.findViewById(R.id.passd);
        u = (EditText) rootView.findViewById(R.id.usrid);
        b = (Button) rootView.findViewById(R.id.btnlogin);
        b.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View v) {
        if(attemptLogin()) {
            startActivity(i);
        }
        else if(loginAttempt<3){
            Snackbar.make(v,"Login attemt faild: Incorrect credentials.",Snackbar.LENGTH_SHORT).show();
            ++loginAttempt;
            p.setText("");
            u.setText("");
            u.requestFocus();
        }
        else {
            Toast.makeText(getContext(),"Login Attempt exceeded.",Toast.LENGTH_LONG).show();
            getActivity().finish();
            System.exit(0);
        }
    }

    private boolean attemptLogin() {
        for(String credentials:DUMMY_CREDENTIALS){
            String cr[] = credentials.split(":");
            if((u.getText().toString()).equals(cr[0])){
                try {
                    switch (Integer.parseInt(cr[2])) {
                        case 0:
                            i = new Intent(getContext(), OptionsAdmin.class);
                            break;
                        case 1:
                            i = new Intent(getContext(), OptionsActivity.class);
                            break;
                    }
                }
                catch(NumberFormatException ne){
                    Log.e("LOGINACTIviTY_NUMtype", "Failed to launch activity: numbr format exception");
                }
                return p.getText().toString().equals(cr[1]);
            }
        }
        return false;
    }
}
