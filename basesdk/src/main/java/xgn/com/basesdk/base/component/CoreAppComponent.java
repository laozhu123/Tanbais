package xgn.com.basesdk.base.component;

import javax.inject.Singleton;

import dagger.Component;
import xgn.com.basesdk.base.module.ApiModule;
import xgn.com.basesdk.network.XGRest;

/**
 * Created by huluzi on 2017/8/9.
 */

@Singleton
@Component(modules = {ApiModule.class})
public interface CoreAppComponent {
//    void inject(CoreApplication application);

    XGRest getXGRest();
}
