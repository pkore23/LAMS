package com.beproject.lams;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;


import com.beproject.lams.data.LamsDataContract;
import com.beproject.lams.dummy.EventContent;
import com.beproject.lams.dummy.EventContent.EventItem;
import com.beproject.lams.dummy.EventContent;

import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class EventFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ProgressBar pb;
    private OnListFragmentInteractionListener mListener;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public EventFragment() {
    }

    public static EventFragment newInstance(int columnCount) {
        EventFragment fragment = new EventFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        getLoaderManager().initLoader(Constants.LOADEREVENT, null, this);
        super.onActivityCreated(savedInstanceState);
    }

    EventContent ec;
    MyEventRecyclerViewAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_event_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            ec = new EventContent(null);
            mAdapter = new MyEventRecyclerViewAdapter(ec.ITEMS, mListener);
            recyclerView.addItemDecoration(new DividerItemDecoration(getContext(),LinearLayoutManager.VERTICAL));
            recyclerView.setAdapter(mAdapter);
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        pb =new ProgressBar(getContext(),null,android.R.style.Widget_Material_ProgressBar);
        pb.setIndeterminate(true);

        //pb.setForegroundGravity(View.TEXT_ALIGNMENT_CENTER);
        pb.setVisibility(View.VISIBLE);
        String[] mColumns = {LamsDataContract.Event.COLUMN_EVENT_TYPE, LamsDataContract.Event.COLUMN_EVENT_TOPIC, LamsDataContract.Event.COLUMN_EVENT_STAFF_GEN, LamsDataContract.Event.COLUMN_EVENT_DATE};
        String selection = LamsDataContract.Event.COLUMN_EVENT_DATE+">?";
        String selectionArgs [] = new String[]{"date(now)"};
        return new CursorLoader(getActivity(),
                LamsDataContract.Event.CONTENT_URI,
                mColumns,
                selection,
                selectionArgs,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        ec = new EventContent(data);
        mAdapter.swapItem(ec.ITEMS);
        pb.setVisibility(View.GONE);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        ec = new EventContent(null);
        mAdapter.swapItem(ec.ITEMS);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */


    public interface OnListFragmentInteractionListener {
        //ToDo:Implement DetailView
        void onListFragmentInteraction(EventContent.EventItem item);
    }

}
