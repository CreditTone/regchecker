package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "xwsd.com", 
		message = "小微时贷（www.xwsd.com）是国内知名汽车抵押借贷服务的互联网金融P2P网贷平台，历史年化收益11%-18%，致力于运营安全透明的互联网融资租赁车贷平台.网贷投资,就选小微时贷。", 
		platform = "xwsd", 
		platformName = "小微时贷", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18212345678", "15956434943" })
public class XiaoWeiShiDaiSpider extends PapaSpider {

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

}
