package com.studio.tensor.ldm.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.FileSetting;
import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.dao.FileInfoMapper;
import com.studio.tensor.ldm.pojo.FileInfo;
import com.studio.tensor.ldm.service.FileService;
import com.studio.tensor.ldm.utils.FileUtils;

@Service
public class FileInfoServiceImpl implements FileService
{
	@Autowired
	FileInfoMapper fileInfoMapper;

	@Autowired
	TokenServiceImpl tokenServiceImpl;
	
	@Autowired
	FileSetting fileSetting;
	
	@Autowired
	RoleInfoServiceImpl roleInfoServiceImpl;
	
	@Override
	public ResultBean insertFileByUser(FileInfo fileInfo, String token, String apiKey)
	{
		Integer roleId = tokenServiceImpl.confirmTokenAndReturnRoleId(token);
		if(roleId == null) return ResultBean.tokenKeyNotValid();
		if(!roleInfoServiceImpl.hasPermission(roleId, apiKey))
			return ResultBean.permissionDenied();
		
		return ResultBean.tokenKeyValid(fileInfoMapper.insertSelective(fileInfo));
	}

	@Override
	public ResultBean saveFile(MultipartFile file, String token, String apiKey)
	{
		Integer roleId = tokenServiceImpl.confirmTokenAndReturnRoleId(token);
		if(roleId == null) return ResultBean.tokenKeyNotValid();
		if(!roleInfoServiceImpl.hasPermission(roleId, apiKey))
			return ResultBean.permissionDenied();
			
		String fileName = new Date().getTime() + "";
		FileUtils.saveFile(FileUtils.safeGetInputStream(file), 
				fileName, fileSetting.getSaveFilePath());
		return ResultBean.tokenKeyValid(fileSetting.getGetFilePath() + fileName);
	}
}
