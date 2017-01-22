package com.tutorials.hp.sqliterushorm;

import android.app.Application;

import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.RushCore;

/**
 * Created by Oclemy on 12/8/2016 for ProgrammingWizards Channel and http://www.camposha.com.
 */
public class MainApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        AndroidInitializeConfig config=new AndroidInitializeConfig(getApplicationContext());
        RushCore.initialize(config);
    }
}
