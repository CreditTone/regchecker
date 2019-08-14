package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;
import com.jisucloud.clawler.regagent.util.OCRDecode;
import com.jisucloud.clawler.regagent.util.StringUtil;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import org.openqa.selenium.WebElement;

import java.util.Map;

@Slf4j
@PapaSpiderConfig(
		home = "yesvion.com", 
		message = "粤商贷是隶属于深圳前海斯坦德互联网金融服务有限公司的网贷信息中介平台,坚持合规经营,风控严谨,稳健运营,累计撮合交易金额37亿元,为客户带来1.35亿元收益。", 
		platform = "yesvion", 
		platformName = "yesvionName", 
		tags = { "P2P", "借贷" }, 
		testTelephones = { "15900068904", "18210538513" },
		exclude = true , excludeMsg = "行为反扒")
public class YueShangDaiSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确
	
	private String getImgCode() {
		for (int i = 0 ; i < 3; i++) {
			try {
				WebElement img = chromeDriver.findElementByCssSelector("#imVcode");
				chromeDriver.mouseClick(img);
				byte[] body = chromeDriver.screenshot(img);
				return OCRDecode.decodeImageCode(body, "n4");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return "";
	}
	String code;

	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.get("https://www.yesvion.com/member/common/login/");
			chromeDriver.addAjaxHook(this);
			smartSleep(2000);
			chromeDriver.findElementById("txtUser").sendKeys(account);
			chromeDriver.findElementById("txtPwd").sendKeys("xas2139aa0");
			for (int i = 0; i < 5; i++) {
				code = getImgCode();
				WebElement validate = chromeDriver.findElementById("txtCode");
				validate.clear();
				validate.sendKeys(code);
				chromeDriver.mouseClick(chromeDriver.findElementById("btnLogin"));
				smartSleep(2000);
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
		return HookTracker.builder().addUrl("member/common/actlogin").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		String res = StringUtil.unicodeToString(contents.getTextContents());
		if (!res.contains("验证码")) {
			vcodeSuc = true;
		}
		checkTel = res.contains("次登录") || res.contains("锁定");
	}

}
