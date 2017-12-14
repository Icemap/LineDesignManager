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
}
