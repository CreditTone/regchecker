package com.jisucloud.clawler.regagent.service.impl.health;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
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
public class _39JianKangSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "39健康网,专业的健康资讯门户网站,中国优质医疗保健信息与在线健康服务平台,医疗保健类网站杰出代表,荣获中国标杆品牌称号。提供专业、完善的健康信息服务,包括疾病。";
	}

	@Override
	public String platform() {
		return "39jk";
	}

	@Override
	public String home() {
		return "39.net";
	}

	@Override
	public String platformName() {
		return "39健康网";
	}

	@Override
	public String[] tags() {
		return new String[] {"健康运动", "医疗", "生活应用" , "挂号" , "用药"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13877117175", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://my.39.net/UserService.asmx/CheckPhoneReg";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("pid", "0")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "my.39.net")
					.addHeader("Referer", "https://my.39.net/passport/reg.aspx?ref=http://www.39.net/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
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
