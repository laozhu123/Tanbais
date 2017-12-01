package xiaogao.zjut.tabbaishuo.main.persenter;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xgn.com.basesdk.commonui.rxjava.XgSubscriber;
import xgn.com.basesdk.network.ExceptionHandle;
import xgn.com.basesdk.network.XGRest;
import xiaogao.zjut.tabbaishuo.app.MyApplication;
import xiaogao.zjut.tabbaishuo.interfaces.IUIFragmentConversation;
import xiaogao.zjut.tabbaishuo.net.RetrofitApi;
import xiaogao.zjut.tabbaishuo.net.responses.Helo;

/**
 * Created by Administrator on 2017/12/1.
 */

public class PresenterFragmentConversation extends BasePresenter{
    RetrofitApi retrofitApi;
    IUIFragmentConversation mView;

    public PresenterFragmentConversation(IUIFragmentConversation view) {
        XGRest xgRest = new XGRest();
        retrofitApi = xgRest.create(RetrofitApi.class);
        mView = view;
    }

    public void getNickName(String id) {

        retrofitApi.getNickName(MyApplication.getToken(),new Object())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new XgSubscriber<Helo>(this,true) {
                    @Override
                    public boolean onFailed(ExceptionHandle.ResponseThrowable var1) {
                        return false;
                    }

                    @Override
                    public void onNext(Helo value) {
                        mView.setNickName("helo");
                    }
                });
    }

}
