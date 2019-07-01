package com.jisucloud.clawler.regagent.service.impl.borrow;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.clawler.regagent.service.UsePapaSpider;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Sets;

import java.util.Map;
import java.util.Set;

@Slf4j
@UsePapaSpider
public class MinDaiService implements PapaSpider,AjaxHook {

	private ChromeAjaxHookDriver chromeDriver;
    private boolean checkTel = false;
    private boolean suc = false;
    
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("18369630455", "18210538513");
	}

    @Override
    public String message() {
        return "民贷天下，是一家专注为出借人和借款人提供网络借贷信息中介服务的互联网金融服务平台，通过借贷信息撮合服务为借款人快速、专业地解决资金需求，为出借人提供高效的资金回报。";
    }

    @Override
    public String platform() {
        return "mindai";
    }

    @Override
    public String home() {
        return "bank.mindai.com";
    }

    @Override
    public String platformName() {
        return "民贷天下";
    }

    @Override
    public String[] tags() {
        return new String[]{"理财", "P2P" , "借贷"};
    }

    @Override
    public boolean checkTelephone(String account) {
        try {
            chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
            String url = "https://bank.mindai.com/password-find.html";
            chromeDriver.addAjaxHook(this);
            chromeDriver.get(url);
            Thread.sleep(2000);
            chromeDriver.findElementByCssSelector("#userName").sendKeys(account);
            chromeDriver.findElementByCssSelector("#imgCode").click();
            Thread.sleep(3000);
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
		return HookTracker.builder().addUrl("https://api.mindai.com/").isPOST().requestContentType("application/x-www-form-urlencoded").responseContentType("application/json").build();
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
