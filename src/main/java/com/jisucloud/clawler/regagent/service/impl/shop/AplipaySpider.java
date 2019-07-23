package com.jisucloud.clawler.regagent.service.impl.shop;

import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.i.PapaSpider;
import com.jisucloud.clawler.regagent.i.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;

import org.openqa.selenium.WebElement;

@Slf4j
@UsePapaSpider
public class AplipaySpider extends PapaSpider {
	
	private ChromeAjaxHookDriver chromeDriver;
	
	private boolean checkTelephone = false;
	
	//暂时不能访问此页面，被反扒
	public boolean noSpider = false;

	@Override
	public String message() {
		return "支付宝,全球领先的独立第三方支付平台,致力于为广大用户提供安全快速的电子支付/网上支付/安全支付/手机支付体验,及转账收款/水电煤缴费/信用卡还款/AA收款等生活。";
	}

	@Override
	public String platform() {
		return "alipay";
	}

	@Override
	public String home() {
		return "alipay.com";
	}

	@Override
	public String platformName() {
		return "支付宝";
	}

	@Override
	public String[] tags() {
		return new String[] {"购物" , "理财" , "借贷" , "消费分期" , "保险"};
	}
	
//	@Override
//	public Set<String> getTestTelephones() {
//		return Sets.newHashSet("18800000001", "18210008013", "18210538513");
//	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18210008013", "18800000001");
	}
	
	
	public static void main(String[] args) {
		PapaSpiderTester.testingWithPrint(AplipaySpider.class);
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newNoHookInstance(false, false, CHROME_USER_AGENT);
			chromeDriver.get("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=monline_4_dg&wd=%E6%94%AF%E4%BB%98%E5%AE%9D&rn=5&oq=deep007&rsv_pq=e03caf7600120d11&rsv_t=3990P6z%2BSsvdOqD%2BiMIYugpgcAXsD45qWWKmBY%2FAzfLwOTQ52WTPgrSdZnhUXjG%2BC9Nz&rqlang=cn&rsv_enter=1&inputT=7235&rsv_sug3=56&rsv_sug1=27&rsv_sug7=100&rsv_sug2=0&rsv_sug4=8434");smartSleep(3000);
			for (int i = 0; i < 3; i++) {
				chromeDriver.get("https://accounts.alipay.com/console/querypwd/logonIdInputReset.htm?site=1&page_type=fullpage&scene_code=resetQueryPwd");smartSleep(RANDOM.nextInt(5000));
				if (!chromeDriver.checkElement("#J_rdsSlideResetBtn")) {
					continue;
				}
				WebElement accName = chromeDriver.findElementById("J-accName");
				chromeDriver.keyboardInput(accName, account);
				WebElement rdsSlideReset = chromeDriver.findElementById("J_rdsSlideResetBtn");
				WebElement rdsSlideBtn = chromeDriver.findElementById("J_rdsSlideBtn");
				chromeDriver.switchSlide(rdsSlideReset, rdsSlideBtn);smartSleep(RANDOM.nextInt(3000));
				WebElement next = chromeDriver.findElementByCssSelector("input[class=ui-button-text]");
				chromeDriver.mouseClick(next);smartSleep(RANDOM.nextInt(8000) + 5000);
				if (chromeDriver.checkElement("div.alipay-xbox")) {
					return true;
				}
				String currentUrl = chromeDriver.getCurrentUrl();
				String pageSource = chromeDriver.getPageSource();
				if (currentUrl.contains("queryStrategy.htm?")) {
					return true;
				}else if (pageSource.contains("该账户不存在，请重新输入")) {
					return false;
				}else if (pageSource.contains("暂时不能访问此页面")) {
					noSpider = true;
					return false;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			if (chromeDriver != null) {
				chromeDriver.quit();
			}
		}
		return checkTelephone;
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
