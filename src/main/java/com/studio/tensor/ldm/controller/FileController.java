package com.studio.tensor.ldm.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.studio.tensor.ldm.anno.RegisterToAPIController;
import com.studio.tensor.ldm.service.impl.FileInfoServiceImpl;

@Controller
@RegisterToAPIController
@RequestMapping("/file")
public class FileController 
{
	@Autowired
	FileInfoServiceImpl fileInfoServiceImpl;
}
