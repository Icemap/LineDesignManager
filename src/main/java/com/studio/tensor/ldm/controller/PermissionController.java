package com.studio.tensor.ldm.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.RoleInfo;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.service.impl.APIServiceImpl;
import com.studio.tensor.ldm.service.impl.PermissionServiceImpl;
import com.studio.tensor.ldm.service.impl.UserInfoServiceImpl;
import com.studio.tensor.ldm.utils.FileUtils;

@Controller
@RequestMapping("/permission")
public class PermissionController
{
	@Autowired
	PermissionServiceImpl permissionServiceImpl;
	
	@ResponseBody
	@RequestMapping("/getTree")
	public ResultBean getTree()
	{
		return ResultBean.tokenKeyValid(permissionServiceImpl.getAllTree());
	}
}
