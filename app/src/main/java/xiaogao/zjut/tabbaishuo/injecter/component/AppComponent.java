package xiaogao.zjut.tabbaishuo.injecter.component;

import android.content.Context;

import dagger.Component;
import xgn.com.basesdk.base.component.CoreAppComponent;
import xgn.com.basesdk.base.injector.ContextLife;
import xiaogao.zjut.tabbaishuo.app.MyApplication;
import xiaogao.zjut.tabbaishuo.injecter.AppModule;
import xiaogao.zjut.tabbaishuo.injecter.XGHostAppScope;
import xiaogao.zjut.tabbaishuo.injecter.module.MyApiModule;
import xiaogao.zjut.tabbaishuo.main.MainActivity;
import xiaogao.zjut.tabbaishuo.net.RetrofitApi;

/**
 * Created by Administrator on 2017/8/9.
 */

@XGHostAppScope
@Component(dependencies = {CoreAppComponent.class}, modules = {MyApiModule.class, AppModule.class})
public interface AppComponent extends CoreAppComponent {

    @ContextLife("Application")
    Context getAppContext();

    RetrofitApi getRetrofitApi();

    void inject(MyApplication myApplication);

}
