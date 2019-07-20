package com.jisucloud.clawler.regagent.service.impl.game;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ShengQuYouXiSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "盛趣游戏是中国领先的互动娱乐内容运营平台。利用便捷的销售网络、完善的付费系统、广泛的市场推广网络、强大的技术保障系统、领先的客户服务、稳妥的网络安全系统为用户提供高水准的服务。";
	}

	@Override
	public String platform() {
		return "sdo";
	}

	@Override
	public String home() {
		return "sdo.com";
	}

	@Override
	public String platformName() {
		return "盛趣游戏";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18720982607", "13967075617");
	}


	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://cas.sdo.com/authen/checkAccountType.jsonp?callback=checkAccountType_JSONPMethod&serviceUrl=register.sdo.com&appId=201&areaId=200&authenSource=2&inputUserId="+account+"&locale=zh_CN&productId=1&productVersion=1.7&version=21&sourceId=&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://register.sdo.com/register/index?appId=201&areaId=200&serviceUrl=http%3a%2f%2fwww.sdo.com")
					.addHeader("Accept", "*/*")
					.addHeader("Accept-Language", "zh-CN,zh;q=0.8,zh-TW;q=0.7,zh-HK;q=0.5,en-US;q=0.3,en;q=0.2")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("existing\": 1")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
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
