package xiaogao.zjut.tabbaishuo.main.persenter;

import android.util.Log;
import android.widget.Toast;

import javax.inject.Inject;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import retrofit2.Retrofit;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.interfaces.IUIMain;
import xiaogao.zjut.tabbaishuo.net.RetrofitApi;

/**
 * Created by Administrator on 2017/11/29.
 */

public class PresenterMain extends BasePresenter<IUIMain> {

    private RetrofitApi retrofitApi;

    @Inject
    public PresenterMain(RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
    }


}
