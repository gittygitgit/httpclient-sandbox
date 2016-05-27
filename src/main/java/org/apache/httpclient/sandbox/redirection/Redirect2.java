package org.apache.httpclient.sandbox.redirection;

import java.io.IOException;
import java.io.PrintStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpRequest;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

/**
 * Demonstrates following:
 * <ul>
 * <li>Request a URL which asks client to follow a redirect to a proxy address.
 * <li>Client is setup to automatically handle redirects.
 * </ul>  
 * @author grudkowm
 */
public class Redirect2 {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		
		HttpContext context = new BasicHttpContext();
		HttpClientContext clientContext = HttpClientContext.adapt(context);
//		HttpHost target = clientContext.getTargetHost();
		HttpRequest request = clientContext.getRequest();
//		HttpResponse response = clientContext.getResponse();
//		RequestConfig config = clientContext.getRequestConfig();
//		
		CredentialsProvider credsProvider = new BasicCredentialsProvider();
        credsProvider.setCredentials(
                new AuthScope("aras-bcprox1", 444),
                new UsernamePasswordCredentials("grudkowm", "SPring2016__"));
        CloseableHttpClient httpclient = HttpClients.custom()
                .setDefaultCredentialsProvider(credsProvider).build();
		// Configure the request not to automatically follow the redirect
		clientContext.setRequestConfig(RequestConfig.custom()
	        .setSocketTimeout(1000)
	        .setConnectTimeout(1000)
	        .setRedirectsEnabled(true)
	        .build());

		HttpGet simpleGet = new HttpGet("http://jcenter.bintray.com/");
//		simpleGet.addHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_10_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/50.0.2661.102 Safari/537.36");
		System.out.printf("Issuing request (%s)\n", simpleGet.getRequestLine());
		CloseableHttpResponse response = httpclient.execute(simpleGet, context);
		
		try {
			
			
			// Print some info about the response and entity
		    System.out.printf("Response info [%s, %s]\n", "protocol version", response.getProtocolVersion());
		    System.out.printf("Response info [%s, %s]\n", "status", response.getStatusLine().getStatusCode());
		    System.out.printf("Response info [%s, %s]\n", "reason-phrase", response.getStatusLine().getReasonPhrase());
		    int statusCode = response.getStatusLine().getStatusCode();
		    boolean isOk = statusCode == 200;
		 
		    if (isOk) {
		    	
		    	// Basic entity analysis
		    	HttpEntity entity = response.getEntity();
		    	boolean containsContent = entity != null;
		    	
		    	if (containsContent) {
		    		
		    		boolean unknownLength = entity.getContentLength() == -1;
		    		if (unknownLength) {
		    			// Stream content
		    			PrintStream out = System.out;
		    			entity.writeTo(out);
		    			out.print("\n");
		    		} else {
		    			// Message is self 
		    			
		    		}
		    		
		    	} else {
		    		// No content in the response
		    	}
		    	// Is there any content in the response?
		    } else {
		    	// Non 200 response
		    	
		    	boolean isRedirected = statusCode >=300 && statusCode < 400;
		    	if (isRedirected) {
		    		// Response is asking client to follow a redirection.
		    		System.out.println("Redirected.");
		    	} else {
		    		
		    	}
		    }
		} finally {
		    response.close();
		}
	}
}
