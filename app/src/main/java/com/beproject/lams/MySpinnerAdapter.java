package com.beproject.lams;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;

/**
 * Created by Pradnesh Kore on 02-04-2016.
 */
public class MySpinnerAdapter extends CursorAdapter {

    public MySpinnerAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {

        return null;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

    }
}
