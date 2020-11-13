package com.kiwihouse.service;

import com.kiwihouse.dao.entity.Share;
import com.kiwihouse.domain.vo.Response;

public interface ShareService {
	/**
	 * 添加用户设备分享
	 * @param share
	 * @return
	 */
	Response insert(Share share);

}
