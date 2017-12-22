package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.FileSetting;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.FileInfo;
import com.studio.tensor.ldm.service.impl.FileInfoServiceImpl;

@Controller
@RequestMapping("/file")
public class FileController 
{
	@Autowired
	FileInfoServiceImpl fileInfoServiceImpl;
	
	@Autowired
	FileSetting fileSetting;
	
	@ResponseBody
	@RequestMapping("/saveFile")
	public ResultBean saveFile(MultipartFile file)
	{
		return ResultBean.tokenKeyValid(fileInfoServiceImpl.saveFile(file));
	}
	
	@ResponseBody
	@RequestMapping("/saveData")
	public ResultBean saveData(String fileUrl, Integer belongUserId, String tag)
	{
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileUrl(fileUrl);
		fileInfo.setBelongUserId(belongUserId);
		fileInfo.setTag(tag);
		return ResultBean.tokenKeyValid(fileInfoServiceImpl.insertFileByUser(fileInfo));
	}
	
	@ResponseBody
	@RequestMapping("/selectFileInfoByUserId")
	public ResultBean selectFileInfoByUserId(Integer userId)
	{
		return ResultBean.tokenKeyValid(
				fileInfoServiceImpl.selectFileInfoByUserId(userId));
	}
	
	@ResponseBody
	@RequestMapping("/selectByUserIdAndTag")
	public ResultBean selectByUserIdAndTag(Integer userId, String tag)
	{
		return ResultBean.tokenKeyValid(
				fileInfoServiceImpl.selectByUserIdAndTag(userId, tag));
	}
	
	@ResponseBody
	@RequestMapping("/selectTagByUserId")
	public ResultBean selectTagByUserId(Integer userId)
	{
		return ResultBean.tokenKeyValid(
				fileInfoServiceImpl.selectTagByUserId(userId));
	}
}
