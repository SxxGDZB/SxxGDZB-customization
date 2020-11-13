package com.kiwihouse.dao.mapper;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.Share;

public interface ShareMapper {
	/**
	 * 添加用户设备分享
	 * @param share
	 * @return
	 */
	int insert(Share share);
	/**
	 * 删除分享的设备
	 * @param string
	 * @param userId
	 */
	void delete(@Param("imei") String imei, @Param("userId") Integer userId);
	/**
	 * 查询分享的设备
	 * @param share
	 * @return
	 */
	Share queryShareDev(Share share);

}
