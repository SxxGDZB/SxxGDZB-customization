package com.kiwihouse.util.test;

import com.alibaba.fastjson.JSONObject;

public class GetTokenBean {
	 private String appId;
	    private String appKey;

	    public String getAppId() {
	        return appId;
	    }

	    public void setAppId(String appId) {
	        this.appId = appId;
	    }

	    public String getAppKey() {
	        return appKey;
	    }

	    public void setAppKey(String appKey) {
	        this.appKey = appKey;
	    }

	    @Override
	    public String toString() {
	        return JSONObject.toJSONString(this);

	    }
}
