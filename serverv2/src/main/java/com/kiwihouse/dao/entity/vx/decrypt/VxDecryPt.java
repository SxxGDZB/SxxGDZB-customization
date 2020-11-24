package com.kiwihouse.dao.entity.vx.decrypt;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Setter
@Getter
public class VxDecryPt {
	 private String openId;
	    private String nickName;
	    private int gender;
	    private String language;
	    private String city;
	    private String province;
	    private String country;
	    private String avatarUrl;
	    private String unionId;
	    private Watermark watermark;
}
