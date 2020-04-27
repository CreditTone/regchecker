package com.jisucloud.clawler.regagent.service.impl.health;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "39.net", 
		message = "39健康网,专业的健康资讯门户网站,中国优质医疗保健信息与在线健康服务平台,医疗保健类网站杰出代表,荣获中国标杆品牌称号。提供专业、完善的健康信息服务,包括疾病。", 
		platform = "39jk", 
		platformName = "39健康网", 
		tags = { "健康运动", "医疗", "生活应用" , "挂号" , "用药" }, 
		testTelephones = { "13877117175", "18212345678" })
public class _39JianKangSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://my.39.net/UserService.asmx/CheckPhoneReg";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("phone", account)
	                .add("pid", "0")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Host", "my.39.net")
					.addHeader("Referer", "https://my.39.net/passport/reg.aspx?ref=http://www.39.net/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("已存在")) {
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
