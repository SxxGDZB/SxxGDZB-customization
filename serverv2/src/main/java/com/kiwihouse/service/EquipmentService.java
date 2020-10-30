package com.kiwihouse.service;

import java.util.List;
import java.util.Map;

import com.kiwihouse.common.bean.UserInfo;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.Eqpt4UpdateDto;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.vo.kiwihouse.EqptAddVo;
import com.kiwihouse.vo.kiwihouse.EqptQueryVo;

public interface EquipmentService {
	/**
	 * 	查询设备列表
	 * @param eqptQueryVo
	 * @param authUser
	 * @return
	 */
	Map<String, Object> queryInfo(EqptQueryVo eqptQueryVo);
	/**
	 * 	更新设备信息
	 * @param updateDto
	 * @return
	 */
	Response updateInfo(Eqpt4UpdateDto updateDto);
	/**
	 * 	录入设备信息
	 * @param eqptAddVo
	 * @param userInfo
	 * @return
	 */
	Response addInfo(EqptAddVo eqptAddVo);
	/**
	 * 	删除设备信息
	 * @param eqptSn
	 * @param userInfo
	 * @return
	 */
	Response deleteInfo(String imeis, UserInfo userInfo);
	/**
	 * 查询单个设备信息
	 * @param eqptQueryVo
	 * @return
	 */
	Response selectOneInfo(EqptQueryVo eqptQueryVo);
	/**
	 * 批量添加 ----> 存在相同的则--->修改 设备
	 * @param userList
	 * @return
	 */
	Response insertOrUpdateBatch(List<EqptInfoDto> userList);
	/**
	 * 	查询角色设备列表(不查询状态)
	 * @param eqptQueryVo
	 * @return
	 */
	Map<String, Object> queryRoleDevList(EqptQueryVo eqptQueryVo);
	/**
	 * 修改角色设备列表
	 * @param roleId
	 * @param deptIds
	 * @return
	 */
	Response updateRoleDevList(Integer roleId, String deptIds);
	/**
	 * 修改用户设备列表
	 * @param userId
	 * @param deptIds
	 * @return
	 */
	Response updateUserDevList(Integer userId, String deptIds);
	/**
	 * 	查询用户设备列表(不查询状态)
	 * @param eqptQueryVo
	 * @return
	 */
	Map<String, Object> queryUserDevList(EqptQueryVo eqptQueryVo);

}
