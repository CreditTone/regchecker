package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class HuDongBaiKeSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();


	@Override
	public String message() {
		return "互动百科，原称互动维客，是由潘海东在2005年创建的商业中文百科网站，隶属于北京互动百科网络技术股份有限公司。2013年1月，互动百科词条超过1300万条、5万个分类、68亿文字、721万张图片。";
	}

	@Override
	public String platform() {
		return "baike";
	}

	@Override
	public String home() {
		return "baike.com";
	}

	@Override
	public String platformName() {
		return "互动百科";
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13925306966", "18210538513");
	}

	@Override
	public String[] tags() {
		return new String[] {"社区", "知识" , "学习"};
	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://passport.baike.com/ajaxRegister.do?phone="+account+"&submittype=0";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "passport.baike.com")
					.addHeader("Referer", "http://passport.baike.com/user/userRegister.jsp")
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			if (response.body().string().contains("已被注册")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
