package com.jisucloud.clawler.regagent.service;

import java.util.Map;
import java.util.Random;
import java.util.Set;

import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.Strand;

public abstract class PapaSpider {

	public static final Random RANDOM = new Random();

	public static final String ANDROID_USER_AGENT = "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36";

	public static final String IOS_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_1 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A402 Safari/604.1";

	public static final String CHROME_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0";

	public abstract String message();

	public abstract String platform();

	public abstract String home();

	public abstract String platformName();

	public abstract boolean checkTelephone(String account);

	public abstract boolean checkEmail(String account);

	public abstract Map<String, String> getFields();

	public abstract String[] tags();

	public abstract Set<String> getTestTelephones();

	public final void smartSleep(long millis) {
		try {
			long start = System.currentTimeMillis();
			Strand.sleep(millis);
			System.out.println(millis +" >休眠:" + (System.currentTimeMillis() - start));
		} catch (SuspendExecution | InterruptedException e) {
			e.printStackTrace();
		}
	}
}