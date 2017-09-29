package com.studio.tensor.ldm.bean;

public class ResultBean
{
	/**
	 * 200 : 正常返回
	 * 404 : Token Key不存在
	 * 405 : Token 不合法
	 */
	private Integer code;
	private String messgae;
	private Object resultBean;
	
	public Integer getCode()
	{
		return code;
	}
	public void setCode(Integer code)
	{
		this.code = code;
	}
	public String getMessgae()
	{
		return messgae;
	}
	public void setMessgae(String messgae)
	{
		this.messgae = messgae;
	}
	public Object getResultBean()
	{
		return resultBean;
	}
	public void setResultBean(Object resultBean)
	{
		this.resultBean = resultBean;
	}
	
	public static ResultBean tokenKeyNotExist()
	{
		ResultBean result = new ResultBean();
		result.setCode(404);
		result.setMessgae("用户未曾登录或登录已经过期。");
		return result;
	}
	
	public static ResultBean tokenKeyNotValid()
	{
		ResultBean result = new ResultBean();
		result.setCode(405);
		result.setMessgae("用户Token非法");
		return result;
	}

	public static ResultBean tokenKeyValid(Object resultBean)
	{
		ResultBean result = new ResultBean();
		result.setCode(200);
		result.setMessgae("返回值正常");
		result.setResultBean(resultBean);
		return result;
	}
	

	public static ResultBean tokenKeyValidNotSetResult()
	{
		ResultBean result = new ResultBean();
		result.setCode(200);
		result.setMessgae("返回值正常");
		return result;
	}
}
