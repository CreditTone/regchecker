package com.jisucloud.clawler.regagent.service.impl.news;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

import java.util.HashMap;
import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "toutiao.com", 
		message = "今日头条是北京字节跳动科技有限公司开发的一款基于数据挖掘的推荐引擎产品，为用户推荐信息，提供连接人与信息的服务的产品。由张一鸣于2012年3月创建，2012年8月发布第一个版本。", 
		platform = "toutiao", 
		platformName = "今日头条", 
		tags = { "新闻资讯" }, 
		testTelephones = { "18720000000", "18212345678" })
public class TouTiaoSpider extends PapaSpider {
	
	private static Map<String,String> datas = new HashMap<String, String>();
	static {
		datas.put("0", "35");
		datas.put("1", "34");
		datas.put("2", "37");
		datas.put("3", "36");
		datas.put("4", "31");
		datas.put("5", "30");
		datas.put("6", "33");
		datas.put("7", "32");
		datas.put("8", "3d");
		datas.put("9", "3c");
	}

	private static String encryption(String mobile) {
		String encryption = "";
		for (int i = 0; i < mobile.length(); i++) {
			encryption += datas.get(String.valueOf(mobile.charAt(i)));
		}
		return encryption;
	}

	
	public boolean checkTelephone(String account) {
		String lastMsg = "";
		for (int i = 0; i < 2; i++) {
			try {
				String postData = "mix_mode=1&password=6164766137343634343737&mobile="+encryption(account)+"&iid=77508932374&device_id=68552813776&ac=wifi&channel=ucad&aid=13&app_name=news_article&version_code=500&version_name=5.0.0&device_platform=android&abflag=1&ssmix=a&device_type=8692-A00&os_api=19&os_version=4.4.2&uuid=352284040670808&openudid=3bf22b9088ab9578&manifest_version_code=500&resolution=480*800&dpi=160";
				String url = "https://ic.snssdk.com/user/mobile/login/v2/?iid=77508932374&device_id=68552813776&ac=wifi&channel=ucad&aid=13&app_name=news_article&version_code=500&version_name=5.0.0&device_platform=android&abflag=1&ssmix=a&device_type=8692-A00&os_api=19&os_version=4.4.2&uuid=352284040670808&openudid=3bf22b9088ab9578&manifest_version_code=500&resolution=480*800&dpi=160";
				Request request = new Request.Builder().url(url)
						.addHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.2; 8692-A00 Build/KOT49H) NewsArticle/5.0.0")
						.addHeader("Host", "ic.snssdk.com")
						.addHeader("Cookie", "qh[360]=1; install_id=77508932374; ttreq=1$09cff83c0a9db0aebd8b6ea92bb7b1e9638fdaa8; odin_tt=c35c8bee83a3192ab555bc441bb5e1b503e271d78806685ddda467ac35590cb2683d80d5645486f222a41ab773bc4c4bfcd55b7a25f402fb4bfa70f8d85c462a")
						.addHeader("X-SS-Cookie", "qh[360]=1; install_id=77508932374; ttreq=1$09cff83c0a9db0aebd8b6ea92bb7b1e9638fdaa8; odin_tt=c35c8bee83a3192ab555bc441bb5e1b503e271d78806685ddda467ac35590cb2683d80d5645486f222a41ab773bc4c4bfcd55b7a25f402fb4bfa70f8d85c462a")
						.post(FormBody.create(MediaType.parse("application/x-www-form-urlencoded"), postData))
						.build();
				Response response = okHttpClient.newCall(request).execute();
				lastMsg = response.body().string();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (lastMsg.contains("1033") || lastMsg.contains("1034") || lastMsg.contains("请使用手机验证码登录")) {
			return true;
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
