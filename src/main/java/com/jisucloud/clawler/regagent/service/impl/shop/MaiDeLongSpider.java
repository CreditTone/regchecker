package com.jisucloud.clawler.regagent.service.impl.shop;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class MaiDeLongSpider extends PapaSpider {

	@Override
	public String message() {
		return "麦德龙官方网上商城，精选实体门店优质商品，在线销售进口商品、食品饮料、生活日化，清洁用品等商品，同样的门店活动，不一样的在线购物体验。";
	}

	@Override
	public String platform() {
		return "metromall";
	}

	@Override
	public String home() {
		return "metromall.cn";
	}

	@Override
	public String platformName() {
		return "麦德龙";
	}

	@Override
	public String[] tags() {
		return new String[] {"购物" , "进口商品" , "优质商品"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13991808887", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.metromall.cn/tools/ajax_submit.ashx?operation=check_mobile_exists";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.metromall.cn/user/Reg.aspx")
					.post(createUrlEncodedForm("param="+account+"&name=RegMobile"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			return res.contains("已经注册");
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
