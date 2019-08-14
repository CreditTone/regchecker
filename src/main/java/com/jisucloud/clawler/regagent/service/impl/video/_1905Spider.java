package com.jisucloud.clawler.regagent.service.impl.video;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "1905.com", 
		message = "1905电影网（电影频道官方网站），涵盖最新电影、好看的电影、经典电影、电影推荐、免费电影、高清电影在线观看及海量最新电影图文视频资讯，看电影就上电影网1905.com。", 
		platform = "1905", 
		platformName = "1905电影网", 
		tags = { "影音", "视频" }, 
		testTelephones = { "13925306960", "18210538513" })
public class _1905Spider extends PapaSpider {

	


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "https://passport.1905.com/v2/api/?callback=jQuery110201446535994894&m=user&a=checkEmailOrmobile&format=json&emailormobile=" + account + "&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.1905.com")
					.addHeader("Referer", "https://passport.1905.com/v2/?m=user&a=login&callback_url=http%3A%2F%2Fwww.1905.com%2F?fr=homepc_menu_login")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("200")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
