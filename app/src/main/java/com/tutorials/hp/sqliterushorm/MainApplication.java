package com.tutorials.hp.sqliterushorm;

import android.app.Application;
import co.uk.rushorm.android.AndroidInitializeConfig;
import co.uk.rushorm.core.RushCore;

/**
 * Created by Oclemy on for ProgrammingWizards Channel and http://www.camposha.info.
 - MainApplication class.
 - Extends android.app.Application.
 - This is a gloabl class available to all classes within your project.
 - We initialize our RushORM configuration here using the initialize() method.
 */
public class MainApplication extends Application {

    /*
    when our app is created.
     */
    @Override
    public void onCreate() {
        super.onCreate();

        AndroidInitializeConfig config=new AndroidInitializeConfig(getApplicationContext());
        RushCore.initialize(config);
    }
}
