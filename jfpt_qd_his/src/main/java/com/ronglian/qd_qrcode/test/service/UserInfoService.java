package com.ronglian.qd_qrcode.test.service;
import java.util.List;

import com.ronglian.qd_qrcode.test.entity.UserInfo;

public interface UserInfoService {
	public UserInfo findById(Integer id) throws Exception;

	public UserInfo save(UserInfo entity) throws Exception;
	
	public List<UserInfo> findAll() throws Exception;
	
	public List<UserInfo> findBySql(String name) throws Exception;
}