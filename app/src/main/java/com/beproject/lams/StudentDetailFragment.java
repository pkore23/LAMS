package com.beproject.lams;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.beproject.lams.dummy.StudentListContent;
import com.beproject.lams.service.GetReportStudent;

import java.util.concurrent.ExecutionException;

/**
 * A fragment representing a single Student detail screen.
 * This fragment is either contained in a {@link StudentListActivity}
 * in two-pane mode (on tablets) or a {@link StudentDetailActivity}
 * on handsets.
 */
public class StudentDetailFragment extends Fragment {
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
     * fragment (e.g. upon screen orientation changes).
     */
    public StudentDetailFragment() {
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
        }

        return rootView;
    }
}
