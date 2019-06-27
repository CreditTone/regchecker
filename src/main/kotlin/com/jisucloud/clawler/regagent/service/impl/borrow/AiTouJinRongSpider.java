package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import org.openqa.selenium.Point;
import org.openqa.selenium.interactions.Actions;
import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@UsePapaSpider
public class AiTouJinRongSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "爱投金融(5aitou.com)—成立于2011年的老牌互联网金融平台,上海财经大学金融学博士创立。银行资金存管,中国互联网金融协会首批会员,上海金融信息行业理事单位。";
	}

	@Override
	public String platform() {
		return "5aitou";
	}

	@Override
	public String home() {
		return "5aitou.com";
	}

	@Override
	public String platformName() {
		return "爱投金融";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new AiTouJinRongSpider().checkTelephone("13910252045"));
//		System.out.println(new AiTouJinRongSpider().checkTelephone("18210538513"));
//	}
	
	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(true, null, null);
			chromeDriver.quicklyVisit("https://www.5aitou.com/register.htm");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "checkUserPhone";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					System.out.println(ajax);
					vcodeSuc = true;
					checkTel = ajax.getResponse().contains("false");
				}

				@Override
				public String fixPostData() {
					// TODO Auto-generated method stub
					return null;
				}

				@Override
				public String fixGetData() {
					// TODO Auto-generated method stub
					return null;
				}
			});
			chromeDriver.findElementById("phone").sendKeys(account);
			Thread.sleep(1000);
			chromeDriver.findElementByCssSelector(".lf_selectbox li[role='4']").click();
			Thread.sleep(3000);
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTel;
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
