package com.jason.WeiXinDev.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jason.WeiXinDev.message.resp.NewsMessage;
import com.jason.WeiXinDev.message.resp.TextMessage;
import com.jason.WeiXinDev.util.MessageUtil;

public class CoreService {
	
	private static Logger logger = LoggerFactory.getLogger(CoreService.class);

	public static String process(HttpServletRequest req) throws DocumentException, IOException {
		String respXml = null;

		Map<String, String> map = MessageUtil.parseReq(req);

		
		String MsgType = map.get("MsgType");
		

		// 文本消息
		if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			String msgContent = map.get("Content");
			if(msgContent.equals("周边搜索")){
				TextMessage tm = LBService.initAnswer(map);
				logger.info("周边搜索");
				respXml = MessageUtil.message2xml(tm);
				return respXml;
			}else{
				TextMessage tm = new TextMessage();
				tm.setFromUserName(map.get("ToUserName"));
				tm.setToUserName(map.get("FromUserName"));
				tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				tm.setCreateTime(new Date().getTime());
				String respContent = "Sorry , I can't help you";
				logger.info("no function");
				tm.setContent(respContent);
				respXml = MessageUtil.message2xml(tm);
				return respXml;
			}
		}
		// 图片消息
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			
		}
		// 语音消息
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
			
		}
		// 视频消息
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
			
		}
		// 地理位置消息
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
			NewsMessage nm = LBService.process(map);
			respXml = MessageUtil.message2xml(nm);
			return respXml;
		}
		// 链接消息
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
			
		}
		// 事件推送
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = map.get("Event");
			// 关注
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				
			}
			// 取消关注
			else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				// TODO 取消订阅后用户不会再收到公众账号发送的消息，因此不需要回复
			}
			// 扫描带参数二维码
			else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
				// TODO 处理扫描带参数二维码事件
			}
			// 上报地理位置
			else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
				// TODO 处理上报地理位置事件
			}
			// 自定义菜单
			else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
				// TODO 处理菜单点击事件
			}
		}
		
		return null;
		
	}

}
