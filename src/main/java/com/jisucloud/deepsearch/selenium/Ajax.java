package com.jisucloud.deepsearch.selenium;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ajax {
	
	private String url;
	
	private String method;
	
	private String requestData;
	
	private String response;
	
}
