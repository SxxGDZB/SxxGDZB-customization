package com.kiwihouse.util;
 
import com.alibaba.fastjson.JSONObject;

import org.apache.http.message.BasicHeader;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.codehaus.xfire.util.Base64;
 
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
 
/**
 * @Description
 * @Author liu
 * @Date: 2020-06-04 14:35
 * @Mender:
 */
public class WeixinUtil {
	public static List<BasicHeader> headers = new ArrayList<BasicHeader>();
	static {
		headers.add(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
	}
	static String appId = "wxa8f557ab9e8efe68";
	static String secret = "72028861b37a1cc66a7352379cb3e5da";
	
	/**
	* 根据code获取sessionkey
	*
	* @param code
	* @return
	*/
	private String getSessionKey(String code) {
	String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret="
	+ secret + "&js_code=" + code + "&grant_type=authorization_code";
	JSONObject oppidObj = JSONObject.parseObject(HttpUtil.get(url, null));
	String openid = (String) oppidObj.get("openid");
	String session_key = (String) oppidObj.get("session_key");
	return session_key;
	}
    /**
     * 解析用户信息
     *
     * @return
     */
    public static JSONObject getUserInfo(String encryptedData, String sessionKey, String iv) {
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(sessionKey);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new JSONObject();
    }
    public static void main(String[] args) {
    	String encryptedData = "vfPUMZPy/hew+dtW5qr6eO6rEF9yKoiqiGBa+7r20Jddmo2ZMM9oaK3j1JchYWQpROPXmLmiW2JpbBx6GvEajkO9t7d7D83V108PmqM/w4GE2VhqAc715bLmjcnlDpyBzH2w2z1faTOJFnzg4i9nOtYDnq/RhM39iTaooJaFfgZgdHGSkxh6p2eg8lBqJnk0g9jWCsQhVQVrrMq3EIN6EFkpQNUwpels2xFIzUewZJjRTU326Ju4xpc0pG61rvduw3i5HoDBKwHOckUGN8Rz68PfzeBb9w7ZvHYTnqjLa/NocnqSn5qMbphQZLNOjK6fhxs+rw4pDQSRAIlaNJrLW7cd9ac6iybSvVXeL7DKMc659B/oK70lZvmP0YrYoHQ9zk+9EPpDkYc9dQ7+40J7arNCtGr/1YQ7taaDtCqFl0vKCoLvuoVjYRNmp488y/O7y6HOihnxroPkj/AmBBKT5MByxH1UxnIlqvcyvjxkxj/7zr3YBeFLRxrXUhAuPwogJL+1NKaabKMkCYvcFgnOXTODMZpwXf1JzRlP3X2Moaw=";
    	String iv = "h7HjFxqBWNHCx3z/RXabHw==";
    	String code = "083Umill2h8AZ54l2xol2v2R4I2UmilD";
    	getUserInfo(encryptedData,code,iv);
	}
}