package com.legou.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.legou.common.utils.FastDFSClient;
import com.legou.common.utils.JsonUtils;

@Controller
public class PicUploadController {

	@RequestMapping("/pic/upload")
	@ResponseBody
	public String uploadFile(MultipartFile uploadFile) {

		try {

			FastDFSClient fastDFSClient = new FastDFSClient("classpath:config/fastdfs.properties");

			// asdfasdf.png

			

			int i = uploadFile.getOriginalFilename().lastIndexOf(".") + 1;

			String extnameString = uploadFile.getOriginalFilename().substring(i);

			// group/
			String url = fastDFSClient.uploadFile(uploadFile.getBytes(), extnameString);

			String hostname = "http://192.168.25.133/";

			url = hostname + url;



			Map map = new HashMap();
			map.put("error", 0);
			map.put("url", url);

			return JsonUtils.objectToJson(map);

		} catch (Exception e) {
			Map map = new HashMap();
			map.put("error", 1);
			map.put("message", "上传图片异常");
			return JsonUtils.objectToJson(map);
		}

	}

}
