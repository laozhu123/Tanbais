package xiaogao.zjut.tabbaishuo.app;

/**
 * Created by huluzi on 2017/8/11.
 */

public class Constants {
    /**
     * 全局debug控制开关
     */
    public static final boolean IS_DEBUG = false;

    /****************** 服务器地址 start *********************/
    /**
     * 正式环境
     */
    public static final String MOBILE_SERVICE_DOMAIN_ONLINE = "";
    /**
     * 系统
     */
    public static final int SYSTEM_NAME = 2;
    /****************** 服务器地址 end *********************/


    /****************** 基本参数 start *********************/
    /**
     * 全局缓存
     */
    public static final String MISS_CACHE_ROOT = "cavalier";

    public static String MOBILE_SERVICE_DOMAIN = MOBILE_SERVICE_DOMAIN_ONLINE;
    /**
     * 协议版本号
     */
    public static String PROTOCOL_VERSION = "1.0.0";
    /****************** 基本参数 end *********************/

    /**
     * 用于更新app
     */
    public static final String TBB_VERSION_QUERY = "common/appVersion/query";

    /**
     * 用于进行图片上传
     */
    public static final String IMAGE_UPLOAD_URL = "common/photo/upload";

    /**
     * 用于修改手机号码发送短信
     */
    public static final String SMS_SEND = "common/sms/send";

    /**
     * 获取车辆大小的信息
     */
    public static final String CAR_TYPE_INFO = "common/carTypeList/query";

    /**
     * 骑手端
     */
    public static final String TUBOBO_RIDER = "tuboboDriver";
    /**
     * 更新骑手端的type
     */
    public static final String ANDROID = "android";

    /**
     * 图片长传的bucket
     */
    public static final String BUCKET_NAME_PUBLIC = "public";
    public static final String BUCKET_NAME_PRIVATE = "private";

    /**
     * 图片类型jpg/png
     */
    public static final String IMAGE_ACCOUNT_TYPE_GPEJ = "image/jpeg";
    public static final String IMAGE_ACCOUNT_TYPE_PNG = "image/png";

    /**
     * 项目类型
     */
    public static final String PROJECT_NAME = "driver";

    /**
     * 申请骑士
     */
    public static final String CERTIFICATION_RIDER = "rider";
    /**
     * 项目司机
     */
    public static final String CERTIFICATION_DRIVER = "driver";

    /**
     * 登录账号
     */
    public static final String COMMON_LOGIN_ACCOUNT = "common_login_account";


    /**
     * 是否显示提示信息
     */
    public static final String USER_ID = "useId";

    /**
     * 错误码
     */
    public static final String PWD_ERROR = "0000002";

    /**
     * 获取验证码actionType
     */
    public interface GetVerifyCodeActionType {
        /**
         * 0-注册验证码
         */
        String REGIST_ACTIONTYPE = "r";
        /**
         * 1-重置密码验证码
         */
        String RESET_ACTIONTYPE = "f";
        /** 2-绑定手机号码 */
        // String BIND_PHONE_ACTIONTYPE = "2";
    }


    /**
     * 获取验证码actionType
     */
    public interface PublicGetVerifyCodeActionType {
        /**
         * 找回支付密码
         */
        String FIND_PWD_ACTIONTYPE = "fp";
        /**
         * 修改手机号
         */
        String MODIFY_PWD_ACTIONTYPE = "modify_phone";
    }


    public static final int IS_DIRDER = "is_rider".hashCode();

    public static final int REFRESH_LIST_TIME_OUT = 15;
}
