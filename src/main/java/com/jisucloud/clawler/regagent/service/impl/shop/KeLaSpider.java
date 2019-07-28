package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class KeLaSpider extends PapaSpider {

	

	@Override
	public String message() {
		return "珂兰（珂兰钻石）诞生于2007年，是刚泰控股（股票代码:600687.SH）旗下全资O2O珠宝公司。珂兰凭借尊贵的婚戒定制服务，满足时下年轻人个性化需求，并以浪漫情愫的呈现与时尚设计，为600万新人幸福时刻带来至高礼赞。";
	}

	@Override
	public String platform() {
		return "kela";
	}

	@Override
	public String home() {
		return "kela.com";
	}

	@Override
	public String platformName() {
		return "珂兰钻石";
	}

	@Override
	public String[] tags() {
		return new String[] {"购物" , "首饰"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18779861101", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://www.kela.cn/pc.php?m=User&c=AccountApi&a=checkEmailPhone&emailPhone="+account;
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.kela.cn")
					.addHeader("Referer", "http://www.kela.cn/home/index/index")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已被注册")) {
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
