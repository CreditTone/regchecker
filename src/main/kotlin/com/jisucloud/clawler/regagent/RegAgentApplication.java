package com.jisucloud.clawler.regagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jisucloud.clawler.regagent.util.JJsoupUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RegAgentApplication {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(RegAgentApplication.class);
		JJsoupUtil.useProxy = false;
		application.run(args);
		log.info("SpringApplication启动完成");
	}
	
}
