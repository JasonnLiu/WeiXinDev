package com.jason.WeiXinDev.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.dom4j.DocumentException;

import com.jason.WeiXinDev.service.CoreService;
import com.jason.WeiXinDev.util.SignUtil;

public class CoreServlet extends HttpServlet{
	
	

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		if(SignUtil.checkSign(signature,timestamp,nonce)){
			out.print(echostr);
		}
		out.close();
		out = null;
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		resp.setCharacterEncoding("UTF-8");
		
		String signature = req.getParameter("signature");
		String timestamp = req.getParameter("timestamp");
		String nonce = req.getParameter("nonce");
		String echostr = req.getParameter("echostr");
		
		PrintWriter out = resp.getWriter();
		
		if(SignUtil.checkSign(signature,timestamp,nonce)){
			try {
				String respXml = CoreService.process(req);
				out.print(respXml);
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			
		}
		out.close();
		out = null;
		
	}
	
	private static String strSort(String a ,String b , String c ){
		String[] str = {a,b,c};
		Arrays.sort(str);
		return str[0].concat(str[1]).concat(str[2]);
	}

}
