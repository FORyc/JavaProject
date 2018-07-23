package wechat.utils;

public class WeixinConstants {
	public static final String APP_ID = "";//微信公众号开发的id
	public static final String APP_SECRET = "";//微信公众号开发的密码
	public static final String ACCESS_TOKEN = "";//接入口令，需要请求获取，有效期两个小时

	// 获取菜单的地址
	public static final String GET_MENU = "https://api.weixin.qq.com/cgi-bin/menu/get?access_token=ACCESS_TOKEN";

	// 删除菜单的地址
	public static final String DEL_MENU = "https://api.weixin.qq.com/cgi-bin/menu/delete?access_token=ACCESS_TOKEN";

	// 添加菜单的地址
	public static final String ADD_MENU = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

}
