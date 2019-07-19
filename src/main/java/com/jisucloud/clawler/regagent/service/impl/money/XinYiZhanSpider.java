package com.jisucloud.clawler.regagent.service.impl.money;

import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

@Slf4j
@UsePapaSpider
public class XinYiZhanSpider extends PapaSpider {
	
	private Session session = JJsoup.newSession();
	
	@Override
	public String message() {
		return "新一站保险网是新一站保险代理有限公司所有并运营的在线保险服务网站，是焦点科技旗下继中国制造网后的又一大型电子商务平台。 ";
	}

	@Override
	public String platform() {
		return "xyzbx";
	}

	@Override
	public String home() {
		return "xyz.cn";
	}

	@Override
	public String platformName() {
		return "新一站保险网";
	}

	@Override
	public String[] tags() {
		return new String[] {"保险" , "理财"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("15985268900", "18210538513");
	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "www.xyz.cn");
		headers.put("Referer", "https://www.xyz.cn/u/register.do?xcase=findPasswordEmail");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	private String getImgCode() {
		Connection.Response response;
		String vcode = null;
		for (int i = 0 ; i < 9; i++) {
			try {
				String imageUrl = "https://www.xyz.cn/u/ImageServlet?t="+System.currentTimeMillis();
				response = session.connect(imageUrl).headers(getHeader()).execute();
				byte[] body = response.bodyAsBytes();
				vcode = OCRDecode.decodeImageCode(body);
				response = session.connect("https://www.xyz.cn/u/register.do?xcase=checkCode&_ssl=close&t=" + System.currentTimeMillis())
						.method(Method.POST)
						.headers(getHeader())
						.data("verifyCode", vcode)
						.execute();
				if (response.body().contains("true")) {
					return vcode;
				}else {
					continue;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			String url = "https://www.xyz.cn/u/register.do?xcase=findPasswordEmail";
			String code = getImgCode();
			Connection.Response response = session.connect(url)
					.method(Method.POST)
					.data("findPasswordMethod", "mobile")
					.data("mobileRadio", "off")
					.data("mobile", account)
					.data("verifyCode", code)
					.headers(getHeader()).ignoreContentType(true).execute();
			String res = response.body();
			Document doc = Jsoup.parse(res);
			if (doc.text().contains("手机验证码已成功发送至您的手机")) {
				return true;
			}else {
				return false;
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
