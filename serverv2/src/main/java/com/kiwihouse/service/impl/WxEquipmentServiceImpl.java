package com.kiwihouse.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.DataType;
import com.kiwihouse.common.utils.RedisUtil;
import com.kiwihouse.dao.entity.AuthUser;
import com.kiwihouse.dao.entity.IMEI;
import com.kiwihouse.dao.entity.vx.dev.Add;
import com.kiwihouse.dao.entity.vx.dev.WxEquipment;
import com.kiwihouse.dao.mapper.AuthUserMapper;
import com.kiwihouse.dao.mapper.EquipmentMapper;
import com.kiwihouse.dao.mapper.ShareMapper;
import com.kiwihouse.dao.mapper.WxEquipmentMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.dto.EqptInfoDto;
import com.kiwihouse.service.WxEquipmentService;

@Service
public class WxEquipmentServiceImpl implements WxEquipmentService{

	@Autowired
	WxEquipmentMapper wxEquipmentMapper;
	@Autowired
	RedisUtil redisUtil;
	@Autowired
	AuthUserMapper authUserMapper;
	@Autowired
	EquipmentMapper equipmentMapper;
	@Autowired
	ShareMapper shareMapper;
	@Override
	public Map<String, Object> queryInfo(WxEquipment wxEquipment) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		AuthUser auth = authUserMapper.selectByPrimaryKey(wxEquipment.getUserId());
		if(auth == null) {
			return map;
		}
		if(auth.getUsername().equals(DataType.admin)) {
			wxEquipment.setUserId(null);
		}
			//用户本身的设备列表
			List<WxEquipment> list = wxEquipmentMapper.querInfoByUserIdPage(wxEquipment);
			//分享的设备
			List<WxEquipment> shareDevList = wxEquipmentMapper.queryUserShareDevList(wxEquipment);
			System.out.println(redisUtil.hasKey("867808043290616"));
			//分享的设备
			list.forEach(eqpt -> {
				eqpt.setShareBy(0);
				eqpt.setEqptStatus(String.valueOf(Code.NOTONLINE.getCode()));
                  if (redisUtil.hasKey(eqpt.getImei())) {
                	  eqpt.setEqptStatus(String.valueOf(Code.ONLINE.getCode()));
                	  eqpt.setOnline(true);
                  }
                  for(int i=0;i<shareDevList.size();i++) {
                	  shareDevList.get(i).setShareBy(1);
                	  shareDevList.get(i).setEqptStatus(String.valueOf(Code.NOTONLINE.getCode()));
                      if (redisUtil.hasKey(shareDevList.get(i).getImei())) {
                    	  shareDevList.get(i).setEqptStatus(String.valueOf(Code.ONLINE.getCode()));
                    	  shareDevList.get(i).setOnline(true);
                      }
              	  	if(shareDevList.get(i).getImei().equals(eqpt.getImei())) {
              	  		shareDevList.remove(i);
              	  	}
                  }
			});
			shareDevList.forEach(eqpt ->{
				eqpt.setShareBy(1);
				eqpt.setEqptStatus(String.valueOf(Code.NOTONLINE.getCode()));
				if (redisUtil.hasKey(eqpt.getImei())) {
              	  eqpt.setEqptStatus(String.valueOf(Code.ONLINE.getCode()));
              	  eqpt.setOnline(true);
                }
			});
			list.addAll(shareDevList);
			map.put("data", list);
			map.put("msg", Code.QUERY_SUCCESS.getMsg());
			map.put("count", list.size());
			map.put("code", 0);
		
