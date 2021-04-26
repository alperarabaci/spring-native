//package com.training.demo;
//
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.jmx.support.ConnectorServerFactoryBean;
//
//
//@Configuration
//public class ConnectorServiceFactoryBeanProvider {
//	@Value("${spring.jmx.url}")
//	private String url;
//
//	@Bean
//	public ConnectorServerFactoryBean connectorServerFactoryBean() throws Exception {
//		final ConnectorServerFactoryBean connectorServerFactoryBean = new ConnectorServerFactoryBean();
//		connectorServerFactoryBean.setServiceUrl(url);
//		return connectorServerFactoryBean;
//	}
//	
//}