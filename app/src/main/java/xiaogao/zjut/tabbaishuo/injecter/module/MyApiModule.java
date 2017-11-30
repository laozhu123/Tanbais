package xiaogao.zjut.tabbaishuo.injecter.module;


import dagger.Module;
import dagger.Provides;
import xgn.com.basesdk.network.XGRest;
import xiaogao.zjut.tabbaishuo.injecter.XGHostAppScope;
import xiaogao.zjut.tabbaishuo.net.RetrofitApi;

/**
 * Created by huluzi on 2017/8/10.
 */

@Module
public class MyApiModule {
    @Provides
    @XGHostAppScope
    RetrofitApi provideRetrofit(XGRest xgRest) {
        return xgRest.create(RetrofitApi.class);
    }
}
