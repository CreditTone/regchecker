package com.jisucloud.clawler.regagent.service.impl.life;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpider;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class YYSpider implements PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "YY直播(yy.com)是中国最大的视频直播网站,yy语音官网,提供大神妹子英雄联盟,cf,dota等热门网络和单机游戏视频直播,美女帅哥视频秀场,同城视频互动交友,股票分析培训.速度流畅不卡,数万美女帅哥主播任你看.";
	}

	@Override
	public String platform() {
		return "yy";
	}

	@Override
	public String home() {
		return "yy.com";
	}

	@Override
	public String platformName() {
		return "YY直播";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("娱乐", new String[] { });
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new YYSpider().checkTelephone("13844441111"));
//		System.out.println(new YYSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://lgn.yy.com/lgn/oauth/x2/s/login_asyn.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("pwdencrypt", "7860afdd76fc4f2739ec0a83042316475ede6661d3c15af6cee7c34b3cd864c70333580222482c6f297a984afddc541efec88a98385d364114d818b45119d97de9334ee571e62878c35f69524270e45b4ac0fa35d3aeebaebeb2421a46ffcd7bb335bd0b868fee1f52af0172d13a1d9c2d26506050199251190b5ed088f8828a")
	                .add("oauth_token", "b07f31698b48f8a6459337ac223d20a35fa38518437a51a7971e6dd140412ad2b0729cf3fe92677b6d998eb42b604622")
	                .add("denyCallbackURL", "http://www.yy.com/login/udbCallback?cancel=1")
	                .add("UIStyle", "xelogin")
	                .add("appid", "5719")
	                .add("needVerify", "0")
	                .add("mxc", "")
	                .add("vk", "")
	                .add("isRemMe", "1")
	                .add("mmc", "")
	                .add("vv", "")
	                .add("hiido", "1")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "lgn.yy.com")
					.addHeader("Referer", "https://lgn.yy.com/lgn/oauth/authorize.do?oauth_token=b07f31698b48f8a6459337ac223d20a35fa38518437a51a7971e6dd140412ad2b0729cf3fe92677b6d998eb42b604622&denyCallbackURL=http://www.yy.com/login/udbCallback?cancel=1&cssid=5719&regCallbackURL=//www.yy.com/login/udbCallback&UIStyle=xelogin&rdm=0.7874779273128858")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			JSONObject result = JSON.parseObject(response.body().string());
			if (result.getString("code").equals("1000010")) {
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
