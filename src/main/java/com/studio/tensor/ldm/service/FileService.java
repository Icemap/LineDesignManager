package com.studio.tensor.ldm.service;

import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.bean.ResultBean;
import com.studio.tensor.ldm.pojo.FileInfo;

public interface FileService
{
	ResultBean insertFileByUser(FileInfo fileInfo, String token, String apiKey);
	
	ResultBean saveFile(MultipartFile file, String token, String apiKey);
}
