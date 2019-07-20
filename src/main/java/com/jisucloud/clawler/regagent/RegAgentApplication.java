package com.jisucloud.clawler.regagent;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.scheduling.annotation.EnableScheduling;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;
import co.paralleluniverse.strands.SuspendableRunnable;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@EnableScheduling
@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })
public class RegAgentApplication {

	public static void main(String[] args) {
//		File quasar_core_jar = new File("quasar-core-0.8.0.jar");
//		if (!quasar_core_jar.exists()) {
//			log.warn("请将quasar-core-xx.x.jar放置在本程序所在目录下");
//			return;
//		}
//		if (args == null) {
//			args = new String[] {"-javaagent:"+quasar_core_jar.getAbsolutePath(), "-Dco.paralleluniverse.fibers.verifyInstrumentation=true" , "-Dco.paralleluniverse.fibers.detectRunawayFibers=false"};
//		}else {
//			String[] newArgs = new String[args.length + 3];
//			for (int i = 0; i < args.length; i++) {
//				newArgs[i] = args[i];
//			}
//			newArgs[newArgs.length - 3] = "-javaagent:"+quasar_core_jar.getAbsolutePath();
//			newArgs[newArgs.length - 2] = "-Dco.paralleluniverse.fibers.verifyInstrumentation=true";
//			newArgs[newArgs.length - 1] = "-Dco.paralleluniverse.fibers.detectRunawayFibers=false";
//			args = newArgs;
//		}
//		Class clz = com.esotericsoftware.kryo.Serializer.class;
//		System.out.println(clz.getName());
		SpringApplication application = new SpringApplication(RegAgentApplication.class);
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
					log.info("协程测试:"+Fiber.isCurrentFiber());
				}catch (Exception e) {
					e.printStackTrace();
				} finally {
				}
			}
		}).start();
	}
	
}
