package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.anno.RegisterToAPI;
import com.studio.tensor.ldm.anno.RegisterToAPIController;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.FileInfo;
import com.studio.tensor.ldm.service.impl.FileInfoServiceImpl;

@Controller
@RegisterToAPIController
@RequestMapping("/file")
public class FileController 
{
	@Autowired
	FileInfoServiceImpl fileInfoServiceImpl;
	
	@ResponseBody
	@RequestMapping("/saveFile")
	@RegisterToAPI(apiKey="file-saveFile", apiValue="保存矢量文件并返回URL")
	public ResultBean saveFile(
			@RequestParam(value = "file",required = true)MultipartFile file,
			@RequestParam(value = "token",required = true)String token)
	{
		return fileInfoServiceImpl.saveFile(file, token, "file-saveFile");
	}
	
	@ResponseBody
	@RequestMapping("/saveData")
	@RegisterToAPI(apiKey="file-saveData", apiValue="保存矢量文件URL及其他数据")
	public ResultBean saveData(String fileUrl, Integer belongUserId,
			String tag, String token)
	{
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileUrl(fileUrl);
		fileInfo.setBelongUserId(belongUserId);
		fileInfo.setTag(tag);
		
		return fileInfoServiceImpl.insertFileByUser(fileInfo, token, "file-saveData");
	}
}
