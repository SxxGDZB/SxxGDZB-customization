package com.kiwihouse.service;

import java.util.Map;

import com.kiwihouse.common.bean.UserInfo;
import com.kiwihouse.dao.entity.vx.dev.Add;
import com.kiwihouse.dao.entity.vx.dev.WxEquipment;
import com.kiwihouse.domain.vo.Response;

public interface WxEquipmentService {
	/**
	 * 查询用户的设备列表
	 * @param wxEquipment
	 * @return
	 */
	Map<String, Object> queryInfo(WxEquipment wxEquipment);
	/**
	 * 修改用户的设备
	 * @param wxEquipment
	 * @return
	 */
	Response updateInfo(Add wxEquipment);
	/**
	 * 添加用户的设备
	 * @param wxEquipment
	 * @return
	 */
	Response addInfo(Add wxEquipment);
	/**
	 * 删除用户的设备
	 * @param imeis
	 * @param userId
	 * @return
	 */
	Response deleteInfo(String imeis, Integer userId);

}
