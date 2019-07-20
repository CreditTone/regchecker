package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class LiCaiFanSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "理财范隶属于北京网融天下金融信息服务有限公司。于2014年3月上线。理财范利用对金融产业与中小企业生态的理解，嫁接新兴的风险管理模型，围绕中小企业融资、个人及家庭消费、借贷，提供一站式的网络借贷信息中介服务。";
	}

	@Override
	public String platform() {
		return "licaifan";
	}

	@Override
	public String home() {
		return "licaifan.com";
	}

	@Override
	public String platformName() {
		return "理财范";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "理财" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://wapi.licaifan.com/wapi/user/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("password", "moasd12b45ile")
	                .add("from", "wap")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", ANDROID_USER_AGENT)
					.addHeader("Host", "wapi.licaifan.com")
					.addHeader("Referer", "https://m.licaifan.com/login")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("108")) {
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
