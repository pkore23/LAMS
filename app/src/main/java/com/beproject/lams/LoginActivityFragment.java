package com.beproject.lams;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v7.widget.AppCompatCheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.beproject.lams.service.MyHttpClient;
import com.beproject.lams.service.UserType;

import java.util.concurrent.ExecutionException;

/**
 * A placeholder fragment containing a simple view.
 */
public class LoginActivityFragment extends Fragment implements View.OnClickListener {
    View rootView;
    Button b;
    EditText p,u,ip;
    AppCompatCheckBox cb;
    Intent i;
    String mResponse;
    public ProgressBar mpb;
    private String DUMMY_CREDENTIALS[] = {"admin:admin:0","staff:staff:1"};
    private int loginAttempt;
    public LoginActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_login, container, false);
        loginAttempt = 0;
        SharedPreferences sp = getContext().getSharedPreferences(Constants.PREFERENCE,Context.MODE_PRIVATE);
        p = (EditText) rootView.findViewById(R.id.passd);
        u = (EditText) rootView.findViewById(R.id.usrid);
        ip = (EditText) rootView.findViewById(R.id.ip);
        ip.setText(Constants.ip);
        b = (Button) rootView.findViewById(R.id.btnlogin);
        cb = (AppCompatCheckBox) rootView.findViewById(R.id.cbPerLog);
        b.setOnClickListener(this);
        mpb = new ProgressBar(getContext(),null, android.R.style.Widget_ProgressBar_Inverse);
        mpb.setIndeterminate(true);
        if(sp.getBoolean("perssistlog",false)){
            u.setText(sp.getString("userid",""));
            p.setText(sp.getString("password",""));
            Constants.USERID = u.getText().toString();
            Constants.PASSWD = p.getText().toString();
            cb.setChecked(true);
            b.callOnClick();
        }
        return rootView;
    }

    @Override
    public void onClick(View v) {
        mpb.setVisibility(View.VISIBLE);
        i = new Intent(getContext(),UserActivity.class);
        Constants.ip = ip.getText().toString();
        if(attemptLogin()) {
            mpb.setVisibility(View.GONE);
            startActivity(i);
            if(cb.isChecked()){
                SharedPreferences sp = getContext().getSharedPreferences(Constants.PREFERENCE,Context.MODE_PRIVATE);
                sp.edit().putString("userid",u.getText().toString())
                        .putString("password",p.getText().toString())
                        .putBoolean("perssistlog",true)
                        .apply();
            }
            try{
                UserType ut = new UserType(getContext());
                ut.execute();
                Constants.USERTYPE = ut.get();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        else if(loginAttempt<3){
            Snackbar.make(v,"Login attemt faild: Incorrect credentials.",Snackbar.LENGTH_SHORT).show();
            ++loginAttempt;
            p.setText("");
            u.setText("");
            u.requestFocus();
            mpb.setVisibility(View.GONE);
        }
        else {
            mpb.setVisibility(View.GONE);
            Toast.makeText(getContext(),"Login Attempt exceeded.",Toast.LENGTH_LONG).show();
            getActivity().finish();
            System.exit(0);
        }
    }

    private boolean attemptLogin() {
        MyHttpClient Login = new MyHttpClient(getContext());
        Constants.USERID = u.getText().toString();
        Constants.PASSWD = p.getText().toString();
        Login.execute(u.getText().toString(),p.getText().toString());
        try{
            mResponse = Login.get();
            Log.d("LoginFragent","Response:"+mResponse);
        } catch (InterruptedException e) {
            mResponse = "-1";
            e.printStackTrace();
        } catch (ExecutionException e) {
            mResponse = "-1";
            e.printStackTrace();
        }

        if(mResponse.contains("0")){
            return true;
        }
        else if (mResponse.contains("1")){
            return false;
        }
        else if(mResponse.contains("2")){
            return false;
        }
        else{
            Toast.makeText(getContext(),"Error communicating with server Or Malformed response.",Toast.LENGTH_SHORT).show();
            return false;
        }
    }
}
