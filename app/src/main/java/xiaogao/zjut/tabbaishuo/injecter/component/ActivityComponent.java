package xiaogao.zjut.tabbaishuo.injecter.component;

/**
 * Created by huluzi on 2017/8/10.
 */

import android.content.Context;

import dagger.Component;
import xgn.com.basesdk.base.injector.ContextLife;
import xiaogao.zjut.tabbaishuo.injecter.ActivityScope;
import xiaogao.zjut.tabbaishuo.injecter.module.ActivityModule;
import xiaogao.zjut.tabbaishuo.main.MainActivity;
import xiaogao.zjut.tabbaishuo.main.activity.ActivityJuBao;
import xiaogao.zjut.tabbaishuo.main.activity.ActivitySplash;

@ActivityScope
@Component(
        dependencies = {AppComponent.class},
        modules = {ActivityModule.class})
public interface ActivityComponent extends AppComponent {

    @ContextLife("Activity")
    Context getContext();

    void inject(MainActivity mainActivity);

    void inject(ActivitySplash activitySplash);

    void inject(ActivityJuBao activityJuBao);

//    void inject(ActivityListPage activityListPage);
}
