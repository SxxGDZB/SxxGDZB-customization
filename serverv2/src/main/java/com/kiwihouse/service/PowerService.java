package com.kiwihouse.service;

import com.kiwihouse.domain.vo.Response;
import com.kiwihouse.vo.kiwihouse.ReportedQueryVo;

public interface PowerService {
	/**
	 * 设备功率统计
	 * @param queryPwrVo
	 * @return
	 */
	Response queryPwr(ReportedQueryVo reportedQueryVo);

}
