package com.jisucloud.clawler.regagent.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface PapaSpiderConfig {
	
	/**
	 * 是否排除使用，也可以使用excludeMsg
	 * @return
	 */
	boolean exclude() default false;
	
	
	/**
	 * 排除使用原因，也可以使用exclude = true
	 * @return
	 */
	String excludeMsg() default "";
	
	/**
	 * 无论测试结果如何，都投入使用。默认不开启
	 * @return
	 */
	boolean ignoreTestResult() default false;
	
	String message();

	String platform();
	
	String home();
	
	String platformName();
	
	String[] tags();
	
	String[] testTelephones();
}
