package com.beproject.lams;

import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CheckedTextView;

import com.beproject.lams.AttendanceFragment.OnListFragmentInteractionListener;
import com.beproject.lams.dummy.AttendanceContent;
import com.beproject.lams.dummy.AttendanceContent.AttendanceItem;

import java.util.List;

/**
 * {@link RecyclerView.Adapter} that can display a {@link AttendanceItem} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyAttendanceRecyclerViewAdapter extends RecyclerView.Adapter<MyAttendanceRecyclerViewAdapter.ViewHolder> {

    private List<AttendanceItem> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyAttendanceRecyclerViewAdapter(List<AttendanceContent.AttendanceItem> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_attendance, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.mIdView.setText(mValues.get(position).content);

        holder.mIdView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public void swapItem(List<AttendanceItem> items) {
        mValues = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final AppCompatCheckBox mIdView;
        public AttendanceItem mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = (AppCompatCheckBox) view.findViewById(R.id.chk_id);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mIdView.getText() + "'";
        }
    }
}
