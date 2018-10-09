package com.example.demo;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.Calendar;
import java.util.Properties;

import javax.net.ssl.SSLContext;

import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.EntryEvent;
import org.apache.geode.cache.util.CacheListenerAdapter;
import org.apache.geode.distributed.internal.InternalDistributedSystem;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import com.citi.nam.abanumber.domain.model.GemfireData;


public class TestListner extends CacheListenerAdapter<Object, Object> implements Declarable {

    private static String cleanUpURL = null;
    private static Properties properties = new Properties();
    InternalDistributedSystem ds = InternalDistributedSystem.getAnyInstance();

    public void afterCreate(EntryEvent<Object, Object> event) {
    	System.out.println("custom lstner:::create");
           this.ds.getLogWriter()
                        .config("CustomListener afterCreate " + Calendar.getInstance().getTime() + ":key:"+event.getKey()+":NewValue::"+event.getNewValue()+"::OldValue:"+event.getOldValue());
           GemfireData gd = (GemfireData) event.getNewValue();
           this.ds.getLogWriter().config("CustomListener values:"+gd.getAni()+":::"+gd.getDnis());
    
    }

    public void afterDestroy(EntryEvent<Object, Object> event) {
    	System.out.println("custom lstner:::create");
           this.ds.getLogWriter()
                        .config("CustomListener afterDestroy " + Calendar.getInstance().getTime() + ":key:"+event.getKey()+":NewValue::"+event.getNewValue()+"::OldValue:"+event.getOldValue());
           
           GemfireData gd = (GemfireData) event.getOldValue();
           this.ds.getLogWriter().config("CustomListener values:"+gd.getAni()+":::"+gd.getDnis());

		SSLContext context = null;
		try {
			SSLContextBuilder contextBuilder = SSLContexts.custom();
			if (properties.get("keyStoreURL") != null) {
				URL keyStoreURL = new URL(properties.getProperty("keyStoreURL"));
				char[] keyStorePassword = properties.getProperty("keyStorePassword") == null ? "changeit".toCharArray()
						: properties.getProperty("keyStorePassword").toCharArray();
				contextBuilder = contextBuilder.loadKeyMaterial(keyStoreURL, keyStorePassword, keyStorePassword);
			}
			if (properties.get("trustStoreURL") != null) {
				URL trustStoreURL = new URL(properties.getProperty("trustStoreURL"));
				char[] trustStorePassword = properties.getProperty("trustStorePassword") == null
						? "changeit".toCharArray()
						: properties.getProperty("trustStorePassword").toCharArray();

				contextBuilder = contextBuilder.loadTrustMaterial(trustStoreURL, trustStorePassword,
						TrustSelfSignedStrategy.INSTANCE);
			}
			context = contextBuilder.build();
		} catch (Exception e1) {
			this.ds.getLogWriter().error(e1);
		}
		
		
			SSLConnectionSocketFactory sslFactory = new SSLConnectionSocketFactory(context, 
		        SSLConnectionSocketFactory.getDefaultHostnameVerifier());
//		      CloseableHttpClient client = HttpClients.custom().setSSLSocketFactory(sslFactory).build();

           CloseableHttpClient client = HttpClients.custom().build();
           
           

           RequestBuilder requestBuilder = RequestBuilder.create(properties.getProperty("httpMethod", "POST"));
           HttpPost httpPost = new HttpPost("http://localhost:8010/testlistner");
           try {
			httpPost.setEntity(new StringEntity(gd.getAni()));
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
           requestBuilder.setUri(cleanUpURL);
           CloseableHttpResponse response = null;
           try {
                  response = client.execute(httpPost);
                  int statusCode = response.getStatusLine().getStatusCode();
                  HttpEntity entity = response.getEntity();
                  String responseBody = EntityUtils.toString(entity);
                  this.ds.getLogWriter()
                               .config("CustomListener afterDestroy statusCode" + statusCode + " responseBody " + responseBody);
           } catch (ClientProtocolException e) {
                  // TODO
                  e.printStackTrace();
           } catch (IOException e) {
                  // TODO
                  e.printStackTrace();
           } catch (Exception e) {
                  // TODO
                  e.printStackTrace();
           } finally {
                  try {
                        if (response != null)
                               response.close();
                        client.close();
                  } catch (IOException e) {
                        this.ds.getLogWriter().error(e);
                  }
           }

    }

	public void init(Properties props) {
		cleanUpURL = props.getProperty("security-cleanUpURL");
		 if (props.containsKey("security-proxyHost")) {
		        properties.setProperty("proxyHost", props.getProperty("security-proxyHost"));
		        properties.setProperty("proxyPort", props.getProperty("security-proxyPort", "-1"));
		      }
		      if (props.containsKey("security-keyStoreURL")) {
		        properties.setProperty("keyStoreURL", props.getProperty("security-keyStoreURL"));
		        properties.setProperty("keyStorePassword", props.getProperty("security-keyStorePassword", "changeit"));
		      }
		      if (props.containsKey("security-trustStoreURL")) {
		        properties.setProperty("trustStoreURL", props.getProperty("security-trustStoreURL"));
		        properties.setProperty("trustStorePassword", props.getProperty("security-trustStorePassword", "changeit"));
		      }
		      if (props.containsKey("security-httpMethod")) {
		        properties.setProperty("httpMethod", props.getProperty("security-httpMethod"));
		      }
		      this.ds.getLogWriter().config("Configured values " + properties);
	}




}

