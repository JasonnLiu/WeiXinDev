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
		 * ��Ӧ��Ϣ����
		 */
		TextMessage tm = new TextMessage();
		tm.setFromUserName(ToUserName);
		tm.setToUserName(FromUserName);
		tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		tm.setCreateTime(new Date().getTime());
		String respContent = null;

		// �ı���Ϣ
		if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			respContent = "�����͵����ı���Ϣ��";
		}
		// ͼƬ��Ϣ
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
			respContent = "�����͵���ͼƬ��Ϣ��";
		}
		// ������Ϣ
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
			respContent = "�����͵���������Ϣ��";
		}
		// ��Ƶ��Ϣ
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {
			respContent = "�����͵�����Ƶ��Ϣ��";
		}
		// ����λ����Ϣ
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
			respContent = "�����͵��ǵ���λ����Ϣ��";
		}
		// ������Ϣ
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
			respContent = "�����͵���������Ϣ��";
		}
		// �¼�����
		else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// �¼�����
			String eventType = map.get("Event");
			// ��ע
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
				respContent = "лл���Ĺ�ע��";
			}
			// ȡ����ע
			else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
				// TODO ȡ�����ĺ��û��������յ������˺ŷ��͵���Ϣ����˲���Ҫ�ظ�
			}
			// ɨ���������ά��
			else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
				// TODO ����ɨ���������ά���¼�
			}
			// �ϱ�����λ��
			else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
				// TODO �����ϱ�����λ���¼�
			}
			// �Զ���˵�
			else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
				// TODO ����˵�����¼�
			}
		}
		
		tm.setContent(respContent);
		
		/*
		 * ����Ӧ��Ϣ����ת������Ӧxml
		 */
		respXml = MessageUtil.message2xml(tm);

		return respXml;
	}

}
