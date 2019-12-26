package com.avaal.com.afm2020autoCx;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.hardware.fingerprint.FingerprintManagerCompat;


@RequiresApi(api = Build.VERSION_CODES.M)
public class FingerprintHelper extends FingerprintManagerCompat.AuthenticationCallback {

    private FingerprintHelperListener listener;

    public FingerprintHelper(FingerprintHelperListener listener) {
        this.listener = listener;
    }

    public void startAuth(FingerprintManager fingerprintManager, Object o) {
    }

    public void cancel() {
    }

    //interface for the listner
    interface FingerprintHelperListener {
        void authenticationFailed(String error);
        void authenticationSuccess(FingerprintManager.AuthenticationResult result);
    }

}