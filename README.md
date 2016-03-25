# user-react-native-in-android-service

Android only. 

To build, use "react-native run-android"

To check the result: "adb logcat -s ReactNativeJS" 

You will see: "W/ReactNativeJS(11490): message from activity to service and message from service to JS"

The key to show is we are able to make out of band JS call without Activity context by manually initializing the bridge and send DeviceEvent to JS. This techniques is very useful for android service, notification, broadcast receiver etc.

Code snapshot: 

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
