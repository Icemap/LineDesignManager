package com.studio.tensor.ldm.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.studio.tensor.ldm.bean.LoginResult;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.dao.ApiInfoMapper;
import com.studio.tensor.ldm.service.impl.PermissionServiceImpl;
import com.studio.tensor.ldm.service.impl.RedisServiceImpl;
import com.studio.tensor.ldm.service.impl.UserInfoServiceImpl;
import com.studio.tensor.ldm.utils.SpringContextUtils;

public class PermissionFilter implements Filter
{
	ApiInfoMapper apiInfoMapper;
	PermissionServiceImpl permissionServiceImpl;
	RedisServiceImpl redisServiceImpl;
	UserInfoServiceImpl userInfoServiceImpl;
	
	@Override
	public void init(FilterConfig arg0) throws ServletException
	{
		apiInfoMapper = SpringContextUtils.getBean("apiInfoMapper", ApiInfoMapper.class);
		permissionServiceImpl = SpringContextUtils.getBean("permissionServiceImpl", PermissionServiceImpl.class);
		redisServiceImpl = SpringContextUtils.getBean("redisServiceImpl", RedisServiceImpl.class);
		userInfoServiceImpl = SpringContextUtils.getBean("userInfoServiceImpl", UserInfoServiceImpl.class);
	}
	
	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException
	{
		HttpServletRequest hreq = (HttpServletRequest) req;
		String path = hreq.getServletPath();
		String token = hreq.getParameter("token");
		
		//黑名单模式，无记录的直接通过
		if(!permissionServiceImpl.isApiExist(path))
		{
			if(token != null && redisServiceImpl.isExist(token))
				redisServiceImpl.refreshToken(token);
			chain.doFilter(req, res);
			return;
		}  
		
		//得到用户的RoleId
		if(path.equals("/user/loginBackground"))
		{
			ResultBean adminBean = userInfoServiceImpl.userLogin(
					hreq.getParameter("adminAccount"), 
					hreq.getParameter("adminPassword"));
			if(adminBean.getCode().equals(200))
				token = ((LoginResult)(adminBean.getResultBean())).getToken();
		}
			
		if(token != null)
		{
			String sRoleId = redisServiceImpl.getUserRoleId(token);
			if(sRoleId != null && !sRoleId.equals(""))
			{
				Integer userRoleId = Integer.parseInt(sRoleId);
				if(permissionServiceImpl.isRoleAllowThisApi(path, userRoleId))
				{
					redisServiceImpl.refreshToken(token);
					chain.doFilter(req, res);
					return;
				}
			}
		}

		HttpServletResponse response = (HttpServletResponse) res;
        response.getWriter().append(new Gson().toJson(ResultBean.permissionDenied()));
	}

	@Override
	public void destroy()
	{
		
	}
}
