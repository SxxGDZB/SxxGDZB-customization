package com.kiwihouse.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kiwihouse.common.bean.Code;
import com.kiwihouse.common.bean.UserInfo;
import com.kiwihouse.common.utils.RedisUtil;
import com.kiwihouse.dao.entity.vx.dev.Add;
import com.kiwihouse.dao.entity.vx.dev.WxEquipment;
import com.kiwihouse.dao.mapper.WxEquipmentMapper;
import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.service.WxEquipmentService;

@Service
public class WxEquipmentServiceImpl implements WxEquipmentService{

	@Autowired
	WxEquipmentMapper wxEquipmentMapper;
	@Autowired
	RedisUtil redisUtil;
	@Override
	public Map<String, Object> queryInfo(WxEquipment wxEquipment) {
		// TODO Auto-generated method stub
		Map<String, Object> map = new HashMap<String, Object>();
		List<WxEquipment> list = wxEquipmentMapper.querInfoByUserIdPage(wxEquipment);
		if (wxEquipment != null && wxEquipment.getOnline() != null) {// 如果查询设备状态
			list.forEach(eqpt -> {
				eqpt.setEqptStatus(String.valueOf(Code.NOTONLINE.getCode()));
                  if (redisUtil.hasKey(eqpt.getImei())) {
                	  eqpt.setEqptStatus(String.valueOf(Code.ONLINE.getCode()));
                  }
			});
			List<WxEquipment> collect;
			if ("1".equals(wxEquipment.getOnline())) {
				collect = list.stream().filter(
						eqptInfoDto -> eqptInfoDto.getEqptStatus().equals(String.valueOf(Code.ONLINE.getCode())))
						.collect(Collectors.toList());
			} else if ("-1".equals(wxEquipment.getOnline())) {
				collect = list.stream().filter(
						eqptInfoDto -> eqptInfoDto.getEqptStatus().equals(String.valueOf(Code.NOTONLINE.getCode())))
						.collect(Collectors.toList());
			} else {
				collect = list;
			}
			// 手动处理分页
			map.put("data", collect);
			map.put("msg", Code.QUERY_SUCCESS.getMsg());
			map.put("count", collect.size());
			map.put("code", 0);
		} else {
			map.put("data", list);
			map.put("msg", Code.QUERY_SUCCESS.getMsg());
			map.put("count", list.size());
			map.put("code", 0);
		}
		return map;
	}

	@Override
	public Response updateInfo(Add wxEquipment) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Response addInfo(Add wxEquipment) {
		// TODO Auto-generated method stub
		int count = wxEquipmentMapper.insertSelective(wxEquipment);
		/*添加关联信息*/
		wxEquipmentMapper.inertWxEqAndUser(wxEquipment.getEqptId(),wxEquipment.getUserId());
		return new Response().Success(Code.ADD_SUCCESS,Code.ADD_SUCCESS.getMsg());
	}

	@Override
	public Response deleteInfo(String imeis, Integer userId) {
		// TODO Auto-generated method stub
		String [] arrImei = imeis.split(",");
		for(int i = 0;i<arrImei.length;i++) {
			System.out.println("----------->" + arrImei[i]);
			WxEquipment wxEquipment =wxEquipmentMapper.selectByImei(arrImei[i]);
			wxEquipmentMapper.deleteByImei(arrImei[i]);
			wxEquipmentMapper.deleteWxEqAndUser(wxEquipment.getEqptId(),userId);
		}
		return new Response().Success(Code.DELETE_SUCCESS,Code.DELETE_SUCCESS.getMsg());
	}

}
