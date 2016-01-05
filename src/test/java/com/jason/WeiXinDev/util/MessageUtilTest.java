package com.jason.WeiXinDev.util;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;

import com.jason.WeiXinDev.message.resp.TextMessage;

public class MessageUtilTest {

	@Test
	public void testMessage2xml() {
		TextMessage tm = new TextMessage();
		tm.setFromUserName("from");
		tm.setToUserName("to");
		tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		tm.setCreateTime(new Date().getTime());
		tm.setContent("content");
		
		String xml = MessageUtil.message2xml(tm);
		System.out.println(xml);
		
	}

}
