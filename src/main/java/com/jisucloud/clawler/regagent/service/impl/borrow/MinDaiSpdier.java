package com.jisucloud.clawler.regagent.service.impl.borrow;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.PapaSpiderConfig;

import java.util.Map;
import java.util.Set;

@Slf4j
@PapaSpiderConfig(
		home = "bank.mindai.com", 
		message = "民贷天下，是一家专注为出借人和借款人提供网络借贷信息中介服务的互联网金融服务平台，通过借贷信息撮合服务为借款人快速、专业地解决资金需求，为出借人提供高效的资金回报。", 
		platform = "mindai", 
		platformName = "民贷天下", 
		tags = { "理财", "P2P" , "借贷" }, 
		testTelephones = { "18369630455", "18210538513" })
public class MinDaiSpdier extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
    private boolean checkTel = false;
    private boolean suc = false;
    
    @Override
    public boolean checkTelephone(String account) {
        try {
            chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
            String url = "https://bank.mindai.com/password-find.html";
            chromeDriver.addAjaxHook(this);
            chromeDriver.get(url);smartSleep(2000);
            chromeDriver.findElementByCssSelector("#userName").sendKeys(account);
            chromeDriver.findElementByCssSelector("#imgCode").click();smartSleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
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
		return HookTracker.builder().addUrl("https://api.mindai.com/").isPost().requestContentType("application/x-www-form-urlencoded").responseContentType("application/json").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		if (checkTel) {
			return;
		}
		try {
			JSONObject result = JSON.parseObject(contents.getTextContents());
			String isRegistered = result.getJSONObject("data").getString("isRegistered");
			if (isRegistered != null) {
				suc = true;
				checkTel = isRegistered.equals("1");
			}
		} catch (Exception e) {
		}
	}

}
