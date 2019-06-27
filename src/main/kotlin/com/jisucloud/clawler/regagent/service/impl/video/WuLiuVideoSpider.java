package com.jisucloud.clawler.regagent.service.impl.video;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;

@Slf4j
@UsePapaSpider
public class WuLiuVideoSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;

	@Override
	public String message() {
		return "56网是中国原创视频网站,免费上传搞笑逗趣生活视频,观看优质丰富的特色节目,关注感兴趣的原创导演和美女解说,快速分享及评论互动。";
	}

	@Override
	public String platform() {
		return "56";
	}

	@Override
	public String home() {
		return "56.com";
	}

	@Override
	public String platformName() {
		return "56视频";
	}

	@Override
	public String[] tags() {
		return new String[] {"影音", "视频", "MV"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new WuLiuVideoSpider().checkTelephone("18515290717"));
//		System.out.println(new WuLiuVideoSpider().checkTelephone("18210530000"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			String url = "https://v4.passport.sohu.com/i/verify/mobile/bind?mobile="+account+"&log=1&appid=114105&callback=passport403_cb"+System.currentTimeMillis()+"&_=" + System.currentTimeMillis();
			chromeDriver.quicklyVisit("http://www.56.com/");
			chromeDriver.quicklyVisit(url);
			String res = chromeDriver.getPageSource();
			System.out.println(res);
			if (res.contains("already has bind")) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
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
