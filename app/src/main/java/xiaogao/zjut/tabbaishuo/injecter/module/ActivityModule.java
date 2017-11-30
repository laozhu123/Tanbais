package xiaogao.zjut.tabbaishuo.injecter.module;

import android.content.Context;

import dagger.Module;
import dagger.Provides;
import xgn.com.basesdk.base.injector.ContextLife;
import xiaogao.zjut.tabbaishuo.injecter.ActivityScope;

/**
 * Created by huluzi on 2017/8/10.
 */

@Module
public class ActivityModule {
    private Context mContext;

    public ActivityModule(Context pContext) {
        mContext = pContext;
    }

    @Provides
    @ActivityScope
    @ContextLife("Activity")
    public Context provideContext() {
        return mContext;
    }

}
