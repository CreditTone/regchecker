package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

//@UsePapaSpider
public class LiXiangBaoSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
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
				img.click();smartSleep(1000);
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
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.get("https://www.id68.cn/member/common/login");
			chromeDriver.addAjaxHook(this);smartSleep(3000);
			chromeDriver.findElementById("txtUser").sendKeys(account);
			chromeDriver.findElementById("txtPwd").sendKeys("lvnqwnk12mcxn");
			for (int i = 0; i < 5; i++) {
				WebElement validate = chromeDriver.findElementById("txtCode");
				validate.clear();
				validate.sendKeys(getImgCode());
				chromeDriver.findElementById("btnReg").click();smartSleep(3000);
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

	@Override
	public HookTracker getHookTracker() {
		// TODO Auto-generated method stub
		return HookTracker.builder().addUrl("member/common/actlogin").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		if (!contents.getTextContents().contains("验码错误")) {
			vcodeSuc = true;
			checkTel = contents.getTextContents().contains("密码已连续错误") || contents.getTextContents().contains("锁定");
		}
	}

}
