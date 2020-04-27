package com.jisucloud.clawler.regagent.service.impl.work;


import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "51job.com", 
		message = "前程无忧(NASDAQ:JOBS)是中国具有广泛影响力的人力资源服务供应商,在美国上市的中国人力资源服务企业,创立了网站+猎头+RPO+校园招聘+管理软件的全方位招聘方案.目前51Job有效简历数量超过1.2亿。", 
		platform = "51job", 
		platformName = "前程无忧", 
		tags = { "求职" , "招聘" }, 
		testTelephones = { "18212345678", "15011008001" })
public class QianChengWuYouSpider extends PapaSpider {


	public boolean checkTelephone(String account) {
		try {
			String url = "https://login.51job.com/ajax/checkinfo.php?jsoncallback=jQuery18309636398222161634_"+System.currentTimeMillis()+"&value="+account+"&nation=CN&type=mobile&_=" + System.currentTimeMillis();
			Request request = new Request.Builder().url(url)
					.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0")
					.addHeader("Host", "login.51job.com")
					.addHeader("Referer", "https://login.51job.com/register.php?lang=c&from_domain=i&source=&isjump=0&url=")
					.build();
			Response response = okHttpClient.newCall(request).execute();
			if (response.body().string().contains("result\":1")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
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
