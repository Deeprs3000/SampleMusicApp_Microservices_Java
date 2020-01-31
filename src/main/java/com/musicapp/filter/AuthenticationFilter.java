package com.musicapp.filter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * The Class AuthenticationFilter.
 */
@Component
@Order(-2)
public class AuthenticationFilter implements Filter {
	
    @Value("${oauth.authorize:/authserver/validate}")
    private String authorizeUrl;
    
    @Autowired 
    private DiscoveryClient discoveryClient;
    
	
	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		String header = req.getHeader("Authorization");
		if(!StringUtils.isEmpty(header)) {
			validateToken(header);
			filterChain.doFilter(request, response);
		}else {
			((HttpServletResponse) response).sendError(HttpServletResponse.SC_UNAUTHORIZED, "The token not found.");
		}	
	}

	private ResponseEntity<String> validateToken(String token) {
		StringBuilder sb = new StringBuilder();
		List<ServiceInstance> instances = discoveryClient.getInstances("MUSICAPP-IAM");
		if(!CollectionUtils.isEmpty(instances)) {
			String baseurl = instances.get(0).getUri().toString();
			sb.append(baseurl).append(authorizeUrl);		
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
			headers.add("Authorization", token);
			HttpEntity<String> entity = new HttpEntity<>("body", headers);
			return restTemplate.exchange(sb.toString(), HttpMethod.POST, entity, String.class);
		}
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
		
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		
	}
	
}