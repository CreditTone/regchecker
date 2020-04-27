package com.jisucloud.clawler.regagent.service.impl.life;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "jiehun.com", 
		message = "中国婚嫁领域一站式服务平台。每年春夏秋冬四季在北京、上海、广州、天津、武汉、杭州、成都等地同时举办大型结婚展，包含婚纱摄影、婚纱礼服、婚庆策划、婚宴酒店、结婚钻戒、婚庆用品、婚房装修、婚车等核心结婚消费行业。", 
		platform = "jiehun", 
		platformName = "中国婚博会", 
		tags = { "电商", "结婚" }, 
		testTelephones = { "13426345414", "18210000000" , "18212345678" })
public class ZhongGuoHunBoHuiSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://open.jiehun.com.cn/user/account/get-login";
			RequestBody formBody = FormBody.create(MediaType.parse("application/json;charset=utf-8"), "{\"username\":\""+account+"\",\"password\":\"dasdasdasd\",\"referer\":\"https://bj.jiehun.com.cn/\"}");
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_1 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A402 Safari/604.1")
					.addHeader("Host", "open.jiehun.com.cn")
					.addHeader("Referer", "https://m.jiehun.com.cn/login/?u=%2F")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("10004")) {
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
