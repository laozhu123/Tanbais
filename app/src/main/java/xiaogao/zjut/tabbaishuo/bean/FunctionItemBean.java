package xiaogao.zjut.tabbaishuo.bean;

/**
 * Created by Administrator on 2017/11/19.
 */

public class FunctionItemBean {
    private int imgId;
    private int txId;

    public FunctionItemBean(int imgId, int txId) {
        this.imgId = imgId;
        this.txId = txId;
    }

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public int getTxId() {
        return txId;
    }

    public void setTxId(int txId) {
        this.txId = txId;
    }
}
