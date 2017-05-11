package com.tarena.action.login;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import com.tarena.action.BaseAction;
import com.tarena.util.ImageUtil;

public class CreateImageAction extends BaseAction {
	
	//output
	private InputStream imageStream;
	
	public String execute() {
		//1.生成图片
		Map<String, BufferedImage> map = 
			ImageUtil.createImage();
		//2.读取出验证码，并放入session
		String code = map.keySet().iterator().next();
		session.put("imageCode", code);
		//3.读取出图片
		BufferedImage image = map.get(code);
		try {
			imageStream = ImageUtil.getInputStream(image);
		} catch (IOException e) {
			e.printStackTrace();
			return "error";
		}
		
		return "success";
	}

	public InputStream getImageStream() {
		return imageStream;
	}

	public void setImageStream(InputStream imageStream) {
		this.imageStream = imageStream;
	}

}