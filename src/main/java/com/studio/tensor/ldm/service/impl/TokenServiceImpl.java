package com.studio.tensor.ldm.service.impl;

import java.util.Date;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.stereotype.Service;

import com.studio.tensor.ldm.service.TokenService;
import com.studio.tensor.ldm.utils.HashUtils;

import redis.clients.jedis.Jedis;

@Service
public class TokenServiceImpl implements TokenService
{
	private Jedis jedis;
	private Integer TOKEN_LIFE_TIME;
	private Integer REGISTER_CODE_LIFE_TIME;
	private String SMS_PHONE_KEY_HEADER;
	
	@PostConstruct
	public void onInit()
	{
        jedis = new Jedis("wangqizhi.top");
        TOKEN_LIFE_TIME = 1800;
        REGISTER_CODE_LIFE_TIME = 600;
        SMS_PHONE_KEY_HEADER = "PHONE_";
	}
	
	@PreDestroy
	public void onDestory()
	{
		jedis.close();
	}
	
	@Override
	public String loginSetAndReturnToken(Integer id, Integer roleId)
	{
		String tokenRaw = new Date().getTime() + "" + Math.random();
		String token = HashUtils.getMD5(tokenRaw);
		jedis.set(token, roleId + "");
		return token;
	}

	@Override
	public Boolean confirmToken(String token)
	{
		return jedis.exists(token);
	}

	@Override
	public void refleshKeyLifeTime(String token)
	{
		jedis.expire(token, TOKEN_LIFE_TIME);
	}

	@Override
	public Integer confirmTokenAndReturnRoleId(String token)
	{
		if(!jedis.exists(token)) return null;
		return Integer.parseInt(jedis.get(token));
	}

	@Override
	public String getRegisterCode(String key)
	{
		key = SMS_PHONE_KEY_HEADER + key;
		String timeStamp = new Date().getTime() + "";
		String code = timeStamp.substring(
				timeStamp.length() - 3, timeStamp.length());
		code += (int)(Math.random() * 900 + 100);
		
		jedis.set(key, code);
		jedis.expire(key, REGISTER_CODE_LIFE_TIME);
		return code;
	}

	@Override
	public Boolean compareRegisterCode(String key, String code)
	{
		key = SMS_PHONE_KEY_HEADER + key;
		if(jedis.exists(key) && jedis.get(key).equals(code))
		{
			jedis.del(key);
			return true;
		}
		else
			return false;
	}
}
