package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deep007.spiderbase.okhttp.OKHttpUtil;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import com.google.common.collect.Sets;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class DabaiqicheSpider extends PapaSpider {
	
	OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClient();

	@Override
	public String message() {
		return "“大白汽车分期”是为购车人群提供分期服务的新车购车APP.";
	}

	@Override
	public String platform() {
		return "qufenqi";
	}

	@Override
	public String home() {
		return "qufenqi.com";
	}

	@Override
	public String platformName() {
		return "大白汽车分期";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "消费分期" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538577", "18210538513");
	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		return headers;
	}
	
	
	private String getImgCode() {
		String img = "https://passport.qufenqi.com/verify/getimg?r=0."+System.currentTimeMillis();
		try {
			byte[] body = okHttpClient.newCall(new Request.Builder().url(img).build()).execute().body().bytes();
			return OCRDecode.decodeImageCode(body);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean checkTelephone(String account) {
		String url = "https://passport.qufenqi.com/i/resetloginpass/setaccount";
		for (int i = 0; i < 5; i++) {//最大尝试5次
			try {

				String imgcode = getImgCode();
				FormBody formBody = new FormBody
		                .Builder()
		                .add("mobile", account)
		                .add("imgcode", imgcode)
		                .build();
				Request request = new Request.Builder().url(url)
						.headers(Headers.of(getHeader()))
						.post(formBody)
						.build();
				Response response = okHttpClient.newCall(request).execute();
				if (response != null ) {
					JSONObject result = JSON.parseObject(response.body().string());
					//log.info(result.toJSONString());
					int code = result.getIntValue("code");
					if (code == 1) {//未注册
						return false;
					}
					if (code == 0) {//已注册
						return true;
					}
					//验证码错误
					continue;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		return checkTelephone(account);
	}

	@Override
	public Map<String, String> getFields() {
		return null;
	}

}
