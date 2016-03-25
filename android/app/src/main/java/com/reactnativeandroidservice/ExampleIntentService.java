package com.reactnativeandroidservice;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;


import com.facebook.react.LifecycleState;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.ReactContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.facebook.react.shell.MainReactPackage;
import com.facebook.soloader.SoLoader;

public class ExampleIntentService extends IntentService {
    public final static String SOMETHING = "something";
    private ReactInstanceManager mReactInstanceManager;
    private ReactContext mContext;
    private WritableMap mParams;

    public ExampleIntentService() {
        super("ExampleIntentService");
        Log.d("test", "ExampleIntentService enter");
        mReactInstanceManager = ReactInstanceManager.builder()
                .setApplication(App.application)
                .setBundleAssetName("index.android.bundle")
                .setJSMainModuleName("index.android")
                .addPackage(new MainReactPackage())
                .setUseDeveloperSupport(BuildConfig.DEBUG)
                .setInitialLifecycleState(LifecycleState.BEFORE_RESUME)
                .build();
        SoLoader.loadLibrary("reactnativejni");
        mReactInstanceManager.createReactContextInBackground();
        mReactInstanceManager.addReactInstanceEventListener(new ReactInstanceManager.ReactInstanceEventListener() {
            @Override
            public void onReactContextInitialized(ReactContext context) {
                Log.d("test", "react context initialized");
                mContext = context;
                if (mParams != null) {
                    Log.d("test", "onHandleIntent: send event to JS");
                    mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                            .emit("FromService", mParams);
                    mParams = null;
                }
            }
        });
        Log.d("test", "ExampleIntentService exit");
    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        String something = null;
        if (workIntent.hasExtra(SOMETHING)) {
            something = workIntent.getStringExtra(SOMETHING);
        }
        Log.d("test", "onHandleIntent:" + something);
        WritableMap params = Arguments.createMap();
        params.putString(SOMETHING, something + " and message from service to JS");
        if (mContext != null) {
            Log.d("test", "onHandleIntent: send event to JS");
            mContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class)
                    .emit("FromService", params);
        }
        else {
            Log.d("test", "react context not initialized, save it.");
            mParams = params;
        }
    }
}

