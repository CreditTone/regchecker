package com.jisucloud.clawler.regagent.service.impl.health;

import java.util.Map;


import okhttp3.Request;

import org.jsoup.Connection;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.StringUtil;



@PapaSpiderConfig(
		home = "ranknowcn.com", 
		message = "男性私人医生是一款针对男生生理健康及相关专科类病症，提供免费在线咨询的健康类服务软件。集合上千家医院的在职医生和资源，自动分配所在地就近医院医生为您提供健康指导，免去挂号难，排队久的困扰，提问流程简单便捷，让您随时随地享受VIP健康咨询服务。(建议允许定位以便能准确分配到所在地医院)。", 
		platform = "ranknowcn", 
		platformName = "男性私人医生", 
		tags = {"医疗", "男科"  }, 
		testTelephones = { "13261165342", "18210538513" , "18210538510" })
public class NanXingSiRenYiShengSpider extends PapaSpider {


	@Override
	public boolean checkTelephone(String account) {
		 try {
			 Request request = new Request.Builder().url("http://new.medapp.ranknowcn.com/api/m.php?action=login&version=3.0")
						.post(createUrlEncodedForm("password=oooooi&source=tencent&token=5cd0f47bdc43e&appid=3&switchType=0&deviceid=5cd0f47bdc43e&os=android&vocde=&age=unknown&imei=460383127194006&username="+account+"&version=3.19.0428.1&phonemodel=Nexus+5&mobileTel=&"))
						.build();
	            String body = okHttpClient.newCall(request).execute().body().string();
	            body = StringUtil.unicodeToString(body);
	            return body.contains("密码错误");
	        } catch (Exception e) {
	            e.printStackTrace();
	            return false;
	        }
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
