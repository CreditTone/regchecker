package com.jisucloud.clawler.regagent.service.impl.life;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class ZhangFuTongSpider extends PapaSpider {

	


	@Override
	public String message() {
		return "掌付通是广州掌溢通网络科技有限公司推出的一款集合交通罚款代缴，年票代缴的网上支付平台，专注于自助交易服务平台的建设和运营，通过网站、手机客户端、电话等方式为用户提供。";
	}

	@Override
	public String platform() {
		return "24pay";
	}

	@Override
	public String home() {
		return "24pay.net";
	}

	@Override
	public String platformName() {
		return "掌付通";
	}

	@Override
	public String[] tags() {
		return new String[] {"工具" , "违章查询", "生活应用"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13925306960", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		if (account.length() != 11) {
			return false;
		}
		try {
			String url = "http://www.24pay.net/user/Login.htm";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("user.registName", account)
	                .add("user.registPhoneNo", account)
	                .add("user.userPassword", "x01jhxnsao")
	                .add("user.loginType", "")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.24pay.net")
					.addHeader("Referer", "http://www.24pay.net/Forward.htm?page=urp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request)
					.execute();
			String res = response.body().string();
			if (res.contains("密码不正确")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
