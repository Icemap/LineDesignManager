package com.studio.tensor.ldm.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.service.RedisService;

import redis.clients.jedis.Jedis;

@Service
public class RedisServiceImpl implements RedisService
{
	private Jedis jedis;
	private Integer TOKEN_LIFE_TIME;
	private Integer REGISTER_CODE_LIFE_TIME;
	private String SMS_PHONE_KEY_HEADER;
	private String PAY_KEY_HEADER;
	private Integer PAY_CODE_LIFE_TIME;
	
	@PostConstruct
	public void onInit()
	{
        jedis = new Jedis("120.78.205.53", 8100);
        TOKEN_LIFE_TIME = 1800;
        REGISTER_CODE_LIFE_TIME = 600;
        PAY_CODE_LIFE_TIME = 1800;
        SMS_PHONE_KEY_HEADER = "PHONE_";
        PAY_KEY_HEADER = "PAY_";
	}
	
	@PreDestroy
	public void onDestory()
	{
		jedis.close();
	}
	
	@Override
	public void setToken(String token, Integer userRoleId)
	{
		jedis.set(token, userRoleId.toString());
		jedis.expire(token, TOKEN_LIFE_TIME);
	}

	@Override
	public void refreshToken(String token)
	{
		jedis.expire(token, TOKEN_LIFE_TIME);
	}

	@Override
	public String getUserRoleId(String token)
	{
		if(!jedis.exists(token)) return null;
		return jedis.get(token);
	}

	@Override
	public void setConfirmCode(String phoneNum, String code)
	{
		jedis.set(SMS_PHONE_KEY_HEADER + phoneNum, code);
		jedis.expire(SMS_PHONE_KEY_HEADER + phoneNum, REGISTER_CODE_LIFE_TIME);
	}

	@Override
	public Boolean getConfirmCode(String phoneNum, String code)
	{
		if(!jedis.exists(SMS_PHONE_KEY_HEADER + phoneNum)) return false;
		
		String getCode = jedis.get(SMS_PHONE_KEY_HEADER + phoneNum);
		if(getCode.equals(code))
		{
			jedis.del(SMS_PHONE_KEY_HEADER + phoneNum);
			return true;
		}
		else
			return false;
	}

	@Override
	public Boolean isExist(String key)
	{
		return jedis.exists(key);
	}

	@Override
	public void setPayConfirmCode(Integer userId, String code)
	{
		jedis.set(PAY_KEY_HEADER + userId, code);
		jedis.expire(PAY_KEY_HEADER + userId, PAY_CODE_LIFE_TIME);
	}

	@Override
	public Boolean isPayConfirm(Integer userId, String code)
	{
		if(!jedis.exists(PAY_KEY_HEADER + userId)) return false;
		String getCode = jedis.get(PAY_KEY_HEADER + userId);
		if(getCode.equals(code))
		{
			jedis.del(PAY_KEY_HEADER + userId);
			return true;
		}
		else
			return false;
	}
}