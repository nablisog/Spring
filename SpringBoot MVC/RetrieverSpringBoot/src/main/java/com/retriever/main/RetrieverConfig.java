package com.retriever.main;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;
import java.util.*;

@Configuration
public class RetrieverConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
}
