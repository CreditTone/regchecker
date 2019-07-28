package com.jisucloud.clawler.regagent.service.impl.pay;

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
public class YunFuTongSpider extends PapaSpider {

	
	@Override
	public String message() {
		return "云付通集团是一家综合型互联网企业，全国运营中心设在广州市广州大道中988号圣丰广场国际金融中心25楼。云付通集团以创新理念推出——财富社交平台，主营业务为商务服务业。";
	}

	@Override
	public String platform() {
		return "ipaye";
	}

	@Override
	public String home() {
		return "ipaye.cn";
	}

	@Override
	public String platformName() {
		return "云付通";
	}


	@Override
	public String[] tags() {
		return new String[] {"聚合支付", "科技金融"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13418466345", "13193091201");
	}


	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://user.ipaye.cn/yunpay_static/api/v2/user/isMobileRegistable";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.addHeader("Referer", "http://user.ipaye.cn/yunpay_static/web/user/regist")
					.post(createUrlEncodedForm("terminal=2&mobile=" + account))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已被注册")) {
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
