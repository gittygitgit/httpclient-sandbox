package org.apache.httpclient.sandbox.simple;

import java.io.IOException;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Demonstrates simple use of various API's, including:
 * <ul>
 * <li>{@link HttpContext}</li>
 * <li>{@link RequestConfig}</li>
 * </ul>
 * @author grudkowm
 */
public class Simple2 {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		HttpContext context = new BasicHttpContext();
		HttpClientContext clientContext = HttpClientContext.adapt(context);
//		HttpHost target = clientContext.getTargetHost();
//		HttpRequest request = clientContext.getRequest();
//		HttpResponse response = clientContext.getResponse();
//		RequestConfig config = clientContext.getRequestConfig();
//		
		CloseableHttpClient httpclient = HttpClients.createDefault();
		RequestConfig requestConfig = RequestConfig.custom()
		        .setSocketTimeout(1000)
		        .setConnectTimeout(1000)
		        .build();

		HttpGet httpget1 = new HttpGet("http://carboxbobappqa009:8081/nexus/");
		httpget1.setConfig(requestConfig);
		CloseableHttpResponse response1 = httpclient.execute(httpget1, context);
		try {
		    HttpEntity entity1 = response1.getEntity();
		} finally {
		    response1.close();
		}
		HttpGet httpget2 = new HttpGet("http://carboxbobappqa009:8081/nexus/");
		CloseableHttpResponse response2 = httpclient.execute(httpget2, context);
		try {
		    HttpEntity entity2 = response2.getEntity();
		} finally {
		    response2.close();
		}
	}
}
