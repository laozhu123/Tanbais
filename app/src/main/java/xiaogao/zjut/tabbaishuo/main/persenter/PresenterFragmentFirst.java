package xiaogao.zjut.tabbaishuo.main.persenter;

import javax.inject.Inject;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.bean.LoginRequestBean;
import xiaogao.zjut.tabbaishuo.bean.LoginResponseBean;
import xiaogao.zjut.tabbaishuo.interfaces.IUIFragmentFirst;
import xiaogao.zjut.tabbaishuo.net.NetService;
import xiaogao.zjut.tabbaishuo.utils.EncrtyUtil;


/**
 * Created by Administrator on 2017/12/23.
 */

public class PresenterFragmentFirst extends BasePresenter<IUIFragmentFirst> {
    NetService service;

    @Inject
    public PresenterFragmentFirst() {
    }

    public void testInterface() {

        if (service == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                    .baseUrl("http://106.15.225.82:8080/JiuDians/")
                    .build();
            service = retrofit.create(NetService.class);
        }

        service.login(new LoginRequestBean("6baaaf2cb60949e2a33b6cc3de688e11", "1512833619", "1b9f6b765e4eec0c9ce5cbd786ab1654", "17764507393", EncrtyUtil.md5Encrypt("12345"), "android", "sdf"))               //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Subscriber<LoginResponseBean>() {
                    @Override
                    public void onCompleted() {
//                        getMvpView().showToast("finish");
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showToast(e.getMessage());
                    }

                    @Override
                    public void onNext(LoginResponseBean loginResponseBean) {
                        getMvpView().showToast(loginResponseBean.msg+"helo");
                    }
                });
    }
}
