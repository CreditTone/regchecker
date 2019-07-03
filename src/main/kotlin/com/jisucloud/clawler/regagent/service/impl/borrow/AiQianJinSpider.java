package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class AiQianJinSpider implements PapaSpider {
	
	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();
	
	private boolean checkTelephone = false;
	
	//暂时不能访问此页面，被反扒
	public boolean success = false;//默认false

	@Override
	public String message() {
		return "爱钱进是凡普金科旗下网络借贷信息中介平台,位列第三方权威评级机构网贷天眼全国百强榜前十,始终致力于为用户提供简单、公平的互联网金融信息服务,是消费者心中靠谱的网络借贷。";
	}

	@Override
	public String platform() {
		return "iqianjin";
	}

	@Override
	public String home() {
		return "iqianjin.com";
	}

	@Override
	public String platformName() {
		return "爱钱进";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13879530000", "18210538513");
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://v2.iqianjin.com/C2000/M2001";
			String body = "{\"body\":{\"passwd\":\"79C4A08BAC93B3BF81E7EACD0D76FFF014F44C0C4148755A3CA8365D4137377EAF4E175A9AA5D38A9C74C9382B9E0DA79518789CD8501110139855B4C78A9861B4E6AED96E337B282710A5505DDB9A4199009C2D7C9D7AD9FD7DEC38922548F2BEA43A771D3AE3CE69FF6736BF554404B8E75A0792230A64C8803D58D0ADA5A4\",\"username\":\""+account+"\"},\"comm\":{\"pid\":\"352284040670808\",\"type\":3,\"us\":3279,\"version\":\"4.1.0\"},\"token\":\"E6D564A42E88FDBCDCBD8A240E56EDD037A5296341819DBCF4BC95A3FB9B5CF41FD6EC6DD0740922D6A6248E3D16F337GCA92692B151F3B1ED989CA47E0F333E4\"}";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.myerong.com")
					.addHeader("Referer", "https://www.myerong.com/sites/pages/register/register.html")
					.post(FormBody.create(MediaType.parse("text/plain; charset=UTF-8"), body))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String reString = response.body().string();
			return reString.contains("手机号已注册");
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
		}
		return checkTelephone;
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
