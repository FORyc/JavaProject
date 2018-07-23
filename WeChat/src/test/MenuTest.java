package test;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.junit.Test;

import wechat.utils.HttpClientUtil;
import wechat.utils.WeixinConstants;

/**
 * 自定义菜单的添加删除和获取
 * @author LIU
 *
 */
public class MenuTest {

	/**
	 * 请求获得接入口令，access_token
	 * @throws ClientProtocolException
	 * @throws IOException
	 */
	@Test
	public void test() throws ClientProtocolException, IOException {
		String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+WeixinConstants.APP_ID+"&secret="+WeixinConstants.APP_SECRET;
		String doGet = HttpClientUtil.doGet(url);
		System.out.println(doGet);
	}

	/**
	 * 获取菜单的测试
	 * 
	 * @throws Exception
	 */
	@Test
	public void getMenuTest() throws Exception {
		String uri = WeixinConstants.GET_MENU.replace("ACCESS_TOKEN", WeixinConstants.ACCESS_TOKEN);
		String doGet = HttpClientUtil.doGet(uri);
		System.out.println(doGet);
	}

	/**
	 * 删除菜单： {"errcode":0,"errmsg":"ok"}
	 * 
	 * @throws Exception
	 */
	@Test
	public void delMenuTest() throws Exception {
		String uri = WeixinConstants.DEL_MENU.replace("ACCESS_TOKEN", WeixinConstants.ACCESS_TOKEN);
		String doGet = HttpClientUtil.doGet(uri);
		System.out.println(doGet);
	}

	/**
	 * 添加菜单
	 * {"errcode":0,"errmsg":"ok"}
	 * 
	 * @throws Exception
	 */
	@Test
	public void addMenuTest() throws Exception {
		String uri = WeixinConstants.ADD_MENU.replace("ACCESS_TOKEN", WeixinConstants.ACCESS_TOKEN);
		String param = HttpClientUtil.addMenuParam();
		String doPost = HttpClientUtil.doPost(uri, param);
		System.out.println(doPost);
	}

}
