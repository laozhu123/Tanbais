package xiaogao.zjut.tabbaishuo.app;

/**
 * Created by huluzi on 2017/8/11.
 */

public interface ServerEnv {

    /**
     * 开发环境
     */
    int DEV = 0;

    /**
     * 测试环境（内网）
     */
    int DEBUG = 1;

    /**
     * 预发布环境
     */
    int PRE = 2;

    /**
     * 测试环境（外网）
     */
    int RELEASE_TEST = 3;

    /**
     * 生产环境
     */
    int RELEASE_PRODUCT = 4;
    /**
     * mock环境
     */
    int MOCK = 5;

}