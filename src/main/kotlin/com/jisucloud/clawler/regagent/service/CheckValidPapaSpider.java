package com.jisucloud.clawler.regagent.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.util.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CheckValidPapaSpider implements PapaSpiderTester.PapaSpiderTestListener {
	
	private static Set<Class<? extends PapaSpider>> TEST_SUCCESS_PAPASPIDERS = new HashSet<>();
	private static Set<Class<? extends PapaSpider>> TEST_FAILURE_PAPASPIDERS = new HashSet<>();
	private static Set<Class<? extends PapaSpider>> NOUSE_PAPASPIDERS = new HashSet<>();
	
	private JSONObject checkValidPapaSpiderResult = new JSONObject();
	

	@PostConstruct
	private void init() throws Exception {
		File checkValidPapaSpiderResultFile = new File("checkValidPapaSpiderResult.json");
		if (checkValidPapaSpiderResultFile.exists()) {
			checkValidPapaSpiderResult = JSON.parseObject(FileUtils.readFileToString(checkValidPapaSpiderResultFile));
		}
		Set<Class<? extends PapaSpider>> papaSpiders = new HashSet<>();
		try {
			String basePackage = "com.jisucloud.clawler.regagent.service.impl";
			String searchPaths = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX
					+ ClassUtils.convertClassNameToResourcePath(basePackage) + "/**/*.class";
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
			Resource[] resources = resolver.getResources(searchPaths);
			MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory();
			for (Resource resource : resources) {
				MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
				String className = metadataReader.getClassMetadata().getClassName();
				Class<?> clz = Class.forName(className);
				if (isPapaSpiderClass(clz) && isUsePapaSpider(clz)) {
					papaSpiders.add((Class<? extends PapaSpider>) clz);
				}else if (isPapaSpiderClass(clz)) {
					NOUSE_PAPASPIDERS.add((Class<? extends PapaSpider>) clz);
				}
			}
			log.info("统计撞库结果，投入使用的" + papaSpiders.size() + "家，正在研发待投入使用的" + NOUSE_PAPASPIDERS.size() + "家。");
			log.info("正在研发待列表如下：");
			for (Class<?> clz : NOUSE_PAPASPIDERS) {
				log.info(clz.getName());
			}
//			log.info("开始测试......");
//			PapaSpiderTester.testing(papaSpiders, this);
//			log.info("开始完成，成功" + TEST_SUCCESS_PAPASPIDERS.size() + "个，失败" + TEST_FAILURE_PAPASPIDERS.size() + "个。");
//			if (!TEST_FAILURE_PAPASPIDERS.isEmpty()) {
//				log.info("测试失败列表如下:");
//				for (Class<? extends PapaSpider> clz : TEST_FAILURE_PAPASPIDERS) {
//					log.info(clz.getName());
//				}
//			}
			checkValidPapaSpiderResult.put("time", System.currentTimeMillis());
		}catch(Exception e) {
			log.warn("载入失败", e);
			throw e;
		}
	}
	
	private void saveCheckValidPapaSpiderResult() throws IOException {
		File checkValidPapaSpiderResultFile = new File("checkValidPapaSpiderResult.json");
		JSONObject old = null;
		if (checkValidPapaSpiderResultFile.exists()) {
			old = JSON.parseObject(FileUtils.readFileToString(checkValidPapaSpiderResultFile , "UTF-8"));
		}else {
			old = new JSONObject();
		}
		if (checkValidPapaSpiderResult != null && !checkValidPapaSpiderResult.isEmpty()) {
			
		}
	}
	
	private boolean isCheckValidPapaSpiderResultInvalid(Class<? extends PapaSpider> clz) {
		if (checkValidPapaSpiderResult.containsKey(clz.getName())) {
			long lastCheckTime = checkValidPapaSpiderResult.getLongValue(clz.getName());
			if (System.currentTimeMillis() - lastCheckTime < 3600 * 1000 * 24) {//24小时
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) throws Exception {
		new CheckValidPapaSpider().init();
	}

	public static boolean isPapaSpiderClass(Class<?> clz) {
		return PapaSpider.class.isAssignableFrom(clz);
	}
	
	public static boolean isUsePapaSpider(Class<?> clz) {
		return clz.isAnnotationPresent(UsePapaSpider.class);
	}

	@Override
	public void testSuccess(Class<? extends PapaSpider> clz) {
		log.info("测试成功:"+clz.getName());
		checkValidPapaSpiderResult.put(clz.getName(), System.currentTimeMillis());
		TEST_SUCCESS_PAPASPIDERS.add(clz);
	}

	@Override
	public void testFailure(Class<? extends PapaSpider> clz) {
		log.info("测试失败:"+clz.getName());
		TEST_FAILURE_PAPASPIDERS.add(clz);
	}
}
