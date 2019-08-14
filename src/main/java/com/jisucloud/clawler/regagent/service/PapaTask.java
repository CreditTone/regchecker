package com.jisucloud.clawler.regagent.service;

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
	
	private String name;
	
	private String idcard;
	
	private String papaClz;
	
	
}
