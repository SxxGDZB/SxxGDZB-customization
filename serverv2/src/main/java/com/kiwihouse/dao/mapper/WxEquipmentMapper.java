package com.kiwihouse.dao.mapper;

import java.util.List;

import com.kiwihouse.dao.entity.vx.dev.Add;
import com.kiwihouse.dao.entity.vx.dev.WxEquipment;

public interface WxEquipmentMapper {
    int deleteByPrimaryKey(Integer eqptId);

    int insert(Add record);

    int insertSelective(Add record);

    WxEquipment selectByPrimaryKey(Integer eqptId);

    int updateByPrimaryKeySelective(WxEquipment record);

    int updateByPrimaryKey(WxEquipment record);
    /**
     * 查询用户的设备列表 
     * @param wxEquipment
     * @return
     */
	List<WxEquipment> querInfoByUserIdPage(WxEquipment wxEquipment);
	/**
	 * 添加关联信息
	 * @param eqptId
	 * @param userId
	 */
	int inertWxEqAndUser(Integer eqptId, Integer userId);
	/**
	 * 根据IMEI查询设备
	 * @param string
	 * @return
	 */
	WxEquipment selectByImei(String imei);
	/**
	 * 根据IMEI删除
	 * @param string
	 */
	int deleteByImei(String imei);
	/**
	 * 删除关联信息
	 * @param eqptId
	 * @param userId
	 */
	int deleteWxEqAndUser(Integer eqptId, Integer userId);
	/**
	 * 	根据IMEI修改设备
	 * @param wxEquipment
	 * @return
	 */
	int updateByImei(Add wxEquipment);
}