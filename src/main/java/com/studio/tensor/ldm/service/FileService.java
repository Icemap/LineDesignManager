package com.studio.tensor.ldm.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.studio.tensor.ldm.pojo.FileInfo;

public interface FileService
{
	Boolean insertFileByUser(FileInfo fileInfo);
	
	String saveFile(MultipartFile file);
	
	List<FileInfo> selectFileInfoByUserId(Integer userId);
    List<FileInfo> selectByUserIdAndTag(Integer userId, String tag);
    List<String> selectTagByUserId(Integer userId);
}
