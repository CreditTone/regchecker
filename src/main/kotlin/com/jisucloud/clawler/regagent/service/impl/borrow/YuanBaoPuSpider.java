package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.util.JJsoupUtil;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import me.kagura.JJsoup;
import me.kagura.Session;
import okhttp3.Request;

import org.jsoup.Connection;
import org.jsoup.Connection.Method;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class YuanBaoPuSpider implements PapaSpider {

	@Override
	public String message() {
		return "凭借成熟的大数据技术,元宝铺帮助全国5万多家小微企业从银行等金融机构处获得超过50亿元的授信额度,旗下有数据化信贷解决方案FIDE及以电商贷为首的多款数据信贷.";
	}

	@Override
	public String platform() {
		return "yuanbaopu";
	}

	@Override
	public String home() {
		return "yuanbaopu.com";
	}

	@Override
	public String platformName() {
		return "元宝铺";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("金融理财", new String[] { "借贷", "小微" });
			}
		};
	}

//	public static void main(String[] args) {
//		System.out.println(new YuanBaoPuSpider().checkTelephone("18210538513"));
//		System.out.println(new YuanBaoPuSpider().checkTelephone("18210538577"));
//	}

	private Map<String, String> getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "www.yuanbaopu.com");
		headers.put("Referer", "https://www.yuanbaopu.com/user/register.htm");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return headers;
	}
	
	private String getImgCode(Session session) {
		String img = "https://www.yuanbaopu.com/checkcode/service.htm?time="+System.currentTimeMillis();
		Connection.Response response;
		try {
			response = session.connect(img).execute();
			if (response != null) {
				byte[] body = response.bodyAsBytes();
				return OCRDecode.decodeImageCode(body);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			Session session = JJsoup.newSession();
			String url = "https://www.yuanbaopu.com/register/checkMobile.htm?fieldValue="+account+"&imgCode="+getImgCode(session);
			System.out.println(url);
			Connection.Response response = session.connect(url)
					.headers(getHeader()).ignoreContentType(true).execute();
			if (response != null) {
				return response.body().contains("已被注册");
			}
		} catch (Exception e) {
			if (e.getMessage().contains("Read timed out")) {
				return false;
			}
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
