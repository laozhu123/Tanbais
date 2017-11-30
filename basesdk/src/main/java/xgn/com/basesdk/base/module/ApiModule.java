package xgn.com.basesdk.base.module;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import xgn.com.basesdk.network.XGRest;

/**
 * Created by huluzi on 2017/8/9.
 */

@Module
public class ApiModule {
    public ApiModule() {
    }

    @Provides
    @Singleton
    XGRest provideXgRest() {
        return new XGRest();
    }
}
