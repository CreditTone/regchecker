package com.jisucloud.clawler.regagent.service.impl.work;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class MaKeBoLuoSpider extends PapaSpider {

	private OkHttpClient okHttpClient = new OkHttpClient.Builder().connectTimeout(10, TimeUnit.SECONDS)
			.readTimeout(10, TimeUnit.SECONDS).retryOnConnectionFailure(true).build();

	@Override
	public String message() {
		return "马可波罗网(Makepolo.com),精确采购搜索引擎,是中小企业实现“精确采购搜索”和“精确广告投放”的B2B电子商务平台。马可波罗网满足中小企业用户低投入,高回报的发展。";
	}

	@Override
	public String platform() {
		return "makepolo";
	}

	@Override
	public String home() {
		return "makepolo.com";
	}

	@Override
	public String platformName() {
		return "马可波罗网";
	}

	@Override
	public String[] tags() {
		return new String[] {"b2b" ,"商机" ,"生意"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210538513", "15700102860");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "http://my.b2b.makepolo.com/ucenter/reg/register_checkCode_new.php";
			FormBody formBody = new FormBody
	                .Builder()
	                .add("type", "check_phone")
	                .add("mobile", account)
	                .build();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "my.b2b.makepolo.com")
					.addHeader("Referer", "http://my.b2b.makepolo.com/ucenter/reg/register_new.php")
					.post(formBody)
					.build();
			Response response = okHttpClient.newCall(request).execute();
			String res = StringUtil.unicodeToString(response.body().string());
			if (res.contains("手机已经验证")) {
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
