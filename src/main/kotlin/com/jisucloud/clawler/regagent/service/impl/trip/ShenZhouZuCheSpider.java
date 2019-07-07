package com.jisucloud.clawler.regagent.service.impl.trip;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

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
public class ShenZhouZuCheSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "神州租车 (CAR Inc.)成立于2007年9月，总部位于中国北京。作为中国汽车租赁行业的领跑者，神州租车积极借鉴国际成熟市场成功的汽车租赁模式，并结合中国客户的消费习惯，为广大消费者提供短租、长租及融资租赁等专业化的汽车租赁服务。";
	}

	@Override
	public String platform() {
		return "zuche";
	}

	@Override
	public String home() {
		return "zuche.com";
	}

	@Override
	public String platformName() {
		return "神州租车";
	}

	@Override
	public String[] tags() {
		return new String[] {"出行" , "租车"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252045", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.zuche.com/memberManage/member.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("xname", account)
	                .add("xpassword", "dsadsa123123")
	                .add("xyzm", "请输入右侧验证码")
	                .add("xidtm", "请输入动态验证码")
	                .add("flag", "")
	                .add("autoLogin", "checked")
	                .add("type", "normal")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.zuche.com")
					.addHeader("Referer", "https://passport.zuche.com/member/loginandregist/login.do")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("returnFlag\":\"pwdError")) {
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
