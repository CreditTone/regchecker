package com.jisucloud.clawler.regagent.service.impl.shop;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Random;

import org.openqa.selenium.WebElement;

@Slf4j
@UsePapaSpider
public class AplipaySpider implements PapaSpider {
	
	private ChromeAjaxListenDriver chromeDriver;
	
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

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new AplipaySpider().checkTelephone("13879690000"));
//		System.out.println(new AplipaySpider().checkTelephone("18210538513"));
//	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("img[title=点击图片刷新验证码]");
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			chromeDriver.quicklyVisit("https://www.baidu.com/s?ie=utf-8&f=8&rsv_bp=1&tn=monline_4_dg&wd=%E6%94%AF%E4%BB%98%E5%AE%9D&rn=5&oq=deep007&rsv_pq=e03caf7600120d11&rsv_t=3990P6z%2BSsvdOqD%2BiMIYugpgcAXsD45qWWKmBY%2FAzfLwOTQ52WTPgrSdZnhUXjG%2BC9Nz&rqlang=cn&rsv_enter=1&inputT=7235&rsv_sug3=56&rsv_sug1=27&rsv_sug7=100&rsv_sug2=0&rsv_sug4=8434");
			Thread.sleep(3000);
			for (int i = 0; i < 1; i++) {
				chromeDriver.get("https://auth.alipay.com/login/index.htm?needTransfer=true&goto=http://memberprod.alipay.com/account/reg/index.htm");
				Thread.sleep(5000);
				chromeDriver.get("https://accounts.alipay.com/console/querypwd/logonIdInputReset.htm?site=1&page_type=fullpage&scene_code=resetQueryPwd");
				Thread.sleep(5000);
				WebElement forgetAccount = chromeDriver.findElementByCssSelector("img[title=点击图片刷新验证码]");
				WebElement checkcode = chromeDriver.findElementById("J-checkcode");
				WebElement accName = chromeDriver.findElementById("J-accName");
				WebElement next = chromeDriver.findElementByCssSelector("input[class=ui-button-text]");
				chromeDriver.keyboardInput(accName, account);
				chromeDriver.keyboardInput(checkcode, new Random().nextInt(9) +"kx" +new Random().nextInt(5));
				chromeDriver.mouseClick(next);
				forgetAccount = chromeDriver.findElementByCssSelector("img[title=点击图片刷新验证码]");
				checkcode = chromeDriver.findElementById("J-checkcode");
				accName = chromeDriver.findElementById("J-accName");
				next = chromeDriver.findElementByCssSelector("input[class=ui-button-text]");
				int tempNum = new Random().nextInt(2);
				chromeDriver.keyboardClear(checkcode, 4);
				for (int k = 0; k < tempNum; k ++) {
					chromeDriver.mouseClick(forgetAccount);
				}
				String imageCode = getImgCode();
				chromeDriver.keyboardInput(checkcode, imageCode.toLowerCase());
				Thread.sleep(3000);
				chromeDriver.mouseClick(next);
				Thread.sleep(8000);
				String currentUrl = chromeDriver.getCurrentUrl();
				String pageSource = chromeDriver.getPageSource();
				if (currentUrl.contains("queryStrategy.htm?")) {
					return true;
				}else if (pageSource.contains("请输入正确的验证码")){
					continue;
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
