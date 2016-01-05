package com.jason.WeiXinDev.service;

import java.io.IOException;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.dom4j.DocumentException;

import com.jason.WeiXinDev.message.resp.TextMessage;
import com.jason.WeiXinDev.util.MessageUtil;

public class CoreService {

	public static String process(HttpServletRequest req) throws DocumentException, IOException {
		String respXml = null;

		Map<String, String> map = MessageUtil.parseReq(req);

		String ToUserName = map.get("ToUserName");
		String FromUserName = map.get("FromUserName");
		String msgType = map.get("MsgType");
		
		/*
		 * 响应消息对象
		 */
		TextMessage tm = new TextMessage();
		tm.setFromUserName(ToUserName);
		tm.setToUserName(FromUserName);
		tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		tm.setCreateTime(new Date().getTime());
		String respContent = null;

		// 文本消息
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			respContent = "您发送的是文本消息！";
		}
		// 图片消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			respContent = "您发送的是图片消息！";
		}
		// 语音消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
			respContent = "您发送的是语音消息！";
		}
		// 视频消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
			respContent = "您发送的是视频消息！";
		}
		// 地理位置消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
			respContent = "您发送的是地理位置消息！";
		}
		// 链接消息
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
			respContent = "您发送的是链接消息！";
		}
		// 事件推送
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// 事件类型
			String eventType = map.get("Event");
			// 关注
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				respContent = "谢谢您的关注！";
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
		
		tm.setContent(respContent);
		
		/*
		 * 将响应消息对象转换成响应xml
		 */
		respXml = MessageUtil.message2xml(tm);

		return respXml;
	}

}
