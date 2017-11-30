package xiaogao.zjut.tabbaishuo.main.persenter;

import android.util.Log;

import javax.inject.Inject;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import xgn.com.basesdk.base.mvp.BasePresenter;
import xiaogao.zjut.tabbaishuo.interfaces.IUISplash;
import xiaogao.zjut.tabbaishuo.net.RetrofitApi;

/**
 * Created by Administrator on 2017/11/29.
 */

public class PresenterSplash extends BasePresenter<IUISplash> {

    RetrofitApi retrofitApi;

    @Inject
    public PresenterSplash(RetrofitApi retrofitApi) {
        this.retrofitApi = retrofitApi;
    }

    public void connect(String token) {

        RongIM.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误。可以从下面两点检查 1.  Token 是否过期，如果过期您需要向 App Server 重新请求一个新的 Token
             *                  2.  token 对应的 appKey 和工程里设置的 appKey 是否一致
             */
            @Override
            public void onTokenIncorrect() {
                getMvpView().showToast("请确定Token 是否过期||token 对应的 appKey 和工程里设置的 appKey 是否一致");
                getMvpView().fail();
            }

            /**
             * 连接融云成功
             * @param userid 当前 token 对应的用户 id
             */
            @Override
            public void onSuccess(String userid) {
                Log.d("helo", "--onSuccess" + userid);
                getMvpView().success();
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                getMvpView().showToast("错误码" + errorCode.getValue() + " " + errorCode.getMessage());
                Log.d("helo", errorCode.getValue() + "" + errorCode.getMessage());
                getMvpView().fail();
            }
        });
    }
}
