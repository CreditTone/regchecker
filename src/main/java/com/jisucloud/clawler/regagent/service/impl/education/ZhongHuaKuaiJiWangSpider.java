package com.jisucloud.clawler.regagent.service.impl.education;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZhongHuaKuaiJiWangSpider extends PapaSpider {

	


	@Override
	public String message() {
		return "中华会计网校是专业的会计培训网站，专注会计培训19年，官方咨询热线: 010-82318888。中华会计网校常年开设初级会计职称、中级会计职称、高级会计师、注册会计师、税务师、经济师、ACCA、CMA、美国CPA、会计继续教育等各类财会考试培训，培训效果卓越！";
	}

	@Override
	public String platform() {
		return "chinaacc";
	}

	@Override
	public String home() {
		return "chinaacc.com";
	}

	@Override
	public String platformName() {
		return "中华会计网校";
	}

	@Override
	public String[] tags() {
		return new String[] {"会计", "学校" , "网校"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13771025665", "18209649992");
	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://member.chinaacc.com/uc/loginRegister/userRegister/register/checkPhoneBind/5B409C9726737E77A533B6283D15E599-403f9a54cc3f3d6961d58eacd5ec97a1?jsonpCallback=jsonp1563543908000&mobilePhone="+account+"&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.chinaacc.com/")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			return response.body().string().contains("已绑定");
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