		return map;
	}

	@Override
	public Response updateInfo(Add wxEquipment) {
		// TODO Auto-generated method stub
		try {
			wxEquipmentMapper.updateByImei(wxEquipment);
			return new Response().Success(Code.UPDATE_SUCCESS,Code.UPDATE_SUCCESS.getMsg());
		} catch (Exception e) {
			// TODO: handle exception
			return new Response().Fail(Code.UPDATE_FAIL,Code.UPDATE_FAIL.getMsg());
		}
		
		
		
	}

	@Override
	public Response addInfo(Add wxEquipment) {
		WxEquipment e = wxEquipmentMapper.selectByImei(wxEquipment.getImei());
		if(e == null || e.getEqptId() == null){
			// TODO Auto-generated method stub
			wxEquipmentMapper.insertSelective(wxEquipment);
			/*添加关联信息*/
			wxEquipmentMapper.inertWxEqAndUser(wxEquipment.getEqptId(),wxEquipment.getUserId());
			/*入平台库*/
			EqptInfoDto eqptInfoDto =  equipmentMapper.queryInfoByImei(wxEquipment.getImei());
			if(eqptInfoDto == null) {
				eqptInfoDto = new EqptInfoDto(wxEquipment.getEqptId(),
						wxEquipment.getEqptSn(),wxEquipment.getImei(),
						wxEquipment.getEqptName(),wxEquipment.getProvince(),
						wxEquipment.getCity(),wxEquipment.getDistrict(),
						wxEquipment.getEqptAddr(),wxEquipment.getLongitude(),
						wxEquipment.getLatitude(),wxEquipment.getUserId());
				List<EqptInfoDto> list = new ArrayList<EqptInfoDto>();
				eqptInfoDto.setEqptType("0");
				list.add(eqptInfoDto);
				equipmentMapper.insertOrUpdateBatch(list);
			}
			return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
		}else{
			WxEquipment dev = wxEquipmentMapper.selectByImeiAndUserId(wxEquipment.getImei(),wxEquipment.getUserId());
			if(dev == null) {
				wxEquipmentMapper.updateByImei(wxEquipment);
				//wxEquipmentMapper.inertWxEqAndUser(e.getEqptId(),wxEquipment.getUserId());
				wxEquipmentMapper.deleteWxEq(e.getEqptId());
				/*添加关联信息*/
				wxEquipmentMapper.inertWxEqAndUser(e.getEqptId(),wxEquipment.getUserId());
				return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
			}else {
//				wxEquipmentMapper.deleteWxEq(dev.getEqptId());
//				wxEquipmentMapper.inertWxEqAndUser(dev.getEqptId(),dev.getUserId());
				return new Response().Fail(Code.ADD_FAIL_EXIT,Code.ADD_FAIL_EXIT.getMsg());
			}
		}
	}

	@Override
	public Response deleteInfo(String imeis, Integer userId) {
		// TODO Auto-generated method stub
		String [] arrImei = imeis.split(",");
		for(int i = 0;i<arrImei.length;i++) {
			WxEquipment wxEquipment =wxEquipmentMapper.selectByImei(arrImei[i]);
			//wxEquipmentMapper.deleteByImei(arrImei[i]);
			wxEquipmentMapper.deleteWxEqAndUser(wxEquipment.getEqptId(),userId);
			shareMapper.delete(arrImei[i],userId);
			//删除缓存
			String key = "_" + userId + "_" + arrImei[i];
			if(redisUtil.hasKey(key)) {
				redisUtil.delete(key);
			}
		}
		return new Response().Success(Code.DELETE_SUCCESS,Code.DELETE_SUCCESS.getMsg());
	}

	@Override
	public Map<String, Object>  queryShareDevList(List<IMEI> listIMEI,List<Integer> listUser) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		List<WxEquipment> list = wxEquipmentMapper.queryShareDevList(listIMEI);
//		list.forEach(xx ->{
//			xx.setShareBy(1);
//			xx.setSharerId(listUser.get(index));
//		});
		Stream.iterate(0, i -> i + 1).limit(list.size()).forEach(i -> {  
		       list.get(i).setShareBy(1);  
			   list.get(i).setSharerId(listUser.get(i));
		 });  
		
		map.put("data", list);
		map.put("msg", Code.QUERY_SUCCESS.getMsg());
		map.put("count", list.size());
		map.put("code", 0);
		return map;
	}

	@Override
	public Response queryOneDev(WxEquipment wxEquipment) {
		// TODO Auto-generated method stub
		List<WxEquipment> list = wxEquipmentMapper.querInfoByUserIdPage(wxEquipment);
		WxEquipment e = null;
		if(list == null || list.size() == 0) {
			e = wxEquipmentMapper.selectShareDev(wxEquipment);
		}else {
			e = list.get(0);
		}
		return new Response().Success(Code.QUERY_SUCCESS,Code.QUERY_SUCCESS.getMsg()).addData("data", e);
	}

}
