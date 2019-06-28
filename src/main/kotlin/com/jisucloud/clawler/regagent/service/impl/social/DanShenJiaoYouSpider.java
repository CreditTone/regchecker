package com.jisucloud.clawler.regagent.service.impl.social;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.net.URLDecoder;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class DanShenJiaoYouSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "单身交友是面向适龄男女的两友产品，综合性多维度智能算法帮助男女完成高效匹配，高效达成异友，帮助男女拓展交友广度和深度!";
	}

	@Override
	public String platform() {
		return "zjyuehui";
	}

	@Override
	public String home() {
		return "zjyuehui.cn";
	}

	@Override
	public String platformName() {
		return "单身交友";
	}

	@Override
	public String[] tags() {
		return new String[] {"单身交友" , "婚恋"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18779861101", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://huludst.zjyuehui.cn/(unknown)/user/login.gy?oh=EA0FCB54ED390B576905B4BEFB822A75_1559731414424_96c01059543a3540&ov=0.1";
			String postJson = "{\"uid\":\"\",\"loginTimes\":0,\"did\":\"DuBaM5V8RKBkZBdgsy80uhXs3DljLkDMJ3kt+SvNSu7oMSPGzBcCuih2ybmZOrOVjF33rcDtG7llVNKSekWtNjXQ\",\"account\":\""+account+"\",\"platformInfo\":{\"fid\":\"1000064\",\"platform\":\"3\",\"mobileIP\":\"\",\"versionCode\":97,\"pid\":\"352284040670808\",\"mac\":\"08:6D:41:D5:7A:6A\",\"versionName\":\"5.6.2\",\"signedHash\":\"D0DD4E5E48F0EB5C\",\"h\":800,\"version\":\"40050602\",\"w\":480,\"product\":\"10300\",\"phonetype\":\"a9xproltechn_MMB29M.A9100ZCU1AQB3\",\"netType\":2,\"systemVersion\":\"4.4.2\",\"baseType\":\"mlyh\",\"yyCode\":\"c9cee60adc38ee8277a0627656ab574c\",\"imsi\":\"\",\"packName\":\"com.hzsj.dsjy\",\"release\":\"20190528\"},\"password\":\"gdadfyyhuu\",\"loginType\":0,\"verType\":1}";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.2; 8692-A00 Build/KOT49H)")
					.addHeader("Host", "huludst.zjyuehui.cn")
					.post(FormBody.create(MediaType.get("application/json; charset=utf-8"), postJson))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String errorMsg = response.header("errorMsg");
			errorMsg = URLDecoder.decode(errorMsg);
			if (errorMsg.contains("不存在")) {
				return false;
			}
			if (errorMsg.contains("错误") || errorMsg.contains("不匹配") || errorMsg.contains("或")) {
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
