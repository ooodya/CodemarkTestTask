package com.zaytsev.codemarkTestTask.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@org.springframework.boot.test.context.TestConfiguration
public class TestConfiguration
{
	@Bean
	public RestTemplate restTemplate() 
	{
	    return new RestTemplate();
	}
}
