package com.jisucloud.clawler.regagent.service.impl.news;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "huanqiu.com", 
		message = "环球网是中国领先的国际资讯门户,拥有独立采编权的中央重点新闻网站。环球网秉承环球时报的国际视野,力求及时、客观、权威、独立地报道新闻,致力于应用前沿的互联网。", 
		platform = "huanqiu", 
		platformName = "环球网", 
		tags = { "新闻资讯" }, 
		testTelephones = { "18720982607", "18210538513" })
public class HuanQiuWangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://i.huanqiu.com/index.php?g=auth&m=members&a=check_mobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "i.huanqiu.com")
					.addHeader("Referer", "https://i.huanqiu.com/register")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			System.out.println(res);
			if (res.contains("已存在")) {
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
