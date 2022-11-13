package com.vascomm.publicholiday.config;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
@Configuration
public class GeneralConfig {
	
//	@Value("${server.tomcat.read-timeout}")
//	private Integer readTimeout;
//	
//	@Value("${server.tomcat.connection-timeout}")
//	private Integer connectTimeout;
	
	@Bean
	public RestTemplate restTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

    	TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
    	SSLContext sslContext = SSLContexts.custom()
    	        .loadTrustMaterial(null, acceptingTrustStrategy)
    	        .build();
    	SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);
    	CloseableHttpClient httpClient = HttpClients.custom()
    	        .setSSLSocketFactory(csf)
    	        .build();
    	HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
    	requestFactory.setHttpClient(httpClient);
    	requestFactory.setReadTimeout(18000);
    	requestFactory.setConnectTimeout(50000);

    	RestTemplate restTemplate = new RestTemplate(requestFactory);
    	
        return restTemplate;
    }
	
}