package xiaogao.zjut.tabbaishuo.net;

import io.reactivex.Observable;
import retrofit2.http.POST;
import retrofit2.http.Query;
import xiaogao.zjut.tabbaishuo.bean.LoginResponseBean;

/**
 * Created by Administrator on 2017/12/23.
 */

public interface NetService {
    @POST("user/login")
    Observable<LoginResponseBean> login(
            @Query("sid") String sid,
            @Query("tsp") String tsp,
            @Query("sig") String sig,
            @Query("phone") String phone,
            @Query("password") String password,
            @Query("device") String device,
            @Query("cid") String cid
    );

}
