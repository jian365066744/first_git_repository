package com.ronglian.qd_qrcode.test.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ronglian.qd_qrcode.common.utils.AppUtils;
import com.ronglian.qd_qrcode.common.utils.cache.CacheUtils;
import com.ronglian.qd_qrcode.test.entity.UserInfo;
import com.ronglian.qd_qrcode.test.service.UserInfoService;
@Controller
@RequestMapping("/test")
public class UserInfoController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static String USERINFO_LIST_KEY = "userinfo_initList";
	
	@Autowired
	private UserInfoService userinfoService;
	@Autowired
	private JmsTemplate testjmsTemplate;
	
	@RequestMapping("/")
	@ResponseBody
	public String index(){
		logger.info("request index........");
		return "index";
	}
	@RequestMapping("/info")
	public String info(ModelMap map,HttpServletRequest request){
		request.setAttribute("testList", CacheUtils.get("userinfo_initList"));;
		//return "info";
		return "MonitorThread";
	}
	
	@RequestMapping("/insert")
	@ResponseBody
	public String insert(ModelMap map,UserInfo entity,HttpServletRequest request){
		try {
			List<UserInfo> userinfoList = null;
			if(CacheUtils.get(USERINFO_LIST_KEY)==null){				
				userinfoList = new ArrayList<UserInfo>();
			}else{
				userinfoList = CacheUtils.get(USERINFO_LIST_KEY);
			}
			userinfoService.save(entity);
			userinfoList.add(entity);
			CacheUtils.set(USERINFO_LIST_KEY,60, userinfoList);
			testjmsTemplate.convertAndSend("jfpt_qd_queuetest",entity.getName());
		} catch (Exception e) {
			return "no no no!";
		}
		return "OK";
	}
	
	@RequestMapping("/jms")
	@ResponseBody
	public String testJms() {
		List<UserInfo> users = CacheUtils.get(USERINFO_LIST_KEY);
		List<Map<String,Object>> lists = new ArrayList<Map<String,Object>>();
		if(null != users && users.size() > 0) {
			for(UserInfo user : users) {
				Map<String,Object> maps = new HashMap<String,Object>();
				maps.put("id", user.getId());
				maps.put("name", user.getName());
				lists.add(maps);
			}
		}
		String json = AppUtils.objlist2json(lists);
		return json;
	}
	
}