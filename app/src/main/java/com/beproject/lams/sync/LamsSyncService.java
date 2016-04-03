package com.beproject.lams.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

public class LamsSyncService extends Service {
    private static final Object sSyncAdapterLock = new Object();
    private static LamsSyncAdapter sLamsSyncAdapter = null;

    @Override
    public void onCreate() {
        Log.d("SunshineSyncService", "onCreate - SunshineSyncService");
        synchronized (sSyncAdapterLock) {
            if (sLamsSyncAdapter == null) {
                sLamsSyncAdapter = new LamsSyncAdapter(getApplicationContext(), true);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return sLamsSyncAdapter.getSyncAdapterBinder();
    }
}
