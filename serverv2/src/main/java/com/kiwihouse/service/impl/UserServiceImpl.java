package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.controller.account.params.UserParams;
import com.kiwihouse.dao.entity.AuthRole;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.entity.AuthUserRole;
import com.kiwihouse.dao.entity.UserDev;
import com.kiwihouse.dao.mapper.AuthRoleMapper;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.dao.mapper.AuthUserRoleMapper;
import com.kiwihouse.dao.mapper.EquipmentMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.UserService;
import com.kiwihouse.util.CommonUtil;
import com.kiwihouse.util.Md5Util;

/**
 * @author tomsun28
 * @date 21:15 2018/3/17
 */
@Service("UserService")
public class UserServiceImpl implements UserService {

    @Autowired
    private AuthUserMapper authUserMapper;

    @Autowired
    private AuthUserRoleMapper authUserRoleMapper;

    @Autowired
    AuthRoleMapper authRoleMapper;
    
    @Autowired
    EquipmentMapper equipmentMapper;
    
    @Override
    public String loadAccountRole(Integer appId) throws DataAccessException {

        return authUserMapper.selectUserRoles(appId);
    }

    @Override
    public List<AuthUser> getUserList() throws DataAccessException {
        return authUserMapper.selectUserList();
    }

    @Override
    public List<AuthUser> getUserListByRoleId(Integer roleId) throws DataAccessException {
        return authUserMapper.selectUserListByRoleId(roleId);
    }

    @Override
    public boolean authorityUserRole(Integer uid, int roleId) throws DataAccessException {
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setRoleId(roleId);
        authUserRole.setUserId(uid);
        authUserRole.setCreateTime(new Date());
        authUserRole.setUpdateTime(new Date());
        return authUserRoleMapper.insert(authUserRole) == 1? Boolean.TRUE :Boolean.FALSE;
    }

    @Override
    public boolean deleteAuthorityUserRole(Integer uid, int roleId) throws DataAccessException {
        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setUserId(uid);
        authUserRole.setRoleId(roleId);
        return authUserRoleMapper.deleteByUniqueKey(authUserRole) == 1? Boolean.TRUE : Boolean.FALSE;
    }

    @Override
    public AuthUser getUserByUid(Integer uid) throws DataAccessException {

        return authUserMapper.selectByPrimaryKey(uid);
    }
    @Override
    public AuthUser getUserByUsername(String username){
        return authUserMapper.selectByUsername(username);
    }
    @Override
    public List<AuthUser> getNotAuthorityUserListByRoleId(Integer roleId) throws DataAccessException {

        return authUserMapper.selectUserListExtendByRoleId(roleId);
    }

	@Override
	public List<Map<String, Integer>> queryAuthUserByRoleUserId(Integer userId, Integer roleId) {
		// TODO Auto-generated method stub
		return authUserMapper.queryAuthUserByRoleUserId(userId,roleId);
	}

