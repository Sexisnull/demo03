package com.gsww.uids.gateway.util;

import java.util.Iterator;
import java.util.List;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import net.sf.ehcache.CacheException;
import java.io.IOException;

public class CacheUtil {
	private static final Log a = LogFactory.getLog(CacheUtil.class);

	private static CacheManager b = CacheManager.create();

	private static String c = "gsww";

	public static void createCache(String paramString, int paramInt, boolean paramBoolean1, boolean paramBoolean2,
			long paramLong1, long paramLong2) {
		Cache localCache = new Cache(paramString, paramInt, paramBoolean1, paramBoolean2, paramLong1, paramLong2);
		b.addCache(localCache);
	}

	public static Cache findCacheByName(String paramString) {
		Cache localCache = b.getCache(paramString);
		return localCache;
	}

	public static boolean isExist(String paramString) {
		boolean bool = false;
		Cache localCache = b.getCache(paramString);
		if (localCache != null) {
			bool = true;
		}
		return bool;
	}

	public static boolean setValue(Object paramObject1, Object paramObject2) {
		Cache localCache = b.getCache(c);
		Element localElement = new Element(paramObject1, paramObject2);
		localCache.put(localElement);
		return true;
	}

	public static boolean setValue(Object paramObject1, Object paramObject2, String paramString) {
		Cache localCache = b.getCache(paramString);
		Element localElement = new Element(paramObject1, paramObject2);
		localCache.put(localElement);
		return true;
	}

	public static Object getValue(Object paramObject) {
		Cache localCache = b.getCache(c);
		Element localElement = localCache.get(paramObject);
		if (localElement == null) {
			return null;
		}
		Object localObject = localElement.getObjectValue();
		return localObject;
	}

	public static Object getValue(Object paramObject, String paramString) {
		System.out.println("paramObject" + paramObject.toString());
		System.out.println("paramString" + paramString.toString());
		Cache localCache = CacheManager.create().getCache(paramString);
		if (localCache == null) {
			System.out.println("CacheManager.create"+CacheManager.create());
			System.out.println("CacheManager.create().getCache(paramString)"+CacheManager.create().getCache(paramString));
			System.out.println("localCache为空");
			return null;
		} else {
			Element localElement = localCache.get(paramObject);
			if (localElement == null) {
				return null;
			}
			Object localObject = localElement.getObjectValue();
			return localObject;
		}
	}

	public static boolean removeKey(Object paramObject) {
		Cache localCache = b.getCache(c);
		return localCache.remove(paramObject);
	}

	public static boolean removeKey(Object paramObject, String paramString) {
		Cache localCache = b.getCache(paramString);
		return localCache.remove(paramObject);
	}

	public static boolean removeKeyStartsWith(String paramString) {
		Cache localCache = b.getCache(c);
		List localList = localCache.getKeys();
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			if (str.startsWith(paramString)) {
				localCache.remove(str);
			}
		}
		return true;
	}

	public static boolean removeKeyStartsWith(String paramString1, String paramString2) {
		Cache localCache = b.getCache(paramString2);
		List localList = localCache.getKeys();
		Iterator localIterator = localList.iterator();
		while (localIterator.hasNext()) {
			String str = (String) localIterator.next();
			if (str.startsWith(paramString1)) {
				localCache.remove(str);
			}
		}
		return true;
	}

	public static boolean removeAll(String paramString) throws CacheException, IOException {
		Cache localCache = b.getCache(paramString);
		localCache.removeAll();
		return true;
	}

	public static boolean removeAll() throws CacheException, IOException {
		Cache localCache = b.getCache(c);
		localCache.removeAll();
		return true;
	}

	public static void close() {
		a.info("shutdown cache start");
		b.removalAll();
		b.shutdown();
		a.info("shutdown cache finish");
	}
}