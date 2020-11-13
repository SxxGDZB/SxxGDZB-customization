package com.kiwihouse.dao.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 分享设备-->Redis
 * @author cmx
 *
 */
@ToString
@Setter
@Getter
public class Share {
	private Integer id;
	/**
	 * 分享者的ID 
	 */
	private Integer sharerId;
	/**
	 * 分享给用户  这些用户的ID
	 */
	private String shareUserIds;
	/**
	 * 要分享设备的IMEI
	 */
	private String imei;
	/**
	 * 分享给的单个用户ID
	 */
	private Integer shareUserId;
	
	
}
