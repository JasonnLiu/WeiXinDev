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

	public static String process(HttpServletRequest req)
			throws DocumentException, IOException {
		String respXml = null;

		Map<String, String> map = MessageUtil.parseReq(req);

		String MsgType = map.get("MsgType");

		// �ı���Ϣ
		if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
			String msgContent = map.get("Content");
			if (msgContent.equals("�ܱ�����")) {
				TextMessage tm = LBService.initAnswer(map);
				logger.info("�ܱ�����");
				respXml = MessageUtil.message2xml(tm);
				return respXml;
			} else if (msgContent.length() >= 2
					&& msgContent.substring(0, 2).equals("����")) {
				respXml = LBService.process(map, msgContent.substring(2));
				return respXml;
			} else if (msgContent.equals("��ʼǩ��")) {
				TextMessage tm = SignInService.teacher_process(map);
				logger.info("��ʦ����ǩ��-TextMessage����");
				respXml = MessageUtil.message2xml(tm);
				return respXml;
			} else if (msgContent.equals("��")) {
				TextMessage tm = SignInService.teacher_process(map);
				logger.info("ѧ��ǩ��-TextMessage����");
				respXml = MessageUtil.message2xml(tm);
			} else {
				// ����ChatService
				String respContent = ChatService.process(msgContent);

				TextMessage tm = new TextMessage();
				tm.setFromUserName(map.get("ToUserName"));
				tm.setToUserName(map.get("FromUserName"));
				tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				tm.setCreateTime(new Date().getTime());
				tm.setContent(respContent);
				respXml = MessageUtil.message2xml(tm);
				return respXml;
				/*
				 * TextMessage tm = new TextMessage();
				 * tm.setFromUserName(map.get("ToUserName"));
				 * tm.setToUserName(map.get("FromUserName"));
				 * tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
				 * tm.setCreateTime(new Date().getTime()); String respContent =
				 * "Sorry , I can't help you"; logger.info("no function");
				 * tm.setContent(respContent); respXml =
				 * MessageUtil.message2xml(tm); return respXml;
				 */
			}
		}
		// ͼƬ��Ϣ
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {

		}
		// ������Ϣ
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {

		}
		// ��Ƶ��Ϣ
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VIDEO)) {

		}
		// ����λ����Ϣ
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
			NewsMessage nm = LBService.process(map);
			respXml = MessageUtil.message2xml(nm);
			return respXml;
		}
		// ������Ϣ
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {

		}
		// �¼�����
		else if (MsgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
			// �¼�����
			String eventType = map.get("Event");
			// ��ע
			if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {

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

		return null;

	}

}
