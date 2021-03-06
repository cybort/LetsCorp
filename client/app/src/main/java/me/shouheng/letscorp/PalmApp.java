package me.shouheng.letscorp;

import android.app.Activity;
import android.support.v4.app.Fragment;

import com.crashlytics.android.Crashlytics;
import com.facebook.stetho.Stetho;

import javax.inject.Inject;

import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import dagger.android.support.HasSupportFragmentInjector;
import io.fabric.sdk.android.Fabric;
import me.shouheng.commons.BaseApplication;
import me.shouheng.commons.util.ThemeUtils;
import me.shouheng.letscorp.common.PrefUtils;
import me.shouheng.letscorp.di.DaggerAppComponent;

/**
 * Created by WngShhng on 2018/6/21.*/
public class PalmApp extends BaseApplication implements HasActivityInjector, HasSupportFragmentInjector {

    @Inject
    DispatchingAndroidInjector<Activity> activityInjector;

    @Inject
    DispatchingAndroidInjector<Fragment> fragmentInjector;

    @Override
    public void onCreate() {
        super.onCreate();

        Fabric.with(this, new Crashlytics());

        /* enable stetho only in debug mode */
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this);
        }

        DaggerAppComponent.builder().application(this).build().inject(this);

        boolean isNight = PrefUtils.getInstance().isNightTheme();
        ThemeUtils.setUseThemeStatusBarColor(isNight);
        ThemeUtils.setUseStatusBarColor(isNight);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return activityInjector;
    }

    @Override
    public AndroidInjector<Fragment> supportFragmentInjector() {
        return fragmentInjector;
    }
}
