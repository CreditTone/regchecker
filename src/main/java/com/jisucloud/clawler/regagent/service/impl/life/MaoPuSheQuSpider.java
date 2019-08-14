package com.jisucloud.clawler.regagent.service.impl.life;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "mop.com", 
		message = "猫扑网络流行文化发源地,汇集大杂烩,贴贴、视频、娱乐新闻、文学、汽车等内容为一体的娱乐资讯聚合移动新媒体。坚持BT和YY的娱乐精神,结合大数据,深度挖掘新闻背后的论坛BBS。", 
		platform = "mop", 
		platformName = "猫扑网络", 
		tags = { "资讯", "社区", "论坛" }, 
		testTelephones = { "13910252045", "18210538513" })
public class MaoPuSheQuSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://passport.mop.com/ajax/mobileWasBound?callback=jQuery183030799903888868707_"+System.currentTimeMillis()+"&mobile="+account+"&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "http://www.mop.com/register.html")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("该手机已绑定")) {
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
