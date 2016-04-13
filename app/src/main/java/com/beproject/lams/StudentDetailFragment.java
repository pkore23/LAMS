package com.beproject.lams;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.beproject.lams.data.LamsDataContract;
import com.beproject.lams.dummy.StudentListContent;
import com.beproject.lams.service.GetReportStudent;

import java.util.concurrent.ExecutionException;

/**
 * A fragment representing a single Student detail screen.
 * This fragment is either contained in a {@link StudentListActivity}
 * in two-pane mode (on tablets) or a {@link StudentDetailActivity}
 * on handsets.
 */
public class StudentDetailFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private StudentListContent.StudentListItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes)
     */
    Cursor c;
    public StudentDetailFragment() {
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(Constants.LOADERCONTACT,null, this);
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = Constants.ITMESSTUDENTMAP.get(getArguments().getString(ARG_ITEM_ID));

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.student_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            GetReportStudent gr = new GetReportStudent(getContext());
            gr.execute(mItem.id);
            try {
                String data=gr.get();
                Log.e("report text",data);
                String subs[]=data.split("@@");
                String hmi[]=subs[0].split("::");
                String pds[]=subs[1].split("::");
                String dwm[]=subs[2].split("::");
                String df[]=subs[3].split("::");
                String ccl[]=subs[4].split("::");
                TextView tv;
                //hmi
                tv = (TextView) rootView.findViewById(R.id.hmi_mnth_lec);
                tv.setText(hmi[0]);
                tv = (TextView) rootView.findViewById(R.id.hmi_ovr_lec);
                tv.setText(hmi[1]);
                tv = (TextView) rootView.findViewById(R.id.hmi_mnth_prac);
                tv.setText(hmi[2]);
                tv = (TextView) rootView.findViewById(R.id.hmi_ovr_prac);
                tv.setText(hmi[3]);
                //pds
                tv = (TextView) rootView.findViewById(R.id.pds_mnth_lec);
                tv.setText(pds[0]);
                tv = (TextView) rootView.findViewById(R.id.pds_ovr_lec);
                tv.setText(pds[1]);
                tv = (TextView) rootView.findViewById(R.id.pds_mnth_prac);
                tv.setText(pds[2]);
                tv = (TextView) rootView.findViewById(R.id.pds_mnth_lec);
                tv.setText(pds[3]);
                //df
                tv = (TextView) rootView.findViewById(R.id.df_mnth_lec);
                tv.setText(df[0]);
                tv = (TextView) rootView.findViewById(R.id.df_mnth_lec);
                tv.setText(df[1]);
                tv = (TextView) rootView.findViewById(R.id.df_mnth_prac);
                tv.setText(df[2]);
                tv = (TextView) rootView.findViewById(R.id.df_ovr_prac);
                tv.setText(df[3]);
                //ccl
                tv = (TextView) rootView.findViewById(R.id.ccl_mnth_lec);
                tv.setText(ccl[0]);
                tv = (TextView) rootView.findViewById(R.id.ccl_ovr_lec);
                tv.setText(ccl[1]);
                tv = (TextView) rootView.findViewById(R.id.ccl_mnth_prac);
                tv.setText(ccl[2]);
                tv = (TextView) rootView.findViewById(R.id.ccl_ovr_prac);
                tv.setText(ccl[3]);
                //dwm
                tv = (TextView) rootView.findViewById(R.id.dwm_mnth_lec);
                tv.setText(dwm[0]);
                tv = (TextView) rootView.findViewById(R.id.dwm_ovr_lec);
                tv.setText(dwm[1]);
                tv = (TextView) rootView.findViewById(R.id.dwm_mnth_prac);
                tv.setText(dwm[2]);
                tv = (TextView) rootView.findViewById(R.id.dwm_ovr_prac);
                tv.setText(dwm[3]);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            catch(ArrayIndexOutOfBoundsException e){
                e.printStackTrace();
                Toast.makeText(getContext(),"Unable to load data",Toast.LENGTH_SHORT).show();
                getActivity().finish();
//                getActivity().onBackPressed();
            }
            String[] cols = new String[]{LamsDataContract.Student.COLUMN_CONTACT, LamsDataContract.Student.COLUMN_PCONTACT};
            String selection = LamsDataContract.Student.COLUMN_ENROLL_ID+"=?";
            String Sargs[]=new String[]{mItem.id};
            c= getContext().getContentResolver().query(LamsDataContract.Student.CONTENT_URI,
                    cols,selection,Sargs,null);
            Button b1 = (Button) rootView.findViewById(R.id.call_student);
            b1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String contactNo="tel:"+c.getString(0);
                    Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(contactNo));
                }
            });
            Button b2 = (Button) rootView.findViewById(R.id.call_parent);
            b2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String contactNo="tel:"+c.getString(1);
                    Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse(contactNo));
                }
            });
        }

        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        String[] cols = new String[]{LamsDataContract.Student.COLUMN_CONTACT, LamsDataContract.Student.COLUMN_PCONTACT};
        String selection = LamsDataContract.Student.COLUMN_ENROLL_ID+"=?";
        String Sargs[]=new String[]{mItem.id};
        return new CursorLoader(getActivity(),
                LamsDataContract.Student.CONTENT_URI,
                cols,
                selection,
                Sargs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        c=data;
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }
}
