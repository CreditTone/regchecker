package com.jisucloud.clawler.regagent.service.impl.life;

import com.jisucloud.clawler.regagent.service.PapaSpider;
import com.jisucloud.deepsearch.selenium.mitm.AjaxHook;
import com.jisucloud.deepsearch.selenium.mitm.ChromeAjaxHookDriver;
import com.jisucloud.deepsearch.selenium.mitm.HookTracker;

import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import net.lightbody.bmp.util.HttpMessageContents;
import net.lightbody.bmp.util.HttpMessageInfo;

import org.springframework.stereotype.Component;

import java.util.Map;

@Slf4j
@Component
public class ZhiHuSpider implements PapaSpider {

	private ChromeAjaxHookDriver chromeDriver;
	private boolean checkTel = false;
	private boolean vcodeSuc = false;//验证码是否正确

	@Override
	public String message() {
		return "有问题,上知乎。知乎,可信赖的问答社区,以让每个人高效获得可信赖的解答为使命。知乎凭借认真、专业和友善的社区氛围,结构化、易获得的优质内容,基于问答的内容生产。";
	}

	@Override
	public String platform() {
		return "zhihu";
	}

	@Override
	public String home() {
		return "zhihu.com";
	}

	@Override
	public String platformName() {
		return "知乎";
	}

	@Override
	public String[] tags() {
		return new String[] {"社区", "知识"};
	}

	public static void main(String[] args) throws InterruptedException {
//		System.out.println(new ZhiHuSpider().checkTelephone("13910252000"));
		System.out.println(new ZhiHuSpider().checkTelephone("18210538513"));
	}
	
	@Override
	public boolean checkTelephone(String account) {
		HookTracker hookTracker = HookTracker.builder()
				.addUrl("api/v3/oauth/sign_in")
				.isPOST().build();
		try {
			chromeDriver = ChromeAjaxHookDriver.newChromeInstance(false, false);
			chromeDriver.get("http://www.baidu.com/link?url=3cwo8FrfoIxRrxbMaEh8jzPwVHjjKRHHP_siJJBT-cS&wd=&eqid=851fddb5000085d3000000025d121f68");
			chromeDriver.get("https://www.zhihu.com/signin?next=%2F");
			chromeDriver.addAjaxHook(new AjaxHook() {
				
				@Override
				public void filterResponse(HttpResponse response, HttpMessageContents contents, HttpMessageInfo messageInfo) {
					vcodeSuc = true;
					checkTel = contents.getTextContents().equals("密码错误");
				}
				
				@Override
				public HttpResponse filterRequest(HttpRequest request, HttpMessageContents contents, HttpMessageInfo messageInfo) {
					return null;
				}

				@Override
				public HookTracker getHookTracker() {
					return hookTracker;
				}
			});
			chromeDriver.keyboardInput(chromeDriver.findElementByCssSelector(".SignContainer-inner input[name='username']"), account);
			chromeDriver.keyboardInput(chromeDriver.findElementByCssSelector(".SignContainer-inner input[name='password']"), "x210nsadc2nk");
			//判断是否弹出验证码
			chromeDriver.mouseClick(chromeDriver.findElementByCssSelector("button[type='submit']"));
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
