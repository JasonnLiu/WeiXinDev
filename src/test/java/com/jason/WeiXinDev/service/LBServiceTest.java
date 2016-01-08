package com.jason.WeiXinDev.service;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.junit.Test;

import com.jason.WeiXinDev.message.resp.Article;
import com.jason.WeiXinDev.message.resp.NewsMessage;
import com.jason.WeiXinDev.message.resp.TextMessage;
import com.jason.WeiXinDev.util.MessageUtil;

public class LBServiceTest {

	@Test
	public void testProcess() throws IOException, JSONException {

		Map<String, String> map = new HashMap<String, String>();
		map.put("Location_X", "30.541977");
		map.put("Location_Y", "114.352776");
		map.put("ToUserName", "serv");
		map.put("FromUserName", "jason");
		map.put("FromUserName", "jason");
		map.put("Content", "a");
		String MsgType = "text";
		//String str = LBService.process(map,"美食");
//		System.out.println(nm.getFromUserName());
//		System.out.println(nm.getToUserName());
//		System.out.println(nm.getCreateTime());
//		System.out.println(nm.getArticleCount());
//		System.out.println(nm.getMsgType());
//		List<Article> list = nm.getArticles();
//		
//		for(Article a : list){
//			System.out.println(a.getTitle());
//			System.out.println(a.getUrl());
//			
//		}
		//System.out.println(str);
		
		String respXml;
		
		if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			String msgContent = map.get("Content");
			if (msgContent.equals("周边搜索")) {
				TextMessage tm = LBService.initAnswer(map);
				// logger.info("周边搜索");
				respXml = MessageUtil.message2xml(tm);
				System.out.println(respXml);
			} else if (msgContent.length()>=2 && msgContent.substring(0, 2).equals("附近")) {
				respXml = LBService.process(map, msgContent.substring(2));
				System.out.println(respXml);
			} else {
				TextMessage tm = new TextMessage();
				tm.setFromUserName(map.get("ToUserName"));
				tm.setToUserName(map.get("FromUserName"));
				tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				tm.setCreateTime(new Date().getTime());
				String respContent = "Sorry , I can't help you";
				// logger.info("no function");
				tm.setContent(respContent);
				respXml = MessageUtil.message2xml(tm);
				System.out.println(respXml);			}
		}


	}

}
