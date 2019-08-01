package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@UsePapaSpider
@Slf4j
public class YuanBaoPuSpider extends PapaSpider {

	@Override
	public String message() {
		return "凭借成熟的大数据技术,元宝铺帮助全国5万多家小微企业从银行等金融机构处获得超过50亿元的授信额度,旗下有数据化信贷解决方案FIDE及以电商贷为首的多款数据信贷.";
	}

	@Override
	public String platform() {
		return "yuanbaopu";
	}

	@Override
	public String home() {
		return "yuanbaopu.com";
	}

	@Override
	public String platformName() {
		return "元宝铺";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "电商贷" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538577", "18210538513");
	}

	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "www.yuanbaopu.com");
		headers.put("Referer", "https://www.yuanbaopu.com/user/register.htm");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return Headers.of(headers);
	}
	
	private String getImgCode() {
		String img = "https://www.yuanbaopu.com/checkcode/service.htm?time="+System.currentTimeMillis();
		Response response;
		try {
			response = get(img);
			if (response != null) {
				byte[] body = response.body().bytes();
				return OCRDecode.decodeImageCode(body);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean checkTelephone(String account) {
		for (int i = 0; i < 3; i++) {
			try {
				String url = "https://www.yuanbaopu.com/register/checkMobile.htm?fieldValue="+account+"&imgCode="+getImgCode();
				Request request = new Request.Builder().url(url)
						.headers(getHeader())
						.build();
				Response response = okHttpClient.newCall(request).execute();
				String res = response.body().string();
				if (res.contains("验证码")) {
					continue;
				}
				return res.contains("已被注册");
			} catch (Exception e) {
				e.printStackTrace();
			}
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
