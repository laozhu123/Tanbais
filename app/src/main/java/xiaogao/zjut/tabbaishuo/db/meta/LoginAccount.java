package xiaogao.zjut.tabbaishuo.db.meta;


import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by huluzi on 2017/8/11.
 */

public class LoginAccount extends RealmObject {
    /** 是否是当前帐号 */
    public boolean isLastLogin;
    @PrimaryKey
    public String userId;
    /** 账号名称 */
    public String account;
    /** 密码 */
    public String password;
    /** token */
    public String token;
}
