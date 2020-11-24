package com.kiwihouse.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.message.BasicHeader;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.dao.entity.vx.decrypt.Data;
import com.kiwihouse.dao.entity.vx.decrypt.VxDecryPt;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.util.AesCbcUtil;
import com.kiwihouse.util.HttpUtil;

import io.swagger.annotations.Api;

@Api(tags = "微信小程序解密")
@RestController
@RequestMapping("/decrypt")
public class VxDecryPtController {
	public static List<BasicHeader> headers = new ArrayList<BasicHeader>();
	static {
		headers.add(new BasicHeader("Content-Type", "application/json; charset=utf-8"));
	}
	//小程序唯一标识   (在微信小程序管理后台获取)
	static String appId = "wxa8f557ab9e8efe68";
	 //小程序的 app secret (在微信小程序管理后台获取)
	static String secret = "72028861b37a1cc66a7352379cb3e5da";
	/**
     * 	解密用户敏感数据
     *
     * @param encryptedData 明文,加密数据
     * @param iv            加密算法的初始向量
     * @param code          用户允许登录后，回调内容会带上 code（有效期五分钟），开发者需要将 code 发送到开发者服务器后台，使用code 换取 session_key api，将 code 换成 openid 和 session_key
     * @return
	 * @throws Exception 
     */
    @PostMapping("/decodeUserInfo")
    public Response decodeUserInfo(@RequestBody Data data) throws Exception {
        //登录凭证不能为空
        if (data.getCode() == null || data.getCode().length() == 0) {
            return new Response().Fail(Code.WX_LOGIN_NULL,Code.WX_LOGIN_NULL.getMsg());
        }
        //1、向微信服务器 使用登录凭证 code 获取 session_key 和 openid 
        //授权（必填）
        //String grant_type = "authorization_code";
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret="
         		+ secret + "&js_code=" + data.getCode() + "&grant_type=authorization_code";
        //发送请求
        String sr = HttpUtil.get(url, headers);
        //解析相应内容（转换成json对象） 
        JSONObject json = JSON.parseObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        System.out.println("=============>" + openid);
        // 2、对encryptedData加密数据进行AES解密 
        String result = AesCbcUtil.decrypt(data.getEncryptedData(), session_key, data.getIv(), "UTF-8");
        System.out.println(result);
        VxDecryPt vdp = JSON.parseObject(result, VxDecryPt.class);
        System.out.println(vdp.toString());
		return new Response().Success(Code.LOGIN_SUCC,Code.LOGIN_SUCC.getMsg()).addData("data", vdp);
    }
    
    public static void main(String[] args) throws Exception {
    	String encryptedData = "WMPNmPScc1xAqbCncaQy4VSIjihtFlWT2/ZiuYTs/eVSbroFDRFE34igv+vIC1gd0LDCWoAjJBWtILY7VZcpg/H5QlUUpj/B+Ib0KdL6iWIpwOjha536wKhlPthVMvffP2ni+dB7GRvkWqUMCFojxOZKqIB+iLe55uN6y10ksuGgrCqvAnWayzlhSnAOVdvIJB8Z3SzbBpYK7IiI4bNU0yRCZqqfwC/NSUv25mLn6JXoHZF2qm98YZtTM+54kBEfbPuXAWrVjqlADw8eKycCWkRsKRXd71wLfyNNFSB6hhe/FeQkoHt9CNz1FNlhK8y6us6TZ49XbM1Q6fzd2+nzSi7dtkQhAk9bQfG7Vg6Qjy3yOlDXBeFkHkgBIzANqoWyXmzYGxv47g8UwCKLqnbjGRWp90hjv62MYC1JnOiss6wura/MRgMbk7zI5j+AhzDlqf6+MFlPWS6/dqUHRRJJvShOaMRl3a9Ct2fMFI3P1viWWAK9qNaHX7gxxHucvP1iCtszFO78TGHWKAgq9Cdvw2u6ILGyUMiz38LmJCJ4E6M=";
    	String iv = "FDepUx/WMGYikbTxdjdSEg==";
    	String code = "063Es02w3Ok2lV2iHH3w3pFJHz1Es022";
    	String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appId + "&secret="
         		+ secret + "&js_code=" + code + "&grant_type=authorization_code";
    	String sr = HttpUtil.get(url, headers);
    	System.out.println(sr);
        //解析相应内容（转换成json对象）
        JSONObject json = JSON.parseObject(sr);
        //获取会话密钥（session_key）
        String session_key = json.get("session_key").toString();
        //用户的唯一标识（openid）
        String openid = (String) json.get("openid");
        System.out.println("=============>" + openid);
        String result = AesCbcUtil.decrypt(encryptedData, session_key, iv, "UTF-8");
        System.out.println(result);
	}
}
