package com.jisucloud.clawler.regagent.service.impl.borrow;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "51qianba.com", 
		message = "钱吧(www.51qianba.com)专注于汽车抵押投资,是互联网金融中介的领军者。融合“互联网+金融+汽车”,致力于为投融双方提供安全、高效、便捷、透明的互联网金融信息。", 
		platform = "51qianba", 
		platformName = "钱吧", 
		tags = { "p2p", "借贷" }, 
		testTelephones = { "18210538513", "15161509916" })
public class QianBaSpider extends PapaSpider {

	

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.51qianba.com/web/registerController.do?ajaxregisterStep1";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username_regist", account)
	                .add("ValCode", "wrzq")
	                .add("visitCode", "")
	                .add("recPsnId", "")
	                .add("loginVC", "phone")
	                .add("uiRole", "2")
	                .add("registerflg", "0")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Cookie", "fromApp=true; SEO_META_SET$main=%7B%22TITLE%22%3A%22%E9%92%B1%E5%90%A7-%E4%B8%93%E6%B3%A8%E6%B1%BD%E8%BD%A6%E6%8A%B5%E6%8A%BC%E7%9A%84%E4%BA%92%E8%81%94%E7%BD%91%E6%8A%95%E8%B5%84%E4%B8%AD%E4%BB%8B_%E7%BD%91%E7%BB%9C%E5%80%9F%E8%B4%B7_%E4%B8%AA%E4%BA%BA%E6%8A%95%E8%B5%84_%E8%BD%A6%E8%B4%B7%E6%9C%8D%E5%8A%A1%E5%B9%B3%E5%8F%B0%22%2C%22KEYWORDS%22%3A%22%E9%92%B1%E5%90%A7%EF%BC%8C%E7%BD%91%E7%BB%9C%E5%80%9F%E8%B4%B7%EF%BC%8C%E4%B8%AA%E4%BA%BA%E6%8A%95%E8%B5%84%EF%BC%8C%E6%8A%95%E8%B5%84%E4%B8%AD%E4%BB%8B%EF%BC%8C%E4%BA%92%E8%81%94%E7%BD%91%E9%87%91%E8%9E%8D%EF%BC%8C%E6%B1%BD%E8%BD%A6%E6%8A%B5%E6%8A%BC%EF%BC%8C%E8%BD%A6%E8%B4%B7%22%2C%22DESCRIPTION%22%3A%22%E9%92%B1%E5%90%A7(www.51qianba.com)%E4%B8%93%E6%B3%A8%E4%BA%8E%E6%B1%BD%E8%BD%A6%E6%8A%B5%E6%8A%BC%E6%8A%95%E8%B5%84%EF%BC%8C%E6%98%AF%E4%BA%92%E8%81%94%E7%BD%91%E6%8A%95%E8%B5%84%E4%B8%AD%E4%BB%8B%E7%9A%84%E9%A2%86%E5%86%9B%E8%80%85%E3%80%82%E8%9E%8D%E5%90%88%E2%80%9C%E4%BA%92%E8%81%94%E7%BD%91%2B%E9%87%91%E8%9E%8D%2B%E6%B1%BD%E8%BD%A6%E2%80%9D%EF%BC%8C%E8%87%B4%E5%8A%9B%E4%BA%8E%E4%B8%BA%E6%8A%95%E8%9E%8D%E5%8F%8C%E6%96%B9%E6%8F%90%E4%BE%9B%E5%AE%89%E5%85%A8%E3%80%81%E9%AB%98%E6%95%88%E3%80%81%E4%BE%BF%E6%8D%B7%E3%80%81%E9%80%8F%E6%98%8E%E7%9A%84%E4%BA%92%E8%81%94%E7%BD%91%E9%87%91%E8%9E%8D%E4%BF%A1%E6%81%AF%E6%9C%8D%E5%8A%A1%2C%E4%B8%BA%E6%8A%95%E8%B5%84%E8%80%85%E6%8F%90%E4%BE%9B%E4%BD%8E%E9%A3%8E%E9%99%A9%E3%80%81%E9%AB%98%E5%9B%9E%E6%8A%A5%E7%9A%84%E7%BD%91%E7%BB%9C%E5%80%9F%E8%B4%B7%E4%BA%A7%E5%93%81%E3%80%82%22%7D; mediav=%7B%22eid%22%3A%22259303%22%2C%22ep%22%3A%22%22%2C%22vid%22%3A%22Y.H)LETqE%60%3Amt%25*F..%3Fd%22%2C%22ctn%22%3A%22%22%7D; SEO_META_SET$register=%7B%22TITLE%22%3A%22%E6%B3%A8%E5%86%8C_%E5%8A%A0%E5%85%A5%E9%92%B1%E5%90%A7%EF%BC%8C%E5%BC%80%E5%A7%8B%E8%B5%9A%E9%92%B1%E5%90%A7%22%2C%22KEYWORDS%22%3Anull%2C%22DESCRIPTION%22%3Anull%7D; SEO_META_SET$fdLogin=%7B%22TITLE%22%3A%22%E7%AB%8B%E5%8D%B3%E7%99%BB%E5%BD%95_%E7%95%85%E6%83%B3%E6%8A%95%E8%B5%84%E4%B8%AD%E4%BB%8B%E4%BA%A7%E5%93%81%EF%BC%8C%E4%BB%8E%E7%8E%B0%E5%9C%A8%E5%BC%80%E5%A7%8B%22%2C%22KEYWORDS%22%3A%22%E6%8A%95%E8%B5%84%E4%B8%AD%E4%BB%8B%E4%BA%A7%E5%93%81%22%2C%22DESCRIPTION%22%3A%22%E6%8A%8A%E6%8F%A1%E6%8A%95%E8%B5%84%E4%B8%AD%E4%BB%8B%E4%BA%A7%E5%93%81%EF%BC%8C%E7%8E%B0%E5%9C%A8%E7%AB%8B%E5%88%BB%E7%99%BB%E5%BD%95%22%7D;")
					.addHeader("Referer", "https://www.51qianba.com/web/registerController.do?register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("已注册")) {
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
