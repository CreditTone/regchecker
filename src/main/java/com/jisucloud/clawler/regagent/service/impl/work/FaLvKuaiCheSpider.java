package com.jisucloud.clawler.regagent.service.impl.work;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

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
public class FaLvKuaiCheSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "法律快车,中国法律服务平台的先行者,14年来已为3000万的当事人找到适合的本地律师。网站拥有注册律师近15万名,覆盖刑事、婚姻、合同、劳动、房产等60多个法律专业。";
	}

	@Override
	public String platform() {
		return "lawtime";
	}

	@Override
	public String home() {
		return "lawtime.com";
	}

	@Override
	public String platformName() {
		return "法律快车";
	}

	@Override
	public String[] tags() {
		return new String[] {"法律咨询" , "找律师"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18230012895", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.lawtime.cn/getpass/index.php?m=Index&a=checkMobile";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.lawtime.cn")
					.addHeader("Referer", "http://www.lawtime.cn/getpass/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("验证成功")) {
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
