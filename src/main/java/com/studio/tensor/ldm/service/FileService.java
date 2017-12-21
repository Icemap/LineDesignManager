package com.studio.tensor.ldm.service;

import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.pojo.FileInfo;

public interface FileService
{
	Boolean insertFileByUser(FileInfo fileInfo);
	
	String saveFile(MultipartFile file);
}
