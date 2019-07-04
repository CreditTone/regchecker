package com.jisucloud.clawler.regagent.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.jisucloud.clawler.regagent.service.PapaSpiderService;
import com.jisucloud.clawler.regagent.service.PapaTask;
import com.jisucloud.clawler.regagent.util.StringUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/papa")
@Slf4j
public class PapaController {
	
	@Autowired
	private PapaSpiderService papaSpiderService;
	
	@RequestMapping(value = "/rapeData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object rapeData(@RequestParam(name = "tel") String tel,@RequestParam(name = "name" , required = false) String name, @RequestParam(name = "callurl") String callurl, @RequestParam(name = "needlessCheckPlatforms" , required = false, defaultValue = "") String noCheckPlatform) {
		Set<String> needlessCheckPlatforms = new HashSet<>();
		if (StringUtil.isValidString(noCheckPlatform)) {
			String[] items = noCheckPlatform.split(",");
			for (int i = 0; items != null && i < items.length; i++) {
				String item = items[i].trim();
				if (!item.isEmpty()) {
					needlessCheckPlatforms.add(item);
				}
			}
		}
		PapaTask papaTask = PapaTask.builder().id(UUID.randomUUID().toString()).telephone(tel).callurl(callurl).name(name).needlessCheckPlatforms(needlessCheckPlatforms).build();
		papaSpiderService.addPapaTask(papaTask);
		Map<String,Object> resp = new HashMap<>();
		resp.put("id", papaTask.getId());
		return resp;
	}
	
}
