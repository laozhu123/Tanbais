package xiaogao.zjut.tabbaishuo.main.persenter;

import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.interfaces.IUIFragmentSecond;
import xiaogao.zjut.tabbaishuo.net.RetrofitApi;

/**
 * Created by Administrator on 2017/11/29.
 */

public class PresenterFragmentSecond extends BasePresenter<IUIFragmentSecond> {

    private RetrofitApi retrofitApi;

    @Inject
    public PresenterFragmentSecond(RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
    }

}
