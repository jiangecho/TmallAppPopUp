package com.echo.tmallapppopdialog;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by jiangecho on 16/3/27.
 */
public class App extends Application {
    private AppPopUpConfig appPopUpConfig;

    @Override
    public void onCreate() {
        super.onCreate();
        loadAppPopUpConfig();
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {
                showPopUp(activity);
            }

            @Override
            public void onActivityPaused(Activity activity) {
                dismissPopUp(activity);
            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private void dismissPopUp(Activity activity) {
        View view = activity.findViewById(R.id.penetrate_web_container);
        if (view == null) {
            return;
        }

        String className = activity.getClass().getName();
        for (AppPopUpConfig.ActivityPopupConfig config : appPopUpConfig.getConfigs()) {
            if (className.equals(config.getUri())) {
                config.setTimes(config.getTimes() - 1);
                break;
            }
        }
        ((ViewGroup) view.getParent()).removeView(view);
        ((PenetrateWebViewContainer) view).webView.destroy();
    }

    private void showPopUp(Activity activity) {
        String className = activity.getClass().getName();

        for (AppPopUpConfig.ActivityPopupConfig config : appPopUpConfig.getConfigs()) {
            if (className.equals(config.getUri())) {
                showPopUp(activity, config);
                break;
            }
        }
    }

    private void showPopUp(Activity activity, AppPopUpConfig.ActivityPopupConfig config) {

        View view = activity.findViewById(R.id.webViewContainer);
        if (view != null) {
            return;
        }

        if (!config.isAppear()) {
            long timestamp = System.currentTimeMillis();
            long startTime = config.getStartTime() * 100;
            long endTime = config.getEndTime() * 100;

            if (timestamp < startTime || timestamp > endTime) {
                return;
            }

            // TODO check times
            if (config.getTimes() == 0) {
                return;
            }
        }

        PenetrateWebViewContainer penetrateWebViewContainer = new PenetrateWebViewContainer(this);
        penetrateWebViewContainer.setId(R.id.penetrate_web_container);
        penetrateWebViewContainer.loadUrl(config.getUrl());
        penetrateWebViewContainer.setVisibility(View.INVISIBLE);
        penetrateWebViewContainer.setPenetrateAlpha((int) (config.getModalThreshold() * 255));
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activity.getWindow().addContentView(penetrateWebViewContainer, params);

    }

    // implement your own
    private void loadAppPopUpConfig() {
        try {
            appPopUpConfig = new Gson().fromJson(new FileReader("config.json"), AppPopUpConfig.class);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


}
