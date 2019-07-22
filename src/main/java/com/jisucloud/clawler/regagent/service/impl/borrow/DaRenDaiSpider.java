package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class DaRenDaiSpider extends PapaSpider {

	@Override
	public String message() {
		return "达人贷是名校团队创立的专业P2P网络借贷平台,专注小微金融业务,自建小微资产业务团队和风控体系,银行资金存管,广东互联网金融协会成员,2018胡润新金融百强榜最具潜力P2P。";
	}

	@Override
	public String platform() {
		return "darenloan";
	}

	@Override
	public String home() {
		return "darenloan.com";
	}

	@Override
	public String platformName() {
		return "达人贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.darenloan.com/rf/support/validate/phoneAccount";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.darenloan.com/passport/register")
					.post(FormBody.create(MediaType.parse("application/json;charset=utf-8"), "{\"validate\":\""+account+"\"}"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已注册")) {
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
