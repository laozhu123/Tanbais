package xgn.com.basesdk.network.interfaces;

import android.support.annotation.NonNull;

/**
 * Created by huluzi on 2017/8/9.
 */

public interface INetExternalParams {
    String getUserId();

    String getAppVersion();

    boolean isRelease();

    @NonNull
    String httpHost();

    @NonNull
    String httpSecondHost();

    @NonNull
    String mockHost();

    int connectTimeOut();
}
