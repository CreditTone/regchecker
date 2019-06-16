package com.jisucloud.clawler.regagent.service;

import java.util.Map;

public interface PapaSpider {
	
	public static final String ANDROID_USER_AGENT = "Mozilla/5.0 (Linux; Android 7.0; PLUS Build/NRD90M) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/61.0.3163.98 Mobile Safari/537.36";
	
	public static final String IOS_USER_AGENT = "Mozilla/5.0 (iPhone; CPU iPhone OS 11_0_1 like Mac OS X) AppleWebKit/604.1.38 (KHTML, like Gecko) Version/11.0 Mobile/15A402 Safari/604.1";
	
	public static final String CHROME_USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; WOW64; rv:56.0) Gecko/20100101 Firefox/56.0";

    String message();

    String platform();

    String home();

    String platformName();

    boolean checkTelephone(String account);

    boolean checkEmail(String account);

    Map<String, String> getFields();

    String[] tags();

}