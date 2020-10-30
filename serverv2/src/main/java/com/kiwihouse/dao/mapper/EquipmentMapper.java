package com.kiwihouse.dao.mapper;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import com.kiwihouse.dao.entity.IMEI;
import com.kiwihouse.dao.entity.RoleDev;
import com.kiwihouse.dao.entity.UserDev;
import com.kiwihouse.dto.AlmIdMsgDto;
import com.kiwihouse.dto.Eqpt4UpdateDto;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.dto.KwhAndTimeDto;
import com.kiwihouse.dto.SiteDto;
import com.kiwihouse.vo.kiwihouse.EqptAddVo;
import com.kiwihouse.vo.kiwihouse.EqptQueryVo;

/**
 * @author yjzn
 * @date 2019-12-30-上午 10:28
 */
@Repository
public interface EquipmentMapper {

    //queryInfo
    ArrayList<EqptInfoDto> queryInfo(EqptQueryVo eqptQueryVo);      //不分页

    //ArrayList<EqptInfoDto> queryLimitInfo(EqptQueryVo eqptQueryVo); //分页
    Integer queryInfoRow(EqptQueryVo eqptQueryVo);

    List<String> queryEqptIds(String adminId);

    List<String> queryEqptSns(String adminId);

    AlmIdMsgDto queryLatestAlmInfo(String imei);

    AlmIdMsgDto queryTrulyAlmInfo(String imei);

    @Update("update equipment set user_id=#{userId} where imei=#{imei} and eqpt_sn=#{eqptSn}")
    Integer updateUserId(String imei, String eqptSn, String userId);

    @Update("update equipment set user_id='' where eqpt_sn=#{eqptSn}")
    Integer deleteUserId(String eqptSn);

    //update
    Integer updateInfo(Eqpt4UpdateDto updateDto);

    Integer addDeviceId(@Param("eqptId") String eqptId,
                        @Param("deviceId") String deviceId); //其实是根据eqptId更新deviceId,叫addDeviceId是因为deviceId是从onenet平台上读取到，然后添加到数据库的

    //add
    Integer addInfo(EqptAddVo eqptAddVo);

    Integer addSite(EqptAddVo eqptAddVo);

    Integer addSiteForUpdate(Eqpt4UpdateDto updateDto);


    //delete
    Integer deleteInfo(String eqptSn);

    @Select("select kwh,add_time from electric_energy where eqpt_sn = #{eqptSn} order by add_time desc limit 1;")
    KwhAndTimeDto queryKwh(String eqptSn);

    SiteDto querySiteInfo(EqptAddVo eqptAddVo);

    SiteDto querySiteInfoForUpdate(Eqpt4UpdateDto updateDto);

    @Select("select * from equipment where imei=#{imei} limit 1;")
    EqptInfoDto queryInfoByImei(String imei);

    @Select("select * from equipment where eqpt_sn=#{eqptSn} and imei=#{imei} limit 1;")
    EqptInfoDto queryInfoBySnAndImei(String eqptSn, String imei);

    @Select("select * from equipment where user_id=#{userId}")
    List<EqptInfoDto> querInfoByUserId(String userId);
    
    /**
     * 	根据条件查询  分页
     * @param uid
     * @param eqptQueryVo
     * @param list 
     * @return
     */
	List<EqptInfoDto> querInfoByUserIdPage(@Param("eqptQueryVo") EqptQueryVo eqptQueryVo, @Param("list") List<IMEI> list);
	/**
	 *	 根据条件查询记录总数
	 * @param eqptQueryVo
	 * @param list 
	 * @param userId 
	 * @return
	 */
	Integer queryInfoCount(@Param("eqptQueryVo") EqptQueryVo eqptQueryVo, @Param("list") List<IMEI> list);
	/**
	 * 批量删除设备
	 * @param aqptArr
	 * @return
	 */
	int deleteBatch(String[] imeiArr);
	
	@Select("select * from equipment where eqpt_sn=#{eqptSn} limit 1;")
	EqptInfoDto queryInfoBySn(String eqptSn);
	/**
	 * 查询单个设备
	 * @param eqptQueryVo
	 * @return
	 */
	EqptInfoDto selectOneInfo(EqptQueryVo eqptQueryVo);
	/**
	 * 批量修改设备的分组信息
	 * @param eqptIdArr
	 */
	int updateBatch(String[] eqptIdArr);
	/**
	 * 批量添加 ----> 存在相同的则--->修改 设备
	 * @param userList 
	 * @return
	 */
	int insertOrUpdateBatch(List<EqptInfoDto> userList);
	/**
	 * 添加关联表
	 * @param eqptId
	 * @param roleId
	 * @return
	 */
	int addRoleDev(String eqptId, String roleId);
	/**
	 * 	返回角色设备的IMEI
	 * @param eqptQueryVo
	 * @return
	 */
	List<IMEI> selectRoleImei(Integer roleId);
	/**
	 * 	修改角色设备
	 * @param roleDevList
	 */
	int insertRoleDevList(List<RoleDev> roleDevList);
	/**
	 * 	删除角色设备关系
	 * @param roleId
	 */
	void deleteRoleDev(Integer roleId);
	/**
	 * 	删除用户设备关系
	 * @param userId
	 */
	void deleteUserDev(Integer userId);
	/**
	 *	 修改用户设备
	 * @param roleDevList
	 */
	void insertUserDevList(List<UserDev> roleDevList);
	/**
	 * 	返回用户 下级用户 设备的IMEI
	 * @param eqptQueryVo
	 * @return
	 */
	List<IMEI> selectUserImei(Integer roleId);
}
