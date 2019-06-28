package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.http.OKHttpUtil;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.mockito.internal.util.collections.Sets;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZhongXinJinRongSpider implements PapaSpider {

	private boolean checkTel = false;
	private OkHttpClient okHttpClient = OKHttpUtil.createOkHttpClient();
	
	@Override
	public String message() {
		return "众信金融网致力于金融产品的推广及服务，着力打造为集融资、理财、保险等金融产品及服务为一体的金融咨询及交流平台，方便金融需求方与金融专家的交流，及时获得及时、全面、专业的金融咨询服务。";
	}

	@Override
	public String platform() {
		return "imzhongxin";
	}

	@Override
	public String home() {
		return "imzhongxin.com";
	}

	@Override
	public String platformName() {
		return "众信金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷" , "保险"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newSet("15201215815", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			FormBody formBody = new FormBody
	                .Builder()
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url("https://www.imzhongxin.com/trust/register/checkmobile")
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.imzhongxin.com")
					.addHeader("Referer", "https://www.imzhongxin.com/trust/register/index")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("exist")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		}
		return checkTel;
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
