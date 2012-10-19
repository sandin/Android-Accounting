package com.lds.accounting;

import android.app.Application;

public class AccountApp extends Application {
    

    @Override
    public void onCreate() {
        super.onCreate();
        
        UserManger.getInstance(this); // Init
    }
}
