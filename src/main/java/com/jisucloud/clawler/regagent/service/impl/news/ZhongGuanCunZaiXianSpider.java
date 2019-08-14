package com.jisucloud.clawler.regagent.service.impl.news;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "zol.com", 
		message = "中国领先的IT信息与商务门户, 包括新闻, 商城, 硬件, 下载, 游戏, 手机, 评测等40个大型频道,每天发布大量各类产品促销信息及文章专题,是IT行业的厂商。", 
		platform = "zol", 
		platformName = "中关村在线", 
		tags = { "新闻资讯" , "3C资讯" , "智能手机" }, 
		testTelephones = { "18720982007", "18210538513" })
public class ZhongGuanCunZaiXianSpider extends PapaSpider {


	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://my.zol.com.cn/index.php?c=Ajax_User&a=CheckPhone";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "my.zol.com.cn")
					.addHeader("Referer", "http://my.zol.com.cn/index.php?c=getPassword")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			if (response.body().string().contains("ok")) {
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
