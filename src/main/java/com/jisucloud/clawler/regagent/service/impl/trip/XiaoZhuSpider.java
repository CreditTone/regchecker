package com.jisucloud.clawler.regagent.service.impl.trip;

import com.deep077.spiderbase.selenium.mitm.AjaxHook;
import com.deep077.spiderbase.selenium.mitm.ChromeAjaxHookDriver;
import com.deep077.spiderbase.selenium.mitm.HookTracker;
import com.google.common.collect.Sets;
import com.jisucloud.clawler.regagent.interfaces.PapaSpider;
import com.jisucloud.clawler.regagent.interfaces.UsePapaSpider;
import com.jisucloud.clawler.regagent.util.StringUtil;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

@Slf4j
@UsePapaSpider
public class XiaoZhuSpider extends PapaSpider implements AjaxHook{

	private ChromeAjaxHookDriver chromeDriver;
	boolean checkTel = false;

	@Override
	public String message() {
		return "小猪(xiaozhu.com)是国内知名的特色民宿和短租房预订平台，更是一个充满爱与人情味的社交住宿社区。截至2019年5月，小猪共有超过80万间房源，分布在全球超过700座城市，包括民宿、客栈、城市公寓、轰趴别墅等类型，让旅途中的你充分感受住宿之美、人情之暖、分享之乐。";
	}

	@Override
	public String platform() {
		return "xiaozhu";
	}

	@Override
	public String home() {
		return "xiaozhu.com";
	}

	@Override
	public String platformName() {
		return "小猪";
	}

	@Override
	public String[] tags() {
		return new String[] {"旅游" , "民宿" , "o2o"};
	}
	
	@Override
	public Set<String> getTestTelephones() {
		return Sets.newHashSet("13910200045", "18210538513");
	}

	@Override
	public boolean checkTelephone(String account) {
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(true, true);
			chromeDriver.addAjaxHook(this);
			chromeDriver.get("http://www.xiaozhu.com/register?next=http%3A%2F%2Fwww.xiaozhu.com%2F%3Futm_source%3Dbaidu%26utm_medium%3Dcpc%26utm_content%3Dpinzhuan%26utm_term%3D%25E5%25B0%258F%25E7%258C%25AA%26utm_campaign%3DBDPZ");smartSleep(3000);
			chromeDriver.findElementById("input-mobile").sendKeys(account);
			chromeDriver.findElementById("invitecode").click();smartSleep(3000);
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
		return HookTracker.builder().addUrl("AJAX_CheckMobileExist").build();
	}

	@Override
	public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
		// TODO Auto-generated method stub
		checkTel = StringUtil.unicodeToString(contents.getTextContents()).contains("已被注册");
	}

}
