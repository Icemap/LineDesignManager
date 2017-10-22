package com.studio.tensor.ldm.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.FileSetting;
import com.studio.tensor.ldm.utils.FileUtils;

@Controller
@RequestMapping("/test")
public class TestController
{
	@Autowired
	FileSetting fileSetting;
	
	@ResponseBody
	@RequestMapping("/save")
	public String saveFile(@RequestParam(value = "file",required = true)MultipartFile file)
	{
		String fileName = new Date().getTime() + file.getOriginalFilename();
		FileUtils.saveFile(FileUtils.safeGetInputStream(file), 
				fileName, fileSetting.getSaveFilePath());
		return fileSetting.getGetFilePath() + fileName;
	}
}
