package org.apache.httpclient.sandbox.simple;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Simple example demonstrating an HTTP GET request.
 * 
 * @author grudkowm
 */
public class Simple {

	public static void main(String[] args) throws ClientProtocolException, IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet("http://carboxbobappqa009:8081/nexus/");
//		HttpGet httpget = new HttpGet("http://jcenter.bintray.com/f");
		
		CloseableHttpResponse response = httpclient.execute(httpget);
		
		try {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				long len = entity.getContentLength();
				if (len != -1 && len < 2048) {
					System.out.println(EntityUtils.toString(entity));
				} else {
					// Stream content out
			        
					ContentType contentType = ContentType.getOrDefault(entity);
					Reader reader;
					if (contentType.getCharset() != null) {
						reader = new InputStreamReader(entity.getContent(), contentType.getCharset());
					} else {
						reader = new InputStreamReader(entity.getContent());
					}
					char[] bytes=new char[(int)len];
					reader.read(bytes);
					System.out.println(new String(bytes));
					entity.getContent();
					
				}
			}
		} finally {
			response.close();
		}
	}
}
