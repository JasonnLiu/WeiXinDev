package com.jason.WeiXinDev.service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;

import com.jason.WeiXinDev.message.resp.Article;
import com.jason.WeiXinDev.message.resp.NewsMessage;
import com.jason.WeiXinDev.util.MessageUtil;

public class LBServiceTest {

	@Test
	public void testProcess() throws IOException, JSONException {

		Map<String, String> map = new HashMap<String, String>();
		map.put("Location_X", "30.541977");
		map.put("Location_Y", "114.352776");
		map.put("ToUserName", "serv");
		map.put("FromUserName", "jason");
		NewsMessage nm = LBService.process(map);
		System.out.println(nm.getFromUserName());
		System.out.println(nm.getToUserName());
		System.out.println(nm.getCreateTime());
		System.out.println(nm.getArticleCount());
		System.out.println(nm.getMsgType());
		List<Article> list = nm.getArticles();
		
		for(Article a : list){
			System.out.println(a.getTitle());
			System.out.println(a.getUrl());
			
		}
		System.out.println(MessageUtil.message2xml(nm));
		

	}

}
