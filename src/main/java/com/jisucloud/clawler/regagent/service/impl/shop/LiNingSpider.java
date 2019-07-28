package com.jisucloud.clawler.regagent.service.impl.shop;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class LiNingSpider extends PapaSpider {

	@Override
	public String message() {
		return "李宁官方商城，为你展现李宁运动鞋、跑步鞋、篮球鞋、运动服、羽毛球拍、乒乓球拍、Polo、T恤、背包等专业运动装备。7天无理由退换货，300个城市货到付款。李宁，让改变发生，make the change。";
	}

	@Override
	public String platform() {
		return "lining";
	}

	@Override
	public String home() {
		return "lining.com";
	}

	@Override
	public String platformName() {
		return "李宁官方商城";
	}

	@Override
	public String[] tags() {
		return new String[] {"购物" , "运动鞋" , "户外装备"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13695286288", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://store.lining.com/shop/comm_tplfun/xajax_commUser_pro.php?operFlg=7";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .add("userName", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://store.lining.com/shop/register.html?redirect_url=https://store.lining.com/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			return result.getIntValue("data") == 2;
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
