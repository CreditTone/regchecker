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
		home = "cashlai.com", 
		message = "山东高速金服是中国山东高速金融集团（00412.HK）成员企业，专注中国小微企业抵押类经营性融资服务，着力满足客户“急用钱”、“常用钱”以及围绕住房抵押产生的其他金融需求。公司在市场营销、风险管控、信贷管理、运营服务、科技支撑等方面积累了雄厚的实力。以“服务”之心，公司建设起创新的生态型金融科技聚合平台，逐步引领和变革传统金融业态，在解决小微企业融资难、支持实体经济发展、践行中国普惠金融方面不断贡献力量。", 
		platform = "cashlai", 
		platformName = "山东高速金服", 
		tags = { "p2p", "借贷" , "小微金融" }, 
		testTelephones = { "18210538513", "15120058878" })
public class ShanDongGaoSuJinFuSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.cashlai.com/newCheckUserPhone.do";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("userPhone", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", CHROME_USER_AGENT)
					.addHeader("Referer", "https://www.cashlai.com/cashlai_30/login_reg/register.jsp")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("1")) {
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
