package com.test.nutshell.logintest.injection.component;

import android.app.Application;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Component;
import com.test.nutshell.logintest.data.DataManager;
import com.test.nutshell.logintest.injection.ApplicationContext;
import com.test.nutshell.logintest.injection.module.ApplicationModule;
import com.test.nutshell.logintest.injection.module.ServiceModule;

@Singleton
@Component(modules = {ApplicationModule.class, ServiceModule.class})
public interface ApplicationComponent {
    Context context();

    Application application();

    DataManager dataManager();
}
