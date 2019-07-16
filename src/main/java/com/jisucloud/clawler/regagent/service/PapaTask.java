package com.jisucloud.clawler.regagent.service;

import java.util.Set;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PapaTask {
	
	private String id;

	private String telephone;
	
	private String email;
	
	private String callurl;
	
	private String name;
	
	private Set<String> needlessCheckPlatforms;
	
	public boolean isNeedlessCheck(String platform) {
		return needlessCheckPlatforms != null && needlessCheckPlatforms.contains(platform);
	}
	
}
