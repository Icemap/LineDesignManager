package com.studio.tensor.ldm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.studio.tensor.ldm.anno.RegisterToAPI;
import com.studio.tensor.ldm.anno.RegisterToAPIController;
import com.studio.tensor.ldm.bean.AdminSetting;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.utils.ClassUtils;
import com.studio.tensor.ldm.utils.FileUtils;
import com.studio.tensor.ldm.utils.MethodsUtils;

@Service
public class APIServiceImpl
{
	private AdminSetting adminSetting;
	private Map<String, String> apiMap = new HashMap<>();
	
	@PostConstruct
	public void onInit()
	{
		adminSetting= new Gson().fromJson(
				FileUtils.readResourcesByLines("json_prop/admin_setting.json"),
				AdminSetting.class);
		
        String pkg = "com.studio.tensor.ldm.controller";
        List<Class<?>> classList = ClassUtils.getClassList(
        		pkg, true, RegisterToAPIController.class);
        List<RegisterToAPI> apiList = MethodsUtils.getAnno(classList, RegisterToAPI.class);
        for(RegisterToAPI apiBean : apiList)
        	apiMap.put(apiBean.apiKey(), apiBean.apiValue());
        
        System.out.println(new Gson().toJson(apiMap));
	}
	
	public ResultBean getApiMap(String account, String password)
	{
		if(adminSetting.isAdmin(account, password))
			return ResultBean.isNotAdmin();
		
		return ResultBean.tokenKeyValid(apiMap);
	}
}
