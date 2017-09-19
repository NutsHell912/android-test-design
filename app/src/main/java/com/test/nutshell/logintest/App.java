package com.test.nutshell.logintest;

import android.app.Application;
import android.content.Context;

import com.test.nutshell.logintest.injection.component.ApplicationComponent;
import com.test.nutshell.logintest.injection.component.DaggerApplicationComponent;
import com.test.nutshell.logintest.injection.module.ApplicationModule;

public class App extends Application {

    ApplicationComponent mApplicationComponent;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static App get(Context context) {
        return (App) context.getApplicationContext();
    }

    public ApplicationComponent getComponent() {
        if (mApplicationComponent == null) {
            mApplicationComponent = DaggerApplicationComponent.builder()
                    .applicationModule(new ApplicationModule(this))
                    .build();
        }
        return mApplicationComponent;
    }

    // Needed to replace the component with a test specific one
    public void setComponent(ApplicationComponent applicationComponent) {
        mApplicationComponent = applicationComponent;
    }
}
