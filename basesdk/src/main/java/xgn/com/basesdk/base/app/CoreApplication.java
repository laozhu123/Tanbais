package xgn.com.basesdk.base.app;

import android.app.Application;

import xgn.com.basesdk.base.component.CoreAppComponent;
import xgn.com.basesdk.base.component.DaggerCoreAppComponent;

/**
 * Created by huluzi on 2017/7/13.
 */

public class CoreApplication extends Application {

    private static CoreApplication sInstance;
    protected CoreAppComponent mCoreComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;

        initCoreGraph();
    }

    public void initCoreGraph() {
        mCoreComponent = DaggerCoreAppComponent.builder()
                .build();
    }

    public static Application getInstance() {
        return sInstance;
    }

    public CoreAppComponent getmCoreComponent() {
        return mCoreComponent;
    }

    public void setmCoreComponent(CoreAppComponent mCoreComponent) {
        this.mCoreComponent = mCoreComponent;
    }
}