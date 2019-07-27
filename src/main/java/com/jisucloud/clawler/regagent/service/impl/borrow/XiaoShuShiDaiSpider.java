package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.deepsearch.selenium.Ajax;
import com.jisucloud.deepsearch.selenium.AjaxListener;
import com.jisucloud.deepsearch.selenium.ChromeAjaxListenDriver;
import com.jisucloud.deepsearch.selenium.HeadlessUtil;

import lombok.extern.slf4j.Slf4j;

import com.google.common.collect.Sets;
import org.openqa.selenium.WebElement;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class XiaoShuShiDaiSpider extends PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "小树时代竭诚为您提供年轻人贷款、创业贷款 、小额贷款、网上贷款等服务,另外小树时代还提供贷款申请、条件、流程、政策等资讯。";
	}

	@Override
	public String platform() {
		return "xiaoshushidai";
	}

	@Override
	public String home() {
		return "xiaoshushidai.com";
	}

	@Override
	public String platformName() {
		return "小树时代";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷" , "消费贷" , "小微金融"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910252045", "18210538513");
	}

	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#valicodeImg");
				img.click();smartSleep(1000);
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
			chromeDriver.quicklyVisit("https://www.xiaoshushidai.cn/user-get_password_invest");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "act=check_user";
				}
				
				@Override
				public String[] blockUrl() {
					return new String[] {
							"act=get_register_verify_code"
					};
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					vcodeSuc = true;
					JSONObject result = JSON.parseObject(ajax.getResponse());
					checkTel = result.getIntValue("status") == 1;
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
			chromeDriver.findElementById("password-mobile").sendKeys(account);
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("verify-code");
				validate.clear();
				validate.sendKeys("6315");
				chromeDriver.reInject();
				chromeDriver.findElementById("get-captcha-btn").click();smartSleep(3000);
				if (vcodeSuc) {
					break;
				}
			}
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
