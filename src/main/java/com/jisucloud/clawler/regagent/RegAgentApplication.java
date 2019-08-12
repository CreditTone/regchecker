package com.jisucloud.clawler.regagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.deep007.spiderbase.Init;
import com.deep007.spiderbase.killer.LinuxKiller;
import com.deep077.spiderbase.selenium.mitm.MitmServer;
import com.deep077.spiderbase.selenium.mitm.cache.JedisMitmCacheProvider;
import com.jisucloud.clawler.regagent.mitm.StringRedisTemplateMitmCacheProvider;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RegAgentApplication {
	
	//-Djdk.tls.client.protocols=TLSv1,TLSv1.1,TLSv1.2
	
	public static final String RANDOM_PROXY_HOST = "http-dyn.abuyun.com";
	public static final String RANDOM_PROXY_USERNAME = "H6224X2YF291C2AD";
	public static final String RANDOM_PROXY_PASSWORD = "DFF60822C2F39DCE";
	public static final int RANDOM_PROXY_POST = 9020;
	
	public static String GOOGLE_PROXY_HOST = "5d23158e0d319529.natapp.cc";
	public static int GOOGLE_PROXY_POST = 64445;
	
	public static String CHROME_DRIVER_SERVER = "/root/chromedriver";
	
	public static void init() {
		if (System.getProperty("os.name").toLowerCase().contains("mac")) {
			CHROME_DRIVER_SERVER = "/Users/stephen/Downloads/chromedriver";
		}
		if (System.getProperty("os.name").toLowerCase().contains("win")) {
			CHROME_DRIVER_SERVER = "C:\\chromedriver.exe";
		}
		System.setProperty("webdriver.chrome.driver", CHROME_DRIVER_SERVER);
		Init.initRandomProxy(RANDOM_PROXY_HOST, RANDOM_PROXY_POST, RANDOM_PROXY_USERNAME, RANDOM_PROXY_PASSWORD);
		//Init.initGoogleProxy(GOOGLE_PROXY_HOST, GOOGLE_PROXY_POST, null, null);
	}

	public static void main(String[] args) {
		init();
		//if (kilim.tools.Kilim.trampoline(new Object() {},false,args)) return;
		SpringApplication application = new SpringApplication(RegAgentApplication.class);
		application.run(args);
		log.info("撞库服务启动完成");
		
	}

}
