package com.jisucloud.clawler.regagent.service.impl.money;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;

import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import java.util.Map;


@Slf4j
@PapaSpiderConfig(
		home = "tianaw.com", 
		message = "天安保险即天安财产保险股份有限公司是中国第四家财产险保险公司，也是第二家按照现代企业制度和国际标准组建的股份制商业保险公司。", 
		platform = "tianaw", 
		platformName = "天安保险", 
		tags = { "理财", "保险" }, 
		testTelephones = { "15901537458", "18212345678" },
		ignoreTestResult = true)
public class TianNanBaoXianSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean suc = false;
	
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			String url = "https://tianaw.95505.cn/tacpc/#/login/updatepass";
			chromeDriver.get(url);smartSleep(3000);
			chromeDriver.findElementById("'phoneNumber'").sendKeys(account);
			chromeDriver.findElementById("password").sendKeys("wxy"+account);
			chromeDriver.findElementById("checkPassword").sendKeys("wxy"+account);
			chromeDriver.findElementByCssSelector("button[nztype=primary]").click();
			smartSleep(2000);
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
		return HookTracker.builder().addUrl("/customer_login/setPassword").isPost().build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		checkTel = contents.getTextContents().contains("新密码设置成功");
		suc = true;
	}

}
