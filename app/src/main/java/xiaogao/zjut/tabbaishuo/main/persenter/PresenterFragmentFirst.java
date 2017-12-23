package xiaogao.zjut.tabbaishuo.main.persenter;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import javax.inject.Inject;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xgn.com.basesdk.commonui.rxjava.XgSubscriber;
import xgn.com.basesdk.network.ExceptionHandle;
import xiaogao.zjut.tabbaishuo.bean.LoginRequestBean;
import xiaogao.zjut.tabbaishuo.bean.LoginResponseBean;
import xiaogao.zjut.tabbaishuo.interfaces.IUIFragmentFirst;
import xiaogao.zjut.tabbaishuo.net.NetService;
import xiaogao.zjut.tabbaishuo.net.RetrofitApi;

import static io.rong.imlib.statistics.UserData.phone;

/**
 * Created by Administrator on 2017/12/23.
 */

public class PresenterFragmentFirst extends BasePresenter<IUIFragmentFirst>{
    NetService service;
    @Inject
    public PresenterFragmentFirst() {
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())//新的配置
                .baseUrl("http://106.15.225.82:8080/JiuDians")
                .build();
        service = retrofit.create(NetService.class);


    }

    public void testInterface(){

        service.login("df","sdf","1354894463","sdifewljnfois","sdjfoe","shfoeuh","sdf")               //获取Observable对象
                .subscribeOn(Schedulers.newThread())//请求在新的线程中执行
                .observeOn(Schedulers.io())         //请求完成后在io线程中执行
                .observeOn(AndroidSchedulers.mainThread())//最后在主线程中执行
                .subscribe(new Observer<LoginResponseBean>() {


                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(LoginResponseBean loginResponseBean) {
                        getMvpView().showToast(loginResponseBean.msg);
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showToast(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        getMvpView().showToast("finish");
                    }
                });
    }
}
