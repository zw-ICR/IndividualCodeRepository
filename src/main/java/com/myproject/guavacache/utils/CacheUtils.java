package com.myproject.guavacache.utils;

import java.util.concurrent.ExecutionException;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CacheUtils<K,V>{
	
	@SuppressWarnings("rawtypes")
	private static LoadingCache CACHE;
	
	public static <K,V> void  initCache(final CacheLoader<K,V> acl){
		CacheBuilder<Object, Object> cacheBuilder = CacheBuilder.newBuilder();
		if(acl != null){
			CACHE = cacheBuilder.build(acl);
			return;
		}
	}
	
	@SuppressWarnings("unchecked")
	public static <K,V> void put(K k,V v){
		CACHE.put(k, v);
	}
	
	@SuppressWarnings("unchecked")
	public static <K,V> V get(K k){
		try {
			return (V)CACHE.get(k);
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static void main(String[] args) throws Exception{
		CacheUtils.initCache(new CacheLoader<String, String>(){
			@Override
			public String load(String key) throws Exception {
				// TODO Auto-generated method stub
				return null;
			}
			
		});
		
		CacheUtils.put("String", "sssssssssss");
		System.out.println(CacheUtils.get("String"));
	}

}
