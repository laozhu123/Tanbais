package xiaogao.zjut.tabbaishuo.injecter.module;

import android.support.v4.app.Fragment;

import dagger.Module;
import dagger.Provides;
import xgn.com.basesdk.base.injector.ContextLife;
import xgn.com.basesdk.commonui.injector.FragmentScope;

/**
 * Created by huluzi on 2017/8/14.
 */

@Module
public class FragmentModule {

    private Fragment mContext;

    public FragmentModule(Fragment pContext) {
        mContext = pContext;
    }

    @Provides
    @FragmentScope
    @ContextLife("Fragment")
    public Fragment provideContext() {
        return mContext;
    }



}