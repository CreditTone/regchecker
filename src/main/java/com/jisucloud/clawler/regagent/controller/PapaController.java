package com.jisucloud.clawler.regagent.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.deep007.goniub.killer.LinuxJvmProcrssMonitor;
import com.jisucloud.clawler.regagent.service.PapaTaskConsume;
import com.jisucloud.clawler.regagent.service.PapaTaskConsume.Status;
import com.jisucloud.clawler.regagent.service.PapaTaskProduce;
import com.jisucloud.clawler.regagent.service.TaskStatus;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/papa")
@Slf4j
public class PapaController {
	
	@Autowired
	private PapaTaskProduce papaTaskService;
	
	@Autowired
	private PapaTaskConsume papaSpiderService;
	
	@Autowired
	private ConfigurableApplicationContext context;
	
	private LinuxJvmProcrssMonitor linuxJvmProcrssMonitor = LinuxJvmProcrssMonitor.getThisLinuxJvmProcrssMonitor();
	
	@RequestMapping(value = "/rapeData", method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
	public Object rapeData(@RequestParam(name = "tel") String tel, @RequestParam(name = "name" , required = false) String name, @RequestParam(name = "idcard" , required = false) String idcard) {
		Map<String,Object> resp = new HashMap<>();
		resp.put("id", papaTaskService.allocTask(tel, name, idcard));
		return resp;
	}
	
	@RequestMapping(value = "/shutdown", method = RequestMethod.GET)
	public void shutdown() {
		linuxJvmProcrssMonitor.killAll();
		context.close();
	}
	
	
	@RequestMapping(value = "/status", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public Status status() {
		return papaSpiderService.getStatus();
	}
	
	@RequestMapping(value = "/taskstatus", method = RequestMethod.GET, produces = "application/json;charset=UTF-8")
	public TaskStatus taskStatus(@RequestParam(name = "id") String id) {
		return papaTaskService.getTaskStatus(id);
	}
	
}
