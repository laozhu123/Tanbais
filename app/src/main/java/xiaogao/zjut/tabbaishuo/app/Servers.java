package xiaogao.zjut.tabbaishuo.app;

/**
 * Created by huluzi on 2017/8/11.
 */

public class Servers {
    /**
     * 开发环境
     */

//    private static final String TBB_API_DEV = "http://172.16.1.181:8004/";
//    private static final String TBB_USER_API_DEV = "http://172.16.1.181:8002/";
//    private static final String TBB_PUBLIC_API_DEV = "http://172.16.1.181:8005/";
//    private static final String TBB_WS_URL_DEV = "ws://172.16.1.181:2001/ws";

    private static final String TBB_API_DEV = "http://tubobo-driver.dev.ops.com/";
    private static final String TBB_USER_API_DEV = "http://usercenter-gate.dev.ops.com/";
    private static final String TBB_PUBLIC_API_DEV = "http://tubobo.dev.ops.com/";
    private static final String TBB_WS_URL_DEV = "ws://172.16.1.181:2001/ws";

    /**
     * 测试环境
     */
    private static final String TBB_API_DEBUG = "http://172.16.1.180:8013/";
    private static final String TBB_USER_API_DEBUG = "http://172.16.1.180:8002/";
    private static final String TBB_PUBLIC_API_DEBUG = "http://172.16.1.180:8005/";
    private static final String TBB_WS_URL_DEBUG = "ws://172.16.1.180:2001/ws";

    /**
     * 测试环境（外网）
     */
    private static final String TBB_API_RELEASE_TEST = "http://sjyxtest.yiqiguang.com/tbbrd/";
    private static final String TBB_USER_API_RELEASE_TEST = "http://sjyxtest.yiqiguang.com/tbbuser/";
    private static final String TBB_PUBLIC_API_RELEASE_TEST = "http://sjyxtest.yiqiguang.com/houtai/";
    private static final String TBB_WS_URL_RELEASE_TEST = "ws://101.68.67.180:2001/ws";

    /**
     * 预发环境
     */
    private static final String TBB_API_PRE = "http://driveruat.toobob.com:80";
    private static final String TBB_USER_PRE = "http://139.224.218.36:80/";
    private static final String TBB_PUBLIC_API_PRE = "http://139.196.251.95:80/";
    private static final String TBB_WS_URL_PRE = "ws://106.15.131.47:80/ws";


    /**
     * 生产环境
     */
    private static final String TBB_API_RELEASE_PRODUCT = "http://driver.toobob.com/";
    private static final String TBB_USER_API_RELEASE_PRODUCT = "http://modem.toobob.com/";
    private static final String TBB_PUBLIC_API_RELEASE_PRODUCT = "http://admin.toobob.com/";
    private static final String TBB_WS_URL_RELEASE_PRODUCT = "ws://orderlinks.yiqiguang.com:80/ws";


    /**
     * tubobo协议
     */
    private static final String TBB_PROTOCOL = "";
    private static final String TBB_ABOUT = "";

    public static final String TBB_MOCK = "http://172.16.1.15:8068/mockjsdata/9/";

    /**
     * 跟业务相关的接口需要使用该地址，如：<br/>
     * <ui>
     *     <li>获取个人信息</li>
     *     <li>获取任务列表</li>
     *     <li>获取任务详情</li>
     *     <li>...</li>
     * </ui>
     */
    public static final String getTBBApi() {
        if (ServerConfig.getServerEnv() == ServerEnv.DEV) {
            return TBB_API_DEV;
        } else if (ServerConfig.getServerEnv() == ServerEnv.RELEASE_PRODUCT) {
            return TBB_API_RELEASE_PRODUCT;
        } else if (ServerConfig.getServerEnv() == ServerEnv.DEBUG) {
            return TBB_API_DEBUG;
        } else if (ServerConfig.getServerEnv() == ServerEnv.RELEASE_TEST) {
            return TBB_API_RELEASE_TEST;
        } else if (ServerConfig.getServerEnv() == ServerEnv.MOCK) {
            return TBB_MOCK;
        } else if(ServerConfig.getServerEnv() == ServerEnv.PRE) {
            return TBB_API_PRE;
        }
        return TBB_API_DEV;
    }

