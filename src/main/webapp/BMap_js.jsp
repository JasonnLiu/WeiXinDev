<%@ page language="java" contentType="text/html; charset=utf-8"%>
<%@page import="com.jason.WeiXinDev.util.CommUtil" %>

<%
String ak = CommUtil.getProperty("ak", "B_Map.properties");

float lng_dest = Float.parseFloat(request.getParameter("lng_dest")) ;
float lat_dest = Float.parseFloat(request.getParameter("lat_dest"));
float lng_my = Float.parseFloat(request.getParameter("lng_my"));
float lat_my = Float.parseFloat(request.getParameter("lat_my"));

/*
{"lng":114.363813,"lat":30.548599}
{"lng":114.355759,"lat":30.549198}
{"lng":114.359567,"lat":30.55335}
*/
%>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	<meta name="viewport" content="initial-scale=1.0, user-scalable=no" />
	<style type="text/css">
		body, html,#allmap {width: 100%;height: 100%;overflow: hidden;margin:0;font-family:"";}
	</style>
	<script type="text/javascript" src="http://api.map.baidu.com/api?v=2.0&ak=<%= ak%>"></script>
	<title>步行导航检索</title>
</head>
<body>
	<div id="allmap"></div>
</body>
</html>
<script type="text/javascript">
	// 百度地图API功能
	var map = new BMap.Map("allmap");
	//map.centerAndZoom(new BMap.Point(116.404, 39.915), 11);
	var walking = new BMap.WalkingRoute(map, {renderOptions:{map: map, autoViewport: true}});
	walking.search(new BMap.Point(<%= lng_my%>,<%= lat_my%>),new BMap.Point (<%= lng_dest%>,<%= lat_dest%>));
</script>
