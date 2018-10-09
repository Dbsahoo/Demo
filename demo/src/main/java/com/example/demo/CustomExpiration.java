package com.example.demo;

import java.util.Properties;

import org.apache.geode.cache.CustomExpiry;
import org.apache.geode.cache.Declarable;
import org.apache.geode.cache.ExpirationAction;
import org.apache.geode.cache.ExpirationAttributes;
import org.apache.geode.cache.Region;
import org.apache.geode.distributed.internal.InternalDistributedSystem;

import com.citi.nam.abanumber.domain.model.GemfireData;

public class CustomExpiration implements CustomExpiry<Object, Object>, Declarable{

	public void close() {
		
	}

	public void init(Properties arg0) {
		InternalDistributedSystem.getAnyInstance().getLogWriter().config("CustomExpiration Properties received " + arg0);
		
	}

	public ExpirationAttributes getExpiry(Region.Entry entry) {
		
		InternalDistributedSystem.getAnyInstance().getLogWriter().config("CustomExpiration:Enter");
		GemfireData region = (GemfireData)entry.getValue();
		InternalDistributedSystem.getAnyInstance().getLogWriter().config("CustomExpiration:" + region+"::"+region.isFlag()+":ttl:"+region.getMaxttl());
		if(region.isFlag()) {
			long remainingTime = System.currentTimeMillis() - region.getMaxttl();
			InternalDistributedSystem.getAnyInstance().getLogWriter().config("remainingTime:"+remainingTime);
			return new ExpirationAttributes((int) (remainingTime/1000L), ExpirationAction.DESTROY);
		}
		InternalDistributedSystem.getAnyInstance().getLogWriter().config("CustomExpiration:Set New Expiration");
		return null;
//	    try {
//	    	InternalDistributedSystem.getAnyInstance().getLogWriter().config("CustomExpiration:Enter");
//	    		GemfireData region = (GemfireData)entry.getValue();
//	    		InternalDistributedSystem.getAnyInstance().getLogWriter().config("CustomExpiration:" + region+"::"+region.getMaxttl());
//	    	
//	    	long TTLActual = region.getMaxttl();
//	    	InternalDistributedSystem.getAnyInstance().getLogWriter().config("TTLActual" + TTLActual);
//	    	if (TTLActual > 0L) {
//	    		long TTLRemaining = TTLActual - System.currentTimeMillis();
//	    		InternalDistributedSystem.getAnyInstance().getLogWriter().config("TTLRemaining" + TTLRemaining);
//	    		if (TTLRemaining <= 0L)
//	    	          timeOut = 1;
//	    	        else {
//	    	          timeOut = (int)(TTLRemaining / 1000L);
//	    	        }
//	    		InternalDistributedSystem.getAnyInstance().getLogWriter().config("timeOut" + timeOut);
//	    	}
//	        InternalDistributedSystem.getAnyInstance().getLogWriter().config("Timeout received from application " + timeOut);
//	      } catch (Exception e) {
//	        InternalDistributedSystem.getAnyInstance().getLogWriter().error("Error occured while retrieving timeout ", e);
//	      }
//	    return new ExpirationAttributes(timeOut, ExpirationAction.DESTROY);
	}

}
