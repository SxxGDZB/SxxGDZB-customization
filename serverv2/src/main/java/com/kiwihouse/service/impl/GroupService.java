package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.utils.TimeUtil;
import com.kiwihouse.dao.mapper.GroupMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.GroupDto;
import com.kiwihouse.service.PrivilegeService;
import com.kiwihouse.util.ResultUtil;
import com.kiwihouse.vo.entire.ResultList;
import com.kiwihouse.vo.kiwihouse.GroupAddVo;
import com.kiwihouse.vo.kiwihouse.GroupQueryVo;
import com.kiwihouse.vo.kiwihouse.GroupUpdateVo;

/**
 * @author yjzn
 * @date 2020-03-05-下午 3:49
 */
@Service
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS)
public class GroupService {
    private final Logger logger = LoggerFactory.getLogger(GroupService.class);

    @Autowired
    GroupMapper groupMapper;
    @Autowired
    PrivilegeService privilegeService;

    /**
     *
     * @param groupQueryVo
     * @return
     */
    public Map<String, Object> queryInfo(GroupQueryVo groupQueryVo) {
    	 Map<String, Object> map = new HashMap<String, Object>();
 		map.put("count",  groupMapper.queryInfoRow(groupQueryVo));
 		List<GroupDto> list = new ArrayList<GroupDto>();
 		if (groupQueryVo.getPage() != null) {
 			groupQueryVo.setPage((groupQueryVo.getPage() - 1) * groupQueryVo.getLimit());
 			list  = groupMapper.queryInfo(groupQueryVo);
 			list.forEach(groupDto -> {
 	            String cron = groupDto.getCron();
 	            if(!StringUtils.isBlank(cron) && !"ncron".equals(cron)){
 	                String[] crons = cron.split(" ");
 	                groupDto.setTime(crons[2]+":"+crons[1]+":"+crons[0]);
 	                groupDto.setDate(crons[3]);
 	                String cron4 = crons[4];
 	                if("*".equals(cron4)){
 	                    groupDto.setMonths(new String[]{"1","2","3","4","5","6","7","8","9","10","11","12"});
 	                }else{
 	                    String[] split = cron4.split(",");
 	                    groupDto.setMonths(split);
 	                }
 	            }
 	        });
 		} else {
 			
 		}
 		
 		map.put("data", list);
		return map;
    }

    /**
     *
     * @param groupAddVo
     * @return
     */
    public Response addInfo(GroupAddVo groupAddVo) {
        //groupAddVo.setCron("ncron");
        groupAddVo.setAddTime(TimeUtil.getCurrentTime());
        return new Response().Success(6666,"return a add success").addData("add",ResultUtil.verifyAdd(groupMapper.addInfo(groupAddVo)));
    }

    /**
     * delete group by groupId
     * @param groupId 分组ID
     * @param adminId 管理员ID
     * @return
     */
    public ResultList deleteInfo(String groupId, Integer roleId) {

        //不能删除非空分组
        if(!verifyEqpt(groupId)){
            return new ResultList(Code.DELETE_FAIL.getCode(),"不能删除非空分组",null);
        }

        if(roleId == 100){
            //一级管理员执行删除
            return doDelete(groupId);
        }else{
            //判断只能删除属于自己的分组
            if(verifyGroupId(groupId,roleId)){
                return doDelete(groupId);
            }else{
                return new ResultList(Code.PRIVILEGE_FAIL.getCode(),"没有删除权限",null);
            }
        }
    }

    /**
     * 执行删除操作
     * @param groupId
     * @return
     */
    private ResultList doDelete(String groupId){
        return ResultUtil.verifyDelete(groupMapper.toDelete(groupId));
    }

    private boolean verifyEqpt(String groupId){
        List<String> list = groupMapper.queryEqpts(groupId);
        return list.isEmpty();
    }
    /**
     * 判断该分组是否属于该管理员
     * @param groupId
     * @param adminId
     * @return
     */
    private boolean verifyGroupId(String groupId, Integer roleId){
        List<String> list = groupMapper.queryGroups(roleId);
        return list.contains(groupId);
    }

    /**
     * 修改分组信息
     *  1.如果修改分组所属管理员需要将设备对应的管理员同步修改
     * @param groupUpdateVo
     * @return
     */
    public ResultList updateInfo(GroupUpdateVo groupUpdateVo) {
        String doAdminId = groupUpdateVo.getDoAdminId();
        if(privilegeService.isTopMg(doAdminId)) {
            //一级管理员直接操作
            groupUpdateVo.setDoAdminId(null);
            return doUpdate(groupUpdateVo);
        }else{
            //其他管理员需判断该分组是否属于他
            return doUpdate(groupUpdateVo);
        }
    }

    @Transactional(rollbackFor = Exception.class)
    public ResultList doUpdate(GroupUpdateVo groupUpdateVo){
       // String adminId = groupUpdateVo.getAdminId();
        //if(StringUtils.isBlank(adminId)){
        //    return ResultUtil.verifyUpdate(updateGroup(groupUpdateVo));
        //}else{
            Integer groupRow = updateGroup(groupUpdateVo);
            if(0==groupRow){
                return new ResultList(Code.UPDATE_NULL.getCode(),Code.UPDATE_NULL.getMsg(),null);
            }else{
                return ResultUtil.verifyUpdate(updateEqpt(groupUpdateVo));
            }
       // }
    }

    public Integer updateGroup(GroupUpdateVo groupUpdateVo){
        return groupMapper.updateGroup(groupUpdateVo);
    }
    public Integer updateEqpt(GroupUpdateVo groupUpdateVo){
        return groupMapper.updateEqpt(groupUpdateVo);
    }
    /**
     * 	查询分组信息
     * @param groupAddVo
     * @return
     */
	public GroupDto selectOneInfo(GroupQueryVo groupQueryVo) {
		// TODO Auto-generated method stub
		return groupMapper.selectOneInfo(groupQueryVo);
	}
}
