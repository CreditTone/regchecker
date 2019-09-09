package com.jisucloud.clawler.regagent.service.impl.video;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.Request;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;



@Slf4j
@PapaSpiderConfig(
		home = "mgtv.com", 
		message = "芒果TV是湖南广播电视台旗下唯一互联网视频平台，独家提供湖南卫视所有栏目高清视频直播点播，并为用户提供各类热门电影、电视剧、综艺、动漫、音乐、娱乐等内容。", 
		platform = "mgtv", 
		platformName = "芒果TV", 
		tags = { "影音", "视频", "TV" }, 
		testTelephones = { "18720982607", "18210538513" })
public class MangGuoTVSpider extends PapaSpider {
	
	private Headers getHeader() {
		Map<String, String> headers = new HashMap<>();
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0");
		headers.put("Host", "i.mgtv.com");
		headers.put("Referer", "https://www.mgtv.com/");
		headers.put("X-Requested-With", "XMLHttpRequest");
		return Headers.of(headers);
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				String imageUrl = "https://i.mgtv.com/vcode?from=pcclient&time="+System.currentTimeMillis();
				Request request = new Request.Builder().url(imageUrl)
						.headers(getHeader())
						.build();
				byte[] body = okHttpClient.newCall(request).execute().body().bytes();
				return OCRDecode.decodeImageCode(body);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	
	public boolean checkTelephone(String account) {
		try {
			okHttpClient.newCall(createRequest("https://www.mgtv.com/")).execute().body().close();
		} catch (IOException e1) {
		}
		for (int i = 0 ; i < 6; i++) {
			try {
				String url = "https://i.mgtv.com/account/loginVerify";
				FormBody formBody = new FormBody
		                .Builder()
						.add("account", account)
						.add("sub", "1")
						.add("vcode", getImgCode())
						.add("pwd", "13734bd8b64952f8732b11653fbb5a5e8e777a19306593078f3d071df2cd5fe9b7beaf51e02372e37ad87a5caf63d009d587dfc32fafcdada52d067153317c6453e937aafeba7b0b20d0c3eb2a73f92d04a838507dcb688fa9bd779ae6564d6187a33de2518aaef9903e014a104447f0bd712f6ce35fabe8919969467aeb7ab7")
						.add("remember", "1")
		                .build();
				Request request = new Request.Builder().url(url)
						.headers(getHeader())
						.post(formBody)
						.build();
				String res = okHttpClient.newCall(request).execute().body().string();
				if (res.contains("code\":-125")) {//验证码错误
					continue;
				}
				if (res.contains("\\u5e10\\u53f7\\u6216\\u5bc6\\u7801\\u8f93")) {
					return true;
				}else {
					return false;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
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
