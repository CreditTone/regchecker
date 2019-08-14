package com.jisucloud.clawler.regagent.service.impl.education;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "smartstudy.com", 
		message = "智课网(SmartStudy)汇聚托福、雅思、GRE、GAMT、SAT五大出国考试各科首席讲师，在线讲授出国考试精品课程与热门教材题库逐题精讲。顶级名师团队，影视级大片品质，智能学习过程管理，基于知识点。", 
		platform = "smartstudy", 
		platformName = "智课网", 
		tags = { "托福", "雅思", "GRE" }, 
		testTelephones = { "15956434943", "18210538513" })
public class ZhiXueWangSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://api.smartstudy.com/usert/signin";
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Referer", "https://www.smartstudy.com/signin?smartRedirect=https%3A%2F%2Fwww.smartstudy.com%2F")
					.addHeader("X-Requested-With", "XMLHttpRequest")
					.post(createUrlEncodedForm("account="+account+"&password=dasdsa12312&from=pc&group=C&countryCode=86"))
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("失败")) {
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
