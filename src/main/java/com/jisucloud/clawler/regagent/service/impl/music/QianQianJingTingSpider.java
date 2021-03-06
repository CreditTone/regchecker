package com.jisucloud.clawler.regagent.service.impl.music;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "taihe.com", 
		message = "千千音乐是中国音乐门户之一,为你提供海量正版高品质音乐,权威的音乐榜单,快速的新歌速递,契合你的主题电台,人性化的歌曲搜索,让你更快地找到喜爱的音乐。", 
		platform = "taihe", 
		platformName = "千千音乐", 
		tags = { "音乐" }, 
		testTelephones = { "15985260000", "18212345678" })
public class QianQianJingTingSpider extends PapaSpider {

	


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://passport.taihe.com/v2/api/checkphone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("login_id", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.taihe.com")
					.addHeader("Referer", "http://passport.taihe.com/v2/web/register.html?u=http%3A%2F%2Fmusic.taihe.com%2F%3Ffr%3Dhao123")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getString("error_msg").contains("exists")) {
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