    /**
     * 跟用户中心相关的接口需要使用该地址，如：<br/>
     * <ui>
     *     <li>登录</li>
     *     <li>注册</li>
     *     <li>注册时获取验证码</li>
     *     <li>...</li>
     * </ui><br/>
     */
    public static final String getTBBUserApi() {
        if (ServerConfig.getServerEnv() == ServerEnv.DEV) {
            return TBB_USER_API_DEV;
        } else if (ServerConfig.getServerEnv() == ServerEnv.RELEASE_PRODUCT) {
            return TBB_USER_API_RELEASE_PRODUCT;
        } else if (ServerConfig.getServerEnv() == ServerEnv.DEBUG) {
            return TBB_USER_API_DEBUG;
        } else if (ServerConfig.getServerEnv() == ServerEnv.RELEASE_TEST) {
            return TBB_USER_API_RELEASE_TEST;
        } else if (ServerConfig.getServerEnv() == ServerEnv.MOCK) {
            return TBB_MOCK;
        } else if (ServerConfig.getServerEnv() == ServerEnv.PRE) {
            return TBB_USER_PRE;
        }
        return TBB_USER_API_DEV;
    }

    /**
     * <ui>
     *     <li>查询版本</li>
     *     <li>图片上传</li>
     *     <li>修改密码中的获取验证码</li>
     * </ui><br/>
     * 这三个接口需要使用该地址
     *
     */
    public static final String getTBBPublicApi() {
        if (ServerConfig.getServerEnv() == ServerEnv.DEV) {
            return TBB_PUBLIC_API_DEV;
        } else if (ServerConfig.getServerEnv() == ServerEnv.RELEASE_PRODUCT) {
            return TBB_PUBLIC_API_RELEASE_PRODUCT;
        } else if (ServerConfig.getServerEnv() == ServerEnv.DEBUG) {
            return TBB_PUBLIC_API_DEBUG;
        } else if (ServerConfig.getServerEnv() == ServerEnv.RELEASE_TEST) {
            return TBB_PUBLIC_API_RELEASE_TEST;
        } else if (ServerConfig.getServerEnv() == ServerEnv.MOCK) {
            return TBB_MOCK;
        } else if (ServerConfig.getServerEnv() == ServerEnv.PRE) {
            return TBB_PUBLIC_API_PRE;
        }
        return TBB_USER_API_DEV;
    }

    /**
     * 获取长连接的地址，包括了端口号
     */
    public static final String getWsUrl() {
        if (ServerConfig.getServerEnv() == ServerEnv.DEV) {
            return TBB_WS_URL_DEV;
        } else if (ServerConfig.getServerEnv() == ServerEnv.RELEASE_PRODUCT) {
            return TBB_WS_URL_RELEASE_PRODUCT;
        } else if (ServerConfig.getServerEnv() == ServerEnv.DEBUG) {
            return TBB_WS_URL_DEBUG;
        } else if (ServerConfig.getServerEnv() == ServerEnv.RELEASE_TEST) {
            return TBB_WS_URL_RELEASE_TEST;
        } else if (ServerConfig.getServerEnv() == ServerEnv.MOCK) {
            return TBB_WS_URL_DEV;
        } else if (ServerConfig.getServerEnv() == ServerEnv.PRE) {
            return TBB_WS_URL_PRE;
        }
        return TBB_WS_URL_DEV;
    }


    public static final String getProtocolUrl() {
        return TBB_PROTOCOL;
    }

    public static final String getAboutUrl() {
        return TBB_ABOUT;
    }
}
