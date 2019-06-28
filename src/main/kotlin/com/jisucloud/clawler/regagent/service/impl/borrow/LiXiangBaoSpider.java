package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
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
public class LiXiangBaoSpider implements PapaSpider {

	private ChromeAjaxListenDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "理想宝,银行资金存管,多家上市公司联合刘晓庆明星企业共同打造,合规运营3年,为145万投资人赚取收益近3亿元。手机投资,100元起投,多种产品可选,投资期限灵活。";
	}

	@Override
	public String platform() {
		return "id68";
	}

	@Override
	public String home() {
		return "id68.com";
	}

	@Override
	public String platformName() {
		return "理想宝";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
	}

	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13900002045", "18210538513");
	}
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imVcode");
				img.click();
				Thread.sleep(1000);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "n4");
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
			chromeDriver.quicklyVisit("https://www.id68.cn/member/common/login");
			chromeDriver.addAjaxListener(new AjaxListener() {
				
				@Override
				public String matcherUrl() {
					return "member/common/actlogin";
				}
				
				@Override
				public String[] blockUrl() {
					return null;
				}
				
				@Override
				public void ajax(Ajax ajax) throws Exception {
					if (!ajax.getResponse().contains("验码错误")) {
						vcodeSuc = true;
						if (ajax.getResponse().contains("手机号码")) {
							checkTel = false;
						}else {
							checkTel = ajax.getResponse().contains("密码错误") || ajax.getResponse().contains("密码已连续错误") || ajax.getResponse().contains("锁定");
						}
					}
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
			chromeDriver.findElementById("txtUser").sendKeys(account);
			chromeDriver.findElementById("txtPwd").sendKeys("lvnqwnk12mcxn");
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("txtCode");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.reInject();
				chromeDriver.findElementById("btnReg").click();
				Thread.sleep(3000);
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
