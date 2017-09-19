package com.test.nutshell.logintest.injection.component;

import dagger.Subcomponent;
import com.test.nutshell.logintest.injection.PerActivity;
import com.test.nutshell.logintest.injection.module.ActivityModule;
import com.test.nutshell.logintest.ui.main.MainActivity;

/**
 * This component inject dependencies to all Activities across the application
 */
@PerActivity
@Subcomponent(modules = ActivityModule.class)
public interface ActivityComponent {

    void inject(MainActivity mainActivity);

}
