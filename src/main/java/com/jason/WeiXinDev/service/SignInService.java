package com.jason.WeiXinDev.service;

import java.util.Date;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jason.WeiXinDev.dao.SignInDao;
import com.jason.WeiXinDev.message.resp.TextMessage;
import com.jason.WeiXinDev.util.MessageUtil;

public class SignInService {

	private static Logger logger = LoggerFactory.getLogger(LBService.class);

	public static TextMessage stu_process(Map<String, String> map) {
		TextMessage tm = new TextMessage();
		String ToUserName = map.get("ToUserName");
		String FromUserName = map.get("FromUserName");
		tm.setFromUserName(ToUserName);
		tm.setToUserName(FromUserName);
		tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		tm.setCreateTime(new Date().getTime());
		String respContent = "ǩ��ʧ��";
		if (SignInDao.stu_sign(map)) {
			respContent = "ǩ���ɹ�";
		}
		tm.setContent(respContent);
		return tm;
	}

	public static TextMessage teacher_process(Map<String, String> map) {
		TextMessage tm = new TextMessage();
		String ToUserName = map.get("ToUserName");
		String FromUserName = map.get("FromUserName");
		tm.setFromUserName(ToUserName);
		tm.setToUserName(FromUserName);
		tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		tm.setCreateTime(new Date().getTime());
		// TODO �ж��Ƿ�����ʦ��������Ϣ������Ϣ�������ݿ�
		String respContent;
		if (SignInDao.checkTea(FromUserName)) {
			SignInDao.tea_signin();
			respContent = "�ѳɹ�����ǩ����10���Ӻ�ǩ������";
		} else {
			respContent = "����ǩ��ʧ��";
		}

		tm.setContent(respContent);
		return tm;
	}

}
