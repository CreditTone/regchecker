package com.jisucloud.clawler.regagent.service.impl.shop;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class GuoMeiSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	
	@Override
	public String message() {
		return "国美电器（GOME）成立于1987年1月1日，总部位于香港，是中国大陆家电零售连锁企业。2009年入选中国世界纪录协会中国最大的家电零售连锁企业。";
	}

	@Override
	public String platform() {
		return "gome";
	}

	@Override
	public String home() {
		return "gome.com";
	}

	@Override
	public String platformName() {
		return "国美电器";
	}

	@Override
	public Map<String, String[]> tags() {
		return new HashMap<String, String[]>() {
			{
				put("金融", new String[] { "储蓄"});
			}
		};
	}

//	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new GuoMeiSpider().checkTelephone("18210538000"));
//		System.out.println(new GuoMeiSpider().checkTelephone("18210538513"));
//	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = HeadlessUtil.getChromeDriver(false, null, null);
			String url = "https://reg.gome.com.cn/register/index/person?intcmp=reg-public01003";
			chromeDriver.setAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "register/validateExist/refuse.do";
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					checkTel = ajax.getResponse().endsWith("k3NDpzaG93VmFsaWRhdGVDb2Rl") || ajax.getResponse().startsWith("true");
				}
			});
			chromeDriver.get(url);
			Thread.sleep(3000);
			chromeDriver.findElementByLinkText("同意协议").click();
			Thread.sleep(3000);
			chromeDriver.findElementById("mobile").sendKeys(account);
			chromeDriver.findElementById("verifyCode").click();
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
