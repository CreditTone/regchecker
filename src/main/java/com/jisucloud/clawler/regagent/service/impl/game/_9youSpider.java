package com.jisucloud.clawler.regagent.service.impl.game;

import com.deep007.spiderbase.StringUtil;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class _9youSpider extends PapaSpider {

	@Override
	public String message() {
		return "互动娱乐社区2.0,娱乐时尚尽在久游... 久游一卡通充值 网上银行直充 二维码充值 余额查询 客服中心 在线服务 微信服务 在线举报 联系客服 游乐会中心 游乐会会员申请";
	}

	@Override
	public String platform() {
		return "9you";
	}

	@Override
	public String home() {
		return "9you.com";
	}

	@Override
	public String platformName() {
		return "久游网";
	}

	@Override
	public String[] tags() {
		return new String[] {"游戏"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18209649992", "18210538513");
	}

	@Override
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
