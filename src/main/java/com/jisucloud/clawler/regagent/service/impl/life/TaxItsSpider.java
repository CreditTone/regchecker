package com.jisucloud.clawler.regagent.service.impl.life;

import java.util.Map;

import java.util.UUID;

import org.jsoup.Jsoup;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import org.jsoup.Connection;
import org.springframework.http.MediaType;

@PapaSpiderConfig(
		home = "its.tax.sh.gov.cn", 
		message = "个人所得税APP是由国家税务总局主办，为贯彻落实党中央、国务院提出的个人所得税综合与分类相结合的税制改革要求，为保障全国自然人纳税人能够及时享受税改红利，而推出的一款APP。", 
		platform = "gerensuodeshui", 
		platformName = "个人所得税", 
		tags = { "纳税", "五险一金" }, 
		testTelephones = { "18210538513" , "18210008510" },
		exclude = true, excludeMsg = "接口404")
public class TaxItsSpider extends PapaSpider {


	@Override
	public boolean checkTelephone(String account) {
		// TODO Auto-generated method stub
		String sbuuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
		String postData = " {\n" + 
				"                    \"dlbz\":\""+account+"\",\n" + 
				"                    \"mm\":\"NJ2jhyBb5UWIaANmTNj+Ugej5mcgl3bOpH3fajGyzk+KPydzDwWx90RnslNbneip7HpqkAGLPEHhNuwb9Bx+tU78fPMToKcFThmMGcni+jBi/Yg/+PL2qf3aX19/Jrfx34haTw4tQXcZsuIiPj0aHLXNvGuE2wOXrhTV26wPPIY=\",\n" + 
				"                    \"appVersion\":\"1.1.8\",\n" + 
				"                    \"sbuuid\":\""+sbuuid+"\",\n" + 
				"                    \"sbcs\":\"LGE\",\n" + 
				"                    \"sbczxt\":\"android\"\n" + 
				"                }";
		try {
			String body = Jsoup.connect("https://mits.anhui.chinatax.gov.cn:17001/web/zh/login/zhmmdl")
			        .requestBody(postData)
			        .userAgent("Dalvik/2.1.0 (Linux; U; Android 5.1.1; Nexus 5 Build/LMY48M)")
			        .header("Content-Type", MediaType.APPLICATION_JSON_UTF8_VALUE)
			        .ignoreContentType(true)
			        .method(Connection.Method.POST)
			        .execute().body();
			return !body.contains("账号或密码错误,请重新输入");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

	@Override
	public boolean checkEmail(String account) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Map<String, String> getFields() {
		// TODO Auto-generated method stub
		return null;
	}

}
