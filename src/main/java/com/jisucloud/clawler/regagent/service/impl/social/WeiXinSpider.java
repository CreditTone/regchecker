package com.jisucloud.clawler.regagent.service.impl.social;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.service.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "pc.weixin.qq.com", 
		message = "微信(WeChat)是腾讯公司于2011年1月21日推出的一个为智能终端提供即时通讯服务的免费应用程序，由张小龙所带领的腾讯广州研发中心产品团队打造。微信支持跨通信运营商、跨操作系统平台通过网络快速发送免费。", 
		platform = "wechat", 
		platformName = "微信", 
		tags = { "社交" , "通信" , "金融" , "支付" , "生活应用" }, 
		testTelephones = { "13910250000", "18212345678" })
public class WeiXinSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://m.dianping.com/account/ajax/unloginVerifyOldMobile";
			String postdata = "mobile=86_"+account+"&dpid=&cx=&unlogin=true";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", ANDROID_USER_AGENT)
					.addHeader("Host", "m.dianping.com")
					.addHeader("Origin", "https://m.dianping.com")
					.addHeader("X-Requested-With", "com.dianping.v1")
					.addHeader("Referer", "https://m.dianping.com/account/unloginVerifyOldMobile")
					.post(FormBody.create(MediaType.get("application/x-www-form-urlencoded"), postdata))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String errorMsg = response.body().string();
			JSONObject result = JSON.parseObject(errorMsg);
			if (result.getIntValue("code") == 200) {
				return result.getJSONObject("msg").getBooleanValue("hasWX");
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
