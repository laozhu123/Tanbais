package xiaogao.zjut.tabbaishuo.db;


import io.realm.Realm;
import xiaogao.zjut.tabbaishuo.db.meta.LoginAccount;

/**
 * Created by huluzi on 2017/8/11.
 */

public class LoginAccountManager {
    private static final String LAST_LOGIN = "isLastLogin";
    private static LoginAccountManager sInstance = null;

    public static synchronized LoginAccountManager getInstance() {
        if (sInstance == null) {
            sInstance = new LoginAccountManager();
        }

        return sInstance;
    }

    /**
     * 获取当前帐号
     */
    public LoginAccount getCurrentAccount() {
        LoginAccount loginAccount;
        loginAccount = Realm.getDefaultInstance().where(LoginAccount.class).equalTo(LAST_LOGIN, true).findFirst();
        return loginAccount;
    }

}
