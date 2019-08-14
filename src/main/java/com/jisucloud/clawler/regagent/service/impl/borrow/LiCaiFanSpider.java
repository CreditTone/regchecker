package com.jisucloud.clawler.regagent.service.impl.borrow;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Request;
import okhttp3.Response;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "licaifan.com", 
		message = "理财范隶属于北京网融天下金融信息服务有限公司。于2014年3月上线。理财范利用对金融产业与中小企业生态的理解，嫁接新兴的风险管理模型，围绕中小企业融资、个人及家庭消费、借贷，提供一站式的网络借贷信息中介服务。", 
		platform = "licaifan", 
		platformName = "理财范隶", 
		tags = { "P2P", "理财" , "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class LiCaiFanSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://wapi.licaifan.com/wapi/user/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("username", account)
	                .add("password", "moasd12b45ile")
	                .add("from", "wap")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", ANDROID_USER_AGENT)
					.addHeader("Host", "wapi.licaifan.com")
					.addHeader("Referer", "https://m.licaifan.com/login")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("108")) {
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
