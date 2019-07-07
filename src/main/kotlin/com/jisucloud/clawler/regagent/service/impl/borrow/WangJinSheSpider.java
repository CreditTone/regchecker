package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

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
public class WangJinSheSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "网金社是由恒生电子、蚂蚁金服、中投保共同设立的浙江互联网金融资产交易中心股份有限公司推出的互联网金融平台。 ";
	}

	@Override
	public String platform() {
		return "wjs";
	}

	@Override
	public String home() {
		return "wjs.com";
	}

	@Override
	public String platformName() {
		return "网金社";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.wjs.com/web/login/mobileLogin";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobileNo", account)
	                .add("password", "2dd573568d7358ef618ce940565c5b7b8cc0b2ee7b8f2916f2b6b91aed0e4561ce70ccb76b9f4604f725ead9a6483a18fb05bbfa093a90ce6f84651c33292acec95c2e698a18f2c4ba9f7d5bcadeae783afa261cb4cd8a2ac426efc82d1cb632f63bc0c46adb5ab7e62911b5431d7ab1b76f29fee4f12a63700a6c448fb6eb15")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "www.wjs.com")
					.addHeader("Referer", "https://www.wjs.com/web/login/index")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			return res.contains("密码不正确");
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
