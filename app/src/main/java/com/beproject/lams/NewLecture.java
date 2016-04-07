package com.beproject.lams;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Spinner;

import com.beproject.lams.data.LamsDataContract;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NewLecture.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NewLecture#newInstance} factory method to
 * create an instance of this fragment.
 */
public class
NewLecture extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private View rootView;
    private OnFragmentInteractionListener mListener;
    private SimpleCursorAdapter mAdapter;
    ProgressBar pb;
    private Button newLec;

    public NewLecture() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NewLecture.
     */
    public static NewLecture newInstance() {
        NewLecture fragment = new NewLecture();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        getLoaderManager().initLoader(Constants.LOADERLECID,null,this);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_new_lecture, container, false);
        final Spinner sp = (Spinner) rootView.findViewById(R.id.spn_lec_id);
        /*Cursor c = getContext().getContentResolver().query(LamsDataContract.Subject.CONTENT_URI,
                new String[] {LamsDataContract.Subject._ID,LamsDataContract.Subject.COLUMN_SUB_ID},
                null,
                null,
                LamsDataContract.Subject.COLUMN_SUB_ID+" asc");
        mAdapter = new SimpleCursorAdapter(getContext(),android.R.layout.simple_spinner_item, c, new String[] {LamsDataContract.Subject.COLUMN_SUB_ID}, new int[] {android.R.id.text1}, 0);
        mAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        sp.setAdapter(mAdapter);
        mAdapter.swapCursor(c);
        mAdapter.notifyDataSetChanged();*/
        final Spinner classT = (Spinner) rootView.findViewById(R.id.spn_class);
        final Spinner lecT = (Spinner) rootView.findViewById(R.id.spn_type);
        final Spinner dept = (Spinner) rootView.findViewById(R.id.spn_dept);
        Button b = (Button) rootView.findViewById(R.id.btn_new_lec);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getContext(),AttendanceActivity.class);
                Constants.lec_type = lecT.getSelectedItem().toString();
                Constants.dept = dept.getSelectedItem().toString();
                Constants.Class_t = classT.getSelectedItem().toString();
                Constants.sub = sp.getSelectedItem().toString();
                startActivity(i);
            }
        });
        return rootView;
    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        pb = new ProgressBar(getContext(),null,android.R.style.Widget_Material_ProgressBar);
        pb.setIndeterminate(true);
        pb.setVisibility(View.VISIBLE);
        String column[] = {LamsDataContract.Subject._ID,LamsDataContract.Subject.COLUMN_SUB_ID};
        return new CursorLoader(getContext(),
                LamsDataContract.Subject.CONTENT_URI,
                column,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
//        mAdapter.swapCursor(data);
        pb.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    mAdapter.swapCursor(null);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
