//监听当前程序在前台还是后台的方法
    private void initActivityLifeCycleCallbacks(){
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityStopped(Activity activity) {
                activityCount--;
                if (activityCount == 0) {
                    LogUtils.e("viclee", ">>>>>>>>>>>>>>>>>>>切到后台  lifecycle");
                    uploadKeyAppClose();
                }
            }

            @Override
            public void onActivityStarted(Activity activity) {
                if (activityCount == 0) {
                    LogUtils.e("viclee", ">>>>>>>>>>>>>>>>>>>切到前台  lifecycle");
                    uploadKeyAppOpen();
                }
                activityCount++;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }
        });
    }