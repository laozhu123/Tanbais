package xiaogao.zjut.tabbaishuo.net;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import xgn.com.basesdk.network.utils.HeaderKey;
import xiaogao.zjut.tabbaishuo.net.responses.Helo;

/**
 * Created by huluzi on 2017/8/10.
 */

public interface RetrofitApi {

    /**
     * 重新申请骑士
     */
    @Headers(HeaderKey.MOCK_HOST + ":change")
    @POST("task/doing")
    Observable<Helo> helo(
            @Header("Authorization")
                    String token,
            @Body
                    Object object);

    /**
     * 获取nickName
     */
    @Headers(HeaderKey.MOCK_HOST + ":change")
    @POST("task/getNickName")
    Observable<Helo> getNickName(
            @Header("Authorization")
                    String token,
            @Body
                    Object object);


}
