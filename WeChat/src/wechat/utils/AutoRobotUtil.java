package wechat.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * 请求图灵机器人的类
 * @author LIU
 *
 */
public class AutoRobotUtil {
	public static final String URL = "http://openapi.tuling123.com/openapi/api/v2";
	public static final String APIKEY = "";//申请图灵机器人后会得到的一个apikey
	public static final String USERID = "";//userid实际可以随便写
	
	/**
	 * 封装消息发送的json,并请求接口返回json对象
	 * @param text
	 * @return
	 */
	public static JSONObject getMsg(String text){
		JSONObject root = new JSONObject();
		JSONObject perception = new JSONObject();
		JSONObject userInfo = new JSONObject();
		
		JSONObject inputText = new JSONObject();
		
		inputText.put("text", text);//返回文本消息
		perception.put("inputText", inputText);
	
		userInfo.put("apiKey", APIKEY);
		userInfo.put("userId", USERID);
		
		root.put("reqType", 0);
		root.put("perception", perception);
		root.put("userInfo", userInfo);
		
		System.out.println("发送的数据：      "+root.toJSONString());
		
		String doPost = HttpClientUtil.doPost(URL, root.toString());
		System.err.println("POST请求返回的数据：     "+doPost);
		JSONObject parse = (JSONObject) JSON.parse(doPost);
		System.out.println("返回数据转换成json格式：       "+parse);
		//拿到返回的结果内容，结果是一个json数组
		JSONArray object = (JSONArray) parse.get("results");
		JSONObject jsonObject = object.getJSONObject(0);
		//取出json数组中的json对象
		JSONObject content = (JSONObject) jsonObject.get("values");
		return content;
	}
}
