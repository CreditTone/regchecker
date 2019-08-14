package com.jisucloud.clawler.regagent.service.impl.game;


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
		home = "9you.com", 
		message = "互动娱乐社区2.0,娱乐时尚尽在久游... 久游一卡通充值 网上银行直充 二维码充值 余额查询 客服中心 在线服务 微信服务 在线举报 联系客服 游乐会中心 游乐会会员申请", 
		platform = "9you", 
		platformName = "久游网", 
		tags = { "游戏" }, 
		testTelephones = { "18209649992", "18210538513" })
public class _9youSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://passport.9you.com/check_user.php";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://passport.9you.com/mobile_regist.php")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("存在")) {
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
