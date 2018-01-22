package com.studio.tensor.ldm.service.impl;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.service.RedisService;

import redis.clients.jedis.Jedis;

@Service
public class RedisServiceImpl implements RedisService
{
	private Integer TOKEN_LIFE_TIME;
	private Integer REGISTER_CODE_LIFE_TIME;
	private String SMS_PHONE_KEY_HEADER;
	private String PAY_KEY_HEADER;
	private Integer PAY_CODE_LIFE_TIME;
	
	@PostConstruct
	public void onInit()
	{
        TOKEN_LIFE_TIME = 1800;
        REGISTER_CODE_LIFE_TIME = 600;
        PAY_CODE_LIFE_TIME = 1800;
        SMS_PHONE_KEY_HEADER = "PHONE_";
        PAY_KEY_HEADER = "PAY_";
	}
	
	private Jedis getJedis()
	{
		return new Jedis("120.78.205.53", 8100);
	}
	
	@PreDestroy
	public void onDestory()
	{
	}
	
	@Override
	public void setToken(String token, String userRoleId)
	{
		Jedis jedis = getJedis();
		jedis.set(token, userRoleId);
		jedis.expire(token, TOKEN_LIFE_TIME);
		jedis.close();
	}

	@Override
	public void refreshToken(String token)
	{
		Jedis jedis = getJedis();
		jedis.expire(token, TOKEN_LIFE_TIME);
		jedis.close();
	}

	@Override
	public String getUserRoleId(String token)
	{
		Jedis jedis = getJedis();
		if(!jedis.exists(token)) return null;
		String result = jedis.get(token);
		jedis.close();
		
		return result;
	}

	@Override
	public void setConfirmCode(String phoneNum, String code)
	{
		Jedis jedis = getJedis();
		jedis.set(SMS_PHONE_KEY_HEADER + phoneNum, code);
		jedis.expire(SMS_PHONE_KEY_HEADER + phoneNum, REGISTER_CODE_LIFE_TIME);
		jedis.close();
	}

	@Override
	public Boolean getConfirmCode(String phoneNum, String code)
	{
		Jedis jedis = getJedis();
		if(!jedis.exists(SMS_PHONE_KEY_HEADER + phoneNum)) return false;
		
		String getCode = jedis.get(SMS_PHONE_KEY_HEADER + phoneNum);
		if(getCode.equals(code))
		{
			jedis.del(SMS_PHONE_KEY_HEADER + phoneNum);
			jedis.close();
			return true;
		}
		else
		{
			jedis.close();
			return false;
		}
	}

	@Override
	public Boolean isExist(String key)
	{
		Jedis jedis = getJedis();
		Boolean result = jedis.exists(key);
		jedis.close();
		return result;
	}

	@Override
	public void setPayConfirmCode(Integer userId, String code)
	{
		Jedis jedis = getJedis();
		jedis.set(PAY_KEY_HEADER + userId, code);
		jedis.expire(PAY_KEY_HEADER + userId, PAY_CODE_LIFE_TIME);
		jedis.close();
	}

	@Override
	public Boolean isPayConfirm(Integer userId, String code)
	{
		Jedis jedis = getJedis();
		if(!jedis.exists(PAY_KEY_HEADER + userId)) return false;
		String getCode = jedis.get(PAY_KEY_HEADER + userId);
		if(getCode.equals(code))
		{
			jedis.del(PAY_KEY_HEADER + userId);
			jedis.close();
			return true;
		}
		else
		{
			jedis.close();
			return false;
		}
	}
}
