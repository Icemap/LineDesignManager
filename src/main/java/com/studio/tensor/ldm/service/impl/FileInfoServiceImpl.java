package com.studio.tensor.ldm.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.FileSetting;
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
	FileSetting fileSetting;
	
	@Override
	public Boolean insertFileByUser(FileInfo fileInfo)
	{
		return fileInfoMapper.insertSelective(fileInfo) == 1;
	}

	@Override
	public String saveFile(MultipartFile file)
	{
		String name = FileUtils.saveFile(file, fileSetting.getSaveFilePath());
		return fileSetting.getGetFilePath() + name;
	}
}
