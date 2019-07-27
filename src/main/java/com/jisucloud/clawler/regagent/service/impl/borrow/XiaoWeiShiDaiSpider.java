package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class XiaoWeiShiDaiSpider extends PapaSpider {

	@Override
	public String message() {
		return "小微时贷（www.xwsd.com）是国内知名汽车抵押借贷服务的互联网金融P2P网贷平台，历史年化收益11%-18%，致力于运营安全透明的互联网融资租赁车贷平台.网贷投资,就选小微时贷。";
	}

	@Override
	public String platform() {
		return "xwsd";
	}

	@Override
	public String home() {
		return "xwsd.com";
	}

	@Override
	public String platformName() {
		return "小微时贷";
	}

	@Override
	public String[] tags() {
		return new String[] {"p2p", "借贷"};
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://user.xwsd.com//common/isPhoneExist";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://user.xwsd.com/page/common/index.html")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getJSONObject("data").getIntValue("isExist") == 1) {
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
		return Sets.newHashSet("18210538513", "15956434943");
	}

}
