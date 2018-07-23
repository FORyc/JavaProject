package wechat.utils;

import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

/**
 * 解析用户输入的信息
 * @author LIU
 *
 */
public class XmlParseUtil {
	public static Map<String, String> xmlParse(Reader reader) throws Exception {
		XmlPullParser parser = XmlPullParserFactory.newInstance().newPullParser();
		
		//解析
		parser.setInput(reader);
		//拿到事件
		int eventType = parser.getEventType();
		Map<String, String> map = new HashMap<>();
		//判断是否是文档结尾
		while(eventType!=XmlPullParser.END_DOCUMENT){
			//拿到标签
			String tagName = parser.getName();
			//验证时标签的开始，而不是xml的开始
			if(!"xml".equals(tagName)&&eventType==XmlPullParser.START_TAG){
				//拿到标签值，放入map
				String tagValue = parser.nextText();
				map.put(tagName, tagValue);
			}
			//设置到下一个事件
			eventType = parser.next();
		}
		return map;
	}

}
