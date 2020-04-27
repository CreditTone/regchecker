package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Headers;
import okhttp3.Request;
import okhttp3.Response;



import java.util.HashMap;
import java.util.Map;


@PapaSpiderConfig(
		home = "yuanbaopu.com", 
		message = "凭借成熟的大数据技术,元宝铺帮助全国5万多家小微企业从银行等金融机构处获得超过50亿元的授信额度,旗下有数据化信贷解决方案FIDE及以电商贷为首的多款数据信贷.", 
		platform = "yuanbaopu", 
		platformName = "元宝铺", 
		tags = { "P2P", "电商贷" , "借贷" }, 
		testTelephones = { "18210538577", "18212345678" })
@Slf4j
public class YuanBaoPuSpider extends PapaSpider {
	

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
