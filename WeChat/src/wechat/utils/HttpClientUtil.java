package wechat.utils;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

public class HttpClientUtil {
	
	/**
	 * get请求
	 * @param url
	 * @return
	 */
	public static String doGet(String url) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(url);
			HttpResponse httpResponse = httpClient.execute(httpGet);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String entityStr = "";
			if (statusCode == 200) {
				HttpEntity entity = httpResponse.getEntity();
				entityStr = EntityUtils.toString(entity, "UTF-8");

			}
			return entityStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}

	}

	/**
	 * post请求
	 * @param url
	 * @param param
	 * @return
	 */
	public static String doPost(String url,String param) {
		try {
			HttpClient httpClient = new DefaultHttpClient();
			HttpPost httpPost = new HttpPost(url);
			StringEntity stringEntity = new StringEntity(param, "UTF-8");
			httpPost.setEntity(stringEntity);
			HttpResponse httpResponse = httpClient.execute(httpPost);
			int statusCode = httpResponse.getStatusLine().getStatusCode();
			String entityStr = "";
			if (statusCode == 200) {
				HttpEntity entity = httpResponse.getEntity();
				entityStr = EntityUtils.toString(entity, "utf-8");

			}
			return entityStr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 自定义菜单
	 * @return
	 */
	public static String addMenuParam(){
		//一个菜单条
		JSONObject root = new JSONObject();
		
		//一级菜单，装两个菜单条
		JSONArray first = new JSONArray();
		
		//返回主页的菜单
		JSONObject main = new JSONObject();
		//设置主页的属性
		main.put("type", "view");
		main.put("name", "首页");
		main.put("url", "http://");
		
		//功能菜单
		JSONObject function = new JSONObject();
		//功能菜单的子菜单
		JSONArray second = new JSONArray();
		
		//为功能菜单添加子菜单
		JSONObject binder = new JSONObject();
		binder.put("type", "view");
		binder.put("name", "这是空白");
		binder.put("url", "http://www.baidu.com");
		JSONObject customer = new JSONObject();
		customer.put("type", "view");
		customer.put("name", "这是空白");
		customer.put("url", "http://www.baidu.com");
		
		second.add(binder);
		second.add(customer);
		
		function.put("name", "功能菜单");
		function.put("sub_button", second);
		
		first.add(main);
		first.add(function);
		
		root.put("button", first);
		
		return root.toString();
	}
}