	@Override
	public Response updateByPrimaryKeySelective(AuthUser authUser) {
		// TODO Auto-generated method stub
		try {
			if(authUser.getPassword()!=null) {
				String salt = CommonUtil.getRandomString(6);
		        // 存储到数据库的密码为 MD5(原密码+盐值)
		        authUser.setPassword(Md5Util.md5(authUser.getPassword() + salt));
		        authUser.setSalt(salt);
			}
			if(authUser.getRoleId() != null) {
				//修改用户角色关联表
				authUserRoleMapper.updateByUniqueKey(authUser.getUid(),authUser.getRoleId());
			}
			if(authUser.getUsername() != null || authUser.getPhone() != null) {
				int count = authUserMapper.updateByPrimaryKeySelective(authUser);
				if(count > 0) {
					return new Response().Fail(Code.UPDATE_SUCCESS,Code.UPDATE_SUCCESS.getMsg());
				}
			}
			if(authUser.getEqptIds() != null ) {
				String [] deptArr = authUser.getEqptIds().split(",");
				List<UserDev> roleDevList = new ArrayList<UserDev>();
		        for(int i = 0;i<deptArr.length;i++) {
		        	UserDev userDev = new UserDev(authUser.getUid(),Integer.valueOf(deptArr[i]));
		        	roleDevList.add(userDev);
		        }
		        equipmentMapper.deleteUserDev(authUser.getUid());
		        equipmentMapper.insertUserDevList(roleDevList);
			}
			return new Response().Fail(Code.UPDATE_SUCCESS,Code.UPDATE_SUCCESS.getMsg());
		}catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.UPDATE_FAIL,Code.UPDATE_FAIL.getMsg());
		}
	}

	@Override
	public Map<String, Object> getList(Integer page, Integer limit,Integer roleId,Integer userId) {
		// TODO Auto-generated method stub
		 Map<String, Object> map = new HashMap<String, Object>();
		 List<AuthUser> list = null;
		 Integer adminId = null;
		 //AuthRole authRole =  authRoleMapper.selectIsAdmin(userId);
		 AuthUser auth = authUserMapper.selectByPrimaryKey(userId);
		 if(auth.getUsername().equals(DataType.admin)) {
			 adminId = 2;
		 }
		 int count = 0;
		 if(limit != null) {
			 list = authUserMapper.getList((page - 1 ) * limit,limit,roleId,adminId);
		 }else {
			 list = authUserMapper.getList(null,null,roleId,adminId);
		 }
		 count = authUserMapper.getListCount(roleId,adminId);
		 map.put("data", list);
		 map.put("count", count);
		 return map;
	}

	@Override
	public Response insert(AuthUser authUser) {
		String salt = CommonUtil.getRandomString(6);
		authUser.setSalt(salt);
		authUser.setPassword(Md5Util.md5(authUser.getPassword() + salt));
		// TODO Auto-generated method stub
		int count = authUserMapper.insertSelective(authUser);
		if(count>0) {
			//用户角色
			AuthUserRole authUserRole = new AuthUserRole();
			authUserRole.setRoleId(authUser.getRoleId());
			authUserRole.setUserId(authUser.getUid());
			authUserRoleMapper.insert(authUserRole);
			//用户设备
			if(authUser.getEqptIds() != null && !"".equals(authUser.getEqptIds())) {
				String [] deptArr = authUser.getEqptIds().split(",");
				List<UserDev> roleDevList = new ArrayList<UserDev>();
		        for(int i = 0;i<deptArr.length;i++) {
		        	UserDev userDev = new UserDev(authUser.getUid(),Integer.valueOf(deptArr[i]));
		        	roleDevList.add(userDev);
		        }
		        equipmentMapper.insertUserDevList(roleDevList);
			}
		}
		return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
	}

	@Override
	public Response deleteBatch(String userIds) {
		// TODO Auto-generated method stub
		int count = authUserMapper.deleteBatch(userIds.split(","));
		if(count > 0) {
			equipmentMapper.deleteUserDevBatch(userIds.split(","));
		}
		return new Response().Success(Code.DELETE_SUCCESS,Code.DELETE_SUCCESS.getMsg());
	}

	@Override
	public Response shareList(Integer roleId, Integer userId) {
		// TODO Auto-generated method stub
		 Integer adminId = null;
		 //AuthRole authRole =  authRoleMapper.selectIsAdmin(userId);
		 AuthUser auth = authUserMapper.selectByPrimaryKey(userId);
		 if(auth.getUsername().equals(DataType.admin)) {
			 adminId = 2;
		 }
		 List<AuthUser> list  = authUserMapper.shareList(roleId,adminId);
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", list);
	}

	@Override
	public Response queryByPhone(String phone) {
		// TODO Auto-generated method stub
		AuthUser authUser = authUserMapper.queryByPhone(phone);
		return new Response().Success(6666, "查询成功").addData("data", authUser);
	}
	/**
	 * 修改用户的openId、和unionId
	 */
	@Override
	public void updateByVxId(UserParams params) {
		// TODO Auto-generated method stub
		authUserMapper.updateByVxId(params);
	}
}
