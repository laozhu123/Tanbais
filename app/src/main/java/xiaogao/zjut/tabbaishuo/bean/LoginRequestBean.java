package xiaogao.zjut.tabbaishuo.bean;

/**
 * Created by Administrator on 2017/12/23.
 */

public class LoginRequestBean {
    public String sid;
    public String tsp;
    public String sig;
    public String phone;
    public String password;
    public String device;
    public String cid;

    public LoginRequestBean(String sid, String tsp, String sig, String phone, String password, String device, String cid) {
        this.sid = sid;
        this.tsp = tsp;
        this.sig = sig;
        this.phone = phone;
        this.password = password;
        this.device = device;
        this.cid = cid;
    }
}
