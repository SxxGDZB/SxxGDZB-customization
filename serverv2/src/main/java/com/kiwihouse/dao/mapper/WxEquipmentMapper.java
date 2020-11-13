package com.kiwihouse.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kiwihouse.dao.entity.IMEI;
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
     * @param imeiArr 
     * @return
     */
	List<WxEquipment> querInfoByUserIdPage(@Param("wxEquipment") WxEquipment wxEquipment);
	/**
	 * 添加关联信息
	 * @param eqptId
	 * @param userId
	 */
	int inertWxEqAndUser(Integer eqptId, Integer userId);
	/**
	 * 根据IMEI查询设备
	 * @param userId 
	 * @param string
	 * @return
	 */
	WxEquipment selectByImei( String imei);
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
	/**
	 * 根据IMEI和userId查询设备
	 * @param imei
	 * @param userId
	 * @return
	 */
	WxEquipment selectByImeiAndUserId(@Param("imei") String imei, @Param("userId") Integer userId);
	/**
	 * 根据设备ID解除关联
	 * @param eqptId
	 */
	void deleteWxEq(Integer eqptId);
	/**
	 * 查询设备分享列表
	 * @param listIMEI 
	 * @return
	 */
	List<WxEquipment> queryShareDevList(List<IMEI> listIMEI);
	/**
	 * 查询用户的   接受的 分享设备
	 * @param userId
	 * @return
	 */
	List<WxEquipment> queryUserShareDevList(Integer userId);
}