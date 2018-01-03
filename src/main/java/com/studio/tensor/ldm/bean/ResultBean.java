package com.studio.tensor.ldm.bean;

public class ResultBean
{
	/**
	 * 200 : 正常返回
	 * 401 : 用户权限不足
	 * 403 : 用户名错误或密码错误
	 * 405 : Token 不合法
	 * 406 : 非管理员身份
	 * 407 : 该手机已经注册
	 * 408 : 图片类型不合法
	 * 409 : 地图类型不合法
	 * 410 : 生成图片错误
	 */
	
	private Integer code;
	private String message;
	private Object resultBean;
	
	public Integer getCode()
	{
		return code;
	}
	public void setCode(Integer code)
	{
		this.code = code;
	}
	public Object getResultBean()
	{
		return resultBean;
	}
	public void setResultBean(Object resultBean)
	{
		this.resultBean = resultBean;
	}
	
	public static ResultBean tokenKeyNotValid()
	{
		ResultBean result = new ResultBean();
		result.setCode(405);
		result.setMessage("用户Token非法");
		return result;
	}

	public static ResultBean tokenKeyValid(Object resultBean)
	{
		ResultBean result = new ResultBean();
		result.setCode(200);
		result.setMessage("返回值正常");
		result.setResultBean(resultBean);
		return result;
	}
	

	public static ResultBean tokenKeyValidNotSetResult()
	{
		ResultBean result = new ResultBean();
		result.setCode(200);
		result.setMessage("返回值正常");
		return result;
	}
	
	public static ResultBean userNotExist()
	{
		ResultBean result = new ResultBean();
		result.setCode(403);
		result.setMessage("用户名错误或密码错误");
		return result;
	}

	public static ResultBean isNotAdmin()
	{
		ResultBean result = new ResultBean();
		result.setCode(406);
		result.setMessage("非管理员身份");
		return result;
	}
	
	public static ResultBean permissionDenied()
	{
		ResultBean result = new ResultBean();
		result.setCode(401);
		result.setMessage("用户权限不足");
		return result;
	}
	

	public static ResultBean phoneNumExist()
	{
		ResultBean result = new ResultBean();
		result.setCode(407);
		result.setMessage("该手机已经注册");
		return result;
	}
	
	public static ResultBean picTypeIllegal()
	{
		ResultBean result = new ResultBean();
		result.setCode(408);
		result.setMessage("图片类型不合法");
		return result;
	}
	
	public static ResultBean mapTypeIllegal()
	{
		ResultBean result = new ResultBean();
		result.setCode(409);
		result.setMessage("地图类型不合法");
		return result;
	}
	
	public static ResultBean makePicError()
	{
		ResultBean result = new ResultBean();
		result.setCode(410);
		result.setMessage("生成图片错误");
		return result;
	}
	
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
}
