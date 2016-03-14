package com.beproject.lams;

/**
 * Created by Pradnesh Kore on 14-03-2016.
 */
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.beproject.lams.data.LamsDataContract;

import java.util.List;

/**
 * Created by skyfishjy on 10/31/14.
 */
public class MyListCursorAdapter extends CursorRecyclerViewAdapter<MyListCursorAdapter.ViewHolder>{

    public MyListCursorAdapter(Context context,Cursor cursor){
        super(context,cursor);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView tvId;
        public TextView tvContent;

        public ViewHolder(View view) {
            super(view);
            tvId =(TextView) view.findViewById(R.id.id);
            tvContent =(TextView) view.findViewById(R.id.content);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event, parent, false);
        ViewHolder vh = new ViewHolder(itemView);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, Cursor cursor) {
        viewHolder.tvId.setText(String.valueOf(cursor.getPosition()));
        viewHolder.tvContent.setText(cursor.getString(cursor.getColumnIndex(LamsDataContract.Event.COLUMN_EVENT_HEADER)));
    }
}