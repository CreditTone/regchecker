package com.jisucloud.clawler.regagent.service.impl.education;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
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
public class ZuoYeBangSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "作业帮致力于为全国中小学生提供全学段的学习辅导服务,截至目前,作业帮月活跃用户超8000万,是中小学在线教育领军品牌。";
	}

	@Override
	public String platform() {
		return "zuoyebang";
	}

	@Override
	public String home() {
		return "zuoyebang.com";
	}

	@Override
	public String platformName() {
		return "作业帮";
	}

	@Override
	public String[] tags() {
		return new String[] {"考试", "学习", "教育"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15584382173", "18210530000");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://zhibo.zuoyebang.com/session/pc/login";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36")
					.addHeader("Referer", " http://zhibo.zuoyebang.com/")
					.post(createUrlEncodedForm("phone="+account+"&password=d8f3d86362bda3241985d4d1aae91c30&os=pcweb&appId=homework&channel=&plat=pc&cType=pc&fr=pc"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("21004") || res.contains("21007")) {
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
