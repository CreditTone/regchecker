package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.OCRDecode;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import org.openqa.selenium.WebElement;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class YinDuoWangSpider extends PapaSpider implements AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "银多网是一个立足普惠金融、合规运营的互联网金融信息中介平台，时刻践行“服务高于金融”的理念，通过互联网技术融合传统金融的创新模式，带领有理财需求的用户。";
	}

	@Override
	public String platform() {
		return "yinduowang";
	}

	@Override
	public String home() {
		return "yinduowang.com";
	}

	@Override
	public String platformName() {
		return "银多网";
	}

	@Override
	public String[] tags() {
		return new String[] {"P2P", "借贷"};
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
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.get("https://www.baidu.com/link?url=bZ3z2HWzTi0fDvJ7ddVPW9aBsLVKOgsqcs_TEBYcVsVtk_ztXwNsjsVAbqYEf4tb&wd=&eqid=cdc510d90004fb09000000025d088c68");
			chromeDriver.get("https://www.yinduowang.com/login");
			chromeDriver.addAjaxHook(this);
			smartSleep(2000);
			chromeDriver.findElementById("mobile").sendKeys(account);
			chromeDriver.findElementById("pwd").sendKeys("lvnqwnk12mcxn");
			for (int i = 0; i < 5; i++) {
				chromeDriver.findElementByCssSelector("div[class='login-btn userLogin login-btnactive']").click();
				smartSleep(3000);
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
		return HookTracker.builder().addUrl("https://www.yinduowang.com/public/login").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		vcodeSuc = true;
		checkTel = contents.getTextContents().contains("密码错误") || contents.getTextContents().contains("锁定");
	}

}
