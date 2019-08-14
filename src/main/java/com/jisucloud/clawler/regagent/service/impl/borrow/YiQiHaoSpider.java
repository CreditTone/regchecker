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
		home = "yiqihao.com", 
		message = "一起好金服是华中地区互联网金融平台,致力于为高成长人群提供专业的线上借贷撮合服务;以小额分散为原则、人工智能结合大数据,践行“普惠金融”,勇于创新“科技金融。", 
		platform = "yiqihao", 
		platformName = "一起好金服", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15985268904", "18210538513" })
public class YiQiHaoSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.yiqihao.com/user/login";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("account", account)
	                .add("password", "mobisa1221ale")
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.yiqihao.com")
					.addHeader("Referer", "https://www.yiqihao.com/user/login?redirect=/")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = response.body().string();
			if (res.contains("次机会") || res.contains("已锁定") ) {
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
