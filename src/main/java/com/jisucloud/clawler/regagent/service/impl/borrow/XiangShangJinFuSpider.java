package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class XiangShangJinFuSpider extends PapaSpider {

	@Override
	public String message() {
		return "向上金服(xiangshang360.com)-互联网金融协会会员,国内专业的互联网金融投资平台 ,注册资本1亿元,具备国家信息安全等级保护三级认证,平台用户超700+万,成交额破500亿,银行资金存管,投资体验升级.交易过程公开透明,诚信运营六周年。新手注册领1000元礼包。";
	}

	@Override
	public String platform() {
		return "xiangshang360";
	}

	@Override
	public String home() {
		return "xiangshang360.com";
	}

	@Override
	public String platformName() {
		return "向上金服";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268904", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.xiangshang360.cn/xweb/register/checkMobile?mobile=" + account;
			Session session = JJsoup.newSession();
			Connection.Response response = session.connect(url)
					.method(Method.POST)
					.header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.header("Host", "www.xiangshang360.com")
					.header("Referer", "https://www.xiangshang360.cn/xweb/logout")
					.ignoreContentType(true)
					.execute();
			if (response.body().contains("已注册")) {
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
