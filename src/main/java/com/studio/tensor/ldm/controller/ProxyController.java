package com.studio.tensor.ldm.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/proxy")
public class ProxyController
{
	@RequestMapping("/proxy")
	public String get(HttpServletResponse response) throws IOException
	{
		return "/proxy";
	}
}
