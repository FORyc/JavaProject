package wechat.web.controller;

import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;

import wechat.utils.AutoRobotUtil;
import wechat.utils.SecurityUtil;
import wechat.utils.XmlParseUtil;



/**
 * 微信认证和对话的controller
 * @author LIU
 *
 */
@Controller
@RequestMapping("/index")
public class IndexController {

	/**
	 * 处理微信认证，get提交
	 */
	@RequestMapping(method = RequestMethod.GET)
	public void signature(HttpServletRequest request, HttpServletResponse response) throws Exception {
		/*
		 * signature	微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
			timestamp	时间戳
			nonce	随机数
			echostr	随机字符串
		 * */
		String signature = request.getParameter("signature");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String token = "";//所设置的口令
		
		//1）将token、timestamp、nonce三个参数进行字典序排序 
		String[] strArr = {token,nonce,timestamp};
		Arrays.sort(strArr);
		
		//2）将三个参数字符串拼接成一个字符串进行sha1加密 
		StringBuffer sb = new StringBuffer();
		for (String string : strArr) {
			sb.append(string);
		}
		String sbString = sb.toString();
		String sha1 = SecurityUtil.sha1(sbString);
		//3）开发者获得加密后的字符串可与signature对比，标识该请求来源于微信
		if(sha1.equals(signature)){
			response.getWriter().print(echostr);
		}else{
			throw new RuntimeException("连接失败");
		}
	}

	
	@RequestMapping(method=RequestMethod.POST)
	public void doMsg(HttpServletRequest request,HttpServletResponse response) throws Exception{
		//System.out.println("post........");
		ServletInputStream inputStream = request.getInputStream();
		//转换成字节流
		InputStreamReader reader = new InputStreamReader(inputStream, "UTF-8");
		Map<String, String> xmlMap = XmlParseUtil.xmlParse(reader);
		//System.out.println(xmlMap);
		
		//解析请求数据
		String xmlResult = parseMap2Xml(xmlMap);
		
		String event = xmlMap.get("Event");
		if("event".equals(xmlMap.get("MsgType"))&&"subscribe".equals(event)){
			xmlMap.put("MsgType", "text");
			xmlMap.put("Content", "欢迎你");
		}
		response.setContentType("text/html; charset=UTF-8");
		response.getWriter().print(xmlResult);
		
	}


	/**
	 * 拼接返回的xml
	 * @param xmlMap
	 * @return
	 */
	private String parseMap2Xml(Map<String, String> xmlMap) {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append("<ToUserName>"+xmlMap.get("FromUserName")+"</ToUserName>");
		sb.append("<FromUserName>"+xmlMap.get("ToUserName")+"</FromUserName>");
		sb.append("<CreateTime>"+new Date()+"</CreateTime>");
		//判断数据类型，根据不同的数据类型来返回相应的数据类型
		String type = xmlMap.get("MsgType");
		if (type.equals("text")) {
			sb.append("<MsgType>"+xmlMap.get("MsgType")+"</MsgType>");
			//拿到用户输入的信息，请求机器人，得到json数据
			String content = xmlMap.get("Content");
			JSONObject msg = AutoRobotUtil.getMsg(content);
			sb.append("<Content>"+msg.get("text")+"</Content>");
		}else if(type.equals("image")){
			sb.append("<MsgType>" + type + "</MsgType>");
			sb.append("<Image><MediaId>" + xmlMap.get("MediaId") + "</MediaId></Image>");
		}else if (type.equals("vioce")) {
			sb.append("<MsgType>" + type + "</MsgType>");
			sb.append("<Voice><MediaId>" + xmlMap.get("MediaId") + "</MediaId></Voice>");
		}
		sb.append("</xml>");
		return sb.toString();
	}
	
}
