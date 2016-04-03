package com.beproject.lams.sync;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class LamsAuthenticatorService extends Service {
    // Instance field that stores the authenticator object
    private LamsAuthenticator mAuthenticator;

    @Override
    public void onCreate() {
        // Create a new authenticator object
        mAuthenticator = new LamsAuthenticator(this);
    }

    /*
     * When the system binds to this Service to make the RPC call
     * return the authenticator's IBinder.
     */
    @Override
    public IBinder onBind(Intent intent) {
        return mAuthenticator.getIBinder();
    }
}
