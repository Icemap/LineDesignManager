package com.studio.tensor.ldm.bean;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.studio.tensor.ldm.utils.FileUtils;

@Component
public class FileSetting
{
	private String saveFilePath;
	private String getFilePath;
	
	@PostConstruct
	public void onInit()
	{
		FileSetting fs = new Gson().fromJson(
					FileUtils.readResourcesByLines("json_prop/fileSetting.json"),
					FileSetting.class);
		saveFilePath = fs.getSaveFilePath();
		getFilePath = fs.getGetFilePath();
	}
	
	public String getSaveFilePath()
	{
		return saveFilePath;
	}
	public void setSaveFilePath(String saveFilePath)
	{
		this.saveFilePath = saveFilePath;
	}
	public String getGetFilePath()
	{
		return getFilePath;
	}
	public void setGetFilePath(String getFilePath)
	{
		this.getFilePath = getFilePath;
	}
}
