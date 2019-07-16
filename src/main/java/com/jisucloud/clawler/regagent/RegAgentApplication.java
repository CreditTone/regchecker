package com.jisucloud.clawler.regagent;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.jisucloud.clawler.regagent.util.JJsoupUtil;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RegAgentApplication {

	public static void main(String[] args) {
		if (args == null) {
			args = new String[] {"-javaagent:/Users/stephen/.m2/repository/co/paralleluniverse/quasar-core/0.7.10/quasar-core-0.7.10.jar"};
		}else {
			String[] newArgs = new String[args.length + 1];
			for (int i = 0; i < args.length; i++) {
				newArgs[i] = args[i];
			}
			newArgs[newArgs.length - 1] = "-javaagent:/Users/stephen/.m2/repository/co/paralleluniverse/quasar-core/0.7.10/quasar-core-0.7.10.jar";
			args = newArgs;
		}
		log.info("SpringApplication正在启动");
		SpringApplication application = new SpringApplication(RegAgentApplication.class);
		JJsoupUtil.useProxy = false;
		application.run(args);
		log.info("SpringApplication启动完成");
		new Fiber<Void>("Caller", new SuspendableRunnable() {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			@Override
			public void run() throws SuspendExecution, InterruptedException {
				try {
					log.info("协程测试成功......"+Fiber.isCurrentFiber());
				}catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			}
		}).start();
		new Thread() {
			public void run() {
				try {
					log.info("协程测试成功......"+Fiber.isCurrentFiber());
				}catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			};
		}.start();
	}
	
}
