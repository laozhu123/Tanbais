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
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivitySetNickname;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivitySetOccuption;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivityShowPicture;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivityXiangCe;
import xiaogao.zjut.tabbaishuo.main.activity.login.ActivityLogin;
import xiaogao.zjut.tabbaishuo.main.activity.login.ActivityRegisterFirst;
import xiaogao.zjut.tabbaishuo.main.activity.login.ActivityRegisterSecond;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityBaseInfo;
import xiaogao.zjut.tabbaishuo.main.activity.common.ActivityJuBao;
import xiaogao.zjut.tabbaishuo.main.activity.ActivitySplash;
import xiaogao.zjut.tabbaishuo.main.activity.my.ActivityZobz;
import xiaogao.zjut.tabbaishuo.main.activity.setting.ActivitySuggestionResponse;
import xiaogao.zjut.tabbaishuo.main.activity.setting.ActivityUserBook;

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

    void inject(ActivityZobz activityZobz);

    void inject(ActivityBaseInfo activityBaseInfo);

    void inject(ActivityXiangCe activityXiangCe);

    void inject(ActivityShowPicture activityShowPicture);

    void inject(ActivitySuggestionResponse activitySuggestionResponse);

    void inject(ActivitySetNickname activitySetNickname);

    void inject(ActivitySetOccuption activitySetOccuption);

    void inject(ActivityUserBook activityUserBook);

    void inject(ActivityRegisterFirst activityRegisterFirst);

    void inject(ActivityRegisterSecond activityRegisterSecond);

    void inject(ActivityLogin activityLogin);
}
