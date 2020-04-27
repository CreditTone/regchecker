package com.jisucloud.clawler.regagent.service.impl.health;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "kfyao.com", 
		message = "快方送药是国内智能柜新零售开创者,自成立以来先后获得九合创投、竞技创投及天图资本等知名投资机构3亿投资,总部位于北京,旗下快方送药是国内领先的送药上门服务品牌。", 
		platform = "kfyao", 
		platformName = "快方送药", 
		tags = { "医疗", "购药" }, 
		testTelephones = { "15510257873", "18212345678" })
public class KuaiFangSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "http://okm.kfyao.com/v3/ps.php";
			String postData = "pd=21312adasdasd&id="+account+"&ty=4&at=a&tk=cea6049b14626f8845a5b6e3607c471c9dca39ac&mk=0a52ae3b93463dbe3192d6dca64bfbd7&ps=88abbed20a4421e5b923e5fcaed92a44";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Dalvik/1.6.0 (Linux; U; Android 4.4.2; 8692-A00 Build/KOT49H)")
					.addHeader("Host", "okm.kfyao.com")
					.addHeader("Referer", "https://my.39.net/passport/reg.aspx?ref=http://www.39.net/")
					.post(FormBody.create(MediaType.parse("application/x-www-form-urlencoded;charset=UTF-8"), postData))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			res = StringUtil.unicodeToString(res);
			if (res.contains("登录密码错误")) {
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
