package xiaogao.zjut.tabbaishuo.net;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;
import xiaogao.zjut.tabbaishuo.bean.LoginRequestBean;
import xiaogao.zjut.tabbaishuo.bean.LoginResponseBean;

/**
 * Created by Administrator on 2017/12/23.
 */

public interface NetService {
    @POST("user/login")
    Observable<LoginResponseBean> login(
             @Body LoginRequestBean loginRequestBean
            );

}
