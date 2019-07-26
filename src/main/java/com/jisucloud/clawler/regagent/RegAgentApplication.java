package com.jisucloud.clawler.regagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RegAgentApplication {

	public static void main(String[] args) {
		//if (kilim.tools.Kilim.trampoline(new Object() {},false,args)) return;
		SpringApplication application = new SpringApplication(RegAgentApplication.class);
		application.run(args);
		log.info("撞库服务启动完成");
	}

}
