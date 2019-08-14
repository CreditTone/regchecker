package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;



import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "51jindan.com", 
		message = "我要金蛋 深圳市伯利滋财富在线我要借款 信息披露 安全中心 关于我们平台公告更多 项目真实 保证项目真实,为理性投资者搭建一个透明安全的平台 投资保障 资金专户管理,国资背景第三方支付通道。", 
		platform = "51jindan", 
		platformName = "我要金蛋", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class WoYaoJinDanSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.51jindan.com/service/person/checkMobileIfRegister";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.51jindan.com")
					.addHeader("Referer", "https://www.51jindan.com")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			return res.contains("已被注册");
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
