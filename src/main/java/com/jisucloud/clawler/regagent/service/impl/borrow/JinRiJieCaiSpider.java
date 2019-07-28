package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class JinRiJieCaiSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "今日捷财是上海捷财金融信息服务有限公司旗下一个独立公正、真实透明的金融信息服务平台，是中国互联网金融协会会员单位、上海市互联网金融行业协会会员单位，为融资人提供一个快速获取低成本资金的融资平台。 ";
	}

	@Override
	public String platform() {
		return "51jiecai";
	}

	@Override
	public String home() {
		return "51jiecai.com";
	}

	@Override
	public String platformName() {
		return "今日捷财";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.51jiecai.com/passport/api/wp/sms/forgetLoginPwd.html?app=4&did=&uid=" + account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.51jiecai.com/passport/reg/index.html")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("success")) {
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

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15161509916");
	}

}
