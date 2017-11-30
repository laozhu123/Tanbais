package xiaogao.zjut.tabbaishuo.injecter.component;

import android.support.v4.app.Fragment;

import dagger.Component;
import xgn.com.basesdk.base.injector.ContextLife;
import xgn.com.basesdk.commonui.injector.FragmentScope;
import xiaogao.zjut.tabbaishuo.injecter.module.FragmentModule;
import xiaogao.zjut.tabbaishuo.main.fragmentTab.FragmentSecond;


@FragmentScope
@Component(
		dependencies = {ActivityComponent.class},
		modules = {FragmentModule.class}
)
public interface FragmentComponent {

	@ContextLife("Fragment")
    Fragment getContext();

	void inject(FragmentSecond fragmentSecond);
}
