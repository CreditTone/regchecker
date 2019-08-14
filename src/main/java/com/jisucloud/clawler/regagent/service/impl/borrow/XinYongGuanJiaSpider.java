package com.jisucloud.clawler.regagent.service.impl.borrow;

import java.util.Map;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import okhttp3.Request;

@PapaSpiderConfig(
		home = "51nbapi.com", 
		message = "信用管家是借钱贷款APP，凭身份证、信用分或信用卡等借钱贷款。借钱贷款，就找信用管家借钱！", 
		platform = "51nbapi", 
		platformName = "信用管家", 
		tags = {  "P2P", "借贷"  }, 
		testTelephones = { "13910252000", "18210538513" })
public class XinYongGuanJiaSpider extends PapaSpider {

	public boolean checkTelephone(String account) {
		 try {
			 Request request = new Request.Builder().url("https://api.51nbapi.com/mapi/cspuser/phone_user/login.json")
						.post(createUrlEncodedForm("deviceId=865166021433753&password=356151536&appVersion=4.5.8&location=%E5%8C%97%E4%BA%AC%E5%B8%82&phoneType=oppo%20r9%20plusm%20a&phoneVersion=5.1.1&appChannel=yingyb&phone="+account+"&phoneSystem=A&appName=zhengxindaikuan"))
						.build();
	            String body = okHttpClient.newCall(request).execute().body().string();
	            //{"result":{"message":"对不起，当前手机号还未注册！","appName":"mapi","status":"error","code":"E2017K000000013","success":"false"}}
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
