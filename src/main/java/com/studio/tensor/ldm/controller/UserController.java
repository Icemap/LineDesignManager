package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.studio.tensor.ldm.anno.RegisterToAPI;
import com.studio.tensor.ldm.anno.RegisterToAPIController;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.UserInfo;
import com.studio.tensor.ldm.service.impl.UserInfoServiceImpl;
import com.studio.tensor.ldm.utils.HashUtils;
import com.studio.tensor.ldm.utils.StringUtils;

@Controller
@RegisterToAPIController
@RequestMapping("/user")
public class UserController
{
}
