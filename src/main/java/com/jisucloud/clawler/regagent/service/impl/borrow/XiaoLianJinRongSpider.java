package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;


import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "facebank.com", 
		message = "笑脸金融是阳光保险集团旗下成员企业在深圳投资发起成立,由深圳光华普惠科技有限公司运营。拥有健全严格的风险管控体系,汇集优质资产, 1000元起投、收益高,安全有保障。", 
		platform = "facebank", 
		platformName = "笑脸金融", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" },
		exclude = true, 
		excludeMsg = "由于笑脸平台服务升级，当前版本的APP已不再提供服务。请去应用商店下载和安装最新版本的「笑脸金融」APP")
public class XiaoLianJinRongSpider extends PapaSpider {

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
			System.out.println(result);
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
