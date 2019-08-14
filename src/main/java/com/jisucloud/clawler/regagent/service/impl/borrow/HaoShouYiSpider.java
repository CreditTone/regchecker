package com.jisucloud.clawler.regagent.service.impl.borrow;

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
		home = "haoshouyi.com", 
		message = "好收益-天涯金服旗下-专注于消费金融的网络借贷信息中介平台。好收益为出借用户提供三重安全保障:足值抵押+推荐方回购+借款人风险保证金。", 
		platform = "haoshouyi", 
		platformName = "好收益", 
		tags = { "贷超", "借贷" }, 
		testTelephones = { "15985268900", "18210538513" })
public class HaoShouYiSpider extends PapaSpider {

	
	
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.haoshouyi.com/index/member/register";
			String content = "MemberModel%5Busername%5D=&MemberModel%5Bpassword%5D=&MemberModel%5BpasswordConfirm%5D=&MemberModel%5Binvitecode%5D=&MemberModel%5BbonusCode%5D=&MemberModel%5BverifyCode%5D=&MemberModel%5Bmobile%5D="+account+"&MemberModel%5BsmsCode%5D=&MemberModel%5BagreeTerms%5D=1&ajax=member-model-form";
			RequestBody formBody = FormBody.create(MediaType.parse("application/x-www-form-urlencoded; charset=UTF-8"), content);
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "www.haoshouyi.com")
					.addHeader("Referer", "https://www.haoshouyi.com/index/member/register")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("已被取用")) {
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
