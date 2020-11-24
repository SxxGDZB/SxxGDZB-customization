package com.kiwihouse.service;

import java.util.List;
import java.util.Map;

import com.kiwihouse.dao.entity.IMEI;
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
	/**
	 * 分享设备列表
	 * @param listIMEI
	 * @param listUser 
	 * @return
	 */
	Map<String, Object> queryShareDevList(List<IMEI> listIMEI, List<Integer> listUser);
	/**
	 * 	查询单个设备信息
	 * @param wxEquipment
	 * @return
	 */
	Response queryOneDev(WxEquipment wxEquipment);

}
