package com.jason.WeiXinDev.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jason.WeiXinDev.message.resp.NewsMessage;
import com.jason.WeiXinDev.message.resp.TextMessage;
import com.jason.WeiXinDev.util.CommUtil;
import com.jason.WeiXinDev.util.LBSUtil;
import com.jason.WeiXinDev.util.MessageUtil;

public class LBService {
	
	//private static Logger logger = LoggerFactory.getLogger(LBService.class);
	
	private static String url_init = "http://api.map.baidu.com/place/v2/search?query=Query&location=loc_x,loc_y&radius=2000&output=json&ak=AK";

	public static TextMessage initAnswer(Map<String,String> map) {
		TextMessage tm = new TextMessage();
		String ToUserName = map.get("ToUserName");
		String FromUserName = map.get("FromUserName");
		tm.setFromUserName(ToUserName);
		tm.setToUserName(FromUserName);
		tm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
		tm.setCreateTime(new Date().getTime());
		String respContent = "请发送您的地理位置";
		tm.setContent(respContent);
		//logger.info("发送location");
		return tm;
	}

	public static NewsMessage process(Map<String, String> map) throws IOException {
		//String respContent = null;
		//logger.info("LBService.process()....start");
		
		String Location_X = map.get("Location_X");
		String Location_Y = map.get("Location_Y");
		//String Scale = map.get("Scale");
		
		//转换坐标,将微信地地图的坐标转换为百度地图的坐标
		//logger.info("convCoord....start");
		String[] B_Location = null;
		try {
			B_Location = LBSUtil.convCoord(Location_X,Location_Y);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//logger.info("convCoord....end");
		//访问 Place api ,返回poi的json对象数组
		//logger.info("访问place api....start");
		String loc_x =B_Location[0]; 
		String loc_y =B_Location[1];
		String ak = CommUtil.getProperty("ak", "B_Map.properties");
		
		String url_replace = url_init.replace("loc_x", loc_x).replace("loc_y", loc_y).replace("AK", ak);
		String url_encoded = null;
		try {
			url_encoded = url_replace.replace("Query", URLEncoder.encode("美食", "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String respStr = httpRequest(url_encoded);
		JSONObject jo = null ;
		try {
			jo = new JSONObject(respStr);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		
		JSONArray joa = null;
		try {
			joa = (JSONArray)jo.get("results");
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//logger.info("访问place api....end");
		//创建图文消息    访问 javascript api获取步行规划
		//logger.info("创建NewsMessage....start");
		NewsMessage nm = new NewsMessage();
		nm.setFromUserName(map.get("ToUserName"));
		nm.setToUserName(map.get("FromUserName"));
		nm.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
		nm.setCreateTime(new Date().getTime());
		
		LBSUtil.createNewsMessage(joa,B_Location,nm);
		//logger.info("创建NewsMessage....end");
		//logger.info("LBService.process()....end"); 
		return nm;
	}
	
	//no output    GET
	public static String httpRequest(String urlStr){
		String respStr = null;
		URL url;
		try {
			url = new URL(urlStr);
			HttpURLConnection httpConn = (HttpURLConnection) url.openConnection();
			httpConn.setDoOutput(false);
			httpConn.setDoInput(true);
			httpConn.setRequestMethod("GET");
			
			InputStream in = httpConn.getInputStream();
			InputStreamReader isr = new InputStreamReader(in,"UTF-8");
			BufferedReader br = new BufferedReader(isr);
			String str = null;
			StringBuffer buffer = new StringBuffer();
			while( (str = br.readLine()) != null){
				buffer.append(str);
			}
			br.close();
			isr.close();
			in.close();
			in= null;
			httpConn.disconnect();
			respStr = buffer.toString();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return respStr;
	}

	
}
