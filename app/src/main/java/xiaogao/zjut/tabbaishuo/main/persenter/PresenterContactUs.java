package xiaogao.zjut.tabbaishuo.main.persenter;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xgn.com.basesdk.commonui.rxjava.XgSubscriber;
import xgn.com.basesdk.network.ExceptionHandle;
import xiaogao.zjut.tabbaishuo.interfaces.IUIActivityContactUs;
import xiaogao.zjut.tabbaishuo.net.RetrofitApi;
import xiaogao.zjut.tabbaishuo.net.responses.Helo;

/**
 * Created by Administrator on 2017/12/20.
 */

public class PresenterContactUs extends BasePresenter<IUIActivityContactUs> {

    RetrofitApi retrofitApi;

    @Inject
    public PresenterContactUs(RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
    }

    public void getNet(){
        retrofitApi.getNickName("tt",new Object()).subscribe(new XgSubscriber<Helo>(this,true) {
            @Override
            public boolean onFailed(ExceptionHandle.ResponseThrowable var1) {
                return false;
            }

            @Override
            public void onNext(Helo helo) {

            }
        });
    }

}
