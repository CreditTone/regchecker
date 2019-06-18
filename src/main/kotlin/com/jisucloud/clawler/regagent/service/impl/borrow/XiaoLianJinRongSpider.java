package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class XiaoLianJinRongSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	@Override
	public String message() {
		return "笑脸金融是阳光保险集团旗下成员企业在深圳投资发起成立,由深圳光华普惠科技有限公司运营。拥有健全严格的风险管控体系,汇集优质资产, 1000元起投、收益高,安全有保障。";
	}

	@Override
	public String platform() {
		return "facebank";
	}

	@Override
	public String home() {
		return "facebank.com";
	}

	@Override
	public String platformName() {
		return "笑脸金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new XiaoLianJinRongSpider().checkTelephone("15985268904"));
//		System.out.println(new XiaoLianJinRongSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://cic.facebank.cn/app/preLogin";
			String json = "{\"data\":{\"mobile\":\""+account+"\",\"businessChanel\":\"\",\"channel\":\"PRO\",\"curversion\":\"smilebank3.1.3\",\"deviceName\":\"8692-A00\",\"deviceNetState\":\"wifi\",\"deviceNo\":\"3bf22b9088ab9578\",\"deviceScreenSize\":\"480*800\",\"jPushDeviceTokenNew\":\"1a0018970aee405cfd8\",\"macAddress\":\"08:6D:41:D5:7A:6A\",\"messageId\":\"preLogin\",\"origin\":\"app\",\"platform\":\"android\",\"platformVersion\":\"4.4.2\"}}";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json") , json);
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "okhttp/3.10.0")
					.addHeader("Host", "cic.facebank.cn")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (!result.getJSONObject("data").getString("userStatus").equals("100")) {
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
