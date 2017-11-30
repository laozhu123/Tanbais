package xiaogao.zjut.tabbaishuo.utils;

import android.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import xiaogao.zjut.tabbaishuo.app.Constants;


/**
 * Created by huluzi on 2017/8/11.
 */

public class AESUtils {

    private static final String PASSWORD = "eGluZ3Vhbmd0YmI=";
    private static final String ENCODE = "UTF-8";
    private static final String ALGORITHM = "AES/CBC/NoPadding";
    private static final String IV = "svtpdprtrsjxabcd";


    /**
     * 加密
     *
     * @param content 需要加密的内容
     */
    public static String encrypt(String content) {
        if(Constants.IS_DEBUG) {
            return  content;
        }

        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);// 创建密码器
            int blockSize = cipher.getBlockSize();
            byte[] dataBytes = content.getBytes(ENCODE);
            int plaintextLength = dataBytes.length;
            if (plaintextLength % blockSize != 0) {
                plaintextLength = plaintextLength + (blockSize - (plaintextLength % blockSize));
            }
            byte[] plaintext = new byte[plaintextLength];
            System.arraycopy(dataBytes, 0, plaintext, 0, dataBytes.length);
            SecretKeySpec keyspec = new SecretKeySpec(PASSWORD.getBytes(ENCODE), "AES");
            IvParameterSpec ivspec = new IvParameterSpec(IV.getBytes(ENCODE));
            cipher.init(Cipher.ENCRYPT_MODE, keyspec, ivspec);// 初始化

            byte[] result = cipher.doFinal(plaintext);
            return Base64.encodeToString(result,Base64.DEFAULT); // 加密
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
