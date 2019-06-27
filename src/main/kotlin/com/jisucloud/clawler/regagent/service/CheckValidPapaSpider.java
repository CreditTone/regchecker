package com.jisucloud.clawler.regagent.service;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.ClassUtils;

import com.jisucloud.clawler.regagent.util.PapaSpiderTester;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CheckValidPapaSpider implements PapaSpiderTester.PapaSpiderTestListener {
	
	private static Set<Class<? extends PapaSpider>> TEST_SUCCESS_PAPASPIDERS = new HashSet<>();
	private static Set<Class<? extends PapaSpider>> TEST_FAILURE_PAPASPIDERS = new HashSet<>();
	private static Set<Class<? extends PapaSpider>> NOUSE_PAPASPIDERS = new HashSet<>();

	@PostConstruct
	private void init() {
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
			PapaSpiderTester papaSpiderTester = new PapaSpiderTester();
			papaSpiderTester.testing(papaSpiders, this);
		}catch(Exception e) {
			log.warn("载入失败", e);
		}
	}

	public static void main(String[] args) throws Exception {
		
	}

	public static boolean isPapaSpiderClass(Class<?> clz) {
		return PapaSpider.class.isAssignableFrom(clz);
	}
	
	public static boolean isUsePapaSpider(Class<?> clz) {
		return clz.isAnnotationPresent(UsePapaSpider.class);
	}

	@Override
	public void testSuccess(Class<? extends PapaSpider> clz) {
		TEST_SUCCESS_PAPASPIDERS.add(clz);
	}

	@Override
	public void testFailure(Class<? extends PapaSpider> clz) {
		TEST_FAILURE_PAPASPIDERS.add(clz);
	}
}
