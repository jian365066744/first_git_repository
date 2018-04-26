/**
 * 
 */
package com.ronglian;

import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/** 
* @author zhangjian 
* @version JDK1.8
* 创建时间：2018年4月23日 下午4:31:28 
* 类说明 :
*/
public class TestMap implements Runnable {

//	HashMap<Integer,Integer> map ;
	ConcurrentHashMap<Integer, Integer> map;
	int i;
//	public TestMap(HashMap<Integer,Integer> map,int i) {
//		this.map = map;
//		this.i = i;
//	}
	
	public TestMap(ConcurrentHashMap<Integer,Integer> map,int i) {
		this.map = map;
		this.i = i;
	}
	public static void main(String[] args) {
//		ExecutorService exService = Executors.newSingleThreadExecutor();
		ExecutorService exService = Executors.newCachedThreadPool();
//		HashMap<Integer,Integer> map = new HashMap<Integer,Integer>();
		ConcurrentHashMap<Integer,Integer> map = new ConcurrentHashMap<Integer,Integer>();
		CountDownLatch cl = new CountDownLatch(10);
		final AtomicInteger at = new AtomicInteger();
		for (int i=0;i<10;i++) {
			do {
				exService.submit(new TestMap(map,at.incrementAndGet()-1));
			} while (i == at.get()-1);
			
		}
		
//		System.out.println(map);
	}
	
	public void add(int i) {
		System.out.println(i);
		map.put(i, i);
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		/** 
		* @author  zhangjian
		* @version JDK1.8
		* 创建时间：2018年4月23日 下午5:16:09 
		* 方法说明 :
		*/
		add(i);
	}
}
