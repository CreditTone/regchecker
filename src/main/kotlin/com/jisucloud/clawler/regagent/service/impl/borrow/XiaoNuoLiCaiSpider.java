package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import com.google.common.collect.Sets;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class XiaoNuoLiCaiSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "小诺理财是诺远科技发展有限公司（以下简称：诺远科技）旗下互联网金融信息中介服务平台，创建于2015年，总部位于北京。 小诺理财风险管理依托于FICO评分系统，结合本土化大数据模型，利用物联网技术创新及大数据征信系统。";
	}

	@Override
	public String platform() {
		return "nyonline";
	}

	@Override
	public String home() {
		return "nyonline.com";
	}

	@Override
	public String platformName() {
		return "小诺理财";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "理财" , "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252000", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://nyonline.cn/ups/app/v4_0_0/dologin.c";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("version", "2.7.9")
	                .add("client", "2")
	                .add("channel", "wandoujia")
	                .add("imei", "1b1a96e945e97e73f4a221eb87122ceb")
	                .add("platform", "0")
	                .add("terminal", "2")
	                .add("accounttype", "1")
	                .add("name", account)
	                .add("salt", "631011905CFAC6F8DB1B48085654032C")
	                .add("saltpwd", "1248d4de26d3e433774cba4338a76269")
	                .add("deviceid", "AoA9pCQ8fN9uxHWVRLgE9GpEpkl_29Nf_gb_kit1ZbIO")
	                .add("tk", "bX2WCbk1kh+hpFfUmMQuWBz1UjVnFz2NRS+0LJPtZOAXxBU8sTK4dmafw43xG6q4ERO3dryKs2nmOOmtHs3s2q4+ifn+FPOI3xYQdCohYyQ=")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("Host", "nyonline.cn")
					.addHeader("Referer", "https://nyonline.cn/ups/app/v4_0_0/dologin.c")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getIntValue("code") == -10) {
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
