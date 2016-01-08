package com.jason.WeiXinDev.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jason.WeiXinDev.message.resp.Article;
import com.jason.WeiXinDev.message.resp.NewsMessage;

public class LBSUtil {

	private static Logger logger = LoggerFactory.getLogger(LBSUtil.class);
	
	private static String api_url = "http://api.map.baidu.com/ag/coord/convert?from=2&to=4&x=location_X&y=location_Y";
	private static String js_api_url = "weixinmptest0111.aliapp.com/BMap_js.jsp?";

	public static String[] convCoord(String location_X, String location_Y) throws IOException, JSONException {

		String api_url_replaced = api_url.replace("location_X", location_X).replace("location_Y", location_Y);

		URL url = new URL(api_url_replaced);

		HttpURLConnection convertConn = (HttpURLConnection) url.openConnection();
		convertConn.setDoOutput(false);
		convertConn.setDoInput(true);
		convertConn.setRequestMethod("GET");

		InputStream in = convertConn.getInputStream();
		InputStreamReader isr = new InputStreamReader(in);
		BufferedReader br = new BufferedReader(isr);
		String str = null;
		StringBuffer buffer = new StringBuffer();
		while ((str = br.readLine()) != null) {
			buffer.append(str);
		}
		br.close();
		isr.close();
		in.close();
		in = null;
		convertConn.disconnect();
		JSONObject jo = new JSONObject(buffer.toString());
		String[] convert = new String[2];
		convert[0] = jo.getString("x");
		convert[1] = jo.getString("y");

		convert[0] = new String(Base64.decodeBase64(convert[0]), "UTF-8");
		convert[1] = new String(Base64.decodeBase64(convert[1]), "UTF-8");

		return convert;
	}

	public static void createNewsMessage(JSONArray joa,String[] MyLocation,NewsMessage nm) {
		logger.info("LBSUtil createNewsMessage ");
		if (joa.length() < 5) {
			nm.setArticleCount(joa.length());
			createNewsMessage(joa,joa.length(),nm,MyLocation);
		}else{
			nm.setArticleCount(5);
			createNewsMessage(joa,5,nm,MyLocation);
		}
	}
	
	public static void createNewsMessage(JSONArray joa , int length , NewsMessage nm,String[] MyLocation){
		List<Article> list = new ArrayList<Article>();
		for(int i = 0 ; i < length ; i++){
			
			Article a = new Article();
			try {
				JSONObject jo = joa.getJSONObject(i);
				//"location":{"lng":114.363813,"lat":30.548599}
				a.setTitle(jo.getString("name"));
				JSONObject jo_loc =  new JSONObject(jo.getString("location"));
				String lng_dest =  String.valueOf(jo_loc.get("lng"));
				String lat_dest =  String.valueOf(jo_loc.get("lat"));
				//lng--Y¾­¶È   lat--XÎ³¶È
				String lng_my = MyLocation[1];
				String lat_my = MyLocation[0];
				
				String url = js_api_url + "lng_dest=" + lng_dest + "&lat_dest=" + lat_dest + "&lng_my=" + lng_my + "&lat_my=" + lat_my;
				System.out.print(url);
				a.setUrl(url);
				
				list.add(a);
				
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			nm.setArticles(list);
			
		}
	}

}
